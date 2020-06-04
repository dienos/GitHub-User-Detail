package com.jth.kakao.pay.test.ui.search

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import com.jth.kakao.pay.test.R
import com.jth.kakao.pay.test.databinding.ActivitySearchListBinding
import com.jth.kakao.pay.test.usecase.CommonUseCase
import com.jth.kakao.pay.test.ui.base.BaseBindingActivity

class SearchActivity : BaseBindingActivity<ActivitySearchListBinding>() {
    private lateinit var menuSearch: MenuItem
    private lateinit var searchView: SearchView
    private lateinit var viewModel: SearchViewModel

    override fun getLayoutResId(): Int = R.layout.activity_search_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel =
            SearchViewModel(CommonUseCase(this))

        viewModel = binding.viewModel as SearchViewModel
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_activity_search, menu)
        menuSearch = menu.findItem(R.id.menu_activity_search_query)

        searchView = menuSearch.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                hideSoftKeyboard()
                collapseSearchView()
                viewModel.getRepository(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        menuSearch.expandActionView()

        return true
    }

    override fun onStop() {
        super.onStop()
        viewModel.disposable.clear()
    }

    private fun hideSoftKeyboard() {
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(searchView.windowToken, 0)
        }
    }

    private fun collapseSearchView() {
        menuSearch.collapseActionView()
    }

}