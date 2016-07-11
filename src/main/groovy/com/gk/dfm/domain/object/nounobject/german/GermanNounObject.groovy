package com.gk.dfm.domain.object.nounobject.german

import com.gk.dfm.domain.object.GermanObject
import com.gk.dfm.domain.object.NumberAndGender
import com.gk.dfm.domain.object.adjective.german.DeclensionType
import com.gk.dfm.domain.object.adjective.german.GermanAdjective
import com.gk.dfm.domain.object.noun.german.GermanNoun
import com.gk.dfm.domain.object.nounobject.Determiner
import com.gk.dfm.domain.verb.german.objects.Case

/**
 * Created by Mr. President on 6/19/2016.
 */
class GermanNounObject implements GermanObject {

    GermanNoun noun
    ObjectNumber number
    Determiner determiner
    GermanAdjective adjective

    String decline(Case objectCase) {
        def numberAndGender = NumberAndGender.get(number, noun.gender)

        def optionalDeclinedDeterminer = GermanDeterminerDecliner.declineDeterminer(determiner,
                numberAndGender, objectCase)
        def declinedDeterminer = optionalDeclinedDeterminer.map({ it + " " }).orElse("")

        def declinedAdjective = adjective.declineBeforeNoun(DeclensionType.forDeterminer(determiner),
                numberAndGender, objectCase)
        def declinedNoun = noun.declension.get(number, objectCase)

        return declinedDeterminer + declinedAdjective + declinedNoun
    }

}
