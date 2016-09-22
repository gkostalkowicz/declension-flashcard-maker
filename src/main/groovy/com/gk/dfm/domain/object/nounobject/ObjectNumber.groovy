package com.gk.dfm.domain.object.nounobject

/**
 * Created by Mr. President on 6/19/2016.
 */
enum ObjectNumber {

    SINGULAR, PLURAL;

    ObjectNumber inverted() {
        switch (this) {
            case SINGULAR:
                return PLURAL
            case PLURAL:
                return SINGULAR
            default:
                throw new RuntimeException("Unknown object number: $this")
        }
    }

}