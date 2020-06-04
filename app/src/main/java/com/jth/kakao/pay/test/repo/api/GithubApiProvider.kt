package com.jth.kakao.pay.test.repo.api

import com.jth.kakao.pay.test.util.Const
import com.jth.kakao.pay.test.util.PreferencesUtil

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


fun provideAuthApi(): AuthApi = Retrofit.Builder()
    .baseUrl("https://github.com/")
    .client(provideOkHttpClient(provideLoggingInterceptor(), null))
    .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(AuthApi::class.java)

fun provideGithubApi(): GithubApi = Retrofit.Builder()
    .baseUrl("https://api.github.com/")
    .client(
        provideOkHttpClient(
            provideLoggingInterceptor(),
            provideAuthInterceptor()
        )
    )
    .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(GithubApi::class.java)

private fun provideOkHttpClient(
    interceptor: HttpLoggingInterceptor,
    authInterceptor: AuthInterceptor?
): OkHttpClient =
    OkHttpClient.Builder().run {
        if (null != authInterceptor) {
            addInterceptor(authInterceptor)
        }

        addInterceptor(interceptor)
        build()
    }

private fun provideLoggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

private fun provideAuthInterceptor(): AuthInterceptor =
    AuthInterceptor(PreferencesUtil.getString(Const.TOKEN_KEY))


internal class AuthInterceptor(private val token: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
        val request = request().newBuilder().run {
            addHeader("Authorization", "token $token")
            build()
        }

        proceed(request)
    }

}
