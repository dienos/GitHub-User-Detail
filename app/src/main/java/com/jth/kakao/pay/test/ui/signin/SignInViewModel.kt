package com.jth.kakao.pay.test.ui.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jth.kakao.pay.test.usecase.CommonUseCase
import com.jth.kakao.pay.test.util.Const
import com.jth.kakao.pay.test.util.PreferencesUtil
import com.jth.kakao.pay.test.BuildConfig
import com.jth.kakao.pay.test.repo.SignInRepository
import com.jth.kakao.pay.test.repo.api.provideAuthApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class SignInViewModel(private val useCase: CommonUseCase) : ViewModel() {
    var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    internal val disposable = CompositeDisposable()

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
        disposable.add(provideAuthApi().getAccessToken(
            BuildConfig.GITHUB_CLIENT_ID,
            BuildConfig.GITHUB_CLIENT_SECRET, code)
            .map { it }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading(true) }
            .doOnTerminate { isLoading(false) }
            .subscribe({ token ->
                token?.let {
                    PreferencesUtil.set(Const.TOKEN_KEY, it.accessToken)
                    SignInRepository.githubAccessToken = it
                    useCase.startSearchActivity()
                    useCase.finish()
                } ?: useCase.showToast("token is null")
            }) {
                it.message?.let { msg ->
                    useCase.showToast(msg)
                }
            })
    }
}