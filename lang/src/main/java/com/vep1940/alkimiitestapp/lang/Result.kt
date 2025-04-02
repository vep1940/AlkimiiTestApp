package com.vep1940.alkimiitestapp.lang

fun <T> T.success(): Result<T> = Result.success(this)

fun <T> Throwable.failure(): Result<T> = Result.failure(this)