package nikitafrolov.network.result

internal interface IHttpResponse {
    val statusCode: Int
    val statusMessage: String?
    val url: String?
}
