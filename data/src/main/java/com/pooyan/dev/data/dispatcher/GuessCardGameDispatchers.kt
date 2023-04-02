package com.pooyan.dev.data.dispatcher

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val cardGameDispatchers: GuessCardGameDispatchers)

enum class GuessCardGameDispatchers {
    IO
}
