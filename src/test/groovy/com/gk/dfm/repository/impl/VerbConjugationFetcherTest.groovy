package com.gk.dfm.repository.impl

import com.gk.dfm.domain.verb.german.conjugation.ConjugationPerson
import com.gk.dfm.repository.impl.fetch.VerbConjugationFetcher
import org.junit.Test

/**
 * Created by Mr. President on 03.07.2016.
 */
class VerbConjugationFetcherTest {

    VerbConjugationFetcher fetcher = new VerbConjugationFetcher()

    @Test
    void "given a verb when fetchConjugation then fetch correct conjugation"() {
        given:
        def verb = "gehen"

        when:
        def conjugatedVerb = fetcher.fetchConjugation(verb)

        then:
        assert conjugatedVerb.get(ConjugationPerson.SINGULAR_1ST).particle == null
        assert conjugatedVerb.get(ConjugationPerson.SINGULAR_2ND).particle == null
        assert conjugatedVerb.get(ConjugationPerson.SINGULAR_3RD).particle == null
        assert conjugatedVerb.get(ConjugationPerson.PLURAL_1ST).particle == null
        assert conjugatedVerb.get(ConjugationPerson.PLURAL_2ND).particle == null
        assert conjugatedVerb.get(ConjugationPerson.PLURAL_3RD).particle == null

        assert conjugatedVerb.get(ConjugationPerson.SINGULAR_1ST).coreVerb == "gehe"
        assert conjugatedVerb.get(ConjugationPerson.SINGULAR_2ND).coreVerb == "gehst"
        assert conjugatedVerb.get(ConjugationPerson.SINGULAR_3RD).coreVerb == "geht"
        assert conjugatedVerb.get(ConjugationPerson.PLURAL_1ST).coreVerb == "gehen"
        assert conjugatedVerb.get(ConjugationPerson.PLURAL_2ND).coreVerb == "geht"
        assert conjugatedVerb.get(ConjugationPerson.PLURAL_3RD).coreVerb == "gehen"
    }

    @Test
    void "given a verb with alternative conjugation when fetchVerbConjugation then fetch correct conjugation"() {
        given:
        def verb = "backen"

        when:
        def conjugatedVerb = fetcher.fetchConjugation(verb)

        then:
        assert conjugatedVerb.get(ConjugationPerson.SINGULAR_1ST).particle == null
        assert conjugatedVerb.get(ConjugationPerson.SINGULAR_2ND).particle == null

        assert conjugatedVerb.get(ConjugationPerson.SINGULAR_1ST).coreVerb == "backe"
        assert conjugatedVerb.get(ConjugationPerson.SINGULAR_2ND).coreVerb == "bäckst"
    }

    @Test
    void "given a separable verb when fetchVerbConjugation then fetch correct conjugation"() {
        given:
        def verb = "aufhören"

        when:
        def conjugatedVerb = fetcher.fetchConjugation(verb)

        then:
        assert conjugatedVerb.get(ConjugationPerson.SINGULAR_1ST).particle == "auf"
        assert conjugatedVerb.get(ConjugationPerson.SINGULAR_2ND).particle == "auf"

        assert conjugatedVerb.get(ConjugationPerson.SINGULAR_1ST).coreVerb == "höre"
        assert conjugatedVerb.get(ConjugationPerson.SINGULAR_2ND).coreVerb == "hörst"
    }

}
