package com.abualgait.msayed.thiqah.ui.postdetailsactivity

import Utils
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.abualgait.msayed.thiqah.R
import com.abualgait.msayed.thiqah.shared.data.model.PostPOJO
import com.abualgait.msayed.thiqah.shared.ui.activity.BaseActivity
import com.abualgait.msayed.thiqah.shared.util.ThreadUtil
import kotlinx.android.synthetic.main.activity_post_details_main_view.*
import kotlinx.android.synthetic.main.activity_posts.*
import org.koin.android.viewmodel.ext.android.viewModel


class PostDetailsActivity : BaseActivity<PostDetailsActivityVm>() {

    override fun onRetryClicked() {
        super.onRetryClicked()
        checkValidation()
    }

    companion object {

        fun startActivity(mActivity: Activity?, withflag: Boolean = false, id: Int) {

            val mIntent = Intent(mActivity, PostDetailsActivity::class.java)
            val bundle = Bundle()
            bundle.putInt("id", id)
            mIntent.putExtras(bundle)

            if (withflag)
                mIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or
                        Intent.FLAG_ACTIVITY_NEW_TASK
            mActivity!!.startActivity(mIntent)
        }
    }

    override val vm: PostDetailsActivityVm by viewModel()
    override var layoutId: Int = R.layout.activity_post_details
    var post_id = 0

    override fun doOnCreate() {
        super.doOnCreate()

        val args: Bundle? = intent.extras
        if (args != null) {
            post_id = args.getInt("id")

        }
        invalidateOptionsMenu()
        hasBackNavigation = true
        hasSwipeRefresh = true
        checkValidation()
    }


    override fun onSwipeRefresh() {
        super.onSwipeRefresh()
        checkValidation()

    }


    @SuppressLint("CheckResult")
    private fun checkValidation() {

        if (Utils.isOnline(activity())) {

            getPlacesFromApi()
        } else {
            loadPlaceFromDB()
        }
    }

    private fun loadPlaceFromDB() {
        vm.getPostDetailsLiveData()
            .observe(
                this,
                Observer { response ->
                    showMainLayout(view)
                    checkResponse(response)

                })

        vm.getError()
            .observe(
                this,
                Observer { error ->
                    Log.e("Error", error)
                    showErrorData()

                })
        vm.getPostDetailsFromDatabase(post_id.toString())
    }

    private fun getPlacesFromApi() {
        showLoader()
        vm.getPostDetailsLiveData()
            .observe(
                this,
                Observer { response ->
                    showMainLayout(view)
                    checkResponse(response)

                })

        vm.getError()
            .observe(
                this,
                Observer { error ->
                    Log.e("Error", error)
                    showErrorData()

                })


        vm.getPostDetailsApi(post_id.toString())


    }

    private fun checkResponse(response: PostPOJO?) {
        if (response != null) {
            postTitle.text = response.title
            postBody.text = response.body
        } else {
            showEmptyData()
        }

    }


    override fun showLoading() {
        super.showLoading()
        ThreadUtil.runOnUiThread { mSwipeRefresh.isRefreshing = true }
    }

    override fun hideLoading() {
        ThreadUtil.runOnUiThread { mSwipeRefresh.isRefreshing = false }
    }


}
