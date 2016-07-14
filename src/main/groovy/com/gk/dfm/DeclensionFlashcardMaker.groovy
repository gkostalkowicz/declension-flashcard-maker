package com.gk.dfm

import com.gk.dfm.domain.expression.NominativeExpression
import com.gk.dfm.domain.expression.PrepositionExpression
import com.gk.dfm.domain.expression.Sentence
import com.gk.dfm.input.NounAndAdjectiveListReader
import com.gk.dfm.input.VerbListReader
import com.gk.dfm.logic.RandomExpressionGenerator
import com.gk.dfm.logic.impl.source.RandomWordSource
import com.gk.dfm.render.Renderer
import com.gk.dfm.render.german.GermanRenderer
import com.gk.dfm.render.polish.PolishRenderer
import com.gk.dfm.repository.AdjectiveDeclensionRepository
import com.gk.dfm.repository.NounDeclensionRepository
import com.gk.dfm.repository.VerbConjugationRepository

/**
 * Created by Mr. President on 6/12/2016.
 */
class DeclensionFlashcardMaker {

    private static final FLASHCARD_CNT = 1000
    private static final OUTPUT_COLUMN_SEPARATOR = "\t"

    private RandomExpressionGenerator expressionGenerator
    private Renderer germanRenderer
    private Renderer polishRenderer

    static void main(String[] args) {
        new DeclensionFlashcardMaker(args)
    }

    private DeclensionFlashcardMaker(String[] args) {
        def verbsFilename = args[0]
        def nounsFilename = args[1]
        def verbRepositoryFilename = args[2]
        def nounRepositoryFilename = args[3]
        def adjectiveRepositoryFilename = args[4]
        def outputFilename = args[5]

        def verbConjugationRepository = new VerbConjugationRepository(verbRepositoryFilename)
        def nounDeclensionRepository = new NounDeclensionRepository(nounRepositoryFilename)
        def adjectiveDeclensionRepository = new AdjectiveDeclensionRepository(adjectiveRepositoryFilename)
        try {
            def randomWordSource = new RandomWordSource()
            def verbListReader = new VerbListReader(verbConjugationRepository)
            def nounAndAdjectiveListReader = new NounAndAdjectiveListReader(nounDeclensionRepository,
                    adjectiveDeclensionRepository)

            randomWordSource.setVerbs(verbListReader.readVerbs(verbsFilename))
            def nounsAndAdjectives = nounAndAdjectiveListReader.readNounsAndAdjectives(nounsFilename)
            randomWordSource.setNouns(nounsAndAdjectives.nouns)
            randomWordSource.setAdjectives(nounsAndAdjectives.adjectives)

            expressionGenerator = new RandomExpressionGenerator(randomWordSource)
            germanRenderer = new GermanRenderer()
            polishRenderer = new PolishRenderer()

            def outputStream = new BufferedWriter(new FileWriter(outputFilename))
            try {
                for (int i = 0; i < FLASHCARD_CNT; i++) {
                    outputStream.writeLine(generateFlashcard())
                }
            } finally {
                outputStream.close()
            }
        } finally {
            verbConjugationRepository.close()
            nounDeclensionRepository.close()
            adjectiveDeclensionRepository.close()
        }
    }

    private String generateFlashcard() {
        def expression = expressionGenerator.generateExpression()
        if (expression instanceof Sentence) {
            def sentence = expression as Sentence
            return polishRenderer.renderSentence(sentence) + OUTPUT_COLUMN_SEPARATOR +
                    germanRenderer.renderSentence(sentence)
        } else if (expression instanceof NominativeExpression) {
            def nominativeExpression = expression as NominativeExpression
            return polishRenderer.renderNominativeExpression(nominativeExpression) + OUTPUT_COLUMN_SEPARATOR +
                    germanRenderer.renderNominativeExpression(nominativeExpression)
        } else if (expression instanceof PrepositionExpression) {
            def prepositionExpression = expression as PrepositionExpression
            return polishRenderer.renderPrepositionExpression(prepositionExpression) + OUTPUT_COLUMN_SEPARATOR +
                    germanRenderer.renderPrepositionExpression(prepositionExpression)
        } else {
            throw new RuntimeException("Unknown expression class: " + expression.class)
        }
    }

}
