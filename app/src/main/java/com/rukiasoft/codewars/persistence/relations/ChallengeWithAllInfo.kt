package com.rukiasoft.codewars.persistence.relations

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import com.rukiasoft.codewars.persistence.entities.*

class ChallengeWithAllInfo {
    @Embedded
    var challenge: Challenge? = null

    @Relation(parentColumn = "id", entityColumn = "challenge_id")
    var challengeLanguageAuthored: MutableList<ChallengeLanguageAuthored>? = null

    @Relation(parentColumn = "id", entityColumn = "challenge_id")
    var challengeLanguageCompleted: MutableList<ChallengeLanguageCompleted>? = null

    @Relation(parentColumn = "id", entityColumn = "challenge_id")
    var tags: MutableList<ChallengeTag>? = null

}