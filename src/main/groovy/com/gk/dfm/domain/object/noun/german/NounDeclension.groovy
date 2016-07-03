package com.gk.dfm.domain.object.noun.german

import com.fasterxml.jackson.annotation.JsonProperty
import com.gk.dfm.domain.object.nounobject.german.ObjectNumber
import com.gk.dfm.domain.verb.german.objects.Case
import com.gk.dfm.util.Table

/**
 * Created by Mr. President on 01.07.2016.
 */
class NounDeclension {

    @JsonProperty
    private Table<ObjectNumber, Case, String> declensionMap = new Table()

    String get(ObjectNumber declensionNumber, Case declensionCase) {
        def declinedNoun = declensionMap.get(declensionNumber, declensionCase)
        if (declinedNoun == null) {
            throw new RuntimeException("Couldn't get declined noun for number $declensionNumber and case" +
                    " $declensionCase")
        }
        return declinedNoun
    }

    void put(ObjectNumber declensionNumber, Case declensionCase, String declinedNoun) {
        declensionMap.put(declensionNumber, declensionCase, declinedNoun)
    }

}
