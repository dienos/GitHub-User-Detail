package com.jth.kakao.pay.test.repo.api

import com.jth.kakao.pay.test.repo.model.GithubRepo
import com.jth.kakao.pay.test.repo.model.RepoSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("search/repositories")
    fun searchRepository(@Query("q") query: String): Call<RepoSearchResponse>

    @GET("repos/{owner}/{name}")
    fun getRepository(@Path("owner") ownerLogin: String, @Path("name") repoName: String): Call<GithubRepo>
}
