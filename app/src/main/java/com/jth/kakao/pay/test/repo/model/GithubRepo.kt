package com.jth.kakao.pay.test.repo.model

import com.google.gson.annotations.SerializedName

data class GithubRepo(
    val name: String,

    @SerializedName("full_name")
    val fullName: String,

    val owner: GithubOwner,

    val description: String,

    val language: String,

    @SerializedName("updated_at")
    val updatedAt: String,

    @SerializedName("stargazers_count")
    val stars: Int
)
