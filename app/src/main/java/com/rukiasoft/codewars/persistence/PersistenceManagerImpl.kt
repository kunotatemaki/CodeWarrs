package com.rukiasoft.codewars.persistence

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.rukiasoft.codewars.persistence.db.CodeWarsDatabase
import com.rukiasoft.codewars.persistence.relations.ChallengeWithAllInfo
import com.rukiasoft.codewars.persistence.relations.UserWithAllInfo
import com.rukiasoft.codewars.persistence.utils.ChallengesToStore
import java.util.*
import javax.inject.Inject

class PersistenceManagerImpl @Inject constructor(private val db: CodeWarsDatabase) : PersistenceManager {

    companion object {
        /**
         * A good page size is a value that fills at least a screen worth of content on a large
         * device so the User is unlikely to see a null item.
         * You can play with this constant to observe the paging behavior.
         * <p>
         * It's possible to vary this with list device size, but often unnecessary, unless a user
         * scrolling on a large device is expected to scroll through items more quickly than a small
         * device, such as when the large device uses a grid layout of items.
         */
        private const val PAGE_SIZE = 30

    }


    override fun insertUserInfo(user: UserWithAllInfo) {

        user.skils?.let { db.skillDao().insert(it) }
        user.languages?.let { db.languageDao().insert(it) }

        user.user?.let {
            //check if the user exists
            val item = getUserInfoWithoutLiveData(it.userName)
            if (item != null) {
                //exist -> update it
                item.updateUserInfo(it)
                db.userInfoDao().insert(item)
            } else {
                db.userInfoDao().insert(it)
            }
        }

    }

    override fun getListUsersByDate() = db.userInfoDao().getListUsersByDate()

    override fun getListUsersByRank() = db.userInfoDao().getListUsersByRank()

    override fun getUserInfo(userName: String) = db.userInfoDao().getUser(userName)

    override fun getUserInfoWithoutLiveData(userName: String) = db.userInfoDao().getUserWithoutLiveData(userName)

    override fun getChallengesCompleted(userName: String): LiveData<PagedList<ChallengeWithAllInfo>> {
        return LivePagedListBuilder(db.challengeDao().getListChallengeCompleted(userName), PAGE_SIZE)
                .build()
    }

    override fun getChallengesAuthored(userName: String): LiveData<PagedList<ChallengeWithAllInfo>> {
        return LivePagedListBuilder(db.challengeDao().getListChallengeAuthored(userName), PAGE_SIZE)
                .build()
    }

    override fun getNumberChallengesAuthored(userName: String): LiveData<Int> {
        return db.challengeDao().getNumberOfChallenges(userName, true)
    }

    override fun getNumberChallengesCompleted(userName: String): LiveData<Int> {
        return db.challengeDao().getNumberOfChallenges(userName, false)
    }

    override fun insertChallenges(challengesToStore: ChallengesToStore) {
        db.challengeDao().insert(challengesToStore.challenge)
        db.challengeLanguageAuthoredDao().insert(challengesToStore.challengeLanguageAuthored)
        db.challengeLanguageCompletedDao().insert(challengesToStore.challengeLanguageCompleted)
        db.challengeTagDao().insert(challengesToStore.tags)
    }

    override fun storeInfoOfDownloadedAuthoredChallenges(authoredDate: Date, userName: String) {
        db.userInfoDao().storeAuthoredInfo(authoredDate, userName)
    }

    override fun storeInfoOfDownloadedCompletedChallenges(pages: Int?, items: Int?, completedDate: Date, userName: String) {
        db.userInfoDao().storeCompletedInfo(pages, items, completedDate, userName)
    }

    override fun deleteDb() {

        db.userInfoDao().deleteAll()

    }

}
