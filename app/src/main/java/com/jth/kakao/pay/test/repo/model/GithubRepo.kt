package com.jth.kakao.pay.test.repo.model

import com.google.gson.annotations.SerializedName

data class GithubRepo(
    val name: String, @field:SerializedName("full_name")
    val fullName: String,
    val owner: GithubOwner, val description: String, val language: String,
    @field:SerializedName("updated_at")
    val updatedAt: String, @field:SerializedName("stargazers_count")
    val stars: Int
)
