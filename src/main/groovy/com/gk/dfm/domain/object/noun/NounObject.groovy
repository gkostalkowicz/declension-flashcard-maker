package com.gk.dfm.domain.object.noun

import com.gk.dfm.domain.object.GermanObject
import com.gk.dfm.domain.object.PolishObject
import com.gk.dfm.domain.object.SentenceObject
import com.gk.dfm.domain.object.noun.german.GermanNounObject
import com.gk.dfm.domain.object.noun.polish.PolishNounObject

/**
 * Created by Mr. President on 6/19/2016.
 */
class NounObject implements SentenceObject {

    PolishNounObject polishNounObject
    GermanNounObject germanNounObject

    NounObject(Noun noun, ArticleType articleType, ObjectNumber number) {
        polishNounObject = new PolishNounObject(noun: noun.polishNoun)
        germanNounObject = new GermanNounObject(noun: noun.germanNoun, number: number, articleType: articleType)
    }

    PolishObject getPolishObject() {
        polishNounObject
    }

    GermanObject getGermanObject() {
        germanNounObject
    }
}
