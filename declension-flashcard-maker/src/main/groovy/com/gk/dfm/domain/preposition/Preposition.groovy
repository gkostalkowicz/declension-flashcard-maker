package com.gk.dfm.domain.preposition

import com.gk.dfm.domain.verb.german.objects.Case

/**
 * Created by Mr. President on 13.07.2016.
 */
enum Preposition {

    AUS(Case.DATIVE),
    AUSSER(Case.DATIVE),
    BEI(Case.DATIVE),
    GEGENUEBER(Case.DATIVE),
    MIT(Case.DATIVE),
    NACH(Case.DATIVE),
    SEIT(Case.DATIVE),
    VON(Case.DATIVE),
    ZU(Case.DATIVE),

    BIS(Case.ACCUSATIVE),
    DURCH(Case.ACCUSATIVE),
    ENTLANG(Case.ACCUSATIVE),
    FUER(Case.ACCUSATIVE),
    GEGEN(Case.ACCUSATIVE),
    OHNE(Case.ACCUSATIVE),
    UM(Case.ACCUSATIVE);

    final Case objectCase

    private Preposition(Case objectCase) {
        this.objectCase = objectCase
    }

}
