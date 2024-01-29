package nikitafrolov.network.di

import nikitafrolov.network.result.retrofit.ResultAdapterFactory
import okhttp3.OkHttpClient
import org.koin.core.annotation.Factory
import retrofit2.Converter
import retrofit2.Retrofit

@Factory
class RetrofitProvider(
    private val okHttpClient: OkHttpClient,
    private val converterFactory: Converter.Factory,
) {
    fun provide(baseUrl: String): Retrofit.Builder {
        return Retrofit.Builder().apply {
            baseUrl(baseUrl)
            addCallAdapterFactory(ResultAdapterFactory())
            client(okHttpClient)
            addConverterFactory(converterFactory)
        }
    }
}
