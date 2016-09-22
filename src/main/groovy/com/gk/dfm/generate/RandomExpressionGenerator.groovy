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

    private static final Map<ExpressionType, Double> EXPRESSION_TYPE_TO_PROBABILITY = [
            //(ExpressionType.NOMINATIVE_EXPRESSION) : 0.25,
            //(ExpressionType.PREPOSITION_EXPRESSION): 0.75,
            (ExpressionType.SENTENCE)              : null
    ]

    private ExpressionGenerator generator

    RandomExpressionGenerator(RandomWordSource randomWordSource) {
        generator = new ExpressionGenerator(randomWordSource)
    }

    Expression generateExpression() {
        def expressionType = RandomUtil.pickElementWithProbability(EXPRESSION_TYPE_TO_PROBABILITY)
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
