package com.gk.dfm.repository.impl

import com.gk.dfm.domain.object.noun.german.Gender
import com.gk.dfm.domain.object.nounobject.german.ObjectNumber
import com.gk.dfm.domain.verb.german.objects.Case
import org.junit.Test

/**
 * Created by Mr. President on 01.07.2016.
 */
class NounDeclensionFetcherTest {

    @Test
    void "given a noun and gender when fetchDeclensionMap then fetch correct declension"() {
        given:
        def noun = "Tag"
        def gender = Gender.MASCULINE
        def declensionFetcher = new NounDeclensionFetcher()

        when:
        def declension = declensionFetcher.fetchDeclension(gender, noun)

        then:
        assert declension.get(ObjectNumber.SINGULAR, Case.DATIVE) == "Tag"
        assert declension.get(ObjectNumber.PLURAL, Case.DATIVE) == "Tagen"
        assert declension.get(ObjectNumber.SINGULAR, Case.ACCUSATIVE) == "Tag"
        assert declension.get(ObjectNumber.PLURAL, Case.ACCUSATIVE) == "Tage"
    }

}
