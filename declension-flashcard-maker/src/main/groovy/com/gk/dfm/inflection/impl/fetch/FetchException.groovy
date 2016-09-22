package com.gk.dfm.inflection.impl.fetch

/**
 * Created by Mr. President on 06.07.2016.
 */
class FetchException extends Exception {

    private String word

    FetchException(String message) {
        super(message)
    }

    FetchException(String message, String word) {
        super(message)
        this.word = word
    }

    String getWord() {
        return word
    }
}
