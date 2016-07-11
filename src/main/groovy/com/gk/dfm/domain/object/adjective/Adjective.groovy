package com.gk.dfm.domain.object.adjective

import com.gk.dfm.domain.object.adjective.german.GermanAdjective
import com.gk.dfm.domain.object.adjective.german.NullGermanAdjective
import com.gk.dfm.domain.object.adjective.polish.NullPolishAdjective
import com.gk.dfm.domain.object.adjective.polish.PolishAdjective

/**
 * Created by Mr. President on 10.07.2016.
 */
class Adjective {

    static final Adjective NULL_ADJECTIVE = new Adjective(
            polishAdjective: new NullPolishAdjective(),
            germanAdjective: new NullGermanAdjective())

    PolishAdjective polishAdjective
    GermanAdjective germanAdjective

}
