package com.gk.dfm.domain.verb

import com.gk.dfm.domain.object.SentenceObject
import com.gk.dfm.domain.object.noun.ArticleType

/**
 * Created by Mr. President on 6/12/2016.
 */
class GermanDeclensionTemplate {

    String template
    Map<ObjectPlaceholder, ObjectDefinition> objectDefinitionByPlaceholder

    String declineTemplate(Map<ObjectPlaceholder, SentenceObject> objectByPlaceholder) {
        def declinedTemplate = template

        objectByPlaceholder.forEach({ placeholder, object ->
            def declinedObject = object.germanObject.decline(objectDefinitionByPlaceholder[placeholder].objectCase)
            declinedTemplate = declinedTemplate.replace(placeholder.name(), declinedObject)
        })

        return declinedTemplate
    }

}
