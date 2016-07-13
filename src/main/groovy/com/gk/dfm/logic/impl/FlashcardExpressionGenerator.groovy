package com.gk.dfm.logic.impl

import com.gk.dfm.domain.object.ObjectClass
import com.gk.dfm.domain.object.SentenceObject
import com.gk.dfm.domain.preposition.german.GermanPrepositionDecliner
import com.gk.dfm.domain.preposition.polish.PolishPrepositionDecliner
import com.gk.dfm.domain.verb.german.objects.Case
import com.gk.dfm.domain.verb.german.objects.ObjectPlaceholder
import com.gk.dfm.logic.impl.source.RandomWordSource

/**
 * Created by Mr. President on 13.07.2016.
 */
class FlashcardExpressionGenerator {

    private RandomWordSource randomWordSource

    FlashcardExpressionGenerator(RandomWordSource randomWordSource) {
        this.randomWordSource = randomWordSource
    }

    Flashcard generateSentence() {
        def verb = randomWordSource.pickVerb()

        def objectDefinitionByPlaceholder = verb.germanVerb.declensionTemplate.objectDefinitionByPlaceholder
        def objectByPlaceholder = new HashMap<ObjectPlaceholder, SentenceObject>()
        objectDefinitionByPlaceholder.forEach({ placeholder, objectDefinition ->
            objectByPlaceholder[placeholder] = randomWordSource.pickObject(objectDefinition.objectClass)
        })

        def subject = randomWordSource.pickSubject()

        def polishSide = verb.polishVerb.createSentence(subject, objectByPlaceholder)
        def germanSide = verb.germanVerb.createSentence(subject, objectByPlaceholder)
        return new Flashcard(polishSide, germanSide)
    }

    Flashcard generateNominativeExpression() {
        def object = randomWordSource.pickObject(ObjectClass.ANYTHING)
        def polishSide = object.polishObject.decline()
        def germanSide = object.germanObject.decline(Case.NOMINATIVE)
        return new Flashcard(polishSide, germanSide)
    }

    Flashcard generatePrepositionExpression() {
        def object = randomWordSource.pickObject(ObjectClass.ANYTHING)
        def preposition = randomWordSource.pickPreposition()
        def polishSide = PolishPrepositionDecliner.createExpression(preposition, object)
        def germanSide = GermanPrepositionDecliner.createExpression(preposition, object)
        return new Flashcard(polishSide, germanSide)
    }


}
