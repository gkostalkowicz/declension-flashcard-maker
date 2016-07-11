package com.gk.dfm.repository.impl.serialize

import com.fasterxml.jackson.databind.ObjectMapper
import com.gk.dfm.util.CharsetConstants
import groovy.util.logging.Slf4j

/**
 * Created by Mr. President on 11.07.2016.
 */
@Slf4j
abstract class AbstractInflectionSetSerializer<T> {

    private Class<T> inflectionSetClass
    private File repositoryFile
    private ObjectMapper mapper = new ObjectMapper()
    private String elementsName

    AbstractInflectionSetSerializer(String repositoryFilename, Class<T> inflectionSetClass, String elementsName) {
        this.repositoryFile = new File(repositoryFilename)
        this.inflectionSetClass = inflectionSetClass
        this.elementsName = elementsName
    }

    T loadInflectionSet() {
        try {
            def reader = new BufferedReader(new InputStreamReader(new FileInputStream(repositoryFile),
                    CharsetConstants.FILE_CHARSET_NAME))
            return mapper.readValue(reader, inflectionSetClass)
        } catch (IOException e) {
            log.warn("Problem reading repository file with '{}'; empty repository initialized.", elementsName)
            log.debug("Error details.", e)
            return createDefaultInflectionSet()
        }
    }

    void saveInflectionSet(T inflectionSet) {
        def writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(repositoryFile),
                CharsetConstants.FILE_CHARSET_NAME))
        mapper.writeValue(writer, inflectionSet)
    }

    protected abstract T createDefaultInflectionSet();

}
