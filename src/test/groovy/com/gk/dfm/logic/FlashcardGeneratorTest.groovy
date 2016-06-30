package com.gk.dfm.logic

import com.gk.dfm.domain.Case
import com.gk.dfm.domain.object.noun.*
import com.gk.dfm.domain.subject.Subject
import com.gk.dfm.domain.verb.*
import org.junit.Test

/**
 * Created by Mr. President on 6/19/2016.
 */
class FlashcardGeneratorTest {

    @Test
    void "for given word lists when generateFlashcard then generate 1 valid flashcard"() {
        given:
        def subject = Subject.SINGULAR_1ST

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
                        suffix: null
                )
        )

        def object = new NounObject(
                new Noun(
                        polishNoun: new PolishNoun(
                                noun: "pies"
                        ),
                        germanNoun: new GermanNoun(
                                noun: "Hund",
                                gender: Gender.MASCULINE
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
        assert flashcard == "ja, miec (co? pies)" + "\t" + "ich haben den Hund"
    }

}
