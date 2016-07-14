package com.gk.dfm.generate

import com.gk.dfm.domain.expression.Expression
import com.gk.dfm.generate.impl.ExpressionGenerator
import com.gk.dfm.generate.impl.ExpressionType
import com.gk.dfm.generate.impl.source.RandomUtil
import com.gk.dfm.generate.impl.source.RandomWordSource

/**
 * Created by Mr. President on 14.07.2016.
 */
class RandomExpressionGenerator {

    private static final double PICK_NOMINATIVE_EXPRESSION_CHANCE = 0.5
    private static final double PICK_PREPOSITION_EXPRESSION_CHANCE = 0.5
    private static final EXPRESSION_TYPE_TO_PROBABILITY = [
            (ExpressionType.NOMINATIVE_EXPRESSION) : PICK_NOMINATIVE_EXPRESSION_CHANCE,
            (ExpressionType.PREPOSITION_EXPRESSION): PICK_PREPOSITION_EXPRESSION_CHANCE
    ]

    private ExpressionGenerator generator

    RandomExpressionGenerator(RandomWordSource randomWordSource) {
        generator = new ExpressionGenerator(randomWordSource)
    }

    Expression generateExpression() {
        def expressionType = RandomUtil.pickElementWithProbability(EXPRESSION_TYPE_TO_PROBABILITY,
                ExpressionType.SENTENCE)
        switch (expressionType) {
            case ExpressionType.SENTENCE:
                return generator.generateSentence()
            case ExpressionType.NOMINATIVE_EXPRESSION:
                return generator.generateNominativeExpression()
            case ExpressionType.PREPOSITION_EXPRESSION:
                return generator.generatePrepositionExpression()
            default:
                throw new RuntimeException("Unknown expression type: $expressionType")
        }
    }

}
