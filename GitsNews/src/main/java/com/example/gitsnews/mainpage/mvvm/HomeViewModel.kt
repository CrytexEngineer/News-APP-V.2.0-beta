package com.example.gitsnews.mainpage.mvvm;

import android.app.Application
import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.basemvvm.base.data.model.Article
import id.co.gits.gitsbase.BaseViewModel
import id.co.gits.gitsutils.SingleLiveEvent
import id.gits.gitsmvvmkotlin.data.source.GitsDataSource
import radhika.yusuf.id.mvvmkotlin.utils.chocohelper.ChocoChips


class HomeViewModel(context: Application) : BaseViewModel(context) {

    var movieListLive = id.gits.gitsmvvmkotlin.util.SingleLiveEvent<List<Article>>()
    val snackBarMessageRemote = id.gits.gitsmvvmkotlin.util.SingleLiveEvent<String>()



    override fun start() {
        super.start()
        ChocoChips.inject(this)
        getArticles()
    }

    override fun onClearDisposable() {
        super.onClearDisposable()
        gitsRepository.onClearDisposables()
    }


    private fun getArticles() {
        gitsRepository.getArticles(object : GitsDataSource.GetArticlesCallback {
            override fun onShowProgressDialog() {

            }

            override fun onHideProgressDialog() {

            }

            override fun onSuccess(data: List<Article>) {
                movieListLive.apply {
                    value = data
                }
            }

            override fun onFinish() {

            }

            override fun onFailed(statusCode: Int, errorMessage: String?) {
                snackBarMessageRemote.value = errorMessage
            }
        })
    }
}
