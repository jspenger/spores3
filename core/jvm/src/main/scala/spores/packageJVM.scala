package spores

import upickle.default.*

import spores.*
import spores.jvm.*


/** Alias for [[spores.jvm.Spore.apply]]. */
inline def sp[T](inline fun: T): Spore[T] = {
  Spore.apply[T](fun)
}

/** Alias for [[spores.jvm.Spore.applyWithEnv]]. */
inline def spe[E, T](inline env: E)(inline fun: E => T)(using rw: Spore[ReadWriter[E]]): Spore[T] = {
  Spore.apply[E => T](fun).withEnv(env)
}

/** Alias for [[spores.jvm.Spore.applyWithCtx]]. */
inline def spc[E, T](inline env: E)(inline fun: E ?=> T)(using rw: Spore[ReadWriter[E]]): Spore[T] = {
  Spore.apply[E ?=> T](fun).withCtx(env)
}

/** Alias for [[spores.jvm.AutoCapture.apply]]. */
inline def spauto[T](inline fun: T): Spore[T] = {
  AutoCapture.apply[T](fun)
}

/** Alias for [[spores.Env.apply]]. */
inline def spenv[T](inline env: T)(using rw: Spore[ReadWriter[T]]): Spore[T] = {
  Env.apply[T](env)
}
