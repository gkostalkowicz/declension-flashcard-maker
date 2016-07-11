package com.gk.dfm.repository.impl.serialize

import com.gk.dfm.repository.impl.AdjectiveDeclensionSet

/**
 * Created by Mr. President on 01.07.2016.
 */
class AdjectiveDeclensionSetSerializer extends AbstractInflectionSetSerializer<AdjectiveDeclensionSet> {

    AdjectiveDeclensionSetSerializer(String repositoryFilename) {
        super(repositoryFilename, AdjectiveDeclensionSet, "adjectives")
    }

    @Override
    protected AdjectiveDeclensionSet createDefaultInflectionSet() {
        new AdjectiveDeclensionSet()
    }

}
