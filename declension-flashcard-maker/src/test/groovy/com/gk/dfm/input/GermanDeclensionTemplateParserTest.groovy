package com.gk.dfm.input

import com.gk.dfm.domain.object.ObjectClass
import com.gk.dfm.domain.verb.german.objects.Case
import com.gk.dfm.domain.verb.german.objects.ObjectPlaceholder
import org.junit.Test

/**
 * Created by Mr. President on 6/19/2016.
 */
class GermanDeclensionTemplateParserTest {

    @Test
    void "given 1 object definition when parseGermanDeclensionTemplate then return 1 parsed definition"() {
        given:
        def templateString = "von X(3,a)"

        when:
        def template = new GermanDeclensionTemplateParser().parseGermanDeclensionTemplate(templateString)

        then:
        assert template.template == "von X"
        def objectDefinitionByPlaceholder = template.objectDefinitionByPlaceholder
        assert objectDefinitionByPlaceholder.size() == 1
        assert objectDefinitionByPlaceholder[ObjectPlaceholder.X].objectCase == Case.DATIVE
        assert objectDefinitionByPlaceholder[ObjectPlaceholder.X].objectClass == ObjectClass.ANYTHING
        assert objectDefinitionByPlaceholder[ObjectPlaceholder.Y] == null
    }

    @Test
    void "given 2 object definitions when parseGermanDeclensionTemplate then return 2 parsed definitions"() {
        given:
        def templateString = "X(3,p) für Y(4,a)"

        when:
        def template = new GermanDeclensionTemplateParser().parseGermanDeclensionTemplate(templateString)

        then:
        assert template.template == "X für Y"
        def objectDefinitionByPlaceholder = template.objectDefinitionByPlaceholder
        assert objectDefinitionByPlaceholder.size() == 2
        assert objectDefinitionByPlaceholder[ObjectPlaceholder.X].objectCase == Case.DATIVE
        assert objectDefinitionByPlaceholder[ObjectPlaceholder.X].objectClass == ObjectClass.PERSON
        assert objectDefinitionByPlaceholder[ObjectPlaceholder.Y].objectCase == Case.ACCUSATIVE
        assert objectDefinitionByPlaceholder[ObjectPlaceholder.Y].objectClass == ObjectClass.ANYTHING
    }

}
