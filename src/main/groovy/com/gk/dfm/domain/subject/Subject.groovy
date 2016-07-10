package com.gk.dfm.domain.subject

import com.gk.dfm.domain.verb.german.conjugation.ConjugationPerson

/**
 * Created by Mr. President on 6/19/2016.
 */
enum Subject {

    SINGULAR_1ST, SINGULAR_2ND, MASCULINE_SINGULAR_3RD, FEMININE_SINGULAR_3RD, NEUTER_SINGULAR_3RD,
    PLURAL_1ST, PLURAL_2ND, PLURAL_3RD, FORMAL_2ND;

    ConjugationPerson toConjugationPerson() {
        switch (this) {
            case SINGULAR_1ST:
                return ConjugationPerson.SINGULAR_1ST;
            case SINGULAR_2ND:
                return ConjugationPerson.SINGULAR_2ND;
            case MASCULINE_SINGULAR_3RD:
            case FEMININE_SINGULAR_3RD:
            case NEUTER_SINGULAR_3RD:
                return ConjugationPerson.SINGULAR_3RD;
            case PLURAL_1ST:
                return ConjugationPerson.PLURAL_1ST;
            case PLURAL_2ND:
                return ConjugationPerson.PLURAL_2ND;
            case PLURAL_3RD:
            case FORMAL_2ND:
                return ConjugationPerson.PLURAL_3RD;
            default:
                throw new UnsupportedOperationException("Can't map subject " + this + " to a conjugation person");
        }
    }

}