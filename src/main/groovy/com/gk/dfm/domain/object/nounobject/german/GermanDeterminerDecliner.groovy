package com.gk.dfm.domain.object.nounobject.german

import com.gk.dfm.domain.object.NumberAndGender
import com.gk.dfm.domain.object.nounobject.Determiner
import com.gk.dfm.domain.verb.german.objects.Case

import static com.gk.dfm.domain.object.NumberAndGender.*
import static com.gk.dfm.domain.object.nounobject.Determiner.*
import static com.gk.dfm.domain.verb.german.objects.Case.*

/**
 * Created by Mr. President on 10.07.2016.
 */
class GermanDeterminerDecliner {

    private static final Map<Determiner, Map<Case, Map<NumberAndGender, String>>> DECLENSION_MAP = [
            (DEFINITE_ARTICLE)     : [
                    (NOMINATIVE): [
                            (MASCULINE_SINGULAR): "der",
                            (NEUTER_SINGULAR)   : "das",
                            (FEMININE_SINGULAR) : "die",
                            (PLURAL)            : "die",
                    ],
                    (ACCUSATIVE): [
                            (MASCULINE_SINGULAR): "den",
                            (NEUTER_SINGULAR)   : "das",
                            (FEMININE_SINGULAR) : "die",
                            (PLURAL)            : "die",
                    ],
                    (DATIVE)    : [
                            (MASCULINE_SINGULAR): "dem",
                            (NEUTER_SINGULAR)   : "dem",
                            (FEMININE_SINGULAR) : "der",
                            (PLURAL)            : "den",
                    ],
                    (GENITIVE)  : [
                            (MASCULINE_SINGULAR): "des",
                            (NEUTER_SINGULAR)   : "des",
                            (FEMININE_SINGULAR) : "der",
                            (PLURAL)            : "der",
                    ],
            ],
            (DIESER)               : [
                    (NOMINATIVE): [
                            (MASCULINE_SINGULAR): "dieser",
                            (NEUTER_SINGULAR)   : "dieses",
                            (FEMININE_SINGULAR) : "diese",
                            (PLURAL)            : "diese",
                    ],
                    (ACCUSATIVE): [
                            (MASCULINE_SINGULAR): "diesen",
                            (NEUTER_SINGULAR)   : "dieses",
                            (FEMININE_SINGULAR) : "diese",
                            (PLURAL)            : "diese",
                    ],
                    (DATIVE)    : [
                            (MASCULINE_SINGULAR): "diesem",
                            (NEUTER_SINGULAR)   : "diesem",
                            (FEMININE_SINGULAR) : "dieser",
                            (PLURAL)            : "diesen",
                    ],
                    (GENITIVE)  : [
                            (MASCULINE_SINGULAR): "dieses",
                            (NEUTER_SINGULAR)   : "dieses",
                            (FEMININE_SINGULAR) : "dieser",
                            (PLURAL)            : "dieser",
                    ],
            ],
            (JEDER)                : [
                    (NOMINATIVE): [
                            (MASCULINE_SINGULAR): "jeder",
                            (NEUTER_SINGULAR)   : "jedes",
                            (FEMININE_SINGULAR) : "jede",
                    ],
                    (ACCUSATIVE): [
                            (MASCULINE_SINGULAR): "jeden",
                            (NEUTER_SINGULAR)   : "jedes",
                            (FEMININE_SINGULAR) : "jede",
                    ],
                    (DATIVE)    : [
                            (MASCULINE_SINGULAR): "jedem",
                            (NEUTER_SINGULAR)   : "jedem",
                            (FEMININE_SINGULAR) : "jeder",
                    ],
                    (GENITIVE)  : [
                            (MASCULINE_SINGULAR): "jedes",
                            (NEUTER_SINGULAR)   : "jedes",
                            (FEMININE_SINGULAR) : "jeder",
                    ],
            ],
            (ALLE)                 : [
                    (NOMINATIVE): [
                            (PLURAL): "alle",
                    ],
                    (ACCUSATIVE): [
                            (PLURAL): "alle",
                    ],
                    (DATIVE)    : [
                            (PLURAL): "allen",
                    ],
                    (GENITIVE)  : [
                            (PLURAL): "aller",
                    ],
            ],
            (JENER)                : [
                    (NOMINATIVE): [
                            (MASCULINE_SINGULAR): "jener",
                            (NEUTER_SINGULAR)   : "jenes",
                            (FEMININE_SINGULAR) : "jene",
                            (PLURAL)            : "jene",
                    ],
                    (ACCUSATIVE): [
                            (MASCULINE_SINGULAR): "jenen",
                            (NEUTER_SINGULAR)   : "jenes",
                            (FEMININE_SINGULAR) : "jene",
                            (PLURAL)            : "jene",
                    ],
                    (DATIVE)    : [
                            (MASCULINE_SINGULAR): "jenem",
                            (NEUTER_SINGULAR)   : "jenem",
                            (FEMININE_SINGULAR) : "jener",
                            (PLURAL)            : "jenen",
                    ],
                    (GENITIVE)  : [
                            (MASCULINE_SINGULAR): "jenes",
                            (NEUTER_SINGULAR)   : "jenes",
                            (FEMININE_SINGULAR) : "jener",
                            (PLURAL)            : "jener",
                    ],
            ],
            (SOLCHER)              : [
                    (NOMINATIVE): [
                            (MASCULINE_SINGULAR): "solcher",
                            (NEUTER_SINGULAR)   : "solches",
                            (FEMININE_SINGULAR) : "solche",
                            (PLURAL)            : "solche",
                    ],
                    (ACCUSATIVE): [
                            (MASCULINE_SINGULAR): "solchen",
                            (NEUTER_SINGULAR)   : "solches",
                            (FEMININE_SINGULAR) : "solche",
                            (PLURAL)            : "solche",
                    ],
                    (DATIVE)    : [
                            (MASCULINE_SINGULAR): "solchem",
                            (NEUTER_SINGULAR)   : "solchem",
                            (FEMININE_SINGULAR) : "solcher",
                            (PLURAL)            : "solchen",
                    ],
                    (GENITIVE)  : [
                            (MASCULINE_SINGULAR): "solchen",
                            (NEUTER_SINGULAR)   : "solchen",
                            (FEMININE_SINGULAR) : "solcher",
                            (PLURAL)            : "solcher",
                    ],
            ],
            (WELCHER)              : [
                    (NOMINATIVE): [
                            (MASCULINE_SINGULAR): "welcher",
                            (NEUTER_SINGULAR)   : "welches",
                            (FEMININE_SINGULAR) : "welche",
                            (PLURAL)            : "welche",
                    ],
                    (ACCUSATIVE): [
                            (MASCULINE_SINGULAR): "welchen",
                            (NEUTER_SINGULAR)   : "welches",
                            (FEMININE_SINGULAR) : "welche",
                            (PLURAL)            : "welche",
                    ],
                    (DATIVE)    : [
                            (MASCULINE_SINGULAR): "welchem",
                            (NEUTER_SINGULAR)   : "welchem",
                            (FEMININE_SINGULAR) : "welcher",
                            (PLURAL)            : "welchen",
                    ],
                    (GENITIVE)  : [
                            (MASCULINE_SINGULAR): "welches",
                            (NEUTER_SINGULAR)   : "welches",
                            (FEMININE_SINGULAR) : "welcher",
                            (PLURAL)            : "welcher",
                    ],
            ],
            (INDEFINITE_ARTICLE)   : [
                    (NOMINATIVE): [
                            (MASCULINE_SINGULAR): "ein",
                            (NEUTER_SINGULAR)   : "ein",
                            (FEMININE_SINGULAR) : "eine",
                    ],
                    (ACCUSATIVE): [
                            (MASCULINE_SINGULAR): "einen",
                            (NEUTER_SINGULAR)   : "ein",
                            (FEMININE_SINGULAR) : "eine",
                    ],
                    (DATIVE)    : [
                            (MASCULINE_SINGULAR): "einem",
                            (NEUTER_SINGULAR)   : "einem",
                            (FEMININE_SINGULAR) : "einer",
                    ],
                    (GENITIVE)  : [
                            (MASCULINE_SINGULAR): "eines",
                            (NEUTER_SINGULAR)   : "eines",
                            (FEMININE_SINGULAR) : "einer",
                    ],
            ],
            (KEIN)                 : [
                    (NOMINATIVE): [
                            (MASCULINE_SINGULAR): "kein",
                            (NEUTER_SINGULAR)   : "kein",
                            (FEMININE_SINGULAR) : "keine",
                            (PLURAL)            : "keine",
                    ],
                    (ACCUSATIVE): [
                            (MASCULINE_SINGULAR): "keinen",
                            (NEUTER_SINGULAR)   : "kein",
                            (FEMININE_SINGULAR) : "keine",
                            (PLURAL)            : "keine",
                    ],
                    (DATIVE)    : [
                            (MASCULINE_SINGULAR): "keinem",
                            (NEUTER_SINGULAR)   : "keinem",
                            (FEMININE_SINGULAR) : "keiner",
                            (PLURAL)            : "keinen",
                    ],
                    (GENITIVE)  : [
                            (MASCULINE_SINGULAR): "keines",
                            (NEUTER_SINGULAR)   : "keines",
                            (FEMININE_SINGULAR) : "keiner",
                            (PLURAL)            : "keiner",
                    ],
            ],
            (PLURAL_2ND_POSSESSIVE): [
                    (NOMINATIVE): [
                            (MASCULINE_SINGULAR): "euer",
                            (NEUTER_SINGULAR)   : "euer",
                            (FEMININE_SINGULAR) : "eure",
                            (PLURAL)            : "eure",
                    ],
                    (ACCUSATIVE): [
                            (MASCULINE_SINGULAR): "euren",
                            (NEUTER_SINGULAR)   : "euer",
                            (FEMININE_SINGULAR) : "eure",
                            (PLURAL)            : "eure",
                    ],
                    (DATIVE)    : [
                            (MASCULINE_SINGULAR): "eurem",
                            (NEUTER_SINGULAR)   : "eurem",
                            (FEMININE_SINGULAR) : "eurer",
                            (PLURAL)            : "euren",
                    ],
                    (GENITIVE)  : [
                            (MASCULINE_SINGULAR): "eures",
                            (NEUTER_SINGULAR)   : "eures",
                            (FEMININE_SINGULAR) : "eurer",
                            (PLURAL)            : "eurer",
                    ],
            ],
            (EINIGE)               : [
                    (NOMINATIVE): [
                            (PLURAL): "einige",
                    ],
                    (ACCUSATIVE): [
                            (PLURAL): "einige",
                    ],
                    (DATIVE)    : [
                            (PLURAL): "einigen",
                    ],
                    (GENITIVE)  : [
                            (PLURAL): "einiger",
                    ],
            ],
            (MEHRERE)              : [
                    (NOMINATIVE): [
                            (PLURAL): "mehrere",
                    ],
                    (ACCUSATIVE): [
                            (PLURAL): "mehrere",
                    ],
                    (DATIVE)    : [
                            (PLURAL): "mehreren",
                    ],
                    (GENITIVE)  : [
                            (PLURAL): "mehrerer",
                    ],
            ],
            (VIELE)                : [
                    (NOMINATIVE): [
                            (PLURAL): "viele",
                    ],
                    (ACCUSATIVE): [
                            (PLURAL): "viele",
                    ],
                    (DATIVE)    : [
                            (PLURAL): "vielen",
                    ],
                    (GENITIVE)  : [
                            (PLURAL): "vieler",
                    ],
            ],
            (WENIGE)               : [
                    (NOMINATIVE): [
                            (PLURAL): "viele",
                    ],
                    (ACCUSATIVE): [
                            (PLURAL): "viele",
                    ],
                    (DATIVE)    : [
                            (PLURAL): "vielen",
                    ],
                    (GENITIVE)  : [
                            (PLURAL): "vieler",
                    ],
            ],
    ]

