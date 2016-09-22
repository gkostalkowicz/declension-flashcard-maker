package com.gk.dfm.domain.verb

import com.gk.dfm.domain.verb.german.GermanVerb
import com.gk.dfm.domain.verb.polish.PolishVerb
import groovy.transform.ToString

/**
 * Created by Mr. President on 6/12/2016.
 */
@ToString(includePackage = false)
class Verb {

    PolishVerb polishVerb
    GermanVerb germanVerb

}
