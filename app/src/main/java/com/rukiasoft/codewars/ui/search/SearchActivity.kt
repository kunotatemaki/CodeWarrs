package com.rukiasoft.codewars.ui.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.graphics.drawable.AnimatedVectorDrawableCompat
import android.view.Menu
import android.view.MenuItem
import com.rukiasoft.codewars.R
import kotlinx.android.synthetic.main.activity_search_screen.*
import timber.log.Timber

class SearchActivity : BaseActivity() {

    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)

        setContentView(R.layout.activity_search_screen)

        viewModel.animateFab.observe(this, Observer {
            it?.let {
                if(it){
                    //animate transition
                    if(viewModel.isInSearchMode()){
                        searchToSend()
                    }else{
                        sendToSearch()
                    }

                    //reset value
                    viewModel.animateFab.value = false
                }
            }
        })

        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            viewModel.animateFab.value = true

        }

        if(viewModel.isInSearchMode()) {
            fab.setImageDrawable(resourcesManager.getDrawable(R.drawable.ic_search_white_24dp))
        }else{
            fab.setImageDrawable(resourcesManager.getDrawable(R.drawable.ic_send_white_24dp))
        }
    }

    private fun searchToSend(){
        //animate the button
        val avd = AnimatedVectorDrawableCompat.create(fab.context, R.drawable.avd_anim_search_to_send)
        fab.setImageDrawable(avd)
        avd?.start()
        viewModel.setStateAsToSend()
    }

    private fun sendToSearch(){
        //animate the button
        val avd = AnimatedVectorDrawableCompat.create(fab.context, R.drawable.avd_anim_send_to_search)
        fab.setImageDrawable(avd)
        avd?.start()
        viewModel.setStateAsSearching()
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
