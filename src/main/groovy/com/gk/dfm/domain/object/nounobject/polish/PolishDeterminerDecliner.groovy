package com.gk.dfm.domain.object.nounobject.polish

import com.gk.dfm.domain.object.nounobject.Determiner

import static com.gk.dfm.domain.object.nounobject.Determiner.*

/**
 * Created by Mr. President on 10.07.2016.
 */
class PolishDeterminerDecliner {

    private static final Map<Determiner, String> POLISH_DETERMINER = [
            (DEFINITE_ARTICLE)                 : "(określony)",
            (DIESER)                           : "ten",
            (JEDER)                            : "każdy",
            (ALLE)                             : "wszystkie",
            (JENER)                            : "tamten",
            (SOLCHER)                          : "taki",
            (WELCHER)                          : "który/jaki",
            (INDEFINITE_ARTICLE)               : "(nieokreślony)",
            (SINGULAR_1ST_POSSESSIVE)          : "mój",
            (SINGULAR_2ND_POSSESSIVE)          : "twój",
            (MASCULINE_SINGULAR_3RD_POSSESSIVE): "jego (on)",
            (FEMININE_SINGULAR_3RD_POSSESSIVE) : "jej",
            (NEUTER_SINGULAR_3RD_POSSESSIVE)   : "jego (ono)",
            (PLURAL_1ST_POSSESSIVE)            : "nasz",
            (PLURAL_2ND_POSSESSIVE)            : "wasz",
            (PLURAL_3RD_POSSESSIVE)            : "ich",
            (FORMAL_2ND_POSSESSIVE)            : "pani/pana",
            (KEIN)                             : "żaden",
            (NO_DETERMINER)                    : "(-)",
            (EINIGE)                           : "parę",
            (MEHRERE)                          : "kilka",
            (VIELE)                            : "wiele",
            (WENIGE)                           : "niewiele"
    ]

    static String declineDeterminer(Determiner determiner) {
        POLISH_DETERMINER[determiner]
    }

}
