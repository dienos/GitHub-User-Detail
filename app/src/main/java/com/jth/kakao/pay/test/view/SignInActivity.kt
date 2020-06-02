package com.jth.kakao.pay.test.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.jth.kakao.pay.test.R
import com.jth.kakao.pay.test.databinding.ActivitySignInBinding
import com.jth.kakao.pay.test.usecase.CommonUseCase
import com.jth.kakao.pay.test.util.PreferencesUtil
import com.jth.kakao.pay.test.view.base.BaseBindingActivity
import com.jth.kakao.pay.test.viewmodel.SignInViewModel
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : BaseBindingActivity<ActivitySignInBinding>() {
    private lateinit var useCase: CommonUseCase
    private lateinit var viewModel: SignInViewModel

    override fun getLayoutResId(): Int = R.layout.activity_sign_in

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        useCase = CommonUseCase(this)

        binding.lifecycleOwner = this
        binding.viewModel = SignInViewModel(useCase)
        viewModel = binding.viewModel as SignInViewModel

        PreferencesUtil.init(applicationContext)
        viewModel.checkTokenProcess()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        showLoading()

        intent?.let {
            it.data?.let { uri ->

                val code = uri.getQueryParameter("code")

                code?.let { code ->
                    viewModel.getAccessToken(code)

                } ?: useCase.showToast("code is null")
            } ?: useCase.showToast("data is null")
        } ?: useCase.showToast("intent is null")
    }

    private fun showLoading() {
        pb_loading.visibility = View.VISIBLE
        bt_start.visibility = View.GONE
    }
}