package com.gk.dfm.input

import com.gk.dfm.domain.object.ObjectClass
import com.gk.dfm.domain.verb.german.objects.Case
import com.gk.dfm.domain.verb.german.objects.GermanDeclensionTemplate
import com.gk.dfm.domain.verb.german.objects.ObjectDefinition
import com.gk.dfm.domain.verb.german.objects.ObjectPlaceholder

import java.util.regex.Pattern

/**
 * Created by Mr. President on 6/19/2016.
 */
class GermanDeclensionTemplateParser {

    static GermanDeclensionTemplate parseGermanDeclensionTemplate(String templateString) {
        Map<ObjectPlaceholder, ObjectDefinition> objectDefinitionByPlaceholder = new HashMap<>()
        for (ObjectPlaceholder placeholder : ObjectPlaceholder.values()) {
            def placeholderName = placeholder.name()
            def definitionPattern = Pattern.compile(placeholderName + "\\((.),(.)\\)")
            def definitionMatcher = definitionPattern.matcher(templateString)
            if (definitionMatcher.find()) {
                def objectCase = parseCase(definitionMatcher.group(1))
                def objectClass = parseObjectClass(definitionMatcher.group(2))
                def objectDefinition = new ObjectDefinition(
                        objectCase: objectCase,
                        objectClass: objectClass
                )
                objectDefinitionByPlaceholder[placeholder] = objectDefinition

                templateString = templateString.replaceFirst(definitionPattern, placeholderName)
            }
        }
        return new GermanDeclensionTemplate(
                objectDefinitionByPlaceholder: objectDefinitionByPlaceholder,
                template: templateString)
    }

    private static Case parseCase(String caseString) {
        switch (caseString) {
            case "3":
                return Case.DATIVE;
            case "4":
                return Case.ACCUSATIVE;
            default:
                throw new RuntimeException("Unknown case: '$caseString'");
        }
    }

    private static ObjectClass parseObjectClass(String objectClassString) {
        switch (objectClassString) {
            case "p":
                return ObjectClass.PERSON;
            case "t":
                return ObjectClass.THING;
            case "a":
                return ObjectClass.ANYTHING;
            default:
                throw new RuntimeException("Unknown object class: '$objectClassString'");
        }
    }

}
