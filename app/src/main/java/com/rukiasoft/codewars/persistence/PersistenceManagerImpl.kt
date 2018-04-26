package com.rukiasoft.codewars.persistence

import com.rukiasoft.codewars.persistence.db.CodeWarsDatabase
import com.rukiasoft.codewars.persistence.relations.UserWithAllInfo
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
        user.user?.let { db.userInfoDao().insert(it) }
        user.skils?.let { db.skillDao().insert(it) }
        user.languages?.let { db.languageDao().insert(it) }


    }

    override fun getListUsersByDate() = db.userInfoDao().getListUsersByDate()

    override fun getListUsersByRank() = db.userInfoDao().getListUsersByRank()

    override fun getUserInfo(userName: String) = db.userInfoDao().getUsers(userName)

    override fun deleteDb() {

        db.userInfoDao().deleteAll()

    }

}
