package com.gk.dfm.repository

import com.gk.dfm.domain.verb.german.conjugation.VerbConjugation
import com.gk.dfm.repository.impl.AbstractInflectionRepository
import com.gk.dfm.repository.impl.VerbConjugationSet
import com.gk.dfm.repository.impl.fetch.VerbConjugationFetcher
import com.gk.dfm.repository.impl.serialize.VerbConjugationSetSerializer

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
