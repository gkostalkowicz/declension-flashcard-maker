package com.gk.dfm.domain.preposition.polish

import com.gk.dfm.domain.object.SentenceObject
import com.gk.dfm.domain.preposition.Preposition

import static com.gk.dfm.domain.preposition.Preposition.*

/**
 * Created by Mr. President on 13.07.2016.
 */
class PolishPrepositionDecliner {

    static String createExpression(Preposition preposition, SentenceObject object) {
        return prepositionToPolish(preposition) + ", " + object.polishObject.decline()
    }

    private static String prepositionToPolish(Preposition preposition) {
        switch (preposition) {
            case AUS:
                return "z(ze środka)"
            case AUSSER:
                return "poza/oprócz"
            case BEI:
                return "u"
            case GEGENUEBER:
                return "naprzeciwko"
            case MIT:
                return "z(czymś/kimś)"
            case NACH:
                return "do(miejsca)"
            case SEIT:
                return "od(momentu)"
            case VON:
                return "od,z(ogólnie)"
            case ZU:
                return "do(osoby)"

            case BIS:
                return "do(od-do)"
            case DURCH:
                return "przez"
            case ENTLANG:
                return "wzdłuż"
            case FUER:
                return "dla"
            case GEGEN:
                return "przeciw"
            case OHNE:
                return "bez"
            case UM:
                return "o/wokół"

            default:
                throw new RuntimeException("Unknown preposition: $preposition")
        }
    }
    
}
