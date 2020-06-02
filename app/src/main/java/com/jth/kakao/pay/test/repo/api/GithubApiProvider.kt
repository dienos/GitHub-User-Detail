package com.jth.kakao.pay.test.repo.api

import android.content.Context

import com.jth.kakao.pay.test.util.Const
import com.jth.kakao.pay.test.util.PreferencesUtil

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubApiProvider {

    fun provideAuthApi(): AuthApi {
        return Retrofit.Builder()
            .baseUrl("https://github.com/")
            .client(provideOkHttpClient(provideLoggingInterceptor(), null))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    fun provideGithubApi(context: Context): GithubApi {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(
                provideOkHttpClient(
                    provideLoggingInterceptor(),
                    provideAuthInterceptor()
                )
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApi::class.java)
    }

    private fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor?
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (null != authInterceptor) {
            builder.addInterceptor(authInterceptor)
        }

        builder.addInterceptor(interceptor)
        return builder.build()
    }

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    private fun provideAuthInterceptor(): AuthInterceptor
            = AuthInterceptor(PreferencesUtil.getString(Const.TOKEN_KEY))


    internal class AuthInterceptor(private val token: String) : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()

            val builder = original.newBuilder()
                .addHeader("Authorization", "token $token")

            val request = builder.build()
            return chain.proceed(request)
        }
    }
}
