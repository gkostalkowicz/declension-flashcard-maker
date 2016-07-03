package com.gk.dfm.repository.impl

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created by Mr. President on 01.07.2016.
 */
class VerbConjugationSetSerializer {

    private static final Logger log = LoggerFactory.getLogger(VerbConjugationSetSerializer)

    private File repositoryFile
    private ObjectMapper mapper

    VerbConjugationSetSerializer(String repositoryFilename) {
        this.repositoryFile = new File(repositoryFilename)
        mapper = new ObjectMapper()
    }

    VerbConjugationSet loadConjugationSet() {
        try {
            return mapper.readValue(repositoryFile, VerbConjugationSet)
        } catch (IOException e) {
            log.warn("Problem reading verb repository file; empty repository initialized.")
            log.debug("Error details.", e)
            return new VerbConjugationSet(conjugations: new HashMap<>())
        }
    }

    void saveConjugationSet(VerbConjugationSet conjugationSet) {
        mapper.writeValue(repositoryFile, conjugationSet)
    }

}
