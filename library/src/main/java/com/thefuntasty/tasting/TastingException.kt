package com.thefuntasty.tasting

import java.lang.RuntimeException

class TastingException : RuntimeException {
    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable) : super(message, cause)

    constructor(cause: Throwable) : super(cause)
}
