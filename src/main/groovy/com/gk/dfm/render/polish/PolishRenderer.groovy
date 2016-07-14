package com.gk.dfm.render.polish

import com.gk.dfm.domain.expression.NominativeExpression
import com.gk.dfm.domain.expression.PrepositionExpression
import com.gk.dfm.domain.expression.Sentence
import com.gk.dfm.domain.object.SentenceObject
import com.gk.dfm.domain.object.adjective.polish.PolishAdjective
import com.gk.dfm.domain.object.nounobject.Determiner
import com.gk.dfm.domain.object.nounobject.german.ObjectNumber
import com.gk.dfm.domain.object.nounobject.polish.PolishNounObject
import com.gk.dfm.domain.preposition.Preposition
import com.gk.dfm.domain.subject.Subject
import com.gk.dfm.render.Renderer

import static com.gk.dfm.domain.object.nounobject.Determiner.*
import static com.gk.dfm.domain.preposition.Preposition.*
import static com.gk.dfm.domain.subject.Subject.*

/**
 * Created by Mr. President on 14.07.2016.
 */
class PolishRenderer implements Renderer {

    private static final Map<Subject, String> POLISH_SUBJECT = [
            (SINGULAR_1ST)          : "ja",
            (SINGULAR_2ND)          : "ty",
            (MASCULINE_SINGULAR_3RD): "on",
            (FEMININE_SINGULAR_3RD) : "ona",
            (NEUTER_SINGULAR_3RD)   : "ono",
            (PLURAL_1ST)            : "my",
            (PLURAL_2ND)            : "wy",
            (PLURAL_3RD)            : "oni/one",
            (FORMAL_2ND)            : "pani/pan"
    ]

    private static final Map<Determiner, String> POLISH_DETERMINER = [
            (DEFINITE_ARTICLE)                 : "(określony)",
            (DIESER)                           : "ten",
            (JEDER)                            : "każdy",
            (ALLE)                             : "wszystkie",
            (JENER)                            : "tamten",
            (SOLCHER)                          : "taki",
            (WELCHER)                          : "który/jaki",
            (INDEFINITE_ARTICLE)               : "(nieokreślony)",
            (SINGULAR_1ST_POSSESSIVE)          : "mój",
            (SINGULAR_2ND_POSSESSIVE)          : "twój",
            (MASCULINE_SINGULAR_3RD_POSSESSIVE): "jego(ten)",
            (FEMININE_SINGULAR_3RD_POSSESSIVE) : "jej",
            (NEUTER_SINGULAR_3RD_POSSESSIVE)   : "jego(to)",
            (PLURAL_1ST_POSSESSIVE)            : "nasz",
            (PLURAL_2ND_POSSESSIVE)            : "wasz",
            (PLURAL_3RD_POSSESSIVE)            : "ich",
            (FORMAL_2ND_POSSESSIVE)            : "pani/pana",
            (KEIN)                             : "żaden",
            (NO_DETERMINER)                    : "(-)",
            (EINIGE)                           : "parę",
            (MEHRERE)                          : "kilka",
            (VIELE)                            : "wiele",
            (WENIGE)                           : "niewiele"
    ]

    @Override
    String renderSentence(Sentence sentence) {
        sentence.with {
            def subjectString = renderSubject(subject)

            def expression = verb.polishVerb.expressionOutline
            objectByPlaceholder.forEach({ placeholder, object ->
                expression = expression.replace(placeholder.name(), renderObject(object))
            })

            return subjectString + ", " + expression
        }
    }

    @Override
    String renderNominativeExpression(NominativeExpression expression) {
        return renderObject(expression.object)
    }

    @Override
    String renderPrepositionExpression(PrepositionExpression expression) {
        return renderPreposition(expression.preposition) + ", " + renderObject(expression.object)
    }

    private static String renderObject(SentenceObject object) {
        def polishObject = object.polishObject
        if (polishObject instanceof PolishNounObject) {
            renderPolishNounObject(polishObject as PolishNounObject)
        } else {
            throw new RuntimeException("Unknown sentence object class: " + polishObject.class)
        }
    }

    private static String renderPolishNounObject(PolishNounObject object) {
        object.with {
            def numberPrefix = number == ObjectNumber.PLURAL ? "(wiele) " : ""
            def declinedDeterminer = renderDeterminer(determiner)
            def declinedAdjective = adjective != null ? renderAdjective(adjective) + " " : ""
            return numberPrefix + declinedDeterminer + " " + declinedAdjective + noun.noun
        }
    }

    private static String renderDeterminer(Determiner determiner) {
        return POLISH_DETERMINER[determiner]
    }

    private static String renderAdjective(PolishAdjective adjective) {
        return adjective.adjective
    }

    private static String renderSubject(Subject subject) {
        return POLISH_SUBJECT[subject]
    }

    private static String renderPreposition(Preposition preposition) {
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
