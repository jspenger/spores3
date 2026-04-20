package spores.v03


private[spores] trait SporeObjectCompanion {
  import Spore.narrow

  def fromClassName[T](className: String): Spore[Nothing, T] = {
    spores.Reflection.loadClassInstance[SporeWithoutEnv[T]](className)
  }

  def fromClassName[E, T](className: String, env: E): Spore[E, T] = {
    val spore = spores.Reflection.loadClassInstance[SporeWithEnv[E, T]](className)
    spore._env = env
    spore
  }

  def fromClassName00[R](className: String): Spore00[Nothing, R] = narrow(fromClassName[Function00[R]](className))
  def fromClassName0[R](className: String): Spore0[Nothing, R] = narrow(fromClassName[Function0[R]](className))
  def fromClassName1[T1, R](className: String): Spore1[Nothing, T1, R] = narrow(fromClassName[Function1[T1, R]](className))
  def fromClassName2[T1, T2, R](className: String): Spore2[Nothing, T1, T2, R] = narrow(fromClassName[Function2[T1, T2, R]](className))
  def fromClassName3[T1, T2, T3, R](className: String): Spore3[Nothing, T1, T2, T3, R] = narrow(fromClassName[Function3[T1, T2, T3, R]](className))
  def fromClassName4[T1, T2, T3, T4, R](className: String): Spore4[Nothing, T1, T2, T3, T4, R] = narrow(fromClassName[Function4[T1, T2, T3, T4, R]](className))
  def fromClassName5[T1, T2, T3, T4, T5, R](className: String): Spore5[Nothing, T1, T2, T3, T4, T5, R] = narrow(fromClassName[Function5[T1, T2, T3, T4, T5, R]](className))
  def fromClassName6[T1, T2, T3, T4, T5, T6, R](className: String): Spore6[Nothing, T1, T2, T3, T4, T5, T6, R] = narrow(fromClassName[Function6[T1, T2, T3, T4, T5, T6, R]](className))
  def fromClassName7[T1, T2, T3, T4, T5, T6, T7, R](className: String): Spore7[Nothing, T1, T2, T3, T4, T5, T6, T7, R] = narrow(fromClassName[Function7[T1, T2, T3, T4, T5, T6, T7, R]](className))
  

  def fromClassName00[E, R](className: String, env: E): Spore00[E, R] = narrow(fromClassName[E, Function00[R]](className, env))
  def fromClassName0[E, R](className: String, env: E): Spore0[E, R] = narrow(fromClassName[E, Function0[R]](className, env))
  def fromClassName1[E, T1, R](className: String, env: E): Spore1[E, T1, R] = narrow(fromClassName[E, Function1[T1, R]](className, env))
  def fromClassName2[E, T1, T2, R](className: String, env: E): Spore2[E, T1, T2, R] = narrow(fromClassName[E, Function2[T1, T2, R]](className, env))
  def fromClassName3[E, T1, T2, T3, R](className: String, env: E): Spore3[E, T1, T2, T3, R] = narrow(fromClassName[E, Function3[T1, T2, T3, R]](className, env))
  def fromClassName4[E, T1, T2, T3, T4, R](className: String, env: E): Spore4[E, T1, T2, T3, T4, R] = narrow(fromClassName[E, Function4[T1, T2, T3, T4, R]](className, env))
  def fromClassName5[E, T1, T2, T3, T4, T5, R](className: String, env: E): Spore5[E, T1, T2, T3, T4, T5, R] = narrow(fromClassName[E, Function5[T1, T2, T3, T4, T5, R]](className, env))
  def fromClassName6[E, T1, T2, T3, T4, T5, T6, R](className: String, env: E): Spore6[E, T1, T2, T3, T4, T5, T6, R] = narrow(fromClassName[E, Function6[T1, T2, T3, T4, T5, T6, R]](className, env))
  def fromClassName7[E, T1, T2, T3, T4, T5, T6, T7, R](className: String, env: E): Spore7[E, T1, T2, T3, T4, T5, T6, T7, R] = narrow(fromClassName[E, Function7[T1, T2, T3, T4, T5, T6, T7, R]](className, env))
}
