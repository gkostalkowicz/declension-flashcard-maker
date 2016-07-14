package com.gk.dfm.logic.impl.source

import com.gk.dfm.domain.object.ObjectClass
import com.gk.dfm.domain.object.SentenceObject
import com.gk.dfm.domain.object.adjective.Adjective
import com.gk.dfm.domain.object.noun.Noun
import com.gk.dfm.domain.object.nounobject.NounObject
import com.gk.dfm.domain.object.nounobject.german.ObjectNumber
import com.gk.dfm.domain.preposition.Preposition
import com.gk.dfm.domain.subject.Subject
import com.gk.dfm.domain.verb.Verb

/**
 * Created by Mr. President on 6/19/2016.
 */
class RandomWordSource {

    private static final double PICK_PLURAL_OBJECT_NUMBER_CHANCE = 0.25
    private static final double PICK_POSSESSIVE_PRONOUN_AS_DETERMINER_CHANCE = 0.25
    private static final double ADD_ADJECTIVE_TO_NOUN_CHANCE = 1.0

    private NounPicker nounPicker
    private DeterminerPicker determinerPicker = new DeterminerPicker()
    List<Verb> verbs
    List<Adjective> adjectives

    void setNouns(List<Noun> nouns) {
        nounPicker = new NounPicker(nouns)
    }

    Subject pickSubject() {
        return RandomUtil.pickElement(Subject.values())
    }

    Verb pickVerb() {
        if (verbs.empty) {
            throw new RuntimeException("Empty verb list")
        }
        return RandomUtil.pickElement(verbs)
    }

    SentenceObject pickObject(ObjectClass objectClass) {
        def noun = nounPicker.pick(objectClass)
        def number = RandomUtil.trueWithChance(PICK_PLURAL_OBJECT_NUMBER_CHANCE) ?
                ObjectNumber.PLURAL : ObjectNumber.SINGULAR
        def determiner = determinerPicker.pick(number,
                RandomUtil.trueWithChance(PICK_POSSESSIVE_PRONOUN_AS_DETERMINER_CHANCE))
        if (adjectives.empty) {
            throw new RuntimeException("Empty adjective list")
        }
        def adjective = RandomUtil.trueWithChance(ADD_ADJECTIVE_TO_NOUN_CHANCE) ?
                RandomUtil.pickElement(adjectives) : null

        return new NounObject(noun, determiner, number, adjective)
    }

    Preposition pickPreposition() {
        return RandomUtil.pickElement(Preposition.values())
    }

}
