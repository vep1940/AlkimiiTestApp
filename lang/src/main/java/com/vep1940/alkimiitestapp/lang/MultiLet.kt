package com.vep1940.alkimiitestapp.lang

inline fun <T : Any, R> multiLet(vararg elements: T?, block: (List<T>) -> R): R? {
    return if (elements.all { it != null }) {
        block(elements.filterNotNull())
    } else null
}