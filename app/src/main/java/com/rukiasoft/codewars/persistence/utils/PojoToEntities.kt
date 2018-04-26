package com.rukiasoft.codewars.persistence.utils

import com.rukiasoft.codewars.persistence.entities.Language
import com.rukiasoft.codewars.persistence.entities.Skill
import com.rukiasoft.codewars.persistence.entities.UserInfo
import com.rukiasoft.codewars.persistence.relations.UserWithAllInfo
import com.rukiasoft.codewars.repository.UserInfoFromServer
import java.util.*

object PojoToEntities{
    fun getUserInfoFromServerResponse(server: UserInfoFromServer): UserWithAllInfo?{
        server.userName?.let {userName ->
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
}