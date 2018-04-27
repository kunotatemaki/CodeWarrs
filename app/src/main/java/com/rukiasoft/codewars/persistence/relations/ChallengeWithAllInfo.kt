package com.rukiasoft.codewars.persistence.relations

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import com.rukiasoft.codewars.persistence.entities.Challenge
import com.rukiasoft.codewars.persistence.entities.ChallengeLanguageAuthored
import com.rukiasoft.codewars.persistence.entities.ChallengeLanguageCompleted
import com.rukiasoft.codewars.persistence.entities.ChallengeTag
import com.rukiasoft.codewars.utils.DateUtils

class ChallengeWithAllInfo {
    @Embedded
    var challenge: Challenge? = null

    @Relation(parentColumn = "id", entityColumn = "challenge_id")
    var challengeLanguageAuthored: MutableList<ChallengeLanguageAuthored>? = null

    @Relation(parentColumn = "id", entityColumn = "challenge_id")
    var challengeLanguageCompleted: MutableList<ChallengeLanguageCompleted>? = null

    @Relation(parentColumn = "id", entityColumn = "challenge_id")
    var tags: MutableList<ChallengeTag>? = null

    fun getDate() = DateUtils.getDateFormatted(challenge?.completedAt)

    fun getTagsInString(): String {
        var sTags = ""
        tags?.forEach {
            sTags += it.name
            if (it != tags!!.last()) {
                sTags += ", "
            }
        }
        return sTags
    }

    fun hasTags() =  !(tags == null || tags!!.isEmpty())

    fun isCompleted() = challenge?.authored?.not() ?: false
    fun isAuthored() = challenge?.authored ?: false

    fun getLanguagesAuthored(): String {
        var languages = ""
        challengeLanguageAuthored?.forEach {
            languages += it.name
            if (it != challengeLanguageAuthored!!.last()) {
                languages += ", "
            }
        }
        return languages
    }

    fun hasLanguagesAuthored(): Boolean {
        return !(challengeLanguageAuthored == null || challengeLanguageAuthored!!.isEmpty()) && challenge!!.authored!!
    }

    fun getLanguagesCompleted(): String {
        var languages = ""
        challengeLanguageCompleted?.forEach {
            languages += it.name
            if (it != challengeLanguageCompleted!!.last()) {
                languages += ", "
            }
        }
        return languages
    }

    fun hasLanguagesCompleted(): Boolean {
        return !(challengeLanguageCompleted == null || challengeLanguageCompleted!!.isEmpty()) && !challenge!!.authored!!
    }

    @Suppress("DEPRECATION")
    fun getDescription(): String? {
        //todo modify the text to get it formatted
        return challenge?.description
    }

}