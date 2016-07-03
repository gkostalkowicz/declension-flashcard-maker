package com.gk.dfm.logic

import com.gk.dfm.domain.object.SentenceObject
import com.gk.dfm.domain.verb.german.objects.ObjectPlaceholder

/**
 * Created by Mr. President on 6/19/2016.
 */
class FlashcardGenerator {

    private static final OUTPUT_COLUMN_SEPARATOR = "\t"

    private RandomWordSource randomWordSource

    String generateFlashcard() {
        def verb = randomWordSource.pickVerb()

        def objectDefinitionByPlaceholder = verb.germanVerb.declensionTemplate.objectDefinitionByPlaceholder
        def objectByPlaceholder = new HashMap<ObjectPlaceholder, SentenceObject>()
        objectDefinitionByPlaceholder.forEach({ placeholder, objectDefinition ->
            objectByPlaceholder[placeholder] = randomWordSource.pickObject(objectDefinition.objectClass)
        })

        def subject = randomWordSource.pickSubject()

        def polishSide = verb.polishVerb.createSentence(subject, objectByPlaceholder)
        def germanSide = verb.germanVerb.createSentence(subject, objectByPlaceholder)
        return polishSide + OUTPUT_COLUMN_SEPARATOR + germanSide
    }

}
