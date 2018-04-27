package com.rukiasoft.codewars.persistence.utils

import com.rukiasoft.codewars.persistence.entities.*
import com.rukiasoft.codewars.persistence.relations.UserWithAllInfo
import com.rukiasoft.codewars.repository.ChallengeFromServer
import com.rukiasoft.codewars.repository.UserInfoFromServer
import java.util.*

object PojoToEntities {
    fun getUserInfoFromServerResponse(server: UserInfoFromServer): UserWithAllInfo? {
        server.userName?.let { userName ->
            val userWithAllInfo = UserWithAllInfo()
            userWithAllInfo.user = UserInfo(server, Date(System.currentTimeMillis()))

            val skills = mutableListOf<Skill>()
            server.skills?.let {
                it.forEach({
                    val skill = Skill(null, it, userName)
                    skills.add(skill)
                })
            }
            userWithAllInfo.skils = skills

            val languages = mutableListOf<Language>()
            server.ranks?.languages?.let {
                it.forEach({
                    val language = Language(null, userName, it.key, it.value.rank, it.value.name,
                            it.value.color, it.value.score)
                    languages.add(language)
                })
            }
            userWithAllInfo.languages = languages
            return userWithAllInfo
        }
        return null
    }

    fun getChallengeFromServerResponse(server: ChallengeFromServer, userName: String, authored: Boolean): ChallengesToStore {
        val challengesToStore = ChallengesToStore()

        server.totalItems?.let { challengesToStore.items = it }
        server.totalPages?.let { challengesToStore.pages = it }

        server.data?.let { challenges ->
            challenges.forEach({

                val challenge = Challenge(it, userName, authored)
                challengesToStore.challenge.add(challenge)

                it.languages?.let {
                    challengesToStore.challengeLanguageAuthored.addAll(it.map { ChallengeLanguageAuthored(null, it, challenge.id) })

                }
                it.completedLanguages?.let {
                    challengesToStore.challengeLanguageCompleted.addAll(it.map { ChallengeLanguageCompleted(null, it, challenge.id) })
                }
                it.tags?.let {
                    challengesToStore.tags.addAll(it.map { ChallengeTag(null, it, challenge.id) })
                }

            })
        }
        return challengesToStore
    }
}