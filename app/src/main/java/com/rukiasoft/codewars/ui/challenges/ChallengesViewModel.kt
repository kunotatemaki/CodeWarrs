package com.rukiasoft.codewars.ui.challenges

import android.arch.lifecycle.ViewModel
import com.rukiasoft.codewars.persistence.PersistenceManager
import com.rukiasoft.codewars.repository.UserInfoRequests
import javax.inject.Inject

class ChallengesViewModel @Inject constructor(private val userInfoRequests: UserInfoRequests,
                                              private val persistenceManager: PersistenceManager) : ViewModel() {
}