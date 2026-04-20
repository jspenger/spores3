package spores.v03

import scala.quoted.*
import spores.Macros


private[v03] object SporeMacro {

  transparent inline def sporeApply[T](inline captures: Any*)(inline body: T): Any = {
    ${ sporeApplyMacro[T]('captures, 'body) }
  }


  private def sporeApplyMacro[T](capturesExpr: Expr[Seq[Any]], bodyExpr: Expr[T])(using Type[T], Quotes): Expr[Any] = {
    import quotes.reflect.*

    val (mode, captureTerms) = Macros.capturesWellFormednessCheck(capturesExpr)

    mode match {
      case 1 => sporeApplyMacro13(bodyExpr, None)
      case 2 => sporeApplyMacro2(capturesExpr, bodyExpr)
      case 3 => sporeApplyMacro13(bodyExpr, Some(captureTerms))
                // Continue with mode `*` for error case `-1` to display further errors
      case -1=> sporeApplyMacro13(bodyExpr, None)
      case _ => throw Exception(s"Invalid mode: $mode.")
    }
  }


  private def sporeApplyMacro2[T](using Quotes)(capturesExpr: Expr[Seq[Any]], bodyExpr: Expr[T])(using Type[T]): Expr[Any] = {
    import quotes.reflect.*

    val bodyCaptureTerms      = Macros.findCapturedIds(bodyExpr.asTerm)
    val bodyCaptureTermsDedup = Macros.sortCaptures(Macros.deduplicateCaptures(bodyCaptureTerms))

    Macros.checkCaptures(List(), bodyCaptureTerms)

    val spore = constructSporeWithoutEnv[T](bodyExpr)
    spore
  }


  private def sporeApplyMacro13[T](using Quotes)(bodyExpr: Expr[T], captureTerms: Option[List[quotes.reflect.Tree]])(using Type[T]): Expr[Any] = {
    import quotes.reflect.*

    // If captureTerms is None then mode is 1 (`*`, capture all).
    // If captureTerms is Some then mode is 3 (explicit capture list).

    val bodyCaptureTerms      = Macros.findCapturedIds(bodyExpr.asTerm)
    val bodyCaptureTermsDedup = Macros.sortCaptures(Macros.deduplicateCaptures(bodyCaptureTerms))
    val arity                 = bodyCaptureTermsDedup.size

    if (captureTerms.isDefined) {
      Macros.checkCaptures(captureTerms.get, bodyCaptureTerms)
    }

    arity match {
      case 0 => {
        val spore = constructSporeWithoutEnv[T](bodyExpr)
        spore
      }

      case 1 => {
        val liftedBody     = Macros.liftBody(Symbol.spliceOwner, bodyCaptureTermsDedup, bodyExpr.asTerm)
        val liftedBodyExpr = liftedBody.asExpr

        val env = bodyCaptureTermsDedup.head

        spores.Macros.typeOf(env).asType match {
          case '[cType] =>
            constructSporeWithEnv[cType, T](liftedBodyExpr.asExprOf[cType => T], env.asExprOf[cType], arity)
        }
      }

      case _ => {
        val captureTermsDedup = captureTerms.map(Macros.deduplicateCaptures).getOrElse(bodyCaptureTermsDedup)

        val liftedBody     = Macros.liftBody(Symbol.spliceOwner, captureTermsDedup, bodyExpr.asTerm)
        val liftedBodyExpr = liftedBody.asExpr

        val captureTpeReprs = captureTermsDedup.map(spores.Macros.typeOf)

        val captureTermsTyped = captureTermsDedup.map {
          c => {
            Typed(c.asExpr.asTerm, TypeTree.of(using spores.Macros.typeOf(c).asType))
          }.asExpr
        }

        tupleTypeOf(captureTpeReprs).asType match {
          case '[tType] =>
            val envs = Expr.ofTupleFromSeq(captureTermsTyped).asExprOf[tType]
            val uncurriedBodyExpr = uncurriedFunction[tType, T](liftedBodyExpr, arity, captureTpeReprs)
            constructSporeWithEnv[tType, T](uncurriedBodyExpr, envs, arity)
        }
      }
    }
  }


  private def tupleTypeOf(using Quotes)(types: List[quotes.reflect.TypeRepr]): quotes.reflect.TypeRepr = {
    import quotes.reflect.*

    val arity = types.length

    if (arity <= 22) {
      // Use TupleN for arity <= 22
      Symbol.classSymbol(s"scala.Tuple$arity").typeRef.appliedTo(types)
    } else {
      // Use Tuple construction for arity > 22
      val consTpe = Symbol.classSymbol("scala.*:").typeRef

      var tmp = TypeRepr.of[scala.EmptyTuple]
      for (h <- types.reverse) {
        tmp   = consTpe.appliedTo(List(h, tmp))
      }
      tmp
    }
  }


  private def uncurriedFunction[E, T](using Quotes)(fun: Expr[Any], arity: Int, captureTpeReprs: List[quotes.reflect.TypeRepr])(using Type[E], Type[T]): Expr[E => T] = {
    import quotes.reflect.*

    def applyCurriedFunction(fun: Expr[Any], envs: Expr[Any], arity: Int): Expr[Any] = {

      if (arity == 1) {
        Select.unique(fun.asTerm, "apply").appliedTo(envs.asTerm).asExpr

      } else {

        val envsTerm = envs.asTerm

        var tmp = fun.asTerm
        for (i <- 1 to arity) {

          val nxt = if (arity <= 22) {
            // Use tuple accessor for arity <= 22
            Select.unique(envsTerm, s"_$i")
          } else {
            // Use productElement for arity > 22
            val el = Select.unique(envsTerm, "productElement").appliedTo(Literal(IntConstant(i - 1)))
            val elTpe = captureTpeReprs(i - 1).asType
            // Cast to elTpe as productElement returns Any
            TypeApply(Select.unique(el, "asInstanceOf"), List(TypeTree.of(using elTpe)))
          }

          tmp = Select.unique(tmp, "apply").appliedTo(nxt)
        }
        tmp.asExpr
      }
    }


    '{
      (envs: E) => {
        ${ 
          applyCurriedFunction(fun, 'envs, arity).asExprOf[T]
        }
      }
    }
  }

  private def constructSporeWithoutEnv[T](bodyExpr: Expr[T])(using Type[T], Quotes): Expr[Any] = {
    Type.of[T] match {
      case '[Function0[r]] => {
        '{
          class anon extends SporeWithoutEnv[T] with Spore0Impl[Nothing, r] {
            override def body: Function0[r] = ${ bodyExpr.asExprOf[Function0[r]] }
            override def apply: r = body()
          }
          new anon(): Spore0[Nothing, r]
        }
      }
      case '[Function1[t1, r]] => {
        '{
          class anon extends SporeWithoutEnv[T] with Spore1Impl[Nothing, t1, r] {
            override def body: Function1[t1, r] = ${ bodyExpr.asExprOf[Function1[t1, r]] }
            override def apply(v1: t1): r = body(v1)
          }
          new anon(): Spore1[Nothing, t1, r]
        }
      }
      case '[Function2[t1, t2, r]] => {
        '{
          class anon extends SporeWithoutEnv[T] with Spore2Impl[Nothing, t1, t2, r] {
            override def body: Function2[t1, t2, r] = ${ bodyExpr.asExprOf[Function2[t1, t2, r]] }
            override def apply(v1: t1, v2: t2): r = body(v1, v2)
          }
          new anon(): Spore2[Nothing, t1, t2, r]
        }
      }
      case '[Function3[t1, t2, t3, r]] => {
        '{
          class anon extends SporeWithoutEnv[Function3[t1, t2, t3, r]] with Spore3Impl[Nothing, t1, t2, t3, r] {
            override def body: Function3[t1, t2, t3, r] = ${ bodyExpr.asExprOf[Function3[t1, t2, t3, r]] }
            override def apply(v1: t1, v2: t2, v3: t3): r = body(v1, v2, v3)
          }
          new anon(): Spore3[Nothing, t1, t2, t3, r]
        }
      }
      case '[Function4[t1, t2, t3, t4, r]] => {
        '{
          class anon extends SporeWithoutEnv[Function4[t1, t2, t3, t4, r]] with Spore4Impl[Nothing, t1, t2, t3, t4, r] {
            override def body: Function4[t1, t2, t3, t4, r] = ${ bodyExpr.asExprOf[Function4[t1, t2, t3, t4, r]] }
            override def apply(v1: t1, v2: t2, v3: t3, v4: t4): r = body(v1, v2, v3, v4)
          }
          new anon(): Spore4[Nothing, t1, t2, t3, t4, r]
        }
      }
      case '[Function5[t1, t2, t3, t4, t5, r]] => {
        '{
          class anon extends SporeWithoutEnv[Function5[t1, t2, t3, t4, t5, r]] with Spore5Impl[Nothing, t1, t2, t3, t4, t5, r] {
            override def body: Function5[t1, t2, t3, t4, t5, r] = ${ bodyExpr.asExprOf[Function5[t1, t2, t3, t4, t5, r]] }
            override def apply(v1: t1, v2: t2, v3: t3, v4: t4, v5: t5): r = body(v1, v2, v3, v4, v5)
          }
          new anon(): Spore5[Nothing, t1, t2, t3, t4, t5, r]
        }
      }
      case '[Function6[t1, t2, t3, t4, t5, t6, r]] => {
        '{
          class anon extends SporeWithoutEnv[Function6[t1, t2, t3, t4, t5, t6, r]] with Spore6Impl[Nothing, t1, t2, t3, t4, t5, t6, r] {
            override def body: Function6[t1, t2, t3, t4, t5, t6, r] = ${ bodyExpr.asExprOf[Function6[t1, t2, t3, t4, t5, t6, r]] }
            override def apply(v1: t1, v2: t2, v3: t3, v4: t4, v5: t5, v6: t6): r = body(v1, v2, v3, v4, v5, v6)
          }
          new anon(): Spore6[Nothing, t1, t2, t3, t4, t5, t6, r]
        }
      }
      case '[Function7[t1, t2, t3, t4, t5, t6, t7, r]] => {
        '{
          class anon extends SporeWithoutEnv[Function7[t1, t2, t3, t4, t5, t6, t7, r]] with Spore7Impl[Nothing, t1, t2, t3, t4, t5, t6, t7, r] {
            override def body: Function7[t1, t2, t3, t4, t5, t6, t7, r] = ${ bodyExpr.asExprOf[Function7[t1, t2, t3, t4, t5, t6, t7, r]] }
            override def apply(v1: t1, v2: t2, v3: t3, v4: t4, v5: t5, v6: t6, v7: t7): r = body(v1, v2, v3, v4, v5, v6, v7)
          }
          new anon(): Spore7[Nothing, t1, t2, t3, t4, t5, t6, t7, r]
        }
      }
      case _ => {
        '{
          class anon extends SporeWithoutEnv[Function00[T]] with Spore00Impl[Nothing, T] {
            override def body: Function00[T] = new Function00[T] { def apply: T = ${ bodyExpr } }
            override def apply: T = body.apply
          }
          new anon(): Spore00[Nothing, T]
        }
      }
    }
  }

  private def constructSporeWithEnv[E, T](liftedBodyExpr: Expr[E => T], envExpr: Expr[E], arity: Int)(using Type[E], Type[T], Quotes): Expr[Any] = {
    Type.of[T] match {
      case '[Function0[r]] => {
        '{
          class anon extends SporeWithEnv[E, Function0[r]] with Spore0Impl[E, r] {
            override def body: E => Function0[r] = ${ liftedBodyExpr.asExprOf[E => Function0[r]] }
            override def apply(): r = body(env)()
          }
          val spore = new anon()
          spore._env = $envExpr
          spore: Spore0[E, r]
        }
      }
      case '[Function1[t1, r]] => {
        '{
          class anon extends SporeWithEnv[E, Function1[t1, r]] with Spore1Impl[E, t1, r] {
            override def body: E => Function1[t1, r] = ${ liftedBodyExpr.asExprOf[E => Function1[t1, r]] }
            override def apply(v1: t1): r = body(env)(v1)
          }
          val spore = new anon()
          spore._env = $envExpr
          spore: Spore1[E, t1, r]
        }
      }
      case '[Function2[t1, t2, r]] => {
        '{
          class anon extends SporeWithEnv[E, Function2[t1, t2, r]] with Spore2Impl[E, t1, t2, r] {
            override def body: E => Function2[t1, t2, r] = ${ liftedBodyExpr.asExprOf[E => Function2[t1, t2, r]] }
            override def apply(v1: t1, v2: t2): r = body(env)(v1, v2)
          }
          val spore = new anon()
          spore._env = $envExpr
          spore: Spore2[E, t1, t2, r]
        }
      }
      case '[Function3[t1, t2, t3, r]] => {
        '{
          class anon extends SporeWithEnv[E, Function3[t1, t2, t3, r]] with Spore3Impl[E, t1, t2, t3, r] {
            override def body: E => Function3[t1, t2, t3, r] = ${ liftedBodyExpr.asExprOf[E => Function3[t1, t2, t3, r]] }
            override def apply(v1: t1, v2: t2, v3: t3): r = body(env)(v1, v2, v3)
          }
          val spore = new anon()
          spore._env = $envExpr
          spore: Spore3[E, t1, t2, t3, r]
        }
      }
      case '[Function4[t1, t2, t3, t4, r]] => {
        '{
          class anon extends SporeWithEnv[E, Function4[t1, t2, t3, t4, r]] with Spore4Impl[E, t1, t2, t3, t4, r] {
            override def body: E => Function4[t1, t2, t3, t4, r] = ${ liftedBodyExpr.asExprOf[E => Function4[t1, t2, t3, t4, r]] }
            override def apply(v1: t1, v2: t2, v3: t3, v4: t4): r = body(env)(v1, v2, v3, v4)
          }
          val spore = new anon()
          spore._env = $envExpr
          spore: Spore4[E, t1, t2, t3, t4, r]
        }
      }
      case '[Function5[t1, t2, t3, t4, t5, r]] => {
        '{
          class anon extends SporeWithEnv[E, Function5[t1, t2, t3, t4, t5, r]] with Spore5Impl[E, t1, t2, t3, t4, t5, r] {
            override def body: E => Function5[t1, t2, t3, t4, t5, r] = ${ liftedBodyExpr.asExprOf[E => Function5[t1, t2, t3, t4, t5, r]] }
            override def apply(v1: t1, v2: t2, v3: t3, v4: t4, v5: t5): r = body(env)(v1, v2, v3, v4, v5)
          }
          val spore = new anon()
          spore._env = $envExpr
          spore: Spore5[E, t1, t2, t3, t4, t5, r]
        }
      }
      case '[Function6[t1, t2, t3, t4, t5, t6, r]] => {
        '{
          class anon extends SporeWithEnv[E, Function6[t1, t2, t3, t4, t5, t6, r]] with Spore6Impl[E, t1, t2, t3, t4, t5, t6, r] {
            override def body: E => Function6[t1, t2, t3, t4, t5, t6, r] = ${ liftedBodyExpr.asExprOf[E => Function6[t1, t2, t3, t4, t5, t6, r]] }
            override def apply(v1: t1, v2: t2, v3: t3, v4: t4, v5: t5, v6: t6): r = body(env)(v1, v2, v3, v4, v5, v6)
          }
          val spore = new anon()
          spore._env = $envExpr
          spore: Spore6[E, t1, t2, t3, t4, t5, t6, r]
        }
      }
      case '[Function7[t1, t2, t3, t4, t5, t6, t7, r]] => {
        '{
          class anon extends SporeWithEnv[E, Function7[t1, t2, t3, t4, t5, t6, t7, r]] with Spore7Impl[E, t1, t2, t3, t4, t5, t6, t7, r] {
            override def body: E => Function7[t1, t2, t3, t4, t5, t6, t7, r] = ${ liftedBodyExpr.asExprOf[E => Function7[t1, t2, t3, t4, t5, t6, t7, r]] }
            override def apply(v1: t1, v2: t2, v3: t3, v4: t4, v5: t5, v6: t6, v7: t7): r = body(env)(v1, v2, v3, v4, v5, v6, v7)
          }
          val spore = new anon()
          spore._env = $envExpr
          spore: Spore7[E, t1, t2, t3, t4, t5, t6, t7, r]
        }
      }
      case _ => {
        '{
          class anon extends SporeWithEnv[E, Function00[T]] with Spore00Impl[E, T] {
            override def body: E => Function00[T] = (e: E) => new Function00[T] {
              def apply: T = ${ liftedBodyExpr.asExprOf[E => T] }(e)
            }
            override def apply: T = body(env).apply
          }
          val spore = new anon()
          spore._env = $envExpr
          spore: Spore00[E, T]
        }
      }
    }
  }
}
