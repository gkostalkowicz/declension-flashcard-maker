package com.gk.dfm.domain.object.nounobject.german

import com.gk.dfm.domain.object.GermanObject
import com.gk.dfm.domain.object.adjective.german.GermanAdjective
import com.gk.dfm.domain.object.noun.german.GermanNoun
import com.gk.dfm.domain.object.nounobject.Determiner

/**
 * Created by Mr. President on 6/19/2016.
 */
class GermanNounObject implements GermanObject {

    GermanNoun noun
    ObjectNumber number
    Determiner determiner
    GermanAdjective adjective

}
