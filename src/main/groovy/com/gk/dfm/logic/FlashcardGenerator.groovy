package com.gk.dfm.logic

import com.gk.dfm.logic.impl.Flashcard
import com.gk.dfm.logic.impl.FlashcardExpressionGenerator
import com.gk.dfm.logic.impl.source.RandomUtil
import com.gk.dfm.logic.impl.source.RandomWordSource

/**
 * Created by Mr. President on 6/19/2016.
 */
class FlashcardGenerator {

    private static final OUTPUT_COLUMN_SEPARATOR = "\t"
    private static final double PICK_NOMINATIVE_EXPRESSION_CHANCE = 0.5
    private static final double PICK_PREPOSITION_EXPRESSION_CHANCE = 0.5
    private static final EXPRESSION_TYPE_TO_PROBABILITY = [
            (ExpressionType.NOMINATIVE_EXPRESSION) : PICK_NOMINATIVE_EXPRESSION_CHANCE,
            (ExpressionType.PREPOSITION_EXPRESSION): PICK_PREPOSITION_EXPRESSION_CHANCE
    ]

    private FlashcardExpressionGenerator generator

    FlashcardGenerator(RandomWordSource randomWordSource) {
        generator = new FlashcardExpressionGenerator(randomWordSource)
    }

    String generateFlashcard() {
        def expressionType = RandomUtil.pickElementWithProbability(EXPRESSION_TYPE_TO_PROBABILITY,
                ExpressionType.SENTENCE)
        def flashcard
        switch (expressionType) {
            case ExpressionType.SENTENCE:
                flashcard = generator.generateSentence()
                break
            case ExpressionType.NOMINATIVE_EXPRESSION:
                flashcard = generator.generateNominativeExpression()
                break
            case ExpressionType.PREPOSITION_EXPRESSION:
                flashcard = generator.generatePrepositionExpression()
                break
            default:
                throw new RuntimeException("Unknown expression type: $expressionType")
        }
        return flashcardToString(flashcard)
    }

    private static String flashcardToString(Flashcard flashcard) {
        return flashcard.polish + OUTPUT_COLUMN_SEPARATOR + flashcard.german
    }

    private static enum ExpressionType {
        SENTENCE, NOMINATIVE_EXPRESSION, PREPOSITION_EXPRESSION
    }

}
