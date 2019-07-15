package com.example.gitsnews.mainpage.mvvm


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.basemvvm.base.data.model.Article
import com.example.gitsnews.R
import com.example.gitsnews.databinding.FragmentHomeBinding
import com.synnapps.carouselview.ImageListener
import id.co.gits.gitsutils.extensions.verticalListStyle
import id.gits.gitsmvvmkotlin.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import radhika.yusuf.id.mvvmkotlin.utils.chocohelper.ChocoBinding
import radhika.yusuf.id.mvvmkotlin.utils.chocohelper.ChocoChips
import radhika.yusuf.id.mvvmkotlin.utils.chocohelper.ChocoViewModel


class HomeFragment : BaseFragment<HomeViewModel>(), HomeUserActionListener {
    @ChocoBinding(R.layout.fragment_home)
    lateinit var mViewDataBinding: FragmentHomeBinding
    @ChocoViewModel
    lateinit var mViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,

        savedInstanceState: Bundle?
    ): View? {
        ChocoChips.inject<FragmentHomeBinding, HomeViewModel, HomeUserActionListener>(this)
        return mViewDataBinding.root
    }

    override fun onViewCreated(paramView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(paramView, savedInstanceState)

    }

    fun onCreateViewModel(): HomeViewModel {
        return ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }


    override fun onCreateObserver(viewModel: HomeViewModel) {
        mViewModel.apply {
            movieListLive.observe(this@HomeFragment, Observer {
                // load movie list to view
                setupArticleList(it!!)
            })
        }
    }

    private fun setupArticleList(article: List<Article>) {
        recycler_main.apply {
            verticalListStyle()
            adapter = HomeAdapter(this@HomeFragment, article)
        }
    }

    override fun setContentData() {
        mViewModel.start()
    }

    override fun setMessageType(): String = MESSAGE_TYPE_SNACK

    override fun onDestroyObserver(viewModel: HomeViewModel) {

    }

    companion object {
        fun newInstance() = HomeFragment().apply {

        }

    }

    var imageListener: ImageListener = object : ImageListener {

        override fun setImageForPosition(position: Int, imageView: ImageView) {

        }
    }


}


fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>) =
    ViewModelProviders.of(
        this, HomeVMFactory.getInstance(
            requireActivity()
                .application
        )
    ).get(viewModelClass)