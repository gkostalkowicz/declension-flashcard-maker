package com.gk.dfm.logic

import com.gk.dfm.domain.object.NumberAndGender
import com.gk.dfm.domain.object.ObjectClass
import com.gk.dfm.domain.object.SentenceObject
import com.gk.dfm.domain.object.adjective.Adjective
import com.gk.dfm.domain.object.adjective.german.AdjectiveDeclension
import com.gk.dfm.domain.object.adjective.german.DeclensionType
import com.gk.dfm.domain.object.adjective.german.RealGermanAdjective
import com.gk.dfm.domain.object.adjective.polish.RealPolishAdjective
import com.gk.dfm.domain.object.noun.Noun
import com.gk.dfm.domain.object.noun.german.Gender
import com.gk.dfm.domain.object.noun.german.GermanNoun
import com.gk.dfm.domain.object.noun.german.NounDeclension
import com.gk.dfm.domain.object.noun.polish.PolishNoun
import com.gk.dfm.domain.object.nounobject.Determiner
import com.gk.dfm.domain.object.nounobject.NounObject
import com.gk.dfm.domain.object.nounobject.german.ObjectNumber
import com.gk.dfm.domain.preposition.Preposition
import com.gk.dfm.domain.subject.Subject
import com.gk.dfm.domain.verb.Verb
import com.gk.dfm.domain.verb.german.GermanVerb
import com.gk.dfm.domain.verb.german.conjugation.ConjugatedVerb
import com.gk.dfm.domain.verb.german.conjugation.ConjugationPerson
import com.gk.dfm.domain.verb.german.conjugation.VerbConjugation
import com.gk.dfm.domain.verb.german.objects.Case
import com.gk.dfm.domain.verb.german.objects.GermanDeclensionTemplate
import com.gk.dfm.domain.verb.german.objects.ObjectDefinition
import com.gk.dfm.domain.verb.german.objects.ObjectPlaceholder
import com.gk.dfm.domain.verb.polish.PolishVerb
import com.gk.dfm.logic.impl.FlashcardExpressionGenerator
import com.gk.dfm.logic.impl.source.RandomWordSource
import org.junit.Test

/**
 * Created by Mr. President on 6/19/2016.
 */
class FlashcardExpressionGeneratorTest {

    @Test
    void "given a subject, verb and object when generateSentence then generate 1 valid flashcard"() {
        given:
        def subject = Subject.SINGULAR_1ST
        def verb = createVerb()
        def object = createNounObject()

        when:
        def flashcard = generateSentence(subject, verb, object)

        then:
        assert flashcard.polish == "ja, miec (co? (określony) pies)"
        assert flashcard.german == "ich habe den Hund"
    }

    @Test
    void "given no determiner when generateSentence then generate a flashcard without a determiner"() {
        given:
        def subject = Subject.SINGULAR_1ST
        def verb = createVerb()
        def object = createNounObject()
        object.germanNounObject.determiner = Determiner.NO_DETERMINER
        object.polishNounObject.determiner = Determiner.NO_DETERMINER

        when:
        def flashcard = generateSentence(subject, verb, object)

        then:
        assert flashcard.polish == "ja, miec (co? (-) pies)"
        assert flashcard.german == "ich habe Hund"
    }

    @Test
    void "given a verb with separable prefix when generateSentence then generate a flashcard ending with the particle"() {
        given:
        def subject = Subject.SINGULAR_1ST
        def verb = createVerb()
        verb.germanVerb.conjugation.put(ConjugationPerson.SINGULAR_1ST,
                new ConjugatedVerb(particle: "auf", coreVerb: "habe"))
        def object = createNounObject()

        when:
        def flashcard = generateSentence(subject, verb, object)

        then:
        assert flashcard.polish == "ja, miec (co? (określony) pies)"
        assert flashcard.german == "ich habe den Hund auf"
    }

    @Test
    void "given a verb with an infix when generateSentence then generate a flashcard with the infix"() {
        given:
        def subject = Subject.SINGULAR_1ST
        def verb = createVerb()
        verb.germanVerb.infix = "auch"
        def object = createNounObject()

        when:
        def flashcard = generateSentence(subject, verb, object)

        then:
        assert flashcard.polish == "ja, miec (co? (określony) pies)"
        assert flashcard.german == "ich habe auch den Hund"
    }

    @Test
    void "given a plural object when generateSentence then generate a flashcard with plural objects"() {
        given:
        def subject = Subject.SINGULAR_1ST
        def verb = createVerb()
        def object = createNounObject()
        object.polishNounObject.number = ObjectNumber.PLURAL
        object.germanNounObject.number = ObjectNumber.PLURAL
        object.germanNounObject.noun.declension.put(ObjectNumber.PLURAL, Case.ACCUSATIVE, "Hunde")

        when:
        def flashcard = generateSentence(subject, verb, object)

        then:
        assert flashcard.polish == "ja, miec (co? (wiele) (określony) pies)"
        assert flashcard.german == "ich habe die Hunde"
    }

