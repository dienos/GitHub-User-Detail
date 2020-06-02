package com.jth.kakao.pay.test.ui.search

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jth.kakao.pay.test.repo.model.GithubRepo

@BindingAdapter("img_url")
fun setUserImg(view: ImageView, url: String?) {
    url?.let {
        Glide.with(view.context).load(url).placeholder(ColorDrawable(Color.GRAY)).into(view)
    }
}

@BindingAdapter(value = ["viewModel", "search_list"])
fun setSearchList(view: RecyclerView, viewModel: SearchViewModel, data: List<GithubRepo>?) {
    if (view.adapter == null) {
        view.itemAnimator = DefaultItemAnimator()
        view.adapter = SearchListAdapter(viewModel = viewModel).apply { submitList(data) }
    } else {
        (view.adapter as? SearchListAdapter)?.submitList(data)
    }
}


