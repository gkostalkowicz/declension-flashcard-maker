package com.gk.dfm.repository.impl.serialize

import com.gk.dfm.repository.impl.VerbConjugationSet

/**
 * Created by Mr. President on 01.07.2016.
 */
class VerbConjugationSetSerializer extends AbstractInflectionSetSerializer<VerbConjugationSet> {

    VerbConjugationSetSerializer(String repositoryFilename) {
        super(repositoryFilename, VerbConjugationSet, "verbs")
    }

    @Override
    protected VerbConjugationSet createDefaultInflectionSet() {
        new VerbConjugationSet()
    }

}
