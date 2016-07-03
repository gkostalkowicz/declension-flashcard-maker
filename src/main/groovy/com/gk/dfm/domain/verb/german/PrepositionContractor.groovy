package com.gk.dfm.domain.verb.german

/**
 * Created by Mr. President on 03.07.2016.
 */
class PrepositionContractor {

    private static final Map<String, String> CONTRACTION_BY_SEPARATE_WORDS = [
            "an dem": "am",
            "an das": "ans",
            "bei dem": "beim",
            "in dem": "im",
            "in das": "ins",
            "von dem": "vom",
            "zu dem": "zum",
            "zu der": "zur"
    ]

    static String contractPrepositionsWithArticles(String expression) {
        CONTRACTION_BY_SEPARATE_WORDS.forEach { separateWords, contraction ->
            expression = expression.replace(separateWords, contraction)
        }
        return expression
    }

}
