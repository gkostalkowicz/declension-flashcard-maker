package com.gk.dfm.domain.subject.polish

import com.gk.dfm.domain.subject.Subject

import static com.gk.dfm.domain.subject.Subject.*

/**
 * Created by Mr. President on 10.07.2016.
 */
class PolishSubjectDecliner {

    private static final Map<Subject, String> POLISH_SUBJECT = [
            (SINGULAR_1ST)          : "ja",
            (SINGULAR_2ND)          : "ty",
            (MASCULINE_SINGULAR_3RD): "on",
            (FEMININE_SINGULAR_3RD) : "ona",
            (NEUTER_SINGULAR_3RD)   : "ono",
            (PLURAL_1ST)            : "my",
            (PLURAL_2ND)            : "wy",
            (PLURAL_3RD)            : "oni/one",
            (FORMAL_2ND)            : "pani/pan"
    ]

    static String declineSubject(Subject subject) {
        POLISH_SUBJECT[subject]
    }

}
