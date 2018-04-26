package com.rukiasoft.codewars.persistence.relations

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import com.rukiasoft.codewars.persistence.entities.Language
import com.rukiasoft.codewars.persistence.entities.Skill
import com.rukiasoft.codewars.persistence.entities.UserInfo

class UserWithAllInfo {
    @Embedded
    var user: UserInfo? = null

    @Relation(parentColumn = "user_name", entityColumn = "user_name")
    var skils: MutableList<Skill>? = null

    @Relation(parentColumn = "user_name", entityColumn = "user_name")
    var languages: MutableList<Language>? = null

}