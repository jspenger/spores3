package spores.v03

import spores.Spore0.`*`
import spores.SporeLambdaBuilder0


type Pickle[+T]        = spores.Spore[T]
type Pickle0[F[_], +T] = spores.Spore0[F, T]
type Pickler[T]        = spores.Spore[upickle.default.ReadWriter[T]]
type Pickler0[F[_], T] = spores.Spore0[F, F[T]]


sealed trait Spore[E, +T] {
  def env: E
  def hasEnv: Boolean
  def className: String = this.getClass().getName()
}


trait Function00[+R] {
  def apply: R
}


sealed trait Spore00[E, +R] extends Spore[E, Function00[R]] with Function00[R]
private[spores] trait Spore00Impl[E, +R] extends Spore00[E, R]

sealed trait Spore0[E, +R] extends Spore[E, Function0[R]] with Function0[R]
private[spores] trait Spore0Impl[E, +R] extends Spore0[E, R]

sealed trait Spore1[E, -T1, +R] extends Spore[E, Function1[T1, R]] with Function1[T1, R]
private[spores] trait Spore1Impl[E, -T1, +R] extends Spore1[E, T1, R]

sealed trait Spore2[E, -T1, -T2, +R] extends Spore[E, Function2[T1, T2, R]] with Function2[T1, T2, R]
private[spores] trait Spore2Impl[E, -T1, -T2, +R] extends Spore2[E, T1, T2, R]

sealed trait Spore3[E, -T1, -T2, -T3, +R] extends Spore[E, Function3[T1, T2, T3, R]] with Function3[T1, T2, T3, R]
private[spores] trait Spore3Impl[E, -T1, -T2, -T3, +R] extends Spore3[E, T1, T2, T3, R]

sealed trait Spore4[E, -T1, -T2, -T3, -T4, +R] extends Spore[E, Function4[T1, T2, T3, T4, R]] with Function4[T1, T2, T3, T4, R]
private[spores] trait Spore4Impl[E, -T1, -T2, -T3, -T4, +R] extends Spore4[E, T1, T2, T3, T4, R]

sealed trait Spore5[E, -T1, -T2, -T3, -T4, -T5, +R] extends Spore[E, Function5[T1, T2, T3, T4, T5, R]] with Function5[T1, T2, T3, T4, T5, R]
private[spores] trait Spore5Impl[E, -T1, -T2, -T3, -T4, -T5, +R] extends Spore5[E, T1, T2, T3, T4, T5, R]

sealed trait Spore6[E, -T1, -T2, -T3, -T4, -T5, -T6, +R] extends Spore[E, Function6[T1, T2, T3, T4, T5, T6, R]] with Function6[T1, T2, T3, T4, T5, T6, R]
private[spores] trait Spore6Impl[E, -T1, -T2, -T3, -T4, -T5, -T6, +R] extends Spore6[E, T1, T2, T3, T4, T5, T6, R]

sealed trait Spore7[E, -T1, -T2, -T3, -T4, -T5, -T6, -T7, +R] extends Spore[E, Function7[T1, T2, T3, T4, T5, T6, T7, R]] with Function7[T1, T2, T3, T4, T5, T6, T7, R]
private[spores] trait Spore7Impl[E, -T1, -T2, -T3, -T4, -T5, -T6, -T7, +R] extends Spore7[E, T1, T2, T3, T4, T5, T6, T7, R]


private[spores] trait SporeWithEnv[E, +T] extends Spore[E, T] with SporeLambdaBuilder0[_, E => T] {
  var _env: E = null.asInstanceOf[E]
  final def env: E = _env
  final def hasEnv: Boolean = true
}

private[spores] trait SporeWithoutEnv[T] extends Spore[Nothing, T] with SporeLambdaBuilder0[_, T] {
  final def env: Nothing = throw new Exception("Spore has no environment.")
  final def hasEnv: Boolean = false
}


object Spore extends SporeObjectCompanion {
  export spores.Spore0.`*`

  transparent inline def apply[T](inline captures: Any*)(inline body: T): Any = {
    SporeMacro.sporeApply[T](captures*)(body)
  }

  def narrow[E, R](spore: Spore[E, Function00[R]]): Spore00[E, R] = spore.asInstanceOf[Spore00[E, R]]
  def narrow[E, R](spore: Spore[E, Function0[R]]): Spore0[E, R] = spore.asInstanceOf[Spore0[E, R]]
  def narrow[E, T1, R](spore: Spore[E, Function1[T1, R]]): Spore1[E, T1, R] = spore.asInstanceOf[Spore1[E, T1, R]]
  def narrow[E, T1, T2, R](spore: Spore[E, Function2[T1, T2, R]]): Spore2[E, T1, T2, R] = spore.asInstanceOf[Spore2[E, T1, T2, R]]
  def narrow[E, T1, T2, T3, R](spore: Spore[E, Function3[T1, T2, T3, R]]): Spore3[E, T1, T2, T3, R] = spore.asInstanceOf[Spore3[E, T1, T2, T3, R]]
  def narrow[E, T1, T2, T3, T4, R](spore: Spore[E, Function4[T1, T2, T3, T4, R]]): Spore4[E, T1, T2, T3, T4, R] = spore.asInstanceOf[Spore4[E, T1, T2, T3, T4, R]]
  def narrow[E, T1, T2, T3, T4, T5, R](spore: Spore[E, Function5[T1, T2, T3, T4, T5, R]]): Spore5[E, T1, T2, T3, T4, T5, R] = spore.asInstanceOf[Spore5[E, T1, T2, T3, T4, T5, R]]
  def narrow[E, T1, T2, T3, T4, T5, T6, R](spore: Spore[E, Function6[T1, T2, T3, T4, T5, T6, R]]): Spore6[E, T1, T2, T3, T4, T5, T6, R] = spore.asInstanceOf[Spore6[E, T1, T2, T3, T4, T5, T6, R]]
  def narrow[E, T1, T2, T3, T4, T5, T6, T7, R](spore: Spore[E, Function7[T1, T2, T3, T4, T5, T6, T7, R]]): Spore7[E, T1, T2, T3, T4, T5, T6, T7, R] = spore.asInstanceOf[Spore7[E, T1, T2, T3, T4, T5, T6, T7, R]]
}
