package com.jth.kakao.pay.test.usecase

import android.content.Context
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import com.jth.kakao.pay.test.model.repository.base.BaseRepository

class CommonUseCase(private val context: Context) {
    fun startLoginProcess() {
        CustomTabsIntent.Builder().build().launchUrl(context, BaseRepository.getLoginUri())
    }

    fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}