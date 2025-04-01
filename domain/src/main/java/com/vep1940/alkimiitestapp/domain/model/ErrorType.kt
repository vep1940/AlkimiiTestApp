package com.vep1940.alkimiitestapp.domain.model

sealed class ErrorType(override val message: String): Throwable(message) {
    data object Server: ErrorType("There is something wrong with the server connection") {
        private fun readResolve(): Any = Server
    }

    data object NoData: ErrorType("Data is empty") {
        private fun readResolve(): Any = NoData
    }
}
