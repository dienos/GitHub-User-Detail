package com.jth.kakao.pay.test.usecase

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import com.jth.kakao.pay.test.repo.BaseRepository
import com.jth.kakao.pay.test.util.Const.TOKEN_KEY
import com.jth.kakao.pay.test.util.PreferencesUtil
import com.jth.kakao.pay.test.view.RepositoryListActivity

class CommonUseCase(private val context: Context) {
    fun checkTokenProcess() {
        if(PreferencesUtil.getString(TOKEN_KEY).isBlank().not()) {
            startRepoListActivity()
        }
    }

    fun startLoginProcess() {
        CustomTabsIntent.Builder().build().launchUrl(context, BaseRepository.getLoginUri())
    }

    fun startRepoListActivity() {
        val intent = Intent(context, RepositoryListActivity::class.java)
        context.startActivity(intent)
    }

    fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun finish() {
        if(context is Activity) {
            context.finish()
        }
    }
}