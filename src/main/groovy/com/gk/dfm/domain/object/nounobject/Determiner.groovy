package com.gk.dfm.domain.object.nounobject

import com.gk.dfm.domain.object.adjective.german.DeclensionType

import static com.gk.dfm.domain.object.adjective.german.DeclensionType.*

/**
 * Determiner, as named in
 * <a href="https://www.lsa.umich.edu/german/hmr/Grammatik/Adjektive/Adjektivendungen.html">this source</a>.
 * <p>
 * Created by Mr. President on 6/19/2016.
 */
enum Determiner {

    DEFINITE_ARTICLE(WEAK, false, true, true),
    DIESER(WEAK, false, true, true),
    JEDER(WEAK, false, true, false),
    ALLE(WEAK, false, false, true),
    JENER(WEAK, false, true, true),
    SOLCHER(WEAK, false, true, true),
    WELCHER(WEAK, false, true, true),

    INDEFINITE_ARTICLE(MIXED, false, true, false),
    KEIN(MIXED, false, true, true),
    SINGULAR_1ST_POSSESSIVE(MIXED, true, true, true),
    SINGULAR_2ND_POSSESSIVE(MIXED, true, true, true),
    MASCULINE_SINGULAR_3RD_POSSESSIVE(MIXED, true, true, true),
    FEMININE_SINGULAR_3RD_POSSESSIVE(MIXED, true, true, true),
    NEUTER_SINGULAR_3RD_POSSESSIVE(MIXED, true, true, true),
    PLURAL_1ST_POSSESSIVE(MIXED, true, true, true),
    PLURAL_2ND_POSSESSIVE(MIXED, true, true, true),
    PLURAL_3RD_POSSESSIVE(MIXED, true, true, true),
    FORMAL_2ND_POSSESSIVE(MIXED, true, true, true),

    NO_DETERMINER(STRONG, false, true, true),
    EINIGE(STRONG, false, false, true),
    MEHRERE(STRONG, false, false, true),
    VIELE(STRONG, false, false, true),
    WENIGE(STRONG, false, false, true);

    private static final WEAK_DECLENSION_DETERMINERS = values().findAll({ it.declensionType == WEAK })
    private static final MIXED_DECLENSION_DETERMINERS = values().findAll({ it.declensionType == MIXED })
    private static final STRONG_DECLENSION_DETERMINERS = values().findAll({ it.declensionType == STRONG })

    final DeclensionType declensionType
    final boolean possessivePronoun
    final boolean hasSingularForm
    final boolean hasPluralForm

    private Determiner(DeclensionType declensionType, boolean possessivePronoun, boolean hasSingularForm,
                       boolean hasPluralForm) {
        this.declensionType = declensionType
        this.possessivePronoun = possessivePronoun
        this.hasSingularForm = hasSingularForm
        this.hasPluralForm = hasPluralForm
    }

    public static List<Determiner> weakDeclensionDeterminers() {WEAK_DECLENSION_DETERMINERS}
    public static List<Determiner> mixedDeclensionDeterminers() {MIXED_DECLENSION_DETERMINERS}
    public static List<Determiner> strongDeclensionDeterminers() {STRONG_DECLENSION_DETERMINERS}

}
