package com.gk.dfm.domain.object.nounobject.german

import com.gk.dfm.domain.object.GermanObject
import com.gk.dfm.domain.object.noun.german.GermanNoun
import com.gk.dfm.domain.verb.german.objects.Case

/**
 * Created by Mr. President on 6/19/2016.
 */
class GermanNounObject implements GermanObject {

    GermanNoun noun
    ObjectNumber number
    ArticleType articleType

    String decline(Case objectCase) {
        def declinedArticle = articleType.decline(ArticleNumberAndGender.get(number, noun.gender), objectCase)
        def declinedNoun = noun.declension.get(number, objectCase)
        return declinedArticle + " " + declinedNoun
    }

}
