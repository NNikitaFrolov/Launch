package nikitafrolov.network.result

sealed class Result<out T> {

    sealed class Success<T> : Result<T>() {

        abstract val value: T

        override fun toString() = "Success($value)"

        data class Value<T>(override val value: T) : Success<T>()

        data class HttpResponse<T>(
            override val value: T,
            override val statusCode: Int,
            override val statusMessage: String? = null,
            override val url: String? = null,
        ) : Success<T>(), IHttpResponse

        object Empty : Success<Nothing>() {

            override val value: Nothing get() = error("No value")

            override fun toString() = "Success"
        }
    }

    sealed class Failure(open val error: Throwable) : Result<Nothing>() {

        override fun toString() = "Failure($error)"

        data class Error(override val error: Throwable) : Failure(error)
    }
}

typealias EmptyResult = Result<Nothing>
