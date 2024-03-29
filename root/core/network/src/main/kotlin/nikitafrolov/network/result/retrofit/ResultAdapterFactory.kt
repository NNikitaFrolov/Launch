package nikitafrolov.network.result.retrofit

import nikitafrolov.model.result.Result
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

internal class ResultAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val rawReturnType: Class<*> = getRawType(returnType)
        if (rawReturnType == Call::class.java && returnType is ParameterizedType) {
            val callInnerType: Type = getParameterUpperBound(0, returnType)
            if (getRawType(callInnerType) == Result::class.java) {
                // resultType is Call<Result<*>> | callInnerType is Result<*>
                return if (callInnerType is ParameterizedType) {
                    ResultCallAdapter<Any?>(getParameterUpperBound(0, callInnerType))
                } else {
                    ResultCallAdapter<Nothing>(Nothing::class.java)
                }
            }
        }

        return null
    }
}

private class ResultCallAdapter<R>(
    private val type: Type,
) :
    CallAdapter<R, Call<Result<R>>> {

    override fun responseType() = type

    override fun adapt(call: Call<R>): Call<Result<R>> = ResultCall(call)
}
