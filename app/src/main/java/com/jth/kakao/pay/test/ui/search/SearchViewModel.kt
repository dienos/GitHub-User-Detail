package com.jth.kakao.pay.test.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jth.kakao.pay.test.repo.api.GithubApiProvider
import com.jth.kakao.pay.test.repo.model.RepoSearchResponse
import com.jth.kakao.pay.test.usecase.CommonUseCase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(private val useCase: CommonUseCase) : ViewModel() {
    companion object {
        const val NO_NAME : String = "noName"
        const val NO_LANGUAGE : String = "not select language"
    }

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

    fun getRepository(searchWord: String) {
        isLoading(true)

        val repoCall: Call<RepoSearchResponse> =
            GithubApiProvider.provideGithubApi().searchRepository(searchWord)

        repoCall.enqueue(object : Callback<RepoSearchResponse> {
            override fun onFailure(call: Call<RepoSearchResponse>, t: Throwable) {
                isLoading(false)

                t.message?.let {
                    useCase.showToast(it)
                }
            }

            override fun onResponse(call: Call<RepoSearchResponse>,
                                    response: Response<RepoSearchResponse>) {
                isLoading(false)

                if (response.isSuccessful) {
                    val searchResult = response.body()
                    setSearchData(searchResult)

                    searchResult?.let {
                        isEmpty(searchResult.items.isEmpty())
                    } ?: isEmpty(true)
                } else {
                    useCase.showToast("search fail")
                }
            }
        })
    }
}