package com.gk.dfm.inflection.impl.fetch

import com.gk.dfm.domain.object.noun.german.Gender
import com.gk.dfm.domain.object.noun.german.NounDeclension
import com.gk.dfm.domain.object.nounobject.ObjectNumber
import com.gk.dfm.domain.verb.german.objects.Case
import com.google.common.base.CharMatcher
import groovy.util.logging.Slf4j
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode

/**
 * Created by Mr. President on 6/30/2016.
 */
@Slf4j
class NounDeclensionFetcher {

    private static final int EXPECTED_TABLE_CNT = 1
    private static final int TABLE_IDX = 0
    private static final int EXPECTED_ROW_CNT = 6
    private static final int EXPECTED_COLUMN_CNT = 5
    private static final int CASE_NAME_COLUMN_IDX = 0
    private static final int SINGULAR_COLUMN_IDX = 2
    private static final int PLURAL_COLUMN_IDX = 4
    private static final int NODE_BEFORE_LINE_BREAK_IDX = 0

    static NounDeclension fetchDeclension(Gender gender, String noun) throws FetchException {
        log.info("Fetching declension for noun '{}', '{}'.", noun, gender)
        sleep(FetchingConstants.MILLISECOND_SLEEP_BETWEEN_REQUESTS)

        def genderSymbol = getGenderSymbol(gender)
        def document = Jsoup.connect("http://www.canoo.net/inflection/$noun:N:$genderSymbol").get()

        def tables = document.select("#WordClass[title=Noun] > table > tbody")

        def tableCnt = tables.size()
        if (tableCnt != EXPECTED_TABLE_CNT) {
            throw new FetchException("$tableCnt declension tables found, $EXPECTED_TABLE_CNT expected", noun)
        }

        def table = tables.get(TABLE_IDX)

        def rowCnt = table.children().size()
        if (rowCnt != EXPECTED_ROW_CNT) {
            throw new FetchException("Declension table contains $rowCnt rows, $EXPECTED_ROW_CNT expected", noun)
        }

        def declension = new NounDeclension()
        addDeclinedNouns(Case.NOMINATIVE, table.child(2), "Nominativ", declension)
        addDeclinedNouns(Case.ACCUSATIVE, table.child(3), "Akkusativ", declension)
        addDeclinedNouns(Case.DATIVE, table.child(4), "Dativ", declension)
        addDeclinedNouns(Case.GENITIVE, table.child(5), "Genitiv", declension)
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

    private static void addDeclinedNouns(Case declensionCase, Element tableRow, String expectedCaseName,
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