    @Test
    void "given a preposition and an article when generateSentence then generate a flashcard with a contraction"() {
        given:
        def subject = Subject.SINGULAR_1ST
        def verb = createVerb()
        def object = createNounObject()
        verb.polishVerb.expressionOutline = "ide do (gdzie? X)"
        verb.germanVerb.verbInfinitive = "gehen"
        verb.germanVerb.conjugation.put(ConjugationPerson.SINGULAR_1ST, new ConjugatedVerb(coreVerb: "gehe"))
        verb.germanVerb.declensionTemplate.template = "in X"
        object.polishNounObject.noun.noun = "kino"
        object.germanNounObject.noun.noun = "Kino"
        object.germanNounObject.noun.gender = Gender.NEUTER
        object.germanNounObject.noun.declension.put(ObjectNumber.SINGULAR, Case.ACCUSATIVE, "Kino")

        when:
        def flashcard = generateSentence(subject, verb, object)

        then:
        assert flashcard.polish == "ja, ide do (gdzie? (określony) kino)"
        assert flashcard.german == "ich gehe ins Kino"
    }

    @Test
    void "given an adjective when generateSentence then generate a flashcard with the adjective"() {
        given:
        def subject = Subject.SINGULAR_1ST
        def verb = createVerb()
        def object = createNounObject()
        object.polishNounObject.adjective = new RealPolishAdjective(adjective: "ciemny")
        object.germanNounObject.adjective = new RealGermanAdjective(adjective: "dunkel",
                declension: new AdjectiveDeclension())
        (object.germanNounObject.adjective as RealGermanAdjective).declension.put(DeclensionType.WEAK,
                NumberAndGender.MASCULINE_SINGULAR, Case.ACCUSATIVE, "dunklen")

        when:
        def flashcard = generateSentence(subject, verb, object)

        then:
        assert flashcard.polish == "ja, miec (co? (określony) ciemny pies)"
        assert flashcard.german == "ich habe den dunklen Hund"
    }

    @Test
    void "given an object when generateNominativeExpression then generate a valid nominative expression"() {
        given:
        def nounDeclension = new NounDeclension()
        nounDeclension.put(ObjectNumber.SINGULAR, Case.NOMINATIVE, "Hund")
        def object = new NounObject(
                new Noun(
                        polishNoun: new PolishNoun(
                                noun: "pies"
                        ),
                        germanNoun: new GermanNoun(
                                noun: "Hund",
                                gender: Gender.MASCULINE,
                                declension: nounDeclension),
                        objectClass: ObjectClass.ANYTHING
                ),
                Determiner.DEFINITE_ARTICLE,
                ObjectNumber.SINGULAR,
                Adjective.NULL_ADJECTIVE)
        def randomWordSource = [pickObject: { object }] as RandomWordSource
        def generator = new FlashcardExpressionGenerator(randomWordSource)

        when:
        def flashcard = generator.generateNominativeExpression()

        then:
        assert flashcard.polish == "(określony) pies"
        assert flashcard.german == "der Hund"
    }

    @Test
    void "given an object and preposition when generatePrepositionExpression then generate a valid preposition expression"() {
        given:
        def nounDeclension = new NounDeclension()
        nounDeclension.put(ObjectNumber.SINGULAR, Case.DATIVE, "Hund")
        def object = new NounObject(
                new Noun(
                        polishNoun: new PolishNoun(
                                noun: "pies"
                        ),
                        germanNoun: new GermanNoun(
                                noun: "Hund",
                                gender: Gender.MASCULINE,
                                declension: nounDeclension),
                        objectClass: ObjectClass.ANYTHING
                ),
                Determiner.DEFINITE_ARTICLE,
                ObjectNumber.SINGULAR,
                Adjective.NULL_ADJECTIVE)
        def preposition = Preposition.MIT
        def randomWordSource = [
                pickObject     : { object },
                pickPreposition: { preposition }
        ] as RandomWordSource
        def generator = new FlashcardExpressionGenerator(randomWordSource)

        when:
        def flashcard = generator.generatePrepositionExpression()

        then:
        assert flashcard.polish == "z(czymś/kimś), (określony) pies"
        assert flashcard.german == "mit dem Hund"
    }

    private static generateSentence(Subject subject, Verb verb, SentenceObject object) {
        given:
        def randomWordSourceStub = [
                pickSubject: { subject },
                pickVerb   : { verb },
                pickObject : { object }
        ] as RandomWordSource

        def flashcardGenerator = new FlashcardExpressionGenerator(randomWordSourceStub)

        when:
        return flashcardGenerator.generateSentence()
    }

    private static Verb createVerb() {
        def verbConjugation = new VerbConjugation()
        verbConjugation.put(ConjugationPerson.SINGULAR_1ST, new ConjugatedVerb(coreVerb: "habe"))

        return new Verb(
                polishVerb: new PolishVerb(
                        expressionOutline: "miec (co? X)"
                ),
                germanVerb: new GermanVerb(
                        verbInfinitive: "haben",
                        infix: null,
                        declensionTemplate: new GermanDeclensionTemplate(
                                template: "X",
                                objectDefinitionByPlaceholder: [(ObjectPlaceholder.X): new ObjectDefinition(
                                        objectCase: Case.ACCUSATIVE,
                                        objectClass: ObjectClass.ANYTHING
                                )]
                        ),
                        conjugation: verbConjugation
                )
        )
    }

    private static NounObject createNounObject() {
        def nounDeclension = new NounDeclension()
        nounDeclension.put(ObjectNumber.SINGULAR, Case.ACCUSATIVE, "Hund")

        return new NounObject(
                new Noun(
                        polishNoun: new PolishNoun(
                                noun: "pies"
                        ),
                        germanNoun: new GermanNoun(
                                noun: "Hund",
                                gender: Gender.MASCULINE,
                                declension: nounDeclension
                        ),
                        objectClass: ObjectClass.PERSON
                ),
                Determiner.DEFINITE_ARTICLE,
                ObjectNumber.SINGULAR,
                Adjective.NULL_ADJECTIVE
        )
    }

}
