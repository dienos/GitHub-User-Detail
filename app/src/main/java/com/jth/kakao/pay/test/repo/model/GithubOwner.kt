package com.jth.kakao.pay.test.repo.model

import com.google.gson.annotations.SerializedName

data class GithubOwner(
    val login: String,

    @SerializedName("avatar_url")
    val avatarUrl: String
)
