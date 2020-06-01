package com.jth.kakao.pay.test.view

import android.content.Intent
import android.os.Bundle
import com.jth.kakao.pay.test.R
import com.jth.kakao.pay.test.databinding.ActivitySignInBinding
import com.jth.kakao.pay.test.model.repository.SignInRepository
import com.jth.kakao.pay.test.usecase.CommonUseCase
import com.jth.kakao.pay.test.view.base.BaseBindingActivity
import com.jth.kakao.pay.test.viewmodel.SignInViewModel

class SignInActivity : BaseBindingActivity<ActivitySignInBinding>()   {
    override fun getLayoutResId(): Int = R.layout.activity_sign_in

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = SignInViewModel(CommonUseCase(this))

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }
}