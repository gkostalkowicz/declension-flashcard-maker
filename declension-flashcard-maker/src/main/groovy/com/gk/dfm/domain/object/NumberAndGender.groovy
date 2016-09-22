package com.gk.dfm.domain.object

import com.gk.dfm.domain.object.noun.german.Gender
import com.gk.dfm.domain.object.nounobject.ObjectNumber

/**
 * Created by Mr. President on 6/19/2016.
 */
enum NumberAndGender {

    MASCULINE_SINGULAR, FEMININE_SINGULAR, NEUTER_SINGULAR, PLURAL;

    static NumberAndGender get(ObjectNumber number, Gender gender) {
        if (number == ObjectNumber.PLURAL) {
            return PLURAL
        } else if (number == ObjectNumber.SINGULAR) {
            switch (gender) {
                case Gender.MASCULINE:
                    return MASCULINE_SINGULAR
                case Gender.FEMININE:
                    return FEMININE_SINGULAR
                case Gender.NEUTER:
                    return NEUTER_SINGULAR
                default:
                    throw new RuntimeException("Can't get NumberAndGender for $gender gender");
            }
        } else {
            throw new RuntimeException("Can't get NumberAndGender for $number number");
        }
    }

}