package com.jth.kakao.pay.test.ui.repo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jth.kakao.pay.test.repo.api.GithubApiProvider
import com.jth.kakao.pay.test.repo.model.GithubOwner
import com.jth.kakao.pay.test.repo.model.GithubRepo
import com.jth.kakao.pay.test.usecase.CommonUseCase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryViewModel(private val useCase: CommonUseCase) : ViewModel() {
    companion object {
        const val NO_DESCRIPTION = "no description"
        const val NO_NAME : String = "no name"
        const val NO_LANGUAGE : String = "not select language"
        const val NO_UPDATE_DATE : String = "no update"
    }

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
        isLoading(true)

        val repoCall = GithubApiProvider.provideGithubApi().getRepository(login, repoName)
        repoCall.enqueue(object : Callback<GithubRepo> {
            override fun onResponse(call: Call<GithubRepo>, response: Response<GithubRepo>) {
                isLoading(false)

                val repo = response.body()
                if (response.isSuccessful) {
                    repo?.let {
                        setRepoData(it)
                    }?:useCase.showToast("repo data is null")
                } else {
                    useCase.showToast(response.message())
                }
            }

            override fun onFailure(call: Call<GithubRepo>, t: Throwable) {
                isLoading(false)

                t.message?.let {
                    useCase.showToast(it)
                }
            }
        })
    }
}