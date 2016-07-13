package com.gk.dfm.logic.impl.source

import com.gk.dfm.domain.object.ObjectClass
import com.gk.dfm.domain.object.noun.Noun

/**
 * Created by Mr. President on 10.07.2016.
 */
class NounPicker {

    private List<Noun> allNouns
    private List<Noun> personNouns
    private List<Noun> thingNouns

    NounPicker(List<Noun> nouns) {
        this.allNouns = nouns
        this.personNouns = nouns.findAll { it.objectClass == ObjectClass.PERSON }
        this.thingNouns = nouns.findAll { it.objectClass == ObjectClass.THING }
    }

    Noun pick(ObjectClass objectClass) {
        def nouns = getNounList(objectClass)
        if (nouns.empty) {
            throw new RuntimeException("Empty noun list for object class $objectClass")
        }
        return RandomUtil.pickElement(nouns)
    }

    private List<Noun> getNounList(ObjectClass objectClass) {
        switch (objectClass) {
            case ObjectClass.PERSON:
                return personNouns
            case ObjectClass.THING:
                return thingNouns
            case ObjectClass.ANYTHING:
                return allNouns
            default:
                throw new RuntimeException("Unknown object class: $objectClass")
        }
    }

}
