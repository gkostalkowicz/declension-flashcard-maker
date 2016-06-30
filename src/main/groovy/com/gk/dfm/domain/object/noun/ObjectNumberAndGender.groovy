package com.gk.dfm.domain.object.noun

/**
 * Created by Mr. President on 6/19/2016.
 */
enum ObjectNumberAndGender {

    MASCULINE_SINGULAR, FEMININE_SINGULAR, NEUTER_SINGULAR, PLURAL;

    static ObjectNumberAndGender get(ObjectNumber number, Gender gender) {
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
                    throw new RuntimeException("Can't get ObjectNumberAndGender for $gender gender");
            }
        } else {
            throw new RuntimeException("Can't get ObjectNumberAndGender for $number number");
        }
    }

}