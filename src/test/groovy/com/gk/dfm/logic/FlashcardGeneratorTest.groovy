package com.gk.dfm.logic

import com.gk.dfm.domain.object.ObjectClass
import com.gk.dfm.domain.object.SentenceObject
import com.gk.dfm.domain.object.noun.Noun
import com.gk.dfm.domain.object.noun.german.Gender
import com.gk.dfm.domain.object.noun.german.GermanNoun
import com.gk.dfm.domain.object.noun.german.NounDeclension
import com.gk.dfm.domain.object.noun.polish.PolishNoun
import com.gk.dfm.domain.object.nounobject.NounObject
import com.gk.dfm.domain.object.nounobject.german.ArticleType
import com.gk.dfm.domain.object.nounobject.german.ObjectNumber
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
import org.junit.Test

/**
 * Created by Mr. President on 6/19/2016.
 */
class FlashcardGeneratorTest {

    @Test
    void "given a subject, verb and object when generateFlashcard then generate 1 valid flashcard"() {
        given:
        def subject = Subject.SINGULAR_1ST
        def verb = createVerb()
        def object = createNounObject()

        when:
        def flashcard = generateFlashcard(subject, verb, object)

        then:
        assert flashcard == "ja, miec (co? pies)" + "\t" + "ich habe den Hund"
    }

    @Test
    void "given a verb with separable prefix when generateFlashcard then generate 1 valid flashcard"() {
        given:
        def subject = Subject.SINGULAR_1ST
        def verb = createVerb()
        verb.germanVerb.conjugation.put(ConjugationPerson.SINGULAR_1ST,
                new ConjugatedVerb(particle: "auf", coreVerb: "habe"))
        def object = createNounObject()

        when:
        def flashcard = generateFlashcard(subject, verb, object)

        then:
        assert flashcard == "ja, miec (co? pies)" + "\t" + "ich habe den Hund auf"
    }

    @Test
    void "given a verb with infix when generateFlashcard then generate 1 valid flashcard"() {
        given:
        def subject = Subject.SINGULAR_1ST
        def verb = createVerb()
        verb.germanVerb.infix = "auch"
        def object = createNounObject()

        when:
        def flashcard = generateFlashcard(subject, verb, object)

        then:
        assert flashcard == "ja, miec (co? pies)" + "\t" + "ich habe auch den Hund"
    }

    @Test
    void "given a plural object when generateFlashcard then generate flashcard with plural objects"() {
        given:
        def subject = Subject.SINGULAR_1ST
        def verb = createVerb()
        def object = createNounObject()
        object.polishNounObject.number = ObjectNumber.PLURAL
        object.germanNounObject.number = ObjectNumber.PLURAL
        object.germanNounObject.noun.declension.put(ObjectNumber.PLURAL, Case.ACCUSATIVE, "Hunde")

        when:
        def flashcard = generateFlashcard(subject, verb, object)

        then:
        assert flashcard == "ja, miec (co? wiele pies)" + "\t" + "ich habe die Hunde"
    }

    @Test
    void "given a preposition and an article when generateFlashcard then generate flashcard with contraction"() {
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
        def flashcard = generateFlashcard(subject, verb, object)

        then:
        assert flashcard == "ja, ide do (gdzie? kino)" + "\t" + "ich gehe ins Kino"
    }

    private static generateFlashcard(Subject subject, Verb verb, SentenceObject object) {
        given:
        def randomWordSourceStub = [
                pickSubject: { -> subject },
                pickVerb   : { -> verb },
                pickObject : { objectClass -> object }
        ] as RandomWordSource

        def flashcardGenerator = new FlashcardGenerator(randomWordSource: randomWordSourceStub)

        when:
        return flashcardGenerator.generateFlashcard()
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
                ArticleType.DEFINITE,
                ObjectNumber.SINGULAR
        )
    }

}
