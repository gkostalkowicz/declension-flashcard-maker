package com.gk.dfm.inflection.impl

import com.gk.dfm.inflection.impl.serialize.AbstractInflectionSetSerializer

/**
 * Created by Mr. President on 11.07.2016.
 */
class AbstractInflectionRepository<T> implements AutoCloseable {

    private AbstractInflectionSetSerializer<T> serializer
    protected final T inflectionSet

    AbstractInflectionRepository(AbstractInflectionSetSerializer<T> serializer) {
        this.serializer = serializer
        this.inflectionSet = serializer.loadInflectionSet()
    }

    @Override
    void close() throws Exception {
        serializer.saveInflectionSet(inflectionSet)
    }

}
