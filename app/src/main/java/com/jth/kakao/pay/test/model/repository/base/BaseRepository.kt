package com.jth.kakao.pay.test.model.repository.base

import android.net.Uri
import com.jth.kakao.pay.test.BuildConfig

object BaseRepository {
    fun getLoginUri(): Uri =
        Uri.Builder().scheme("https")
            .authority("github.com")
            .appendPath("login")
            .appendPath("oauth")
            .appendPath("authorize")
            .appendQueryParameter("client_id", BuildConfig.GITHUB_CLIENT_ID)
            .build()
}