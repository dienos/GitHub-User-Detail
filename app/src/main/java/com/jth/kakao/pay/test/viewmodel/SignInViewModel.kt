package com.jth.kakao.pay.test.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jth.kakao.pay.test.repo.api.GithubApiProvider
import com.jth.kakao.pay.test.repo.model.GithubAccessToken
import com.jth.kakao.pay.test.usecase.CommonUseCase
import com.jth.kakao.pay.test.util.Const
import com.jth.kakao.pay.test.util.PreferencesUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.jth.kakao.pay.test.BuildConfig
import com.jth.kakao.pay.test.repo.SignInRepository

class SignInViewModel(private val useCase: CommonUseCase) : ViewModel() {
    var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    fun startSignIn() {
        useCase.startLoginProcess()
    }

    fun checkTokenProcess() {
        useCase.checkTokenProcess()
    }

    private fun isLoading(loading: Boolean) {
        isLoading.value = loading
    }

    fun getAccessToken(code: String) {
        isLoading(true)

        val accessTokenCall: Call<GithubAccessToken> =
            GithubApiProvider.provideAuthApi().getAccessToken(
                BuildConfig.GITHUB_CLIENT_ID, BuildConfig.GITHUB_CLIENT_SECRET, code
            )

        accessTokenCall.enqueue(object : Callback<GithubAccessToken> {
            override fun onFailure(call: Call<GithubAccessToken>, t: Throwable) {
                isLoading(false)

                t.message?.let {
                    useCase.showToast(it)
                }?:useCase.showToast("onFailure!! message is null")

            }

            override fun onResponse(call: Call<GithubAccessToken>,
                                    response: Response<GithubAccessToken>) {

                isLoading(false)

                val token = response.body()

                token?.let {
                    if(response.isSuccessful) {
                        PreferencesUtil.set(Const.TOKEN_KEY, it.accessToken)
                        SignInRepository.githubAccessToken = it
                        useCase.startRepoListActivity()
                        useCase.finish()
                    } else {
                        useCase.showToast(response.message())
                    }

                }?:useCase.showToast("token is null")
            }
        })
    }
}