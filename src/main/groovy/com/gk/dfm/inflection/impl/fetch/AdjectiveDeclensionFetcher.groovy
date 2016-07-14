package com.gk.dfm.inflection.impl.fetch

import com.gk.dfm.domain.object.NumberAndGender
import com.gk.dfm.domain.object.adjective.german.AdjectiveDeclension
import com.gk.dfm.domain.object.adjective.german.DeclensionType
import com.gk.dfm.domain.verb.german.objects.Case
import com.google.common.base.CharMatcher
import groovy.util.logging.Slf4j
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

/**
 * Created by Mr. President on 11.07.2016.
 */
@Slf4j
class AdjectiveDeclensionFetcher {

    private static final String EXPECTED_POSITIVE_FORMS_CAPTION = "Flektierte Formen Positiv"
    private static final int MINIMAL_EXPECTED_TABLE_COUNT = 7
    private static final int EXPECTED_ROW_CNT = 7
    private static final int EXPECTED_COLUMN_CNT = 9

    static AdjectiveDeclension fetchDeclension(String adjective) throws FetchException {
        log.info("Fetching declension for adjective '{}'.", adjective)
        sleep(FetchingConstants.MILLISECOND_SLEEP_BETWEEN_REQUESTS)

        def document = Jsoup.connect("http://www.canoo.net/inflection/$adjective:A").get()

        def tables = document.select("#WordClass[title=Adjective] > a[name=positiv] ~ table")

        def tablesCnt = tables.size()
        if (tablesCnt < MINIMAL_EXPECTED_TABLE_COUNT) {
            throw new FetchException("Only $tablesCnt TABLE elements found, at least $MINIMAL_EXPECTED_TABLE_COUNT" +
                    " expected", adjective)
        }

        def positiveFormsCaption = tables[0].text()
        if (!positiveFormsCaption.equals(EXPECTED_POSITIVE_FORMS_CAPTION)) {
            throw new FetchException("Positive adjective forms caption is '$positiveFormsCaption'," +
                    " '$EXPECTED_POSITIVE_FORMS_CAPTION' expected", adjective)
        }

        def declension = new AdjectiveDeclension()
        addDeclensionForType(DeclensionType.STRONG, tables[1], tables[2], "Starke Flexion", declension)
        addDeclensionForType(DeclensionType.WEAK, tables[3], tables[4], "Schwache Flexion", declension)
        addDeclensionForType(DeclensionType.MIXED, tables[5], tables[6], "Gemischte Flexion", declension)
        return declension
    }

    private static void addDeclensionForType(DeclensionType declensionType, Element captionTable, Element declensionTable,
                                             String expectedCaptionPrefix, AdjectiveDeclension declension) {
        def caption = captionTable.text()
        if (!caption.startsWith(expectedCaptionPrefix)) {
            throw new FetchException("The current declension type is named '$caption', but '$expectedCaptionPrefix'" +
                    " prefix expected")
        }

        def declensionTableBody = declensionTable.child(0)

        def declensionTableRowCnt = declensionTableBody.children().size()
        if (declensionTableRowCnt != EXPECTED_ROW_CNT) {
            throw new FetchException("Declension table contains $declensionTableRowCnt rows, $EXPECTED_ROW_CNT" +
                    " expected")
        }

        addDeclensionForCase(Case.NOMINATIVE, declensionType, declensionTableBody.child(3), "Nominativ", declension)
        addDeclensionForCase(Case.ACCUSATIVE  , declensionType, declensionTableBody.child(4), "Akkusativ", declension)
        addDeclensionForCase(Case.DATIVE, declensionType, declensionTableBody.child(5), "Dativ", declension)
        addDeclensionForCase(Case.GENITIVE, declensionType, declensionTableBody.child(6), "Genitiv", declension)
    }

    private static void addDeclensionForCase(Case declinedCase, DeclensionType declensionType, Element row,
                                             String expectedCaseName, AdjectiveDeclension declension) {
        def columnCnt = row.children().size()
        if (columnCnt != EXPECTED_COLUMN_CNT) {
            throw new FetchException("Declension row contains $columnCnt columns, $EXPECTED_COLUMN_CNT" +
                    " expected")
        }

        def caseName = row.child(0).text()
        if (caseName != expectedCaseName) {
            throw new FetchException("Declension row describes '$caseName' case, '$expectedCaseName' expected")
        }

        declension.put(declensionType, NumberAndGender.MASCULINE_SINGULAR, declinedCase, trim(row.child(2).text()))
        declension.put(declensionType, NumberAndGender.NEUTER_SINGULAR, declinedCase, trim(row.child(4).text()))
        declension.put(declensionType, NumberAndGender.FEMININE_SINGULAR, declinedCase, trim(row.child(6).text()))
        declension.put(declensionType, NumberAndGender.PLURAL, declinedCase, trim(row.child(8).text()))
    }

    private static String trim(String string) {
        CharMatcher.WHITESPACE.trimFrom(string)
    }

}
