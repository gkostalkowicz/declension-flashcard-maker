package com.gk.dfm.generate.impl.source

import com.gk.dfm.domain.object.ObjectClass
import com.gk.dfm.domain.object.SentenceObject
import com.gk.dfm.domain.object.adjective.Adjective
import com.gk.dfm.domain.object.noun.Noun
import com.gk.dfm.domain.object.nounobject.Determiner
import com.gk.dfm.domain.object.nounobject.NounObject
import com.gk.dfm.domain.object.nounobject.ObjectNumber
import com.gk.dfm.domain.preposition.Preposition
import com.gk.dfm.domain.subject.Subject
import com.gk.dfm.domain.verb.Verb

/**
 * Created by Mr. President on 6/19/2016.
 */
class RandomWordSource {

    private static final STRONG_DECLENSION_WITHOUT_ARTICLE_DETERMINERS = new DeterminerGroup([
            Determiner.NO_DETERMINER])
    private static final STRONG_DECLENSION_PLURAL_NUMBER_DETERMINERS = new DeterminerGroup([
            Determiner.EINIGE, Determiner.MEHRERE, Determiner.VIELE, Determiner.WENIGE])
    private static final WEAK_DECLENSION_TYPES_DETERMINERS = new DeterminerGroup(
            Determiner.weakDeclensionDeterminers())
    private static final MIXED_DECLENSION_TYPES_DETERMINERS = new DeterminerGroup(
            Determiner.mixedDeclensionDeterminers())

    private static final Map<DeterminerGroup, Double> DETERMINER_GROUP_TO_CHANCE = [
            (STRONG_DECLENSION_WITHOUT_ARTICLE_DETERMINERS): 0.0,
            (STRONG_DECLENSION_PLURAL_NUMBER_DETERMINERS)  : 0.2,
            (WEAK_DECLENSION_TYPES_DETERMINERS)            : 0.33,
            (MIXED_DECLENSION_TYPES_DETERMINERS)           : null
    ]

    private static final double PICK_PLURAL_OBJECT_NUMBER_CHANCE = 0.35
    private static final double ADD_ADJECTIVE_TO_NOUN_CHANCE = 0.0

    private NounPicker nounPicker
    private DeterminerPicker determinerPicker = new DeterminerPicker(DETERMINER_GROUP_TO_CHANCE)
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

        def suggestedNumber = RandomUtil.trueWithChance(PICK_PLURAL_OBJECT_NUMBER_CHANCE) ?
                ObjectNumber.PLURAL : ObjectNumber.SINGULAR
        def pickedDeterminer = determinerPicker.pick(RandomUtil.pickElementWithProbability(DETERMINER_GROUP_TO_CHANCE),
                suggestedNumber)
        def determiner = pickedDeterminer.determiner
        def number = pickedDeterminer.number

        if (adjectives.empty) {
            throw new RuntimeException("Empty adjective list")
        }
        def adjective = RandomUtil.trueWithChance(ADD_ADJECTIVE_TO_NOUN_CHANCE) ?
                RandomUtil.pickElement(adjectives) : null

        return new NounObject(noun: noun, determiner: determiner, number: number, adjective: adjective)
    }

    Preposition pickPreposition() {
        return RandomUtil.pickElement(Preposition.values())
    }

}
