package com.gk.dfm.domain.subject.german

import com.gk.dfm.domain.subject.Subject

import static com.gk.dfm.domain.subject.Subject.*

/**
 * Created by Mr. President on 10.07.2016.
 */
class GermanSubjectDecliner {

    private static final Map<Subject, String> GERMAN_SUBJECT = [
            (SINGULAR_1ST)          : "ich",
            (SINGULAR_2ND)          : "du",
            (MASCULINE_SINGULAR_3RD): "er",
            (FEMININE_SINGULAR_3RD) : "sie",
            (NEUTER_SINGULAR_3RD)   : "es",
            (PLURAL_1ST)            : "wir",
            (PLURAL_2ND)            : "ihr",
            (PLURAL_3RD)            : "sie",
            (FORMAL_2ND)            : "Sie"
    ]

    static String declineSubject(Subject subject) {
        GERMAN_SUBJECT[subject]
    }

}
