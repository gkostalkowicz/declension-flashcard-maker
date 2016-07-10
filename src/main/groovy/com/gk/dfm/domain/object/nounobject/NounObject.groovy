package com.gk.dfm.domain.object.nounobject

import com.gk.dfm.domain.object.GermanObject
import com.gk.dfm.domain.object.PolishObject
import com.gk.dfm.domain.object.SentenceObject
import com.gk.dfm.domain.object.noun.Noun
import com.gk.dfm.domain.object.nounobject.german.GermanNounObject
import com.gk.dfm.domain.object.nounobject.german.ObjectNumber
import com.gk.dfm.domain.object.nounobject.polish.PolishNounObject

/**
 * Created by Mr. President on 6/19/2016.
 */
class NounObject implements SentenceObject {

    PolishNounObject polishNounObject
    GermanNounObject germanNounObject

    NounObject(Noun noun, Determiner determiner, ObjectNumber number) {
        polishNounObject = new PolishNounObject(noun: noun.polishNoun, number: number, determiner: determiner)
        germanNounObject = new GermanNounObject(noun: noun.germanNoun, number: number, determiner: determiner)
    }

    PolishObject getPolishObject() {
        polishNounObject
    }

    GermanObject getGermanObject() {
        germanNounObject
    }
}
