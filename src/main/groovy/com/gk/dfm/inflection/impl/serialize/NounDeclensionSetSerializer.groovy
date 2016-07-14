package com.gk.dfm.inflection.impl.serialize

import com.gk.dfm.inflection.impl.NounDeclensionSet

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
