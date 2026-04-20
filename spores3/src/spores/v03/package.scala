package spores

package object v03 {
  export spores.ReadWriters.given

  extension [T](spore: Spore00[Nothing, T]) {
    def copy(): Spore00[Nothing, T] = new Spore00Impl[Nothing, T] with SporeWithoutEnv[Function00[T]] {
      override def className: String = spore.className
      override def body: Function00[T] = spore.asInstanceOf[spores.SporeLambdaBuilder0[_, Function00[T]]].body
      override def apply: T = body.apply
    }
  }

  extension [T](spore: Spore0[Nothing, T]) {
    def copy(): Spore0[Nothing, T] = new Spore0Impl[Nothing, T] with SporeWithoutEnv[Function0[T]] {
      override def className: String = spore.className
      override def body: Function0[T] = spore.asInstanceOf[spores.SporeLambdaBuilder0[_, Function0[T]]].body
      override def apply(): T = body.apply()
    }
  }

  extension [T1, R](spore: Spore1[Nothing, T1, R]) {
    def copy(): Spore1[Nothing, T1, R] = new Spore1Impl[Nothing, T1, R] with SporeWithoutEnv[Function1[T1, R]] {
      override def className: String = spore.className
      override def body: Function1[T1, R] = spore.asInstanceOf[spores.SporeLambdaBuilder0[_, Function1[T1, R]]].body
      override def apply(v1: T1): R = body(v1)
    }
  }

  extension [T1, T2, R](spore: Spore2[Nothing, T1, T2, R]) {
    def copy(): Spore2[Nothing, T1, T2, R] = new Spore2Impl[Nothing, T1, T2, R] with SporeWithoutEnv[Function2[T1, T2, R]] {
      override def className: String = spore.className
      override def body: Function2[T1, T2, R] = spore.asInstanceOf[spores.SporeLambdaBuilder0[_, Function2[T1, T2, R]]].body
      override def apply(v1: T1, v2: T2): R = body(v1, v2)
    }
  }

  extension [T1, T2, T3, R](spore: Spore3[Nothing, T1, T2, T3, R]) {
    def copy(): Spore3[Nothing, T1, T2, T3, R] = new Spore3Impl[Nothing, T1, T2, T3, R] with SporeWithoutEnv[Function3[T1, T2, T3, R]] {
      override def className: String = spore.className
      override def body: Function3[T1, T2, T3, R] = spore.asInstanceOf[spores.SporeLambdaBuilder0[_, Function3[T1, T2, T3, R]]].body
      override def apply(v1: T1, v2: T2, v3: T3): R = body(v1, v2, v3)
    }
  }

  extension [T1, T2, T3, T4, R](spore: Spore4[Nothing, T1, T2, T3, T4, R]) {
    def copy(): Spore4[Nothing, T1, T2, T3, T4, R] = new Spore4Impl[Nothing, T1, T2, T3, T4, R] with SporeWithoutEnv[Function4[T1, T2, T3, T4, R]] {
      override def className: String = spore.className
      override def body: Function4[T1, T2, T3, T4, R] = spore.asInstanceOf[spores.SporeLambdaBuilder0[_, Function4[T1, T2, T3, T4, R]]].body
      override def apply(v1: T1, v2: T2, v3: T3, v4: T4): R = body(v1, v2, v3, v4)
    }
  }

  extension [T1, T2, T3, T4, T5, R](spore: Spore5[Nothing, T1, T2, T3, T4, T5, R]) {
    def copy(): Spore5[Nothing, T1, T2, T3, T4, T5, R] = new Spore5Impl[Nothing, T1, T2, T3, T4, T5, R] with SporeWithoutEnv[Function5[T1, T2, T3, T4, T5, R]] {
      override def className: String = spore.className
      override def body: Function5[T1, T2, T3, T4, T5, R] = spore.asInstanceOf[spores.SporeLambdaBuilder0[_, Function5[T1, T2, T3, T4, T5, R]]].body
      override def apply(v1: T1, v2: T2, v3: T3, v4: T4, v5: T5): R = body(v1, v2, v3, v4, v5)
    }
  }

  extension [T1, T2, T3, T4, T5, T6, R](spore: Spore6[Nothing, T1, T2, T3, T4, T5, T6, R]) {
    def copy(): Spore6[Nothing, T1, T2, T3, T4, T5, T6, R] = new Spore6Impl[Nothing, T1, T2, T3, T4, T5, T6, R] with SporeWithoutEnv[Function6[T1, T2, T3, T4, T5, T6, R]] {
      override def className: String = spore.className
      override def body: Function6[T1, T2, T3, T4, T5, T6, R] = spore.asInstanceOf[spores.SporeLambdaBuilder0[_, Function6[T1, T2, T3, T4, T5, T6, R]]].body
      override def apply(v1: T1, v2: T2, v3: T3, v4: T4, v5: T5, v6: T6): R = body(v1, v2, v3, v4, v5, v6)
    }
  }

  extension [T1, T2, T3, T4, T5, T6, T7, R](spore: Spore7[Nothing, T1, T2, T3, T4, T5, T6, T7, R]) {
    def copy(): Spore7[Nothing, T1, T2, T3, T4, T5, T6, T7, R] = new Spore7Impl[Nothing, T1, T2, T3, T4, T5, T6, T7, R] with SporeWithoutEnv[Function7[T1, T2, T3, T4, T5, T6, T7, R]] {
      override def className: String = spore.className
      override def body: Function7[T1, T2, T3, T4, T5, T6, T7, R] = spore.asInstanceOf[spores.SporeLambdaBuilder0[_, Function7[T1, T2, T3, T4, T5, T6, T7, R]]].body
      override def apply(v1: T1, v2: T2, v3: T3, v4: T4, v5: T5, v6: T6, v7: T7): R = body(v1, v2, v3, v4, v5, v6, v7)
    }
  }

  extension [E, T](spore: Spore00[E, T]) {
    def copy(envir: E): Spore00[E, T] = new Spore00Impl[E, T] with SporeWithEnv[E, Function00[T]] {
      _env = envir
      override def className: String = spore.className
      override def body: E => Function00[T] = spore.asInstanceOf[spores.SporeLambdaBuilder0[_, E => Function00[T]]].body
      override def apply: T = body(envir).apply
    }
  }

  extension [E, T](spore: Spore0[E, T]) {
    def copy(envir: E): Spore0[E, T] = new Spore0Impl[E, T] with SporeWithEnv[E, Function0[T]] {
      _env = envir
      override def className: String = spore.className
      override def body: E => Function0[T] = spore.asInstanceOf[spores.SporeLambdaBuilder0[_, E => Function0[T]]].body
      override def apply(): T = body(envir).apply()
    }
  }

  extension [E, T1, R](spore: Spore1[E, T1, R]) {
    def copy(envir: E): Spore1[E, T1, R] = new Spore1Impl[E, T1, R] with SporeWithEnv[E, Function1[T1, R]] {
      _env = envir
      override def className: String = spore.className
      override def body: E => Function1[T1, R] = spore.asInstanceOf[spores.SporeLambdaBuilder0[_, E => Function1[T1, R]]].body
      override def apply(v1: T1): R = body(envir)(v1)
    }
  }

  extension [E, T1, T2, R](spore: Spore2[E, T1, T2, R]) {
    def copy(envir: E): Spore2[E, T1, T2, R] = new Spore2Impl[E, T1, T2, R] with SporeWithEnv[E, Function2[T1, T2, R]] {
      _env = envir
      override def className: String = spore.className
      override def body: E => Function2[T1, T2, R] = spore.asInstanceOf[spores.SporeLambdaBuilder0[_, E => Function2[T1, T2, R]]].body
      override def apply(v1: T1, v2: T2): R = body(envir)(v1, v2)
    }
  }

  extension [E, T1, T2, T3, R](spore: Spore3[E, T1, T2, T3, R]) {
    def copy(envir: E): Spore3[E, T1, T2, T3, R] = new Spore3Impl[E, T1, T2, T3, R] with SporeWithEnv[E, Function3[T1, T2, T3, R]] {
      _env = envir
      override def className: String = spore.className
      override def body: E => Function3[T1, T2, T3, R] = spore.asInstanceOf[spores.SporeLambdaBuilder0[_, E => Function3[T1, T2, T3, R]]].body
      override def apply(v1: T1, v2: T2, v3: T3): R = body(envir)(v1, v2, v3)
    }
  }

  extension [E, T1, T2, T3, T4, R](spore: Spore4[E, T1, T2, T3, T4, R]) {
    def copy(envir: E): Spore4[E, T1, T2, T3, T4, R] = new Spore4Impl[E, T1, T2, T3, T4, R] with SporeWithEnv[E, Function4[T1, T2, T3, T4, R]] {
      _env = envir
      override def className: String = spore.className
      override def body: E => Function4[T1, T2, T3, T4, R] = spore.asInstanceOf[spores.SporeLambdaBuilder0[_, E => Function4[T1, T2, T3, T4, R]]].body
      override def apply(v1: T1, v2: T2, v3: T3, v4: T4): R = body(envir)(v1, v2, v3, v4)
    }
  }

  extension [E, T1, T2, T3, T4, T5, R](spore: Spore5[E, T1, T2, T3, T4, T5, R]) {
    def copy(envir: E): Spore5[E, T1, T2, T3, T4, T5, R] = new Spore5Impl[E, T1, T2, T3, T4, T5, R] with SporeWithEnv[E, Function5[T1, T2, T3, T4, T5, R]] {
      _env = envir
      override def className: String = spore.className
      override def body: E => Function5[T1, T2, T3, T4, T5, R] = spore.asInstanceOf[spores.SporeLambdaBuilder0[_, E => Function5[T1, T2, T3, T4, T5, R]]].body
      override def apply(v1: T1, v2: T2, v3: T3, v4: T4, v5: T5): R = body(envir)(v1, v2, v3, v4, v5)
    }
  }

  extension [E, T1, T2, T3, T4, T5, T6, R](spore: Spore6[E, T1, T2, T3, T4, T5, T6, R]) {
    def copy(envir: E): Spore6[E, T1, T2, T3, T4, T5, T6, R] = new Spore6Impl[E, T1, T2, T3, T4, T5, T6, R] with SporeWithEnv[E, Function6[T1, T2, T3, T4, T5, T6, R]] {
      _env = envir
      override def className: String = spore.className
      override def body: E => Function6[T1, T2, T3, T4, T5, T6, R] = spore.asInstanceOf[spores.SporeLambdaBuilder0[_, E => Function6[T1, T2, T3, T4, T5, T6, R]]].body
      override def apply(v1: T1, v2: T2, v3: T3, v4: T4, v5: T5, v6: T6): R = body(envir)(v1, v2, v3, v4, v5, v6)
    }
  }

  extension [E, T1, T2, T3, T4, T5, T6, T7, R](spore: Spore7[E, T1, T2, T3, T4, T5, T6, T7, R]) {
    def copy(envir: E): Spore7[E, T1, T2, T3, T4, T5, T6, T7, R] = new Spore7Impl[E, T1, T2, T3, T4, T5, T6, T7, R] with SporeWithEnv[E, Function7[T1, T2, T3, T4, T5, T6, T7, R]] {
      _env = envir
      override def className: String = spore.className
      override def body: E => Function7[T1, T2, T3, T4, T5, T6, T7, R] = spore.asInstanceOf[spores.SporeLambdaBuilder0[_, E => Function7[T1, T2, T3, T4, T5, T6, T7, R]]].body
      override def apply(v1: T1, v2: T2, v3: T3, v4: T4, v5: T5, v6: T6, v7: T7): R = body(envir)(v1, v2, v3, v4, v5, v6, v7)
    }
  }
}
