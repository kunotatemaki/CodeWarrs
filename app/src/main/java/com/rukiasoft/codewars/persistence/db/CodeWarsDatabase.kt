package com.rukiasoft.codewars.persistence.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.rukiasoft.codewars.persistence.daos.LanguageDao
import com.rukiasoft.codewars.persistence.daos.SkillDao
import com.rukiasoft.codewars.persistence.daos.UserInfoDao
import com.rukiasoft.codewars.persistence.entities.Language
import com.rukiasoft.codewars.persistence.entities.Skill
import com.rukiasoft.codewars.persistence.entities.UserInfo
import com.rukiasoft.codewars.persistence.utils.Converters

@Database(entities = [(UserInfo::class), (Language::class), (Skill::class)],
        version = 1)
@TypeConverters(Converters::class)
abstract class CodeWarsDatabase : RoomDatabase() {
    abstract fun userInfoDao(): UserInfoDao
    abstract fun languageDao(): LanguageDao
    abstract fun skillDao(): SkillDao
}
