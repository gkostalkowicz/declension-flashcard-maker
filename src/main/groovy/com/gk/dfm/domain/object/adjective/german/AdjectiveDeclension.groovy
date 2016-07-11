package com.gk.dfm.domain.object.adjective.german

import com.fasterxml.jackson.annotation.JsonProperty
import com.gk.dfm.domain.object.NumberAndGender
import com.gk.dfm.domain.verb.german.objects.Case
import com.gk.dfm.util.Table

/**
 * Created by Mr. President on 10.07.2016.
 */
class AdjectiveDeclension {

    @JsonProperty
    private Map<DeclensionType, Table<NumberAndGender, Case, String>> declensionMap =
            [(DeclensionType.STRONG): new Table(),
             (DeclensionType.WEAK)  : new Table(),
             (DeclensionType.MIXED) : new Table()]

    String get(DeclensionType declensionType, NumberAndGender numberAndGender, Case objectCase) {
        def declinedAdjective = declensionMap[declensionType].get(numberAndGender, objectCase)
        if (declinedAdjective == null) {
            throw new RuntimeException("Couldn't get declined adjective for $declensionType declension type, " +
                    "$numberAndGender number and gender, and $objectCase case")
        }
        return declinedAdjective
    }

    void put(DeclensionType declensionType, NumberAndGender numberAndGender, Case objectCase,
             String declinedAdjective) {
        declensionMap[declensionType].put(numberAndGender, objectCase, declinedAdjective)
    }

}
