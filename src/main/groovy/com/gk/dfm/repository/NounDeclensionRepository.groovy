package com.gk.dfm.repository

import com.gk.dfm.domain.object.noun.german.Gender
import com.gk.dfm.domain.object.noun.german.NounDeclension
import com.gk.dfm.repository.impl.NounDeclensionFetcher
import com.gk.dfm.repository.impl.NounDeclensionSet
import com.gk.dfm.repository.impl.NounDeclensionSetSerializer

/**
 * Created by Mr. President on 6/30/2016.
 */
class NounDeclensionRepository implements AutoCloseable {

    private NounDeclensionFetcher nounDeclensionFetcher = new NounDeclensionFetcher()
    private NounDeclensionSetSerializer nounDeclensionSetSerializer

    private NounDeclensionSet declensionSet

    NounDeclensionRepository(String filename) {
        nounDeclensionSetSerializer = new NounDeclensionSetSerializer(filename)
        declensionSet = nounDeclensionSetSerializer.loadDeclensionSet()
    }

    NounDeclension getDeclension(Gender gender, String noun) {
        if (declensionSet.declensions.get(gender, noun) == null) {
            declensionSet.declensions.put(gender, noun, nounDeclensionFetcher.fetchDeclension(gender, noun))
        }
        return declensionSet.declensions.get(gender, noun)
    }

    @Override
    void close() throws Exception {
        nounDeclensionSetSerializer.saveDeclensionSet(declensionSet)
    }
}
