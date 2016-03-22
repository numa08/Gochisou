package net.numa08.gochisou.kotlin

sealed class Either<L, R> {
    abstract val isRight: Boolean
    abstract val isLeft: Boolean

    class Right<L, R>(val value: R) : Either<L, R>() {
        override val isRight: Boolean
            get() = true
        override val isLeft: Boolean
            get() = false
    }

    class Left<L, R>(val value: L) : Either<L, R>() {
        override val isRight: Boolean
            get() = false
        override val isLeft: Boolean
            get() = true
    }
}

fun <T> tryAllCatch(f: () -> T) : Either<Throwable, T> {
    try {
        val v = f()
        return Either.Right<Throwable, T>(v)
    } catch(t: Throwable) {
        return Either.Left<Throwable, T>(t)
    }
}