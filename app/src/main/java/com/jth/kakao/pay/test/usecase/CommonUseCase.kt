package com.jth.kakao.pay.test.usecase

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import com.jth.kakao.pay.test.repo.BaseRepository
import com.jth.kakao.pay.test.repo.model.GithubRepo
import com.jth.kakao.pay.test.ui.repo.RepositoryActivity
import com.jth.kakao.pay.test.util.Const.TOKEN_KEY
import com.jth.kakao.pay.test.util.PreferencesUtil
import com.jth.kakao.pay.test.ui.search.SearchActivity
import com.jth.kakao.pay.test.util.Const.KEY_REPO_NAME
import com.jth.kakao.pay.test.util.Const.KEY_USER_LOGIN

class CommonUseCase(private val context: Context) {
    fun checkTokenProcess() {
        if(PreferencesUtil.getString(TOKEN_KEY).isBlank().not()) {
            startSearchActivity()
        }
    }

    fun startLoginProcess() {
        CustomTabsIntent.Builder().build().launchUrl(context, BaseRepository.getLoginUri())
    }

    fun startSearchActivity() {
        val intent = Intent(context, SearchActivity::class.java)
        context.startActivity(intent)
    }

    fun startRepositoryActivity(repository : GithubRepo) {
        val intent = Intent(context, RepositoryActivity::class.java)
        intent.putExtra(KEY_USER_LOGIN, repository.owner.login)
        intent.putExtra(KEY_REPO_NAME, repository.name)
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