package com.gk.dfm

import com.gk.dfm.domain.expression.Sentence
import com.gk.dfm.domain.object.NumberAndGender
import com.gk.dfm.domain.object.ObjectClass
import com.gk.dfm.domain.object.SentenceObject
import com.gk.dfm.domain.object.adjective.Adjective
import com.gk.dfm.domain.object.adjective.german.AdjectiveDeclension
import com.gk.dfm.domain.object.adjective.german.DeclensionType
import com.gk.dfm.domain.object.adjective.german.GermanAdjective
import com.gk.dfm.domain.object.adjective.polish.PolishAdjective
import com.gk.dfm.domain.object.noun.Noun
import com.gk.dfm.domain.object.noun.german.Gender
import com.gk.dfm.domain.object.noun.german.GermanNoun
import com.gk.dfm.domain.object.noun.german.NounDeclension
import com.gk.dfm.domain.object.noun.polish.PolishNoun
import com.gk.dfm.domain.object.nounobject.Determiner
import com.gk.dfm.domain.object.nounobject.NounObject
import com.gk.dfm.domain.object.nounobject.ObjectNumber
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
import com.gk.dfm.generate.impl.ExpressionGenerator
import com.gk.dfm.generate.impl.source.RandomWordSource
import com.gk.dfm.render.german.GermanRenderer
import com.gk.dfm.render.polish.PolishRenderer
import org.junit.Test

/**
 * Created by Mr. President on 6/19/2016.
 */
class IntegrationTest {

    PolishRenderer polishRenderer = new PolishRenderer()
    GermanRenderer germanRenderer = new GermanRenderer()

    @Test
    void "given a subject, verb and object when generate and render then generate and render 1 valid flashcard"() {
        given:
        def subject = Subject.SINGULAR_1ST
        def verb = createVerb()
        def object = createNounObject()

        when:
        def sentence = generateSentence(subject, verb, object)

        then:
        assert polishRenderer.renderSentence(sentence) == "ja, miec (co? (der - Hund))"
        assert germanRenderer.renderSentence(sentence) == "ich habe den Hund"
    }

    @Test
    void "given no determiner when renderSentence then render a flashcard without a determiner"() {
        given:
        def subject = Subject.SINGULAR_1ST
        def verb = createVerb()
        def object = createNounObject()
        object.determiner = Determiner.NO_DETERMINER

        when:
        def sentence = generateSentence(subject, verb, object)

        then:
        assert polishRenderer.renderSentence(sentence) == "ja, miec (co? (. - Hund))"
        assert germanRenderer.renderSentence(sentence) == "ich habe Hund"
    }

    @Test
    void "given a verb with separable prefix when renderSentence then render a flashcard ending with the particle"() {
        given:
        def subject = Subject.SINGULAR_1ST
        def verb = createVerb()
        verb.germanVerb.conjugation.put(ConjugationPerson.SINGULAR_1ST,
                new ConjugatedVerb(particle: "auf", coreVerb: "habe"))
        def object = createNounObject()

        when:
        def sentence = generateSentence(subject, verb, object)

        then:
        assert polishRenderer.renderSentence(sentence) == "ja, miec (co? (der - Hund))"
        assert germanRenderer.renderSentence(sentence) == "ich habe den Hund auf"
    }

    @Test
    void "given a verb with an infix when renderSentence then render a flashcard with the infix"() {
        given:
        def subject = Subject.SINGULAR_1ST
        def verb = createVerb()
        verb.germanVerb.infix = "auch"
        def object = createNounObject()

        when:
        def sentence = generateSentence(subject, verb, object)

        then:
        assert polishRenderer.renderSentence(sentence) == "ja, miec (co? (der - Hund))"
        assert germanRenderer.renderSentence(sentence) == "ich habe auch den Hund"
    }

    @Test
    void "given a plural object when renderSentence then render a flashcard with plural objects"() {
        given:
        def subject = Subject.SINGULAR_1ST
        def verb = createVerb()
        def object = createNounObject()
        object.number = ObjectNumber.PLURAL
        object.noun.germanNoun.declension.put(ObjectNumber.PLURAL, Case.ACCUSATIVE, "Hunde")

        when:
        def sentence = generateSentence(subject, verb, object)

        then:
        assert polishRenderer.renderSentence(sentence) == "ja, miec (co? (der - (wiele) - Hund))"
        assert germanRenderer.renderSentence(sentence) == "ich habe die Hunde"
    }

    @Test
    void "given a preposition and an article when renderSentence then render a flashcard with a contraction"() {
        given:
        def subject = Subject.SINGULAR_1ST
        def verb = createVerb()
        def object = createNounObject()
        verb.polishVerb.expressionOutline = "ide do (gdzie? X)"
        verb.germanVerb.verbInfinitive = "gehen"
        verb.germanVerb.conjugation.put(ConjugationPerson.SINGULAR_1ST, new ConjugatedVerb(coreVerb: "gehe"))
        verb.germanVerb.declensionTemplate.template = "in X"
        object.noun.polishNoun.noun = "kino"
        object.noun.germanNoun.noun = "Kino"
        object.noun.germanNoun.gender = Gender.NEUTER
        object.noun.germanNoun.declension.put(ObjectNumber.SINGULAR, Case.ACCUSATIVE, "Kino")

        when:
        def sentence = generateSentence(subject, verb, object)

        then:
        assert polishRenderer.renderSentence(sentence) == "ja, ide do (gdzie? (der - Kino))"
        assert germanRenderer.renderSentence(sentence) == "ich gehe ins Kino"
    }

    @Test
    void "given an adjective when renderSentence then render a flashcard with the adjective"() {
        given:
        def subject = Subject.SINGULAR_1ST
        def verb = createVerb()
        def object = createNounObject()
        object.adjective = new Adjective(
                polishAdjective: new PolishAdjective(adjective: "ciemny"),
                germanAdjective: new GermanAdjective(adjective: "dunkel",
                        declension: new AdjectiveDeclension()
                ))
        object.adjective.germanAdjective.declension.put(DeclensionType.WEAK,
                NumberAndGender.MASCULINE_SINGULAR, Case.ACCUSATIVE, "dunklen")

        when:
        def sentence = generateSentence(subject, verb, object)

        then:
        assert polishRenderer.renderSentence(sentence) == "ja, miec (co? (der - dunkel - Hund))"
        assert germanRenderer.renderSentence(sentence) == "ich habe den dunklen Hund"
    }

    @Test
    void "given an object when renderNominativeExpression then render a valid nominative expression"() {
        given:
        def nounDeclension = new NounDeclension()
        nounDeclension.put(ObjectNumber.SINGULAR, Case.NOMINATIVE, "Hund")
        def object = new NounObject(
                noun: new Noun(
                        polishNoun: new PolishNoun(
                                noun: "pies"
                        ),
                        germanNoun: new GermanNoun(
                                noun: "Hund",
                                gender: Gender.MASCULINE,
                                declension: nounDeclension),
                        objectClass: ObjectClass.ANYTHING
                ),
                determiner: Determiner.DEFINITE_ARTICLE,
                number: ObjectNumber.SINGULAR,
                adjective: null)
        def randomWordSource = [pickObject: { object }] as RandomWordSource
        def generator = new ExpressionGenerator(randomWordSource)

        when:
        def expression = generator.generateNominativeExpression()

        then:
        assert polishRenderer.renderNominativeExpression(expression) == "(der - Hund)"
        assert germanRenderer.renderNominativeExpression(expression) == "der Hund"
    }

    @Test
    void "given an object and preposition when renderPrepositionExpression then render a valid preposition expression"() {
        given:
        def nounDeclension = new NounDeclension()
        nounDeclension.put(ObjectNumber.SINGULAR, Case.DATIVE, "Hund")
        def object = new NounObject(
                noun: new Noun(
                        polishNoun: new PolishNoun(
                                noun: "pies"
                        ),
                        germanNoun: new GermanNoun(
                                noun: "Hund",
                                gender: Gender.MASCULINE,
                                declension: nounDeclension),
                        objectClass: ObjectClass.ANYTHING
                ),
                determiner: Determiner.DEFINITE_ARTICLE,
                number: ObjectNumber.SINGULAR,
                adjective: null)
        def preposition = Preposition.MIT
        def randomWordSource = [
                pickObject     : { object },
                pickPreposition: { preposition }
        ] as RandomWordSource
        def generator = new ExpressionGenerator(randomWordSource)

        when:
        def expression = generator.generatePrepositionExpression()

        then:
        assert polishRenderer.renderPrepositionExpression(expression) == "mit (der - Hund)"
        assert germanRenderer.renderPrepositionExpression(expression) == "mit dem Hund"
    }

    private static Sentence generateSentence(Subject subject, Verb verb, SentenceObject object) {
        given:
        def randomWordSourceStub = [
                pickSubject: { subject },
                pickVerb   : { verb },
                pickObject : { object }
        ] as RandomWordSource

        def flashcardGenerator = new ExpressionGenerator(randomWordSourceStub)

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
                noun: new Noun(
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
                determiner: Determiner.DEFINITE_ARTICLE,
                number: ObjectNumber.SINGULAR,
                adjective: null
        )
    }

}
