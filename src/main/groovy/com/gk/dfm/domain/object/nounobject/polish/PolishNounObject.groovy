package com.gk.dfm.domain.object.nounobject.polish

import com.gk.dfm.domain.object.PolishObject
import com.gk.dfm.domain.object.noun.polish.PolishNoun
import com.gk.dfm.domain.object.nounobject.german.ObjectNumber

/**
 * Created by Mr. President on 6/19/2016.
 */
class PolishNounObject implements PolishObject {

    PolishNoun noun
    ObjectNumber number

    String decline() {
        number == ObjectNumber.SINGULAR ? noun.noun : "wiele " + noun.noun
    }

}
