package id.gits.gitsmvvmkotlin.data.source

import com.example.basemvvm.base.data.model.Article

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

open class GitsRepository(private val remoteDataSource: GitsDataSource,
                          private val localDataSource: GitsDataSource) : GitsDataSource {

    override fun onClearDisposables() {
        remoteDataSource.onClearDisposables()
        localDataSource.onClearDisposables()
    }

    override fun saveArticle(article: List<Article>) {
        localDataSource.saveArticle(article)
    }

    override fun getArticles(callback: GitsDataSource.GetArticlesCallback) {
        remoteDataSource.getArticles(object : GitsDataSource.GetArticlesCallback {
            override fun onShowProgressDialog() {

            }

            override fun onHideProgressDialog() {

            }

            override fun onSuccess(data: List<Article>) {
                saveArticle(data)
                loadArticles(callback)
            }

            override fun onFinish() {
                callback.onFinish()
            }

            override fun onFailed(statusCode: Int, errorMessage: String?) {
                callback.onFailed(statusCode, errorMessage)
            }
        })
    }

    private fun loadArticles(callback: GitsDataSource.GetArticlesCallback) {
        localDataSource.getArticles(object : GitsDataSource.GetArticlesCallback {
            override fun onShowProgressDialog() {

            }

            override fun onHideProgressDialog() {

            }

            override fun onSuccess(data: List<Article>) {
                callback.onSuccess(data)
            }

            override fun onFinish() {
                callback.onFinish()
            }

            override fun onFailed(statusCode: Int, errorMessage: String?) {
                callback.onFailed(statusCode, errorMessage)
            }
        })
    }

}