package com.rukiasoft.codewars.ui.challenges

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.widget.RecyclerView
import com.rukiasoft.codewars.R
import com.rukiasoft.codewars.databinding.ActivityChallengesBinding
import com.rukiasoft.codewars.databinding.GlideBindingComponent
import com.rukiasoft.codewars.ui.common.BaseActivity
import com.rukiasoft.codewars.utils.Constants
import kotlinx.android.synthetic.main.activity_challenges.*

class ChallengesActivity : BaseActivity() {

    private lateinit var viewModel: ChallengesViewModel
    private lateinit var mBinding: ActivityChallengesBinding
    private lateinit var mRecyclerView: RecyclerView

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_completed -> {
                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_authored -> {
                message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChallengesViewModel::class.java)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_challenges, GlideBindingComponent())

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if(intent.hasExtra(Constants.USER_NAME).not()){
            finish()
        }

        viewModel.setUserName(intent.getStringExtra(Constants.USER_NAME))


    }
}
