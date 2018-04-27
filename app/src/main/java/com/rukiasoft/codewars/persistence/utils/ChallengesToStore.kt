package com.rukiasoft.codewars.persistence.utils

import com.rukiasoft.codewars.persistence.entities.Challenge
import com.rukiasoft.codewars.persistence.entities.ChallengeLanguageAuthored
import com.rukiasoft.codewars.persistence.entities.ChallengeLanguageCompleted
import com.rukiasoft.codewars.persistence.entities.ChallengeTag

class ChallengesToStore {

    val challenge: MutableList<Challenge> = mutableListOf()

    val challengeLanguageAuthored: MutableList<ChallengeLanguageAuthored> = mutableListOf()

    val challengeLanguageCompleted: MutableList<ChallengeLanguageCompleted> = mutableListOf()

    val tags: MutableList<ChallengeTag> = mutableListOf()
}