package com.gk.dfm.generate.impl.source

import com.gk.dfm.domain.object.nounobject.Determiner
import com.gk.dfm.domain.object.nounobject.ObjectNumber
import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table

/**
 * Created by Mr. President on 10.07.2016.
 */
class DeterminerPicker {

    private Table<DeterminerGroup, ObjectNumber, List<Determiner>> determinerListTable = HashBasedTable.create()

    DeterminerPicker(Map<DeterminerGroup, Double> determinerGroupToChance) {
        for (def group : determinerGroupToChance.keySet()) {
            determinerListTable.put(group, ObjectNumber.SINGULAR,
                    group.determiners.findAll({ it.hasSingularForm }))
            determinerListTable.put(group, ObjectNumber.PLURAL,
                    group.determiners.findAll({ it.hasPluralForm }))
        }
    }

    PickedDeterminer pick(DeterminerGroup group, ObjectNumber suggestedNumber) {
        def list = determinerListTable.get(group, suggestedNumber)
        if (list == null) {
            throw new RuntimeException("Unknown combination of number '$suggestedNumber' and determiner group" +
                    " '$group'")
        } else if (!list.empty) {
            return new PickedDeterminer(RandomUtil.pickElement(list), suggestedNumber)
        } else {
            def invertedNumber = suggestedNumber.inverted()
            def invertedNumberList = determinerListTable.get(group, invertedNumber)
            if (!invertedNumberList.empty) {
                return new PickedDeterminer(RandomUtil.pickElement(invertedNumberList), invertedNumber)
            } else {
                throw new RuntimeException("Empty determiner group: $group")
            }
        }
    }

    private static class PickedDeterminer {
        Determiner determiner
        ObjectNumber number

        PickedDeterminer(Determiner determiner, ObjectNumber number) {
            this.determiner = determiner
            this.number = number
        }
    }

}
