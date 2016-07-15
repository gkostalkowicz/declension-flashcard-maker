package com.gk.dfm.domain.object.nounobject


import com.gk.dfm.domain.object.SentenceObject
import com.gk.dfm.domain.object.adjective.Adjective
import com.gk.dfm.domain.object.noun.Noun

/**
 * Created by Mr. President on 6/19/2016.
 */
class NounObject implements SentenceObject {

    Noun noun
    ObjectNumber number
    Determiner determiner
    Adjective adjective

}
