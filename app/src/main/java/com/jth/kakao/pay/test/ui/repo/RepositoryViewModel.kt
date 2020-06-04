package com.jth.kakao.pay.test.ui.repo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jth.kakao.pay.test.repo.api.provideGithubApi
import com.jth.kakao.pay.test.repo.model.GithubOwner
import com.jth.kakao.pay.test.repo.model.GithubRepo
import com.jth.kakao.pay.test.usecase.CommonUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class RepositoryViewModel(private val useCase: CommonUseCase) : ViewModel() {
    companion object {
        const val NO_DESCRIPTION = "no description"
        const val NO_NAME : String = "no name"
        const val NO_LANGUAGE : String = "not select language"
        const val NO_UPDATE_DATE : String = "no update"
    }

    internal val disposable = CompositeDisposable()

    var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    var repoData: MutableLiveData<GithubRepo> = MutableLiveData(
        GithubRepo(
            name = "",
            fullName = "",
            owner = GithubOwner("", ""),
            description = "",
            language = "",
            updatedAt = "",
            stars = 0
        )
    )

    private fun isLoading(loading: Boolean) {
        isLoading.value = loading
    }

    private fun setRepoData(data: GithubRepo) {
        repoData.value = data
    }

    fun showRepositoryInfo(login: String, repoName: String) {
        disposable.add(provideGithubApi().getRepository(login, repoName)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{isLoading(true)}
            .doOnError {
                isLoading(false)

                it.message?.let {
                    msg ->
                    useCase.showToast(msg)
                }
            }
            .doOnComplete{isLoading(false)}
            .subscribe( {
                repo ->

                repo?.let {
                    setRepoData(it)
                }?:useCase.showToast("repo data is null")
            }) {
                it.message?.let {
                    msg ->
                    useCase.showToast(msg)
                }
            })
    }
}