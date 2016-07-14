package com.gk.dfm.inflection.impl

import com.gk.dfm.domain.object.NumberAndGender
import com.gk.dfm.domain.object.adjective.german.DeclensionType
import com.gk.dfm.domain.verb.german.objects.Case
import com.gk.dfm.inflection.impl.fetch.AdjectiveDeclensionFetcher
import org.junit.Test

/**
 * Created by Mr. President on 11.07.2016.
 */
class AdjectiveDeclensionFetcherTest {

    @Test
    void"given an adjective when fetchDeclension then fetch correct declension"() {
        given:
        def adjective = "blau"
        def declensionFetcher = new AdjectiveDeclensionFetcher()

        when:
        def declension = declensionFetcher.fetchDeclension(adjective)

        then:
        assert declension.get(DeclensionType.STRONG, NumberAndGender.MASCULINE_SINGULAR, Case.NOMINATIVE) == "blauer"
        assert declension.get(DeclensionType.STRONG, NumberAndGender.MASCULINE_SINGULAR, Case.ACCUSATIVE) == "blauen"
        assert declension.get(DeclensionType.STRONG, NumberAndGender.MASCULINE_SINGULAR, Case.DATIVE) == "blauem"
        assert declension.get(DeclensionType.STRONG, NumberAndGender.MASCULINE_SINGULAR, Case.GENITIVE) == "blauen"

        assert declension.get(DeclensionType.STRONG, NumberAndGender.NEUTER_SINGULAR, Case.NOMINATIVE) == "blaues"
        assert declension.get(DeclensionType.STRONG, NumberAndGender.NEUTER_SINGULAR, Case.ACCUSATIVE) == "blaues"
        assert declension.get(DeclensionType.STRONG, NumberAndGender.NEUTER_SINGULAR, Case.DATIVE) == "blauem"
        assert declension.get(DeclensionType.STRONG, NumberAndGender.NEUTER_SINGULAR, Case.GENITIVE) == "blauen"

        assert declension.get(DeclensionType.STRONG, NumberAndGender.FEMININE_SINGULAR, Case.NOMINATIVE) == "blaue"
        assert declension.get(DeclensionType.STRONG, NumberAndGender.FEMININE_SINGULAR, Case.ACCUSATIVE) == "blaue"
        assert declension.get(DeclensionType.STRONG, NumberAndGender.FEMININE_SINGULAR, Case.DATIVE) == "blauer"
        assert declension.get(DeclensionType.STRONG, NumberAndGender.FEMININE_SINGULAR, Case.GENITIVE) == "blauer"

        assert declension.get(DeclensionType.STRONG, NumberAndGender.PLURAL, Case.NOMINATIVE) == "blaue"
        assert declension.get(DeclensionType.STRONG, NumberAndGender.PLURAL, Case.ACCUSATIVE) == "blaue"
        assert declension.get(DeclensionType.STRONG, NumberAndGender.PLURAL, Case.DATIVE) == "blauen"
        assert declension.get(DeclensionType.STRONG, NumberAndGender.PLURAL, Case.GENITIVE) == "blauer"

        assert declension.get(DeclensionType.WEAK, NumberAndGender.MASCULINE_SINGULAR, Case.NOMINATIVE) == "blaue"
        assert declension.get(DeclensionType.WEAK, NumberAndGender.MASCULINE_SINGULAR, Case.ACCUSATIVE) == "blauen"
        assert declension.get(DeclensionType.WEAK, NumberAndGender.MASCULINE_SINGULAR, Case.DATIVE) == "blauen"
        assert declension.get(DeclensionType.WEAK, NumberAndGender.MASCULINE_SINGULAR, Case.GENITIVE) == "blauen"

        assert declension.get(DeclensionType.WEAK, NumberAndGender.NEUTER_SINGULAR, Case.NOMINATIVE) == "blaue"
        assert declension.get(DeclensionType.WEAK, NumberAndGender.NEUTER_SINGULAR, Case.ACCUSATIVE) == "blaue"
        assert declension.get(DeclensionType.WEAK, NumberAndGender.NEUTER_SINGULAR, Case.DATIVE) == "blauen"
        assert declension.get(DeclensionType.WEAK, NumberAndGender.NEUTER_SINGULAR, Case.GENITIVE) == "blauen"

        assert declension.get(DeclensionType.WEAK, NumberAndGender.FEMININE_SINGULAR, Case.NOMINATIVE) == "blaue"
        assert declension.get(DeclensionType.WEAK, NumberAndGender.FEMININE_SINGULAR, Case.ACCUSATIVE) == "blaue"
        assert declension.get(DeclensionType.WEAK, NumberAndGender.FEMININE_SINGULAR, Case.DATIVE) == "blauen"
        assert declension.get(DeclensionType.WEAK, NumberAndGender.FEMININE_SINGULAR, Case.GENITIVE) == "blauen"

        assert declension.get(DeclensionType.WEAK, NumberAndGender.PLURAL, Case.NOMINATIVE) == "blauen"
        assert declension.get(DeclensionType.WEAK, NumberAndGender.PLURAL, Case.ACCUSATIVE) == "blauen"
        assert declension.get(DeclensionType.WEAK, NumberAndGender.PLURAL, Case.DATIVE) == "blauen"
        assert declension.get(DeclensionType.WEAK, NumberAndGender.PLURAL, Case.GENITIVE) == "blauen"

        assert declension.get(DeclensionType.MIXED, NumberAndGender.MASCULINE_SINGULAR, Case.NOMINATIVE) == "blauer"
        assert declension.get(DeclensionType.MIXED, NumberAndGender.MASCULINE_SINGULAR, Case.ACCUSATIVE) == "blauen"
        assert declension.get(DeclensionType.MIXED, NumberAndGender.MASCULINE_SINGULAR, Case.DATIVE) == "blauen"
        assert declension.get(DeclensionType.MIXED, NumberAndGender.MASCULINE_SINGULAR, Case.GENITIVE) == "blauen"

        assert declension.get(DeclensionType.MIXED, NumberAndGender.NEUTER_SINGULAR, Case.NOMINATIVE) == "blaues"
        assert declension.get(DeclensionType.MIXED, NumberAndGender.NEUTER_SINGULAR, Case.ACCUSATIVE) == "blaues"
        assert declension.get(DeclensionType.MIXED, NumberAndGender.NEUTER_SINGULAR, Case.DATIVE) == "blauen"
        assert declension.get(DeclensionType.MIXED, NumberAndGender.NEUTER_SINGULAR, Case.GENITIVE) == "blauen"

        assert declension.get(DeclensionType.MIXED, NumberAndGender.FEMININE_SINGULAR, Case.NOMINATIVE) == "blaue"
        assert declension.get(DeclensionType.MIXED, NumberAndGender.FEMININE_SINGULAR, Case.ACCUSATIVE) == "blaue"
        assert declension.get(DeclensionType.MIXED, NumberAndGender.FEMININE_SINGULAR, Case.DATIVE) == "blauen"
        assert declension.get(DeclensionType.MIXED, NumberAndGender.FEMININE_SINGULAR, Case.GENITIVE) == "blauen"

        assert declension.get(DeclensionType.MIXED, NumberAndGender.PLURAL, Case.NOMINATIVE) == "blauen"
        assert declension.get(DeclensionType.MIXED, NumberAndGender.PLURAL, Case.ACCUSATIVE) == "blauen"
        assert declension.get(DeclensionType.MIXED, NumberAndGender.PLURAL, Case.DATIVE) == "blauen"
        assert declension.get(DeclensionType.MIXED, NumberAndGender.PLURAL, Case.GENITIVE) == "blauen"
    }

}
