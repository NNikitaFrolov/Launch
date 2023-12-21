package nikitafrolov.exchanger.di

import nikitafrolov.exchanger.data.remote.api.CurrencyApi
import nikitafrolov.exchanger.data.remote.source.CurrencyRemoteDataSourceImpl
import nikitafrolov.exchanger.domain.source.remote.CurrencyRemoteDataSource
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import retrofit2.Retrofit

@Module
@ComponentScan("nikitafrolov.exchanger")
class ExchangerModule {

    @Factory
    internal fun provideCurrencyApi(retrofit: Retrofit): CurrencyApi =
        retrofit.create(CurrencyApi::class.java)

    @Factory
    internal fun provideCurrencyRemoteDataSource(
        dataSource: CurrencyRemoteDataSourceImpl
    ): CurrencyRemoteDataSource = dataSource

}
