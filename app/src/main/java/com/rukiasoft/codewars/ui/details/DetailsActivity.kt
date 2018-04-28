package com.rukiasoft.codewars.ui.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.widget.Toast
import com.rukiasoft.codewars.R
import com.rukiasoft.codewars.databinding.ActivityDetailsBinding
import com.rukiasoft.codewars.databinding.GlideBindingComponent
import com.rukiasoft.codewars.ui.common.BaseActivity
import com.rukiasoft.codewars.utils.Constants
import com.rukiasoft.codewars.vo.Status
import timber.log.Timber

class DetailsActivity : BaseActivity() {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var mBinding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailsViewModel::class.java)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details, GlideBindingComponent())

        mBinding.swipeRefresh.isEnabled = false

        if (intent.hasExtra(Constants.CHALLENGE_ID).not()) {
            finish()
        }

        viewModel.setId(intent.getStringExtra(Constants.CHALLENGE_ID))

        viewModel.details.observe(this, Observer {
            it?.let {
                when(it.status){

                    Status.SUCCESS -> {
                        hideLoading()
                        mBinding.details = it.data
                    }
                    Status.ERROR -> {
                        hideLoading()
                        Toast.makeText(this@DetailsActivity.applicationContext,
                                resourcesManager.getString(R.string.outdated), Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        showLoading()
                    }
                }
            }
        })

    }

    private fun showLoading() {
        mBinding.swipeRefresh.isRefreshing = true
    }

    private fun hideLoading() {
        mBinding.swipeRefresh.isRefreshing = false
    }
}
