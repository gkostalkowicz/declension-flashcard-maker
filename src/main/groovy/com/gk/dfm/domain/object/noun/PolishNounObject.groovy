package com.gk.dfm.domain.object.noun

import com.gk.dfm.domain.object.PolishObject

/**
 * Created by Mr. President on 6/19/2016.
 */
class PolishNounObject implements PolishObject {

    PolishNoun noun

    String decline() {
        noun.noun
    }

}
