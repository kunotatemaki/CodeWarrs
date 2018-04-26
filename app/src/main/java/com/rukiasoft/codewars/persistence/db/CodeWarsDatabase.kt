package com.rukiasoft.codewars.persistence.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.rukiasoft.codewars.persistence.daos.*
import com.rukiasoft.codewars.persistence.entities.*
import com.rukiasoft.codewars.persistence.utils.Converters

@Database(entities = [(UserInfo::class), (Language::class), (Skill::class),
    (Challenge::class), (ChallengeLanguageAuthored::class), (ChallengeLanguageCompleted::class),
    (ChallengeTag::class)],
        version = 1)
@TypeConverters(Converters::class)
abstract class CodeWarsDatabase : RoomDatabase() {
    abstract fun userInfoDao(): UserInfoDao
    abstract fun languageDao(): LanguageDao
    abstract fun skillDao(): SkillDao
    abstract fun challengeDao(): ChallengeDao
    abstract fun challengeTagDao(): ChallengeTagDao
    abstract fun challengeLanguageCompletedDao(): ChallengeLanguageCompletedDao
    abstract fun challengeLanguageAuthoredDao(): ChallengeLanguageAuthoredDao
}
