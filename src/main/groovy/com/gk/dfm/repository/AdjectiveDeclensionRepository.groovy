package com.gk.dfm.repository

import com.gk.dfm.domain.object.adjective.german.AdjectiveDeclension
import com.gk.dfm.repository.impl.AbstractInflectionRepository
import com.gk.dfm.repository.impl.AdjectiveDeclensionSet
import com.gk.dfm.repository.impl.fetch.AdjectiveDeclensionFetcher
import com.gk.dfm.repository.impl.serialize.AdjectiveDeclensionSetSerializer

/**
 * Created by Mr. President on 11.07.2016.
 */
class AdjectiveDeclensionRepository extends AbstractInflectionRepository<AdjectiveDeclensionSet> {

    private AdjectiveDeclensionFetcher fetcher = new AdjectiveDeclensionFetcher()

    AdjectiveDeclensionRepository(String filename) {
        super(new AdjectiveDeclensionSetSerializer(filename))
    }

    AdjectiveDeclension getDeclension(String adjective) {
        if (inflectionSet.declensions[adjective] == null) {
            inflectionSet.declensions[adjective] = fetcher.fetchDeclension(adjective)
        }
        return inflectionSet.declensions[adjective]
    }

}
