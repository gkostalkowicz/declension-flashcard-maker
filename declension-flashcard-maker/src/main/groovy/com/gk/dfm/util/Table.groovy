package com.gk.dfm.util

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * A simple version of {@link com.google.common.collect.Table}, supporting serialization and deserialization for
 * Jackson.
 *
 * Guava's Table has currently only serialization support, but no deserialization yet. Use this class to avoid
 * converting objects to be serialized to a Map of Maps.
 * <p>
 * Created by Mr. President on 02.07.2016.
 */
class Table<R, C, V> {

    @JsonProperty
    private Map<R, Map<C, V>> backingMap = [:]

    void put(R row, C column, V value) {
        if (backingMap[row] == null) {
            backingMap[row] = [:]
        }
        backingMap[row][column] = value
    }

    V get(R row, C column) {
        if (backingMap[row] == null) {
            return null
        }
        return backingMap[row][column]
    }

    void forEach(EntryConsumer<R, C, V> entryConsumer) {
        backingMap.forEach { row, valueByColumn ->
            valueByColumn.forEach { column, value ->
                entryConsumer.accept(row, column, value)
            }
        }
    }

    @FunctionalInterface
    public static interface EntryConsumer<R, C, V> {
        void accept(R row, C column, V value)
    }

}
