package com.example.gitsnews.mainpage.mvvm

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.basemvvm.base.data.model.Article
import com.example.gitsnews.BR
import com.example.gitsnews.databinding.HomeItemBinding


/**
 * Created by irfanirawansukirman on 26/01/18.
 */
class HomeAdapter(
    private val articleItemClickListener: HomeUserActionListener,
    private val data: List<Article>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HomeHolder).bindItem(data[position], articleItemClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomeHolder(
            HomeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        (holder as HomeHolder).apply {
            mainItemBinding.apply {
                setVariable(BR.item, null)
                setVariable(BR.userActionListener, null)
                executePendingBindings()
            }
        }

        super.onViewRecycled(holder)
    }


    override fun getItemCount(): Int = data.size

    class HomeHolder(val mainItemBinding: HomeItemBinding) :
        RecyclerView.ViewHolder(mainItemBinding.root) {


        fun bindItem(article: Article, userActionListener: HomeUserActionListener) {
            mainItemBinding.apply {
                setVariable(BR.item, article)
                setVariable(BR.userActionListener, userActionListener)
                executePendingBindings()
            }
        }


    }
}

