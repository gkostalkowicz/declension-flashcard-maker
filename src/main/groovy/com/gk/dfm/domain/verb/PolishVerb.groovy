package com.gk.dfm.domain.verb

import com.gk.dfm.domain.object.SentenceObject
import com.gk.dfm.domain.subject.Subject
import groovy.transform.ToString

import static com.gk.dfm.domain.subject.Subject.*

/**
 * Created by Mr. President on 6/12/2016.
 */
@ToString(includePackage = false)
class PolishVerb {

    private static final Map<Subject, String> POLISH_SUBJECT = [
            (SINGULAR_1ST): "ja",
            (SINGULAR_2ND): "ty",
            (MASCULINE_SINGULAR_3RD): "on",
            (FEMININE_SINGULAR_3RD): "ona",
            (NEUTER_SINGULAR_3RD): "ono",
            (PLURAL_1ST): "my",
            (PLURAL_2ND): "wy",
            (PLURAL_3RD): "oni/one",
            (FORMAL_2ND): "pan/pani"
    ]

    String expressionOutline

    String createSentence(Subject subject, Map<ObjectPlaceholder, SentenceObject> objectByPlaceholder) {
        def subjectString = POLISH_SUBJECT[subject]

        def expression = expressionOutline
        objectByPlaceholder.forEach({ placeholder, object ->
            expression = expression.replace(placeholder.name(), object.polishObject.decline())
        })

        return subjectString + ", " + expression
    }

}
