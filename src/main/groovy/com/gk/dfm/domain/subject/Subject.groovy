package com.gk.dfm.domain.subject

import com.gk.dfm.domain.verb.Person

/**
 * Created by Mr. President on 6/19/2016.
 */
enum Subject {

    SINGULAR_1ST, SINGULAR_2ND, MASCULINE_SINGULAR_3RD, FEMININE_SINGULAR_3RD, NEUTER_SINGULAR_3RD,
    PLURAL_1ST, PLURAL_2ND, PLURAL_3RD, FORMAL_2ND;

    Person toPerson() {
        switch (this) {
            case SINGULAR_1ST:
                return Person.SINGULAR_1ST;
            case SINGULAR_2ND:
                return Person.SINGULAR_2ND;
            case MASCULINE_SINGULAR_3RD:
            case FEMININE_SINGULAR_3RD:
            case NEUTER_SINGULAR_3RD:
                return Person.SINGULAR_3RD;
            case PLURAL_1ST:
                return Person.PLURAL_1ST;
            case PLURAL_2ND:
                return Person.PLURAL_2ND;
            case PLURAL_3RD:
            case FORMAL_2ND:
                return Person.PLURAL_3RD;
            default:
                throw new UnsupportedOperationException("Can't map subject " + this + " to a person");
        }
    }

}