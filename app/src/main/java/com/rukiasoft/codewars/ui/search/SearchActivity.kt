package com.rukiasoft.codewars.ui.search

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.graphics.drawable.AnimatedVectorDrawableCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewAnimationUtils
import com.rukiasoft.codewars.R
import com.rukiasoft.codewars.databinding.ActivitySearchScreenBinding
import com.rukiasoft.codewars.databinding.GlideBindingComponent
import com.rukiasoft.codewars.model.RevealCoordinates
import com.rukiasoft.codewars.utils.DeviceUtils
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_search_screen.view.*
import timber.log.Timber
import javax.inject.Inject

class SearchActivity : BaseActivity() {

    @Inject
    lateinit var deviceUtils: DeviceUtils

    private lateinit var viewModel: SearchViewModel
    private lateinit var mBinding: ActivitySearchScreenBinding

    private var revealCoordinates = RevealCoordinates()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_screen, GlideBindingComponent())

        viewModel.animateFab.observe(this, Observer {
            it?.let {
                if (it) {
                    //animate transition
                    if (viewModel.searchCardVisible.get()) {

                        if(mBinding.content.nameInput.tagInput.text.isBlank()){
                            mBinding.content.nameInput.tagInputLayout.error = resourcesManager.getString(R.string.empty_name)
                        }else {
                            viewModel.search(mBinding.content.nameInput.tagInput.text.toString())
                            sendToSearch()

                        }
                    } else {
                        searchToSend()
                    }

                    //reset value
                    viewModel.animateFab.value = false
                }
            }
        })

        viewModel.userInfo.observe(this, Observer {
            it?.let {
                Timber.d("")
            }
        })

        setSupportActionBar(mBinding.toolbar)

        mBinding.fab.setOnClickListener {
            viewModel.animateFab.value = true

        }

        mBinding.content.nameInput.visible = viewModel.searchCardVisible
        if (viewModel.searchCardVisible.get().not()) {
            mBinding.fab.setImageDrawable(resourcesManager.getDrawable(R.drawable.ic_search_white_24dp))
        } else {
            mBinding.fab.setImageDrawable(resourcesManager.getDrawable(R.drawable.ic_send_white_24dp))
        }
    }

    private fun searchToSend() {
        //animate the button
        val avd = AnimatedVectorDrawableCompat.create(this.applicationContext, R.drawable.avd_anim_search_to_send)
        mBinding.fab.setImageDrawable(avd)
        avd?.start()
        showCardAnimated()
        viewModel.searchCardVisible.set(true)
    }

    private fun sendToSearch() {
        //animate the button
        val avd = AnimatedVectorDrawableCompat.create(this.applicationContext, R.drawable.avd_anim_send_to_search)
        mBinding.fab.setImageDrawable(avd)
        avd?.start()
        hideCardAnimated()
    }

    private fun showCardAnimated(){
        val x = mBinding.fab.right - (mBinding.fab.width / 2)
        revealCoordinates.cx = x - deviceUtils.dipToPixels(8f).toInt()
        revealCoordinates.cy = (mBinding.content.nameInput.container.bottom - mBinding.content.nameInput.container.top)/2
        revealCoordinates.initialRadius = ((mBinding.fab.right - mBinding.fab.left) / 2).toFloat()
        revealCoordinates.endRadius = Math.max(deviceUtils.getScreenWidth(), deviceUtils.getScreenHeight()).toFloat()
        val anim = ViewAnimationUtils.createCircularReveal(mBinding.content.nameInput.root, revealCoordinates.cx, revealCoordinates.cy,
                revealCoordinates.initialRadius, revealCoordinates.endRadius)

        // make the view visible and start the animation

        viewModel.searchCardVisible.set(true)
        anim.duration = 300
        anim.start()

    }

    private fun hideCardAnimated(){
        val anim = ViewAnimationUtils.createCircularReveal(mBinding.content.nameInput.root, revealCoordinates.cx, revealCoordinates.cy,
                revealCoordinates.endRadius, revealCoordinates.initialRadius)

        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)

                viewModel.searchCardVisible.set(false)
                //mBinding.content.nameInput.container.setVisibility(View.INVISIBLE)
            }
        })
        anim.duration = 150
        anim.start()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_search_screen, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
