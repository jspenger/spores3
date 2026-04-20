package spores.v03


export spores.Duplicable
export spores.Duplicable.given


given duplicableSporeWithoutEnv00[T]: Duplicable[Spore00[Nothing, T]] with
  def duplicate(value: Spore00[Nothing, T]): Spore00[Nothing, T] = value.copy()

given duplicableSporeWithoutEnv0[T]: Duplicable[Spore0[Nothing, T]] with
  def duplicate(value: Spore0[Nothing, T]): Spore0[Nothing, T] = value.copy()

given duplicableSporeWithoutEnv1[T1, R]: Duplicable[Spore1[Nothing, T1, R]] with
  def duplicate(value: Spore1[Nothing, T1, R]): Spore1[Nothing, T1, R] = value.copy()

given duplicableSporeWithoutEnv2[T1, T2, R]: Duplicable[Spore2[Nothing, T1, T2, R]] with
  def duplicate(value: Spore2[Nothing, T1, T2, R]): Spore2[Nothing, T1, T2, R] = value.copy()

given duplicableSporeWithoutEnv3[T1, T2, T3, R]: Duplicable[Spore3[Nothing, T1, T2, T3, R]] with
  def duplicate(value: Spore3[Nothing, T1, T2, T3, R]): Spore3[Nothing, T1, T2, T3, R] = value.copy()

given duplicableSporeWithoutEnv4[T1, T2, T3, T4, R]: Duplicable[Spore4[Nothing, T1, T2, T3, T4, R]] with
  def duplicate(value: Spore4[Nothing, T1, T2, T3, T4, R]): Spore4[Nothing, T1, T2, T3, T4, R] = value.copy()

given duplicableSporeWithoutEnv5[T1, T2, T3, T4, T5, R]: Duplicable[Spore5[Nothing, T1, T2, T3, T4, T5, R]] with
  def duplicate(value: Spore5[Nothing, T1, T2, T3, T4, T5, R]): Spore5[Nothing, T1, T2, T3, T4, T5, R] = value.copy()

given duplicableSporeWithoutEnv6[T1, T2, T3, T4, T5, T6, R]: Duplicable[Spore6[Nothing, T1, T2, T3, T4, T5, T6, R]] with
  def duplicate(value: Spore6[Nothing, T1, T2, T3, T4, T5, T6, R]): Spore6[Nothing, T1, T2, T3, T4, T5, T6, R] = value.copy()

given duplicableSporeWithoutEnv7[T1, T2, T3, T4, T5, T6, T7, R]: Duplicable[Spore7[Nothing, T1, T2, T3, T4, T5, T6, T7, R]] with
  def duplicate(value: Spore7[Nothing, T1, T2, T3, T4, T5, T6, T7, R]): Spore7[Nothing, T1, T2, T3, T4, T5, T6, T7, R] = value.copy()

given duplicableSporeWithEnv00[E, T](using Duplicable[E]): Duplicable[Spore00[E, T]] with
  def duplicate(value: Spore00[E, T]): Spore00[E, T] = value.copy(Duplicable.duplicate(value.env))

given duplicableSporeWithEnv0[E, T](using Duplicable[E]): Duplicable[Spore0[E, T]] with
  def duplicate(value: Spore0[E, T]): Spore0[E, T] = value.copy(Duplicable.duplicate(value.env))

given duplicableSporeWithEnv1[E, T1, R](using Duplicable[E]): Duplicable[Spore1[E, T1, R]] with
  def duplicate(value: Spore1[E, T1, R]): Spore1[E, T1, R] = value.copy(Duplicable.duplicate(value.env))

given duplicableSporeWithEnv2[E, T1, T2, R](using Duplicable[E]): Duplicable[Spore2[E, T1, T2, R]] with
  def duplicate(value: Spore2[E, T1, T2, R]): Spore2[E, T1, T2, R] = value.copy(Duplicable.duplicate(value.env))

given duplicableSporeWithEnv3[E, T1, T2, T3, R](using Duplicable[E]): Duplicable[Spore3[E, T1, T2, T3, R]] with
  def duplicate(value: Spore3[E, T1, T2, T3, R]): Spore3[E, T1, T2, T3, R] = value.copy(Duplicable.duplicate(value.env))

given duplicableSporeWithEnv4[E, T1, T2, T3, T4, R](using Duplicable[E]): Duplicable[Spore4[E, T1, T2, T3, T4, R]] with
  def duplicate(value: Spore4[E, T1, T2, T3, T4, R]): Spore4[E, T1, T2, T3, T4, R] = value.copy(Duplicable.duplicate(value.env))

given duplicableSporeWithEnv5[E, T1, T2, T3, T4, T5, R](using Duplicable[E]): Duplicable[Spore5[E, T1, T2, T3, T4, T5, R]] with
  def duplicate(value: Spore5[E, T1, T2, T3, T4, T5, R]): Spore5[E, T1, T2, T3, T4, T5, R] = value.copy(Duplicable.duplicate(value.env))

given duplicableSporeWithEnv6[E, T1, T2, T3, T4, T5, T6, R](using Duplicable[E]): Duplicable[Spore6[E, T1, T2, T3, T4, T5, T6, R]] with
  def duplicate(value: Spore6[E, T1, T2, T3, T4, T5, T6, R]): Spore6[E, T1, T2, T3, T4, T5, T6, R] = value.copy(Duplicable.duplicate(value.env))

given duplicableSporeWithEnv7[E, T1, T2, T3, T4, T5, T6, T7, R](using Duplicable[E]): Duplicable[Spore7[E, T1, T2, T3, T4, T5, T6, T7, R]] with
  def duplicate(value: Spore7[E, T1, T2, T3, T4, T5, T6, T7, R]): Spore7[E, T1, T2, T3, T4, T5, T6, T7, R] = value.copy(Duplicable.duplicate(value.env))

given [T1](using Duplicable[T1]): Duplicable[Tuple1[T1]] with
  def duplicate(value: Tuple1[T1]): Tuple1[T1] = Tuple1(Duplicable.duplicate(value._1))

given [T1, T2](using Duplicable[T1], Duplicable[T2]): Duplicable[(T1, T2)] with
  def duplicate(value: (T1, T2)): (T1, T2) = (Duplicable.duplicate(value._1), Duplicable.duplicate(value._2))

given [T1, T2, T3](using Duplicable[T1], Duplicable[T2], Duplicable[T3]): Duplicable[(T1, T2, T3)] with
  def duplicate(value: (T1, T2, T3)): (T1, T2, T3) = (Duplicable.duplicate(value._1), Duplicable.duplicate(value._2), Duplicable.duplicate(value._3))

given [T1, T2, T3, T4](using Duplicable[T1], Duplicable[T2], Duplicable[T3], Duplicable[T4]): Duplicable[(T1, T2, T3, T4)] with
  def duplicate(value: (T1, T2, T3, T4)): (T1, T2, T3, T4) = (Duplicable.duplicate(value._1), Duplicable.duplicate(value._2), Duplicable.duplicate(value._3), Duplicable.duplicate(value._4))
