package com.gk.dfm.render.polish

import com.gk.dfm.domain.expression.NominativeExpression
import com.gk.dfm.domain.expression.PrepositionExpression
import com.gk.dfm.domain.expression.Sentence
import com.gk.dfm.domain.object.SentenceObject
import com.gk.dfm.domain.object.adjective.Adjective
import com.gk.dfm.domain.object.noun.Noun
import com.gk.dfm.domain.object.nounobject.Determiner
import com.gk.dfm.domain.object.nounobject.NounObject
import com.gk.dfm.domain.object.nounobject.ObjectNumber
import com.gk.dfm.domain.preposition.Preposition
import com.gk.dfm.domain.subject.Subject
import com.gk.dfm.render.Renderer
import com.gk.dfm.render.german.GermanUtil
import com.google.common.base.Joiner

import static com.gk.dfm.domain.object.nounobject.Determiner.*
import static com.gk.dfm.domain.preposition.Preposition.*
import static com.gk.dfm.domain.subject.Subject.*

/**
 * Created by Mr. President on 14.07.2016.
 */
class PolishRenderer implements Renderer {

    private static final USE_GERMAN_NOUN = true
    private static final USE_GERMAN_ADJECTIVE = true
    private static final USE_GERMAN_DETERMINER = true
    private static final USE_GERMAN_PREPOSITION = true

    private static final Map<Determiner, String> POLISH_DETERMINER_TEXT = [
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
            (NO_DETERMINER)                    : ".",
            (EINIGE)                           : "parę",
            (MEHRERE)                          : "kilka",
            (VIELE)                            : "wiele",
            (WENIGE)                           : "niewiele"
    ]

    private static final Map<Determiner, String> GERMAN_DETERMINER_TEXT = [
            (DEFINITE_ARTICLE)                 : "der",
            (DIESER)                           : "dieser",
            (JEDER)                            : "jeder",
            (ALLE)                             : "alle",
            (JENER)                            : "jener",
            (SOLCHER)                          : "solcher",
            (WELCHER)                          : "welcher",
            (INDEFINITE_ARTICLE)               : "ein",
            (KEIN)                             : "kein",
            (SINGULAR_1ST_POSSESSIVE)          : "mein",
            (SINGULAR_2ND_POSSESSIVE)          : "dein",
            (MASCULINE_SINGULAR_3RD_POSSESSIVE): "sein",
            (FEMININE_SINGULAR_3RD_POSSESSIVE) : "ihr",
            (NEUTER_SINGULAR_3RD_POSSESSIVE)   : "sein",
            (PLURAL_1ST_POSSESSIVE)            : "unser",
            (PLURAL_2ND_POSSESSIVE)            : "euer",
            (PLURAL_3RD_POSSESSIVE)            : "ihr",
            (FORMAL_2ND_POSSESSIVE)            : "Ihr",
            (NO_DETERMINER)                    : ".",
            (EINIGE)                           : "einige",
            (MEHRERE)                          : "mehrere",
            (VIELE)                            : "viele",
            (WENIGE)                           : "wenige"
    ]

    private static final Map<Subject, String> SUBJECT_TEXT = [
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

    private static final Map<Preposition, String> PREPOSITION_TEXT = [
            (AUS)       : "z(ze środka)",
            (AUSSER)    : "poza/oprócz",
            (BEI)       : "u",
            (GEGENUEBER): "naprzeciwko",
            (MIT)       : "z(czymś/kimś)",
            (NACH)      : "do(miejsca)",
            (SEIT)      : "od(momentu)",
            (VON)       : "od,z(ogólnie)",
            (ZU)        : "do(osoby)",
            (BIS)       : "do(od-do)",
            (DURCH)     : "przez",
            (ENTLANG)   : "wzdłuż",
            (FUER)      : "dla",
            (GEGEN)     : "przeciw",
            (OHNE)      : "bez",
            (UM)        : "o/wokół"
    ]

    private static final List<Determiner> DETERMINERS_WITH_IMPLICIT_PLURAL_NUMBER = [ALLE, EINIGE, MEHRERE, VIELE,
                                                                                     WENIGE]

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
        return renderPreposition(expression.preposition) + " " + renderObject(expression.object)
    }

    private static String renderObject(SentenceObject object) {
        if (object instanceof NounObject) {
            renderNounObject(object as NounObject)
        } else {
            throw new RuntimeException("Unknown sentence object class: " + object.class)
        }
    }

    private static String renderNounObject(NounObject object) {
        object.with {
            def explicitPluralNumber = number == ObjectNumber.PLURAL &&
                    !DETERMINERS_WITH_IMPLICIT_PLURAL_NUMBER.contains(determiner)
            def pluralNumberText = explicitPluralNumber ? "(wiele)" : null

            def determinerText = renderDeterminer(determiner)
            def adjectiveText = adjective != null ? renderAdjective(adjective) : null
            def nounText = renderNoun(noun)

            def objectItems = [determinerText, pluralNumberText, adjectiveText, nounText]
            return "(" + Joiner.on(" - ").skipNulls().join(objectItems) + ")"
        }
    }

    private static String renderNoun(Noun noun) {
        return USE_GERMAN_NOUN ? noun.germanNoun.noun : noun.polishNoun.noun
    }

    private static String renderDeterminer(Determiner determiner) {
        return USE_GERMAN_DETERMINER ? GERMAN_DETERMINER_TEXT[determiner] : POLISH_DETERMINER_TEXT[determiner]
    }

    private static String renderAdjective(Adjective adjective) {
        return USE_GERMAN_ADJECTIVE ? adjective.germanAdjective.adjective : adjective.polishAdjective.adjective
    }

    private static String renderSubject(Subject subject) {
        return SUBJECT_TEXT[subject]
    }

    private static String renderPreposition(Preposition preposition) {
        return USE_GERMAN_PREPOSITION ? GermanUtil.prepositionToText(preposition) : PREPOSITION_TEXT[preposition]
    }

}
