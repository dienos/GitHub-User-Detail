package com.jth.kakao.pay.test.repo.model

import com.google.gson.annotations.SerializedName

data class GithubAccessToken(
    @SerializedName("access_token")
    val accessToken: String,

    val scope: String,

    @SerializedName("token_type")
    val tokenType: String
)