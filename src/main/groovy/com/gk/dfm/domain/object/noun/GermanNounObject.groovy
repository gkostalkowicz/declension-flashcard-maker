package com.gk.dfm.domain.object.noun

import com.gk.dfm.domain.Case
import com.gk.dfm.domain.object.GermanObject

/**
 * Created by Mr. President on 6/19/2016.
 */
class GermanNounObject implements GermanObject {

    GermanNoun noun
    ObjectNumber number
    ArticleType articleType

    String decline(Case objectCase) {
        def declinedArticle = articleType.decline(ObjectNumberAndGender.get(number, noun.gender), objectCase)
        return declinedArticle + " " + noun.noun
    }

}
