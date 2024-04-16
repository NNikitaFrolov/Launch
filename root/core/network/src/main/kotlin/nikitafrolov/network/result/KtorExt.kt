package nikitafrolov.network.result

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import nikitafrolov.model.result.Result
import timber.log.Timber

suspend inline fun <reified R> HttpClient.getResult(
    urlString: String,
    builder: HttpRequestBuilder.() -> Unit = {}
): Result<R> = try {
    val response = get(urlString, builder)
    Result.Success.HttpResponse(
        value = response.body<R>(),
        statusCode = response.status.value,
        statusMessage = response.status.description,
        url = response.call.request.url.toString(),
    )
} catch (error: Exception) {
    Timber.e("Request error: ${error.localizedMessage}")
    Result.Failure.Error(error)
}