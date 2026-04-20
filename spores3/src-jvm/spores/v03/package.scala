package spores


package v03 {

  import spores.Spore0
  import spores.Spore0.AST.*


  extension [E, T](spore: Spore[E, T]) {
    def pickle(using Pickler[E]): Pickle[T] = pickle0

    def pickle0[F[_]](using Pickler0[F, E]): Pickle0[F, T] = {
      if (spore.hasEnv) {
        WithEnv(
          Body(
            spore.className,
            2,
            spore.asInstanceOf[spores.SporeLambdaBuilder0[_, E => T]].body
          ),
          spores.Spore0.value(
            spore.env
          )
        )
      }
      else {
        Body(
          spore.className,
          2,
          spore.asInstanceOf[spores.SporeLambdaBuilder0[_, T]].body
        )
      }
    }
  }


  extension [T](spore: Spore[Nothing, T]) {
    def pickle: Pickle[T] = pickle0

    def pickle0[F[_]]: Pickle0[F, T] = {
      Body(
        spore.className,
        2,
        spore.asInstanceOf[spores.SporeLambdaBuilder0[_, T]].body
      )
    }
  }
}
