package com.gk.dfm.domain.expression

import com.gk.dfm.domain.object.SentenceObject
import com.gk.dfm.domain.subject.Subject
import com.gk.dfm.domain.verb.Verb
import com.gk.dfm.domain.verb.german.objects.ObjectPlaceholder

/**
 * Created by Mr. President on 14.07.2016.
 */
class Sentence implements Expression {

    Subject subject
    Verb verb
    Map<ObjectPlaceholder, SentenceObject> objectByPlaceholder

}
