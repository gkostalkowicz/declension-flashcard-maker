package com.gk.dfm

import com.gk.dfm.input.NounAndAdjectiveListReader
import com.gk.dfm.input.VerbListReader
import com.gk.dfm.logic.FlashcardGenerator
import com.gk.dfm.logic.RandomWordSource
import com.gk.dfm.repository.AdjectiveDeclensionRepository
import com.gk.dfm.repository.NounDeclensionRepository
import com.gk.dfm.repository.VerbConjugationRepository

/**
 * Created by Mr. President on 6/12/2016.
 */
class DeclensionFlashcardMaker {

    private static final FLASHCARD_CNT = 1000

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

            def flashcardGenerator = new FlashcardGenerator(randomWordSource: randomWordSource)

            def outputStream = new BufferedWriter(new FileWriter(outputFilename))
            try {
                for (int i = 0; i < FLASHCARD_CNT; i++) {
                    outputStream.writeLine(flashcardGenerator.generateFlashcard())
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

}
