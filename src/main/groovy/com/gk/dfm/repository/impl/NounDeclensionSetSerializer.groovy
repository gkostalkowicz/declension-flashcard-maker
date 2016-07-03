package com.gk.dfm.repository.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.gk.dfm.domain.object.noun.german.Gender
import com.gk.dfm.domain.object.noun.german.NounDeclension
import com.gk.dfm.util.Table
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created by Mr. President on 01.07.2016.
 */
class NounDeclensionSetSerializer {

    private static final Logger log = LoggerFactory.getLogger(NounDeclensionSetSerializer)

    private File repositoryFile
    private ObjectMapper mapper

    NounDeclensionSetSerializer(String repositoryFilename) {
        this.repositoryFile = new File(repositoryFilename)
        mapper = new ObjectMapper()
    }

    NounDeclensionSet loadDeclensionSet() {
        try {
            return mapper.readValue(repositoryFile, NounDeclensionSet)
        } catch (IOException e) {
            log.warn("Problem reading noun repository file; empty repository initialized.")
            log.debug("Error details.", e)
            return new NounDeclensionSet(declensions: new Table<Gender, String, NounDeclension>())
        }
    }

    void saveDeclensionSet(NounDeclensionSet declensionSet) {
        mapper.writeValue(repositoryFile, declensionSet)
    }

}
