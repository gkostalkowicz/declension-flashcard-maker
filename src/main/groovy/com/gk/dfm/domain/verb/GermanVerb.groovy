package com.gk.dfm.domain.verb

import com.gk.dfm.domain.subject.Subject
import com.gk.dfm.domain.object.SentenceObject
import groovy.transform.ToString

import static Subject.FEMININE_SINGULAR_3RD
import static Subject.FORMAL_2ND
import static Subject.MASCULINE_SINGULAR_3RD
import static Subject.NEUTER_SINGULAR_3RD
import static Subject.PLURAL_1ST
import static Subject.PLURAL_2ND
import static Subject.PLURAL_3RD
import static Subject.SINGULAR_1ST
import static Subject.SINGULAR_2ND

/**
 * Created by Mr. President on 6/12/2016.
 */
@ToString(includePackage = false)
class GermanVerb {

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

    String verbInfinitive
    String infix
    GermanDeclensionTemplate declensionTemplate
    String suffix

    String createSentence(Subject subject, Map<ObjectPlaceholder, SentenceObject> objectByPlaceholder) {
        def subjectString = GERMAN_SUBJECT[subject]
        def declinedObjects = declensionTemplate.declineTemplate(objectByPlaceholder)

        def sentence = new StringBuffer()
        sentence.append(subjectString)
        sentence.append(" ")
        sentence.append(verbInfinitive)
        if (infix != null) {
            sentence.append(" ")
            sentence.append(infix)
        }
        sentence.append(" ")
        sentence.append(declinedObjects)
        if (suffix != null) {
            sentence.append(" ")
            sentence.append(suffix)
        }
        return sentence.toString()
    }

}
