package nikitafrolov.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import retrofit2.Converter
import retrofit2.Retrofit
import timber.log.Timber

@Module
@ComponentScan("nikitafrolov.network")
class NetworkModule {

    private fun createOkhttpBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
    }

    private fun createHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            Timber.tag("OkHttp").d(message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Factory
    fun provideOkhttpClient(): OkHttpClient {
        val builder = createOkhttpBuilder()
        builder.addInterceptor(createHttpLoggingInterceptor())
        return builder.build()
    }

    @Factory
    fun provideRetrofit(retrofitProvider: RetrofitProvider, url: String): Retrofit {
        return retrofitProvider
            .provide("https://developers.paysera.com/")
            .build()
    }

    @Factory
    fun provideConverterFactory(): Converter.Factory {
        return Json.asConverterFactory("application/json".toMediaType())
    }
}
