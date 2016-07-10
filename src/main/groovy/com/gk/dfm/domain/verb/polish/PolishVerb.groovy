package com.gk.dfm.domain.verb.polish

import com.gk.dfm.domain.object.SentenceObject
import com.gk.dfm.domain.subject.Subject
import com.gk.dfm.domain.subject.polish.PolishSubjectDecliner
import com.gk.dfm.domain.verb.german.objects.ObjectPlaceholder
import groovy.transform.ToString

/**
 * Created by Mr. President on 6/12/2016.
 */
@ToString(includePackage = false)
class PolishVerb {

    String expressionOutline

    String createSentence(Subject subject, Map<ObjectPlaceholder, SentenceObject> objectByPlaceholder) {
        def subjectString = PolishSubjectDecliner.declineSubject(subject)

        def expression = expressionOutline
        objectByPlaceholder.forEach({ placeholder, object ->
            expression = expression.replace(placeholder.name(), object.polishObject.decline())
        })

        return subjectString + ", " + expression
    }

}
