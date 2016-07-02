package com.gk.dfm.repository.impl

import com.gk.dfm.domain.Case
import com.gk.dfm.domain.object.noun.Gender
import com.gk.dfm.domain.object.noun.ObjectNumber
import com.gk.dfm.domain.object.noun.german.NounDeclension
import com.google.common.base.CharMatcher
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created by Mr. President on 6/30/2016.
 */
class NounDeclensionFetcher {

    private static final long MILLISECOND_SLEEP_BETWEEN_REQUESTS = 1000L

    private static final Logger log = LoggerFactory.getLogger(getClass())

    static NounDeclension fetchDeclension(Gender gender, String noun) {
        log.info("Fetching declension for '{}', '{}'.", noun, gender)
        sleep(MILLISECOND_SLEEP_BETWEEN_REQUESTS)

        def genderSymbol = getGenderSymbol(gender)
        def document = Jsoup.connect("http://www.canoo.net/inflection/$noun:N:$genderSymbol").get()

        def tables = document.select("#WordClass[title=Noun] > table > tbody")

        def tableNum = tables.size()
        assert tableNum == 1: "$tableNum declension tables found, 1 expected"

        def table = tables.get(0)

        def rowNum = table.children().size()
        assert rowNum == 6: "Declension table contains $rowNum rows, 6 expected"

        def declension = new NounDeclension()
        getDeclinedNouns(Case.DATIVE, table.child(4), "Dativ", declension)
        getDeclinedNouns(Case.ACCUSATIVE, table.child(3), "Akkusativ", declension)
        return declension
    }

    private static String getGenderSymbol(Gender gender) {
        switch (gender) {
            case Gender.MASCULINE:
                return "M"
            case Gender.FEMININE:
                return "F"
            case Gender.NEUTER:
                return "N"
            default:
                throw new UnsupportedOperationException("Can't convert gender " + gender + " to gender symbol")
        }
    }

    private static void getDeclinedNouns(Case declensionCase, Element tableRow, String expectedCaseName,
                                         NounDeclension declension) {
        def columnNum = tableRow.children().size()
        assert columnNum == 5: "Declension table row contains $columnNum columns, 5 expected"

        def caseName = tableRow.child(0).text()
        assert caseName == expectedCaseName: "Declension table row describes '$caseName' case, '$expectedCaseName' expected"

        declension.put(ObjectNumber.SINGULAR, declensionCase, getDeclinedNoun(tableRow, 2))
        declension.put(ObjectNumber.PLURAL, declensionCase, getDeclinedNoun(tableRow, 4))
    }

    private static String getDeclinedNoun(Element tableRow, int column) {
        def declinedNoun = ((TextNode) tableRow.child(column).childNode(0)).text()
        return CharMatcher.WHITESPACE.trimFrom(declinedNoun)
    }

}
