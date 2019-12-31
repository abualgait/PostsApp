package com.abualgait.msayed.thiqah.ui.postsactivity

import Utils
import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.abualgait.msayed.thiqah.R
import com.abualgait.msayed.thiqah.shared.adapters.CommonAdapter
import com.abualgait.msayed.thiqah.shared.data.model.PostPOJO
import com.abualgait.msayed.thiqah.shared.interfaces.IDeleteClickListener
import com.abualgait.msayed.thiqah.shared.interfaces.IEditClickListener
import com.abualgait.msayed.thiqah.shared.interfaces.SimpleItemClickListener
import com.abualgait.msayed.thiqah.shared.ui.activity.BaseActivity
import com.abualgait.msayed.thiqah.shared.util.FlashbarUtil
import com.abualgait.msayed.thiqah.shared.util.LanguagePrfs
import com.abualgait.msayed.thiqah.shared.util.ThreadUtil
import com.abualgait.msayed.thiqah.ui.postdetailsactivity.PostDetailsActivity
import kotlinx.android.synthetic.main.activity_posts.*
import kotlinx.android.synthetic.main.activity_posts_main_view.*
import org.koin.android.viewmodel.ext.android.viewModel

class PostsActivity : BaseActivity<PostsActivityVm>(), View.OnClickListener, SimpleItemClickListener,
    IEditClickListener, IDeleteClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.addPost -> {
                if (Utils.isOnline(activity()))
                    callAddPostApi()
                else
                    FlashbarUtil.show(getString(R.string.cannotpost_while_offline), activity = activity())
            }
        }
    }

    override fun onEditClick(`object`: Any) {
        val info = `object` as PostPOJO
        if (Utils.isOnline(activity()))
            callEditPostApi(info)
        else
            FlashbarUtil.show(getString(R.string.cannotedit_while_offline), activity = activity())


    }

    private fun callAddPostApi() {
        vm.addPayload
            .observe(
                this,
                Observer { response ->
                    showMainLayout(view)
                    checkAddPostResponse(response)
                })

        vm.showError
            .observe(
                this,
                Observer {
                    Log.e("Error", it.error?.message)
                    showErrorData()

                })


        vm.addPost(PostPOJO(title = "foo", body = "bar", userId = 1, id = 0))

    }

    private fun checkAddPostResponse(response: PostsActivityVm.ResultUIModel) {

        FlashbarUtil.show(getString(R.string.post_added_str), activity = activity())


    }

    private fun callEditPostApi(info: PostPOJO) {

        vm.editPayload
            .observe(
                this,
                Observer { response ->
                    showMainLayout(view)
                    checkEditResponse(response)
                })

        vm.showError
            .observe(
                this,
                Observer {
                    Log.e("Error", it.error?.message)
                    showErrorData()

                })


        vm.editPost(info.id.toString(), info)

    }

    private fun checkEditResponse(response: PostsActivityVm.ResultUIModel) {

        FlashbarUtil.show(getString(R.string.edited_post_str), activity = activity())

    }

    override fun onDeleteClick(`object`: Any) {
        val info = `object` as PostPOJO
        if (Utils.isOnline(activity()))
            callDeletePostApi(info)
        else
            FlashbarUtil.show(getString(R.string.cannotdelete_while_offline), activity = activity())


    }

    private fun callDeletePostApi(info: PostPOJO) {
        vm.deletePayload
            .observe(
                this,
                Observer { response ->
                    showMainLayout(view)
                    checkDeleteResponse(response)
                })

        vm.showError
            .observe(
                this,
                Observer {
                    Log.e("Error", it.error?.message)
                    showErrorData()

                })


        vm.deletePost(info.id.toString())
    }

    private fun checkDeleteResponse(response: PostsActivityVm.ResultUIModel) {

        FlashbarUtil.show(getString(R.string.deleted_post_str), activity = activity())


    }

    override fun onItemClick(`object`: Any) {
        val info = `object` as PostPOJO
        PostDetailsActivity.startActivity(activity(), id = info.id)
    }


    override fun onRetryClicked() {
        super.onRetryClicked()

        checkValidation()
    }

    override val vm: PostsActivityVm by viewModel()
    override var layoutId: Int = R.layout.activity_posts


    override fun doOnCreate() {
        super.doOnCreate()
        invalidateOptionsMenu()
        hasSwipeRefresh = true
        setAdapter()
        checkValidation()
        setListeners()
    }

    private fun setListeners() {
        addPost.setOnClickListener(this)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.language, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.language -> {
                if (vm.pref.lang == "en") {
                    LanguagePrfs(activity(), false, vm.pref)
                } else {
                    LanguagePrfs(activity(), true, vm.pref)
                }
                val i = Intent(activity(), PostsActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                activity().startActivity(i)
            }
        }
        return true
    }

    override fun onSwipeRefresh() {
        super.onSwipeRefresh()
        checkValidation()

    }


    private fun checkValidation() {
        loadMore = false
        getPosts()
    }


    private fun getPosts() {

        vm.payload
            .observe(
                this,
                Observer {
                    if (it.isLoading) {
                        if (loadMore) {
                            linLoadMore!!.visibility = View.VISIBLE
                        } else {
                            showLoader()
                        }
                    } else if (it.error != null) {
                        showErrorData()
                    } else {
                        checkResponse(it.list)
                    }
                })

        vm.getPosts()


    }

    private fun checkResponse(response: List<PostPOJO>?) {
        if (!loadMore)
            mList!!.clear()

        if (response != null) {
            showMainLayout(view)
            mList!!.addAll(response)
            commonadapter!!.notifyDataSetChanged()
            //loadMore = false
        } else {
            if (mList!!.size == 0)
                showEmptyData()
            else {
                commonadapter!!.notifyDataSetChanged()

            }
        }
    }

    private fun setAdapter() {
        mList = ArrayList()
        val layoutManager = LinearLayoutManager(activity())
        recycler_posts.layoutManager = layoutManager
        commonadapter = CommonAdapter(activity(), mList!!)
        recycler_posts.adapter = commonadapter
        commonadapter!!.setItemClick(this)
        commonadapter!!.setEditClick(this)
        commonadapter!!.setDeleteClick(this)

        recycler_posts.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {

                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                    if (!loadMore) {
                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            loadMore = true
                            if (Utils.isOnline(activity())) {
                                getPosts()
                            }

                        }
                    }
                }
            }

        })
    }

    override fun showLoading() {
        super.showLoading()
        ThreadUtil.runOnUiThread { mSwipeRefresh.isRefreshing = true }
    }

    override fun hideLoading() {
        ThreadUtil.runOnUiThread { mSwipeRefresh.isRefreshing = false }
    }


}
