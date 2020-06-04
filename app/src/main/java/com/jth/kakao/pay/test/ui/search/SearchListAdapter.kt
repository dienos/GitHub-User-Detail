package com.jth.kakao.pay.test.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jth.kakao.pay.test.BR
import com.jth.kakao.pay.test.R
import com.jth.kakao.pay.test.databinding.ItemSearchBinding
import com.jth.kakao.pay.test.repo.model.GithubRepo

private val diffCallback = object : DiffUtil.ItemCallback<GithubRepo>() {
    override fun areItemsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean =
        oldItem.name == newItem.name
}

class SearchListAdapter(val viewModel: SearchViewModel) :
    ListAdapter<GithubRepo, SearchListAdapter.SearchListViewHolder>(diffCallback) {
    override fun onBindViewHolder(holder: SearchListViewHolder, position: Int) {
        val item = getItem(position)
        item?.apply {
            holder.bind(viewModel, this)
        }
    }

    override fun getItemCount() = super.getItemCount()

    override fun getItem(position: Int): GithubRepo? = super.getItem(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return SearchListViewHolder(view)
    }

    class SearchListViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(viewModel: SearchViewModel, repo: GithubRepo?) {
            val binding: ItemSearchBinding = DataBindingUtil.bind(view)!!

            binding.viewModel = viewModel

            repo?.let {
                binding.setVariable(BR.repo, it)
            }
        }
    }
}