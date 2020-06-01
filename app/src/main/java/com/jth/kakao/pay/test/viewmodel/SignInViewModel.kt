package com.jth.kakao.pay.test.viewmodel

import androidx.lifecycle.ViewModel
import com.jth.kakao.pay.test.usecase.CommonUseCase

class SignInViewModel(private val useCase : CommonUseCase) : ViewModel() {
    fun startSignIn() {
        useCase.startLoginProcess()
    }
}