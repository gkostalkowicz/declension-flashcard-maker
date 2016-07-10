package com.gk.dfm.domain.object.nounobject
/**
 * Determiner, as named by
 * <a href="https://www.lsa.umich.edu/german/hmr/Grammatik/Adjektive/Adjektivendungen.html">this source</a>.
 * <p>
 * Created by Mr. President on 6/19/2016.
 */
enum Determiner {

    DEFINITE_ARTICLE(true, true, false),
    DIESER(true, true, false),
    JEDER(true, false, false),
    ALLE(false, true, false),
    JENER(true, true, false),
    SOLCHER(true, true, false),
    WELCHER(true, true, false),

    INDEFINITE_ARTICLE(true, false, false),
    KEIN(true, true, false),
    SINGULAR_1ST_POSSESSIVE(true, true, true),
    SINGULAR_2ND_POSSESSIVE(true, true, true),
    MASCULINE_SINGULAR_3RD_POSSESSIVE(true, true, true),
    FEMININE_SINGULAR_3RD_POSSESSIVE(true, true, true),
    NEUTER_SINGULAR_3RD_POSSESSIVE(true, true, true),
    PLURAL_1ST_POSSESSIVE(true, true, true),
    PLURAL_2ND_POSSESSIVE(true, true, true),
    PLURAL_3RD_POSSESSIVE(true, true, true),
    FORMAL_2ND_POSSESSIVE(true, true, true),

    NO_DETERMINER(true, true, false),
    EINIGE(false, true, false),
    MEHRERE(false, true, false),
    VIELE(false, true, false),
    WENIGE(false, true, false);

    final boolean hasSingularForm;
    final boolean hasPluralForm;
    final boolean possessivePronoun;

    private Determiner(boolean hasSingularForm, boolean hasPluralForm, boolean possessivePronoun) {
        this.hasSingularForm = hasSingularForm
        this.hasPluralForm = hasPluralForm
        this.possessivePronoun = possessivePronoun
    }

}
