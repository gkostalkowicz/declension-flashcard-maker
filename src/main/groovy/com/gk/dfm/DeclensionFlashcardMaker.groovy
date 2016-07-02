package com.gk.dfm

import com.gk.dfm.input.NounListReader
import com.gk.dfm.input.VerbListReader
import com.gk.dfm.logic.FlashcardGenerator
import com.gk.dfm.logic.RandomWordSource
import com.gk.dfm.repository.NounDeclensionRepository

/**
 * Created by Mr. President on 6/12/2016.
 */
class DeclensionFlashcardMaker {

    private static final FLASHCARDS = 5

    static void main(String[] args) {
        new DeclensionFlashcardMaker(args)
    }

    private DeclensionFlashcardMaker(String[] args) {
        def verbsFilename = args[0]
        def nounsFilename = args[1]
        def verbRepositoryFilename = args[2]
        def nounRepositoryFilename = args[3]

        def nounDeclensionRepository = new NounDeclensionRepository(nounRepositoryFilename)
        try {
            def randomWordSource = new RandomWordSource()
            def verbListReader = new VerbListReader()
            def nounListReader = new NounListReader(nounDeclensionRepository)

            randomWordSource.setVerbs(verbListReader.readVerbs(verbsFilename))
            randomWordSource.setNouns(nounListReader.readNouns(nounsFilename))

            def flashcardGenerator = new FlashcardGenerator(randomWordSource: randomWordSource)

            for (int i = 0; i < FLASHCARDS; i++) {
                println flashcardGenerator.generateFlashcard()
            }
        } finally {
            nounDeclensionRepository.close()
        }
    }

}
