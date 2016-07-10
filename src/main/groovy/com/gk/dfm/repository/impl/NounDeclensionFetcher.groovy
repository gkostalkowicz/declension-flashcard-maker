package com.gk.dfm.repository.impl

import com.gk.dfm.domain.object.noun.german.Gender
import com.gk.dfm.domain.object.noun.german.NounDeclension
import com.gk.dfm.domain.object.nounobject.german.ObjectNumber
import com.gk.dfm.domain.verb.german.objects.Case
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

    private static final Logger log = LoggerFactory.getLogger(NounDeclensionFetcher)

    private static final int EXPECTED_TABLE_CNT = 1
    private static final int TABLE_IDX = 0
    private static final int EXPECTED_ROW_CNT = 6
    private static final int DATIVE_ROW_IDX = 4
    private static final int ACCUSATIVE_ROW_IDX = 3
    private static final int EXPECTED_COLUMN_CNT = 5
    private static final int CASE_NAME_COLUMN_IDX = 0
    private static final int SINGULAR_COLUMN_IDX = 2
    private static final int PLURAL_COLUMN_IDX = 4
    private static final int NODE_BEFORE_LINE_BREAK_IDX = 0

    static NounDeclension fetchDeclension(Gender gender, String noun) throws FetchException {
        log.info("Fetching declension for '{}', '{}'.", noun, gender)
        sleep(FetchingConstants.MILLISECOND_SLEEP_BETWEEN_REQUESTS)

        def genderSymbol = getGenderSymbol(gender)
        def document = Jsoup.connect("http://www.canoo.net/inflection/$noun:N:$genderSymbol").get()

        def tables = document.select("#WordClass[title=Noun] > table > tbody")

        def tableCnt = tables.size()
        if (tableCnt != EXPECTED_TABLE_CNT) {
            throw new FetchException("$tableCnt declension tables found, $EXPECTED_TABLE_CNT expected")
        }

        def table = tables.get(TABLE_IDX)

        def rowCnt = table.children().size()
        if (rowCnt != EXPECTED_ROW_CNT) {
            throw new FetchException("Declension table contains $rowCnt rows, $EXPECTED_ROW_CNT expected")
        }

        def declension = new NounDeclension()
        getDeclinedNouns(Case.DATIVE, table.child(DATIVE_ROW_IDX), "Dativ", declension)
        getDeclinedNouns(Case.ACCUSATIVE, table.child(ACCUSATIVE_ROW_IDX), "Akkusativ", declension)
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
        def columnCnt = tableRow.children().size()
        if (columnCnt != EXPECTED_COLUMN_CNT) {
            throw new FetchException("Declension table row contains $columnCnt columns, $EXPECTED_COLUMN_CNT expected")
        }

        def caseName = tableRow.child(CASE_NAME_COLUMN_IDX).text()
        if (caseName != expectedCaseName) {
            throw new FetchException("Declension table row describes '$caseName' case, '$expectedCaseName' expected")
        }

        declension.put(ObjectNumber.SINGULAR, declensionCase, getDeclinedNoun(tableRow, SINGULAR_COLUMN_IDX))
        declension.put(ObjectNumber.PLURAL, declensionCase, getDeclinedNoun(tableRow, PLURAL_COLUMN_IDX))
    }

    private static String getDeclinedNoun(Element tableRow, int column) {
        def declinedNoun = ((TextNode) tableRow.child(column).childNode(NODE_BEFORE_LINE_BREAK_IDX)).text()
        return CharMatcher.WHITESPACE.trimFrom(declinedNoun)
    }

}
