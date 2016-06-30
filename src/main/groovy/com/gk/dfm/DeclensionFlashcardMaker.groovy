package com.gk.dfm

import com.gk.dfm.logic.FlashcardGenerator
import com.gk.dfm.input.NounListReader
import com.gk.dfm.logic.RandomWordSource
import com.gk.dfm.input.VerbListReader

/**
 * Created by Mr. President on 6/12/2016.
 */
class DeclensionFlashcardMaker {

    private static final FLASHCARDS = 50

    static void main(String[] args) {
        new DeclensionFlashcardMaker(args)
    }

    private DeclensionFlashcardMaker(String[] args) {
        String verbsFilename = args[0]
        String nounsFilename = args[1]

        def randomWordSource = new RandomWordSource()
        def verbListReader = new VerbListReader()

        randomWordSource.setVerbs(verbListReader.readVerbs(verbsFilename))
        randomWordSource.setNouns(NounListReader.readNouns(nounsFilename))

        def flashcardGenerator = new FlashcardGenerator(randomWordSource: randomWordSource)

        for (int i = 0; i < FLASHCARDS; i++) {
            println flashcardGenerator.generateFlashcard()
        }
    }

}
