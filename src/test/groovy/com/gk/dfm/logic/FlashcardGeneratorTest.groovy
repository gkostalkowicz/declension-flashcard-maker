package com.gk.dfm.logic

import com.gk.dfm.domain.object.ObjectClass
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
    void "for given word lists when generateFlashcard then generate 1 valid flashcard"() {
        given:
        def subject = Subject.SINGULAR_1ST

        def verbConjugation = new VerbConjugation()
        verbConjugation.put(ConjugationPerson.SINGULAR_1ST, new ConjugatedVerb(coreVerb: "habe"))
        def verb = new Verb(
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

        def nounDeclension = new NounDeclension()
        nounDeclension.put(ObjectNumber.SINGULAR, Case.ACCUSATIVE, "Hund")
        def object = new NounObject(
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

        def randomWordSourceStub = [
                pickSubject: { -> subject },
                pickVerb   : { -> verb },
                pickObject : { objectClass -> object }
        ] as RandomWordSource

        def flashcardGenerator = new FlashcardGenerator(randomWordSource: randomWordSourceStub)

        when:
        def flashcard = flashcardGenerator.generateFlashcard()

        then:
        assert flashcard == "ja, miec (co? pies)" + "\t" + "ich habe den Hund"
    }

}
