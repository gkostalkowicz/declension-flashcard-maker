package com.gk.dfm.render.german

import com.gk.dfm.domain.expression.NominativeExpression
import com.gk.dfm.domain.expression.PrepositionExpression
import com.gk.dfm.domain.expression.Sentence
import com.gk.dfm.domain.object.NumberAndGender
import com.gk.dfm.domain.object.SentenceObject
import com.gk.dfm.domain.object.adjective.Adjective
import com.gk.dfm.domain.object.adjective.german.DeclensionType
import com.gk.dfm.domain.object.nounobject.Determiner
import com.gk.dfm.domain.object.nounobject.NounObject
import com.gk.dfm.domain.preposition.Preposition
import com.gk.dfm.domain.subject.Subject
import com.gk.dfm.domain.verb.german.objects.Case
import com.gk.dfm.domain.verb.german.objects.GermanDeclensionTemplate
import com.gk.dfm.domain.verb.german.objects.ObjectPlaceholder
import com.gk.dfm.render.Renderer

import static com.gk.dfm.domain.preposition.Preposition.GEGENUEBER
import static com.gk.dfm.domain.subject.Subject.*

/**
 * Created by Mr. President on 14.07.2016.
 */
class GermanRenderer implements Renderer {

    private static final Map<Subject, String> SUBJECT_TEXT = [
            (SINGULAR_1ST)          : "ich",
            (SINGULAR_2ND)          : "du",
            (MASCULINE_SINGULAR_3RD): "er",
            (FEMININE_SINGULAR_3RD) : "sie",
            (NEUTER_SINGULAR_3RD)   : "es",
            (PLURAL_1ST)            : "wir",
            (PLURAL_2ND)            : "ihr",
            (PLURAL_3RD)            : "sie",
            (FORMAL_2ND)            : "Sie"
    ]

    @Override
    String renderSentence(Sentence sentence) {
        sentence.verb.germanVerb.with {
            def subjectString = renderSubject(sentence.subject)
            def declinedObjects = PrepositionContractor.contractPrepositionsWithArticles(
                    " " + renderDeclensionTemplate(declensionTemplate, sentence.objectByPlaceholder))

            def conjugatedVerb = conjugation.get(sentence.subject.toConjugationPerson())

            def result = new StringBuffer()
            result.append(subjectString)
            result.append(" ")
            result.append(conjugatedVerb.coreVerb)
            if (infix != null) {
                result.append(" ")
                result.append(infix)
            }
            result.append(declinedObjects)
            if (conjugatedVerb.particle != null) {
                result.append(" ")
                result.append(conjugatedVerb.particle)
            }
            return result.toString()
        }
    }

    @Override
    String renderNominativeExpression(NominativeExpression expression) {
        return renderObject(expression.object, Case.NOMINATIVE)
    }

    @Override
    String renderPrepositionExpression(PrepositionExpression expression) {
        expression.with {
            def prepositionText = renderPreposition(preposition)
            def object = renderObject(object, preposition.objectCase)
            return preposition == GEGENUEBER ? object + " " + prepositionText : prepositionText + " " + object
        }
    }

    private static String renderObject(SentenceObject object, Case objectCase) {
        if (object instanceof NounObject) {
            renderNounObject(object as NounObject, objectCase)
        } else {
            throw new RuntimeException("Unknown sentence object class: " + object.class)
        }
    }

    private static String renderNounObject(NounObject object, Case objectCase) {
        object.with {
            def numberAndGender = NumberAndGender.get(number, noun.germanNoun.gender)

            def optionalDeclinedDeterminer = renderDeterminer(determiner, numberAndGender, objectCase)
            def declinedDeterminer = optionalDeclinedDeterminer.map({ it + " " }).orElse("")

            def declinedAdjective = renderAdjective(adjective, DeclensionType.forDeterminer(determiner),
                    numberAndGender, objectCase)
            def declinedNoun = noun.germanNoun.declension.get(number, objectCase)

            return declinedDeterminer + declinedAdjective + declinedNoun
        }
    }

    private static Optional<String> renderDeterminer(Determiner determiner, NumberAndGender objectNumberAndGender,
                                                     Case objectCase) {
        DeterminerDecliner.declineDeterminer(determiner, objectNumberAndGender, objectCase)
    }

    private static String renderAdjective(Adjective adjective, DeclensionType declensionType,
                                          NumberAndGender numberAndGender, Case objectCase) {
        if (adjective == null) {
            return ""
        } else {
            return adjective.germanAdjective.declension.get(declensionType, numberAndGender, objectCase) + " "
        }
    }

    private static String renderSubject(Subject subject) {
        return SUBJECT_TEXT[subject]
    }

    String renderDeclensionTemplate(GermanDeclensionTemplate declensionTemplate,
                                    Map<ObjectPlaceholder, SentenceObject> objectByPlaceholder) {
        def declinedTemplate = declensionTemplate.template

        objectByPlaceholder.forEach({ placeholder, object ->
            def declinedObject = renderObject(object,
                    declensionTemplate.objectDefinitionByPlaceholder[placeholder].objectCase)
            declinedTemplate = declinedTemplate.replace(placeholder.name(), declinedObject)
        })

        return declinedTemplate
    }

    private static String renderPreposition(Preposition preposition) {
        return GermanUtil.prepositionToText(preposition)
    }

}
