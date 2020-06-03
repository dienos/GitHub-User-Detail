package com.jth.kakao.pay.test.ui.repo

import android.os.Bundle
import android.widget.Toast
import com.jth.kakao.pay.test.R
import com.jth.kakao.pay.test.databinding.ActivityRepositoryBinding
import com.jth.kakao.pay.test.ui.base.BaseBindingActivity
import com.jth.kakao.pay.test.usecase.CommonUseCase
import com.jth.kakao.pay.test.util.Const.KEY_REPO_NAME
import com.jth.kakao.pay.test.util.Const.KEY_USER_LOGIN

class RepositoryActivity : BaseBindingActivity<ActivityRepositoryBinding>() {
    override fun getLayoutResId(): Int = R.layout.activity_repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val login : String? = intent.getStringExtra(KEY_USER_LOGIN)
        val repo : String? = intent.getStringExtra(KEY_REPO_NAME)

        binding.lifecycleOwner = this
        binding.viewModel = RepositoryViewModel(CommonUseCase(this))

        if(login != null && repo != null) {
            (binding.viewModel as RepositoryViewModel).showRepositoryInfo(login = login,repoName = repo)
        } else {
            Toast.makeText(this, "extra data is null", Toast.LENGTH_SHORT).show()
        }
    }
}