package com.gk.dfm.logic.impl

import com.gk.dfm.domain.object.nounobject.Determiner
import com.gk.dfm.domain.object.nounobject.german.ObjectNumber
import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table

/**
 * Created by Mr. President on 10.07.2016.
 */
class DeterminerPicker {

    private Table<ObjectNumber, Boolean, List<Determiner>> determinerListTable = HashBasedTable.create()

    DeterminerPicker() {
        def determiners = Determiner.values()
        determinerListTable.put(ObjectNumber.SINGULAR, false,
                determiners.findAll { it -> it.hasSingularForm && !it.possessivePronoun})
        determinerListTable.put(ObjectNumber.SINGULAR, true,
                determiners.findAll { it -> it.hasSingularForm && it.possessivePronoun})
        determinerListTable.put(ObjectNumber.PLURAL, false,
                determiners.findAll { it -> it.hasPluralForm && !it.possessivePronoun})
        determinerListTable.put(ObjectNumber.PLURAL, true,
                determiners.findAll { it -> it.hasPluralForm && it.possessivePronoun})
    }

    Determiner pick(ObjectNumber number, boolean possessivePronoun) {
        def list = determinerListTable.get(number, possessivePronoun)
        if (list == null) {
            throw new RuntimeException("Unknown combination of number '$number' and possesive pronoun" +
                    " '$possessivePronoun'")
        } else if (list.empty) {
            throw new RuntimeException("No determiners for number '$number' and possesive pronoun" +
                    " '$possessivePronoun'")
        }
        return RandomUtil.pickElement(list)
    }

}
