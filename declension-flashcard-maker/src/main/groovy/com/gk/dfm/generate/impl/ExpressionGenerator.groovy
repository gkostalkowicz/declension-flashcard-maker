package com.gk.dfm.generate.impl

import com.gk.dfm.domain.expression.NominativeExpression
import com.gk.dfm.domain.expression.PrepositionExpression
import com.gk.dfm.domain.expression.Sentence
import com.gk.dfm.domain.object.ObjectClass
import com.gk.dfm.domain.object.SentenceObject
import com.gk.dfm.domain.verb.german.objects.ObjectPlaceholder
import com.gk.dfm.generate.impl.source.RandomWordSource

/**
 * Created by Mr. President on 13.07.2016.
 */
class ExpressionGenerator {

    private RandomWordSource randomWordSource

    ExpressionGenerator(RandomWordSource randomWordSource) {
        this.randomWordSource = randomWordSource
    }

    Sentence generateSentence() {
        def subject = randomWordSource.pickSubject()

        def verb = randomWordSource.pickVerb()

        def objectDefinitionByPlaceholder = verb.germanVerb.declensionTemplate.objectDefinitionByPlaceholder
        def objectByPlaceholder = new HashMap<ObjectPlaceholder, SentenceObject>()
        objectDefinitionByPlaceholder.forEach({ placeholder, objectDefinition ->
            objectByPlaceholder[placeholder] = randomWordSource.pickObject(objectDefinition.objectClass)
        })

        return new Sentence(subject: subject, verb: verb, objectByPlaceholder: objectByPlaceholder)
    }

    NominativeExpression generateNominativeExpression() {
        def object = randomWordSource.pickObject(ObjectClass.ANYTHING)
        return new NominativeExpression(object: object)
    }

    PrepositionExpression generatePrepositionExpression() {
        def preposition = randomWordSource.pickPreposition()
        def object = randomWordSource.pickObject(ObjectClass.ANYTHING)
        return new PrepositionExpression(preposition: preposition, object: object)
    }

}
