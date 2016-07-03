package com.gk.dfm.repository

import com.gk.dfm.domain.verb.german.conjugation.VerbConjugation
import com.gk.dfm.repository.impl.VerbConjugationFetcher
import com.gk.dfm.repository.impl.VerbConjugationSet
import com.gk.dfm.repository.impl.VerbConjugationSetSerializer

/**
 * Created by Mr. President on 6/30/2016.
 */
class VerbConjugationRepository implements AutoCloseable {

    private VerbConjugationFetcher verbConjugationFetcher = new VerbConjugationFetcher()
    private VerbConjugationSetSerializer verbConjugationSetSerializer

    private VerbConjugationSet conjugationSet

    VerbConjugationRepository(String filename) {
        verbConjugationSetSerializer = new VerbConjugationSetSerializer(filename)
        conjugationSet = verbConjugationSetSerializer.loadConjugationSet()
    }

    VerbConjugation conjugateVerb(String infinitive) {
        if (conjugationSet.conjugations.get(infinitive) == null) {
            conjugationSet.conjugations.put(infinitive, verbConjugationFetcher.fetchConjugation(infinitive))
        }
        return conjugationSet.conjugations.get(infinitive)
    }

    @Override
    void close() throws Exception {
        verbConjugationSetSerializer.saveConjugationSet(conjugationSet)
    }
}
