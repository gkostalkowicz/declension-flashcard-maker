package com.gk.dfm.domain.preposition.german

import com.gk.dfm.domain.object.SentenceObject
import com.gk.dfm.domain.preposition.Preposition

import static com.gk.dfm.domain.preposition.Preposition.*

/**
 * Created by Mr. President on 13.07.2016.
 */
class GermanPrepositionDecliner {

    static String createExpression(Preposition preposition, SentenceObject object) {
        return prepositionToGerman(preposition) + " " + object.germanObject.decline(preposition.objectCase)
    }

    private static String prepositionToGerman(Preposition preposition) {
        switch (preposition) {
            case AUS:
                return "aus"
            case AUSSER:
                return "außer"
            case BEI:
                return "bei"
            case GEGENUEBER:
                return "gegenüber"
            case MIT:
                return "mit"
            case NACH:
                return "nach"
            case SEIT:
                return "seit"
            case VON:
                return "von"
            case ZU:
                return "zu"

            case BIS:
                return "bis"
            case DURCH:
                return "durch"
            case ENTLANG:
                return "entlang"
            case FUER:
                return "für"
            case GEGEN:
                return "gegen"
            case OHNE:
                return "ohne"
            case UM:
                return "um"

            default:
                throw new RuntimeException("Unknown preposition: $preposition")
        }
    }

}
