package com.gk.dfm.inflection

import com.gk.dfm.domain.verb.german.conjugation.VerbConjugation
import com.gk.dfm.inflection.impl.AbstractInflectionRepository
import com.gk.dfm.inflection.impl.VerbConjugationSet
import com.gk.dfm.inflection.impl.fetch.VerbConjugationFetcher
import com.gk.dfm.inflection.impl.serialize.VerbConjugationSetSerializer

/**
 * Created by Mr. President on 6/30/2016.
 */
class VerbConjugationRepository extends AbstractInflectionRepository<VerbConjugationSet> {

    private VerbConjugationFetcher fetcher = new VerbConjugationFetcher()

    VerbConjugationRepository(String filename) {
        super(new VerbConjugationSetSerializer(filename))
    }

    VerbConjugation conjugateVerb(String infinitive) {
        if (inflectionSet.conjugations[infinitive] == null) {
            inflectionSet.conjugations[infinitive] = fetcher.fetchConjugation(infinitive)
        }
        return inflectionSet.conjugations[infinitive]
    }

}
