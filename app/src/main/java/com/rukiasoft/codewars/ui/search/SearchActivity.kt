package com.rukiasoft.codewars.ui.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.graphics.drawable.AnimatedVectorDrawableCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.rukiasoft.codewars.R
import com.rukiasoft.codewars.databinding.ActivitySearchScreenBinding
import com.rukiasoft.codewars.databinding.GlideBindingComponent

class SearchActivity : BaseActivity() {

    private lateinit var viewModel: SearchViewModel
    private lateinit var mBinding: ActivitySearchScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_screen, GlideBindingComponent())

        viewModel.animateFab.observe(this, Observer {
            it?.let {
                if (it) {
                    //animate transition
                    if (viewModel.searchCardVisible.get()) {
                        sendToSearch()
                    } else {
                        searchToSend()
                    }

                    //reset value
                    viewModel.animateFab.value = false
                }
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
        viewModel.searchCardVisible.set(true)
    }

    private fun sendToSearch() {
        //animate the button
        val avd = AnimatedVectorDrawableCompat.create(this.applicationContext, R.drawable.avd_anim_send_to_search)
        mBinding.fab.setImageDrawable(avd)
        avd?.start()
        viewModel.searchCardVisible.set(false)
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
