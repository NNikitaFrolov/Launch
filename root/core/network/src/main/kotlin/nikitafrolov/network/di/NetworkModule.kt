package nikitafrolov.network.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import timber.log.Timber

@Module
@ComponentScan("nikitafrolov.network")
class NetworkModule {

    @Single
    fun provideKtor(): HttpClient {
        return HttpClient(CIO) {
            defaultRequest { url("https://developers.paysera.com/") }

            expectSuccess = true

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.tag("Logger Ktor =>").d(message)
                    }
                }
                level = LogLevel.BODY
            }
        }
    }
}