    private static final Map<Determiner, String> POSSESSIVE_PRONOUN_TO_STEM = [
            (SINGULAR_1ST_POSSESSIVE)          : "mein",
            (SINGULAR_2ND_POSSESSIVE)          : "dein",
            (MASCULINE_SINGULAR_3RD_POSSESSIVE): "sein",
            (FEMININE_SINGULAR_3RD_POSSESSIVE) : "ihr",
            (NEUTER_SINGULAR_3RD_POSSESSIVE)   : "sein",
            (PLURAL_1ST_POSSESSIVE)            : "unser",
            (PLURAL_2ND_POSSESSIVE)            : "euer",
            (PLURAL_3RD_POSSESSIVE)            : "ihr",
            (FORMAL_2ND_POSSESSIVE)            : "Ihr",
    ]

    private static final Map<Case, Map<NumberAndGender, String>> POSSESSIVE_PRONOUN_TO_ENDING = [
            (NOMINATIVE): [
                    (MASCULINE_SINGULAR): "",
                    (NEUTER_SINGULAR)   : "",
                    (FEMININE_SINGULAR) : "e",
                    (PLURAL)            : "e",
            ],
            (ACCUSATIVE): [
                    (MASCULINE_SINGULAR): "en",
                    (NEUTER_SINGULAR)   : "",
                    (FEMININE_SINGULAR) : "e",
                    (PLURAL)            : "e",
            ],
            (DATIVE)    : [
                    (MASCULINE_SINGULAR): "em",
                    (NEUTER_SINGULAR)   : "em",
                    (FEMININE_SINGULAR) : "er",
                    (PLURAL)            : "en",
            ],
            (GENITIVE)  : [
                    (MASCULINE_SINGULAR): "es",
                    (NEUTER_SINGULAR)   : "es",
                    (FEMININE_SINGULAR) : "er",
                    (PLURAL)            : "er",
            ],
    ]

    static Optional<String> declineDeterminer(Determiner determiner, NumberAndGender objectNumberAndGender, Case objectCase) {
        if (determiner == NO_DETERMINER) {
            return Optional.empty()
        } else if (determiner.possessivePronoun && determiner != PLURAL_2ND_POSSESSIVE) {
            def stem = POSSESSIVE_PRONOUN_TO_STEM[determiner]
            def ending = POSSESSIVE_PRONOUN_TO_ENDING[objectCase][objectNumberAndGender]
            return Optional.of(stem + ending)
        } else {
            def declinedDeterminer = DECLENSION_MAP[determiner][objectCase][objectNumberAndGender]
            if (declinedDeterminer == null) {
                throw new RuntimeException("Determiner $determiner has no grammatical form for $objectNumberAndGender" +
                        " and $objectCase")
            }
            return Optional.of(declinedDeterminer)
        }
    }

}
