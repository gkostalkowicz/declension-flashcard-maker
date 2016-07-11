package com.gk.dfm.domain.object.adjective.polish

/**
 * Created by Mr. President on 10.07.2016.
 */
class RealPolishAdjective implements PolishAdjective {

    String adjective

    @Override
    String declineBeforeNoun() {
        return adjective + " "
    }

}
