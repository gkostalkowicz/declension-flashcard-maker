package com.gk.dfm.domain.object.nounobject.german

import com.gk.dfm.domain.verb.german.objects.Case

import static com.gk.dfm.domain.object.nounobject.german.ArticleNumberAndGender.*
import static com.gk.dfm.domain.verb.german.objects.Case.ACCUSATIVE
import static com.gk.dfm.domain.verb.german.objects.Case.DATIVE

/**
 * Created by Mr. President on 6/19/2016.
 */
enum ArticleType {

    DEFINITE(Const.DEFINITE_ARTICLE_DECLENSION_MAP);

    static class Const {
        private static final Map<Case, Map<ArticleNumberAndGender, String>> DEFINITE_ARTICLE_DECLENSION_MAP = [
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

    private ArticleType(Map<Case, Map<ArticleNumberAndGender, String>> declensionMap) {
        this.declensionMap = declensionMap
    }

    private Map<Case, Map<ArticleNumberAndGender, String>> declensionMap

    String decline(ArticleNumberAndGender objectNumberAndGender, Case objectCase) {
        declensionMap[objectCase][objectNumberAndGender]
    }

}
