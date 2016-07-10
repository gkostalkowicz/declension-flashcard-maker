package com.gk.dfm.domain.object.nounobject.polish

import com.gk.dfm.domain.object.PolishObject
import com.gk.dfm.domain.object.noun.polish.PolishNoun
import com.gk.dfm.domain.object.nounobject.Determiner
import com.gk.dfm.domain.object.nounobject.german.ObjectNumber

/**
 * Created by Mr. President on 6/19/2016.
 */
class PolishNounObject implements PolishObject {

    PolishNoun noun
    ObjectNumber number
    Determiner determiner

    String decline() {
        def numberPrefix = number == ObjectNumber.PLURAL ? "wiele " : ""
        def declinedDeterminer = PolishDeterminerDecliner.declineDeterminer(determiner)
        return numberPrefix + declinedDeterminer + " " + noun.noun
    }

}
