package com.gk.dfm.repository.impl.serialize

import com.gk.dfm.repository.impl.NounDeclensionSet

/**
 * Created by Mr. President on 01.07.2016.
 */
class NounDeclensionSetSerializer extends AbstractInflectionSetSerializer<NounDeclensionSet> {

    NounDeclensionSetSerializer(String repositoryFilename) {
        super(repositoryFilename, NounDeclensionSet, "nouns")
    }

    @Override
    protected NounDeclensionSet createDefaultInflectionSet() {
        new NounDeclensionSet()
    }

}
