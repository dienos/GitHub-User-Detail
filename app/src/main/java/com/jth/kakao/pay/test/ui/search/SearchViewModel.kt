package com.jth.kakao.pay.test.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jth.kakao.pay.test.repo.api.provideGithubApi
import com.jth.kakao.pay.test.repo.model.GithubRepo
import com.jth.kakao.pay.test.repo.model.RepoSearchResponse
import com.jth.kakao.pay.test.usecase.CommonUseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.lang.IllegalStateException

class SearchViewModel(private val useCase: CommonUseCase) : ViewModel() {
    companion object {
        const val NO_NAME: String = "noName"
        const val NO_LANGUAGE: String = "not select language"
    }

    internal val disposable = CompositeDisposable()

    var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    var isEmpty: MutableLiveData<Boolean> = MutableLiveData(false)
    var searchData: MutableLiveData<RepoSearchResponse> =
        MutableLiveData(RepoSearchResponse(0, arrayListOf()))

    private fun isLoading(loading: Boolean) {
        isLoading.value = loading
    }

    private fun isEmpty(empty: Boolean) {
        isEmpty.value = empty
    }

    private fun setSearchData(data: RepoSearchResponse?) {
        searchData.value = data
    }

    fun startRepository(repository: GithubRepo) {
        useCase.startRepositoryActivity(repository)
    }

    fun getRepository(searchWord: String) {
        disposable.add(provideGithubApi().searchRepository(searchWord)
            .flatMap {
                if (it.totalCount == 0) {
                    Observable.error(IllegalStateException("no search result"))
                } else {
                    Observable.just(it)
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading(true) }
            .doOnTerminate { isLoading(false) }
            .subscribe({ item ->
                setSearchData(item)
                isEmpty(item.totalCount == 0)
            }) {
                isEmpty(true)

                it.message?.let { msg ->
                    useCase.showToast(msg)
                }
            }
        )
    }
}