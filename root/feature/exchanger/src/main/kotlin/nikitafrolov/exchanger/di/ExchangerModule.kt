package nikitafrolov.exchanger.di

import io.ktor.client.HttpClient
import nikitafrolov.exchanger.data.local.source.AccountLocalDataSourceImpl
import nikitafrolov.exchanger.data.remote.api.CurrencyApi
import nikitafrolov.exchanger.data.remote.source.AccountRemoteDataSourceImpl
import nikitafrolov.exchanger.domain.source.local.AccountLocalDataSource
import nikitafrolov.exchanger.domain.source.remote.AccountRemoteDataSource
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
@ComponentScan("nikitafrolov.exchanger")
class ExchangerModule {

    @Factory
    internal fun provideCurrencyApi(ktor: HttpClient): CurrencyApi =
        CurrencyApi(ktor)

    @Factory
    internal fun provideAccountRemoteDataSource(
        dataSource: AccountRemoteDataSourceImpl
    ): AccountRemoteDataSource = dataSource

    @Factory
    internal fun provideAccountLocalDataSource(
        dataSource: AccountLocalDataSourceImpl
    ): AccountLocalDataSource = dataSource
}
