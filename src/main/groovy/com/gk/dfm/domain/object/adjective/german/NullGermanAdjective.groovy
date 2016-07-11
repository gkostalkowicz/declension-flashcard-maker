package com.gk.dfm.domain.object.adjective.german

import com.gk.dfm.domain.object.NumberAndGender
import com.gk.dfm.domain.verb.german.objects.Case

/**
 * Created by Mr. President on 10.07.2016.
 */
class NullGermanAdjective implements GermanAdjective {

    @Override
    String declineBeforeNoun(DeclensionType declensionType, NumberAndGender numberAndGender, Case objectCase) {
        return ""
    }

}
