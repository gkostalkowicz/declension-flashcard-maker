package com.gk.dfm.domain.object.noun

import com.gk.dfm.domain.Case

import static com.gk.dfm.domain.Case.*
import static com.gk.dfm.domain.object.noun.ObjectNumberAndGender.*

/**
 * Created by Mr. President on 6/19/2016.
 */
enum ArticleType {

    DEFINITE(Const.DEFINITE_ARTICLE_DECLENSION_MAP);

    static class Const {
        private static final Map<Case, Map<ObjectNumberAndGender, String>> DEFINITE_ARTICLE_DECLENSION_MAP = [
                (DATIVE)    : [
                        (MASCULINE_SINGULAR): "dem",
                        (FEMININE_SINGULAR) : "der",
                        (NEUTER_SINGULAR)   : "dem",
                        (PLURAL)            : "den",
                ],
                (ACCUSATIVE): [
                        (MASCULINE_SINGULAR): "den",
                        (FEMININE_SINGULAR) : "die",
                        (NEUTER_SINGULAR)   : "das",
                        (PLURAL)            : "die",
                ]
        ]
    }

    private ArticleType(Map<Case, Map<ObjectNumberAndGender, String>> declensionMap) {
        this.declensionMap = declensionMap
    }

    private Map<Case, Map<ObjectNumberAndGender, String>> declensionMap

    String decline(ObjectNumberAndGender objectNumberAndGender, Case objectCase) {
        declensionMap[objectCase][objectNumberAndGender]
    }

}
