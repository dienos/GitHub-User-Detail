package com.jth.kakao.pay.test.repo.model

import com.google.gson.annotations.SerializedName

data class RepoSearchResponse(
    @SerializedName("total_count")
    val totalCount: Int,

    val items: List<GithubRepo>
)
