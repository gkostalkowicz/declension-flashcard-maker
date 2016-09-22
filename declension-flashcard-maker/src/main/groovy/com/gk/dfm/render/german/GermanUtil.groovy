package com.gk.dfm.render.german

import com.gk.dfm.domain.preposition.Preposition

import static com.gk.dfm.domain.preposition.Preposition.*

/**
 * Created by Mr. President on 14.07.2016.
 */
class GermanUtil {

    private static final Map<Preposition, String> GERMAN_PREPOSITION = [
            (AUS)       : "aus",
            (AUSSER)    : "außer",
            (BEI)       : "bei",
            (GEGENUEBER): "gegenüber",
            (MIT)       : "mit",
            (NACH)      : "nach",
            (SEIT)      : "seit",
            (VON)       : "von",
            (ZU)        : "zu",
            (BIS)       : "bis",
            (DURCH)     : "durch",
            (ENTLANG)   : "entlang",
            (FUER)      : "für",
            (GEGEN)     : "gegen",
            (OHNE)      : "ohne",
            (UM)        : "um",
    ]

    static String prepositionToText(Preposition preposition) {
        return GERMAN_PREPOSITION[preposition]
    }

}
