package com.gk.dfm.domain.verb.german.conjugation

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by Mr. President on 03.07.2016.
 */
class VerbConjugation {

    @JsonProperty
    private Map<ConjugationPerson, ConjugatedVerb> conjugatedVerbByPerson = new HashMap<>()

    ConjugatedVerb get(ConjugationPerson person) {
        def conjugatedVerb = conjugatedVerbByPerson.get(person)
        if (conjugatedVerb == null) {
            throw new RuntimeException("Couldn't get declined verb for person $person")
        }
        return conjugatedVerb
    }

    void put(ConjugationPerson person, ConjugatedVerb conjugatedVerb) {
        conjugatedVerbByPerson.put(person, conjugatedVerb)
    }

}
