package com.gk.dfm.inflection

import com.gk.dfm.domain.object.noun.german.Gender
import com.gk.dfm.domain.object.noun.german.NounDeclension
import com.gk.dfm.inflection.impl.AbstractInflectionRepository
import com.gk.dfm.inflection.impl.NounDeclensionSet
import com.gk.dfm.inflection.impl.fetch.NounDeclensionFetcher
import com.gk.dfm.inflection.impl.serialize.NounDeclensionSetSerializer

/**
 * Created by Mr. President on 6/30/2016.
 */
class NounDeclensionRepository extends AbstractInflectionRepository<NounDeclensionSet> {

    private NounDeclensionFetcher nounDeclensionFetcher = new NounDeclensionFetcher()

    NounDeclensionRepository(String filename) {
        super(new NounDeclensionSetSerializer(filename))
    }

    NounDeclension getDeclension(Gender gender, String noun) {
        if (inflectionSet.declensions.get(gender, noun) == null) {
            inflectionSet.declensions.put(gender, noun, nounDeclensionFetcher.fetchDeclension(gender, noun))
        }
        return inflectionSet.declensions.get(gender, noun)
    }

}
