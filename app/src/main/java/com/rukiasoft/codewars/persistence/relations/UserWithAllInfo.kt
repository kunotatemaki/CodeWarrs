package com.rukiasoft.codewars.persistence.relations

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Relation
import com.rukiasoft.codewars.persistence.entities.Language
import com.rukiasoft.codewars.persistence.entities.Skill
import com.rukiasoft.codewars.persistence.entities.UserInfo
import com.rukiasoft.codewars.utils.DateUtils
import java.util.Comparator
import kotlin.math.sign

class UserWithAllInfo {

    @Embedded
    var user: UserInfo? = null

    @Relation(parentColumn = "user_name", entityColumn = "user_name")
    var skils: MutableList<Skill>? = null

    @Relation(parentColumn = "user_name", entityColumn = "user_name")
    var languages: MutableList<Language>? = null

    @Ignore
    var bestLanguage: Language? = null

    fun getDate() = DateUtils.getDateFormatted(user?.lastFetchedInfo)

    fun getBestLanguageName(): String?{
        if(bestLanguage == null){
            calculateBestLanguage()
        }
        return bestLanguage?.languageName
    }

    fun getBestLanguageScore(): String?{
        if(bestLanguage == null){
            calculateBestLanguage()
        }
        return bestLanguage?.score?.toString()
    }


    private fun calculateBestLanguage() {
        bestLanguage = if (languages == null || languages!!.isEmpty()) {
            null
        }else {
            val ordered = languages!!.sortedWith(CompareLanguagesDescending)
            ordered.first()
        }
    }


    class CompareLanguagesDescending {

        companion object : Comparator<Language> {

            override fun compare(a: Language, b: Language): Int {
                if (b.score == null) return 1
                if (a.score == null) return -1
                val result = (a.score!! - b.score!!).toFloat()
                return -sign(result).toInt()
            }
        }
    }
}