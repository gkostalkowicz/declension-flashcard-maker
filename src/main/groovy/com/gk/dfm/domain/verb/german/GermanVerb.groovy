package com.gk.dfm.domain.verb.german

import com.gk.dfm.domain.object.SentenceObject
import com.gk.dfm.domain.subject.Subject
import com.gk.dfm.domain.subject.german.GermanSubjectDecliner
import com.gk.dfm.domain.verb.german.conjugation.VerbConjugation
import com.gk.dfm.domain.verb.german.objects.GermanDeclensionTemplate
import com.gk.dfm.domain.verb.german.objects.ObjectPlaceholder
import groovy.transform.ToString

/**
 * Created by Mr. President on 6/12/2016.
 */
@ToString(includePackage = false)
class GermanVerb {

    String verbInfinitive
    String infix
    GermanDeclensionTemplate declensionTemplate
    VerbConjugation conjugation

    String createSentence(Subject subject, Map<ObjectPlaceholder, SentenceObject> objectByPlaceholder) {
        def subjectString = GermanSubjectDecliner.declineSubject(subject)
        def declinedObjects = PrepositionContractor.contractPrepositionsWithArticles(
                " " + declensionTemplate.declineTemplate(objectByPlaceholder))

        def conjugatedVerb = conjugation.get(subject.toConjugationPerson())

        def sentence = new StringBuffer()
        sentence.append(subjectString)
        sentence.append(" ")
        sentence.append(conjugatedVerb.coreVerb)
        if (infix != null) {
            sentence.append(" ")
            sentence.append(infix)
        }
        sentence.append(declinedObjects)
        if (conjugatedVerb.particle != null) {
            sentence.append(" ")
            sentence.append(conjugatedVerb.particle)
        }
        return sentence.toString()
    }

}
