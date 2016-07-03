package com.gk.dfm.logic

import com.gk.dfm.domain.object.ObjectClass
import com.gk.dfm.domain.object.SentenceObject
import com.gk.dfm.domain.object.noun.Noun
import com.gk.dfm.domain.object.nounobject.NounObject
import com.gk.dfm.domain.object.nounobject.german.ArticleType
import com.gk.dfm.domain.object.nounobject.german.ObjectNumber
import com.gk.dfm.domain.subject.Subject
import com.gk.dfm.domain.verb.Verb

/**
 * Created by Mr. President on 6/19/2016.
 */
class RandomWordSource {

    private static final double PICK_PLURAL_OBJECT_NUMBER_CHANCE = 0.25

    private Random random = new Random()

    List<Verb> verbs
    private List<Noun> allNouns
    private List<Noun> personNouns
    private List<Noun> thingNouns

    void setNouns(List<Noun> nouns) {
        this.allNouns = nouns
        this.personNouns = nouns.findAll { it.objectClass == ObjectClass.PERSON }
        this.thingNouns = nouns.findAll { it.objectClass == ObjectClass.THING }
    }

    Subject pickSubject() {
        Subject[] subjects = Subject.values()
        return subjects[random.nextInt(subjects.length)]
    }

    Verb pickVerb() {
        return verbs.get(random.nextInt(verbs.size()))
    }

    SentenceObject pickObject(ObjectClass objectClass) {
        def nouns = getNounList(objectClass)
        def noun = nouns.get(random.nextInt(nouns.size()))
        def number = random.nextDouble() < PICK_PLURAL_OBJECT_NUMBER_CHANCE ?
                ObjectNumber.PLURAL : ObjectNumber.SINGULAR
        return new NounObject(noun, ArticleType.DEFINITE, number)
    }

    private List<Noun> getNounList(ObjectClass objectClass) {
        switch (objectClass) {
            case ObjectClass.PERSON:
                return personNouns
            case ObjectClass.THING:
                return thingNouns
            case ObjectClass.ANYTHING:
                return allNouns
            default:
                throw new RuntimeException("Unknown object class: $objectClass")
        }
    }

}
