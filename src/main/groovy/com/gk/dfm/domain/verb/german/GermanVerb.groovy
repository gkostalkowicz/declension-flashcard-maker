package com.gk.dfm.domain.verb.german

import com.gk.dfm.domain.object.SentenceObject
import com.gk.dfm.domain.subject.Subject
import com.gk.dfm.domain.verb.german.objects.GermanDeclensionTemplate
import com.gk.dfm.domain.verb.german.objects.ObjectPlaceholder
import groovy.transform.ToString

import static com.gk.dfm.domain.subject.Subject.*

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

    String createSentence(Subject subject, Map<ObjectPlaceholder, SentenceObject> objectByPlaceholder) {
        def subjectString = GERMAN_SUBJECT[subject]
        def declinedObjects = declensionTemplate.declineTemplate(objectByPlaceholder)

        def sentence = new StringBuffer()
        sentence.append(subjectString)
        sentence.append(" ")
        def conjugatedVerb = verbInfinitive // TODO VerbConjugationRepository.instance.conjugateVerb(verbInfinitive, subject.toPerson())
        sentence.append(conjugatedVerb)
        if (infix != null) {
            sentence.append(" ")
            sentence.append(infix)
        }
        sentence.append(" ")
        sentence.append(declinedObjects)
        return sentence.toString()
    }

}
