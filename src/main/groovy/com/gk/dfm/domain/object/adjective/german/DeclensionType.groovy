package com.gk.dfm.domain.object.adjective.german

import com.gk.dfm.domain.object.nounobject.Determiner

import static com.gk.dfm.domain.object.nounobject.Determiner.*

/**
 * Created by Mr. President on 10.07.2016.
 */
enum DeclensionType {

    STRONG, WEAK, MIXED;

    static DeclensionType forDeterminer(Determiner determiner) {
        switch (determiner) {
            case DEFINITE_ARTICLE:
            case DIESER:
            case JEDER:
            case ALLE:
            case JENER:
            case SOLCHER:
            case WELCHER:
                return WEAK

            case INDEFINITE_ARTICLE:
            case KEIN:
            case SINGULAR_1ST_POSSESSIVE:
            case SINGULAR_2ND_POSSESSIVE:
            case MASCULINE_SINGULAR_3RD_POSSESSIVE:
            case FEMININE_SINGULAR_3RD_POSSESSIVE:
            case NEUTER_SINGULAR_3RD_POSSESSIVE:
            case PLURAL_1ST_POSSESSIVE:
            case PLURAL_2ND_POSSESSIVE:
            case PLURAL_3RD_POSSESSIVE:
            case FORMAL_2ND_POSSESSIVE:
                return MIXED

            case NO_DETERMINER:
            case EINIGE:
            case MEHRERE:
            case VIELE:
            case WENIGE:
                return STRONG

            default:
                throw new RuntimeException("Unknown determiner $determiner")
        }
    }

}