package com.jth.kakao.pay.test.view

import android.os.Bundle
import com.jth.kakao.pay.test.R
import com.jth.kakao.pay.test.databinding.ActivityUserListBinding
import com.jth.kakao.pay.test.usecase.CommonUseCase
import com.jth.kakao.pay.test.view.base.BaseBindingActivity
import com.jth.kakao.pay.test.viewmodel.UserListViewModel

class UserListActivity : BaseBindingActivity<ActivityUserListBinding>() {
    override fun getLayoutResId(): Int = R.layout.activity_user_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = UserListViewModel(CommonUseCase(this))
    }
}