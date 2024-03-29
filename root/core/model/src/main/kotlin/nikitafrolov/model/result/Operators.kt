package nikitafrolov.model.result

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
fun <T> Result<T>.isSuccess(): Boolean {
    contract {
        returns(true) implies (this@isSuccess is Result.Success<T>)
    }
    return this is Result.Success
}

fun <T> Result<T>.asSuccess(): Result.Success<T> {
    return this as Result.Success<T>
}

@OptIn(ExperimentalContracts::class)
fun <T> Result<T>.isFailure(): Boolean {
    contract {
        returns(true) implies (this@isFailure is Result.Failure)
    }
    return this is Result.Failure
}

fun <T> Result<T>.asFailure(): Result.Failure {
    return this as Result.Failure
}

fun <T, R> Result<T>.map(transform: (value: T) -> R): Result<R> {
    return when (this) {
        is Result.Success -> Result.Success.Value(transform(value))
        is Result.Failure -> this
    }
}

fun <T, R> Result<T>.flatMap(transform: (result: Result.Success<T>) -> Result<R>): Result<R> {
    return when (this) {
        is Result.Success -> transform(this)
        is Result.Failure -> this
    }
}

fun <T> Result<T>.call(
    failure: (Result.Failure) -> Unit = {},
    success: (Result.Success<T>) -> Unit = {},
) {
    if (this.isSuccess()) {
        success.invoke(this)
    } else {
        failure.invoke(this.asFailure())
    }
}
