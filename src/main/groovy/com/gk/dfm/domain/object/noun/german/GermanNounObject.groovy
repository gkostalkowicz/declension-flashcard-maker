package com.gk.dfm.domain.object.noun.german

import com.gk.dfm.domain.Case
import com.gk.dfm.domain.object.GermanObject
import com.gk.dfm.domain.object.noun.ArticleType
import com.gk.dfm.domain.object.noun.ObjectNumber
import com.gk.dfm.domain.object.noun.ObjectNumberAndGender

/**
 * Created by Mr. President on 6/19/2016.
 */
class GermanNounObject implements GermanObject {

    GermanNoun noun
    ObjectNumber number
    ArticleType articleType

    String decline(Case objectCase) {
        def declinedArticle = articleType.decline(ObjectNumberAndGender.get(number, noun.gender), objectCase)
        def declinedNoun = noun.declension.get(number, objectCase)
        return declinedArticle + " " + declinedNoun
    }

}
