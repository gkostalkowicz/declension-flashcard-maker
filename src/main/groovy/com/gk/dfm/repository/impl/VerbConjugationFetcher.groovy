package com.gk.dfm.repository.impl

import com.gk.dfm.domain.verb.german.conjugation.ConjugatedVerb
import com.gk.dfm.domain.verb.german.conjugation.ConjugationPerson
import com.gk.dfm.domain.verb.german.conjugation.VerbConjugation
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static com.gk.dfm.domain.verb.german.conjugation.ConjugationPerson.*

/**
 * Created by Mr. President on 03.07.2016.
 */
class VerbConjugationFetcher {

    private static final Logger log = LoggerFactory.getLogger(VerbConjugationFetcher)

    private static final int TABLE_IDX = 0
    private static final int HEADING_ROW_IDX = 0
    private static final String EXPECTED_HEADING_NAME = "Indikativ Präsens"
    private static final int PERSON_NAME_COLUMN_IDX = 0
    private static final int CONJUGATED_VERB_COLUMN_IDX = 1
    private static final String VERB_VERSIONS_SEPARATOR = ";"
    private static final String PARTICLE_AND_CORE_SEPARATOR = " "
    private static final Map<ConjugationPerson, Integer> ROW_INDEX_BY_PERSON = [
            (SINGULAR_1ST): 1,
            (SINGULAR_2ND): 2,
            (SINGULAR_3RD): 3,
            (PLURAL_1ST)  : 4,
            (PLURAL_2ND)  : 5,
            (PLURAL_3RD)  : 6
    ]
    private static final Map<ConjugationPerson, String> EXPECTED_PERSON_NAME_BY_PERSON = [
            (SINGULAR_1ST): "ich",
            (SINGULAR_2ND): "du",
            (SINGULAR_3RD): "er/sie/es",
            (PLURAL_1ST)  : "wir",
            (PLURAL_2ND)  : "ihr",
            (PLURAL_3RD)  : "sie/Sie"
    ]

    static VerbConjugation fetchConjugation(String infinitive) {
        log.info("Fetching declension for '{}'.", infinitive)
        sleep(FetchingConstants.MILLISECOND_SLEEP_BETWEEN_REQUESTS)

        def asciiInfinitive = replaceUmlautLettersWithAsciiSpelling(infinitive)
        def document = Jsoup.connect("http://en.bab.la/conjugation/german/$asciiInfinitive").get()

        def tables = document.select(".result-block table > tbody")
        def table = tables.get(TABLE_IDX)

        def heading = table.child(HEADING_ROW_IDX).text()
        assert heading == EXPECTED_HEADING_NAME: "Conjugation table describes '$heading', " +
                "expected '$EXPECTED_HEADING_NAME'"

        def conjugation = new VerbConjugation()
        for (def person : ConjugationPerson.values()) {
            getConjugatedVerb(person, table.child(ROW_INDEX_BY_PERSON[person]), EXPECTED_PERSON_NAME_BY_PERSON[person],
                    conjugation)
        }
        return conjugation
    }

    private static String replaceUmlautLettersWithAsciiSpelling(String word) {
        word.replace("ä", "ae").replace("ö", "oe").replace("ü", "ue")
    }

    private static void getConjugatedVerb(ConjugationPerson person, Element row, String expectedPersonName,
                                          VerbConjugation conjugation) {
        def personName = row.child(PERSON_NAME_COLUMN_IDX).text()
        assert personName == expectedPersonName: "Row describes conjugation for '$personName' person, " +
                "expected '$expectedPersonName'"

        def conjugatedVerbs = row.child(CONJUGATED_VERB_COLUMN_IDX).text()
        def verbSeparatorIdx = conjugatedVerbs.indexOf(VERB_VERSIONS_SEPARATOR)
        def conjugatedVerb = verbSeparatorIdx == -1 ? conjugatedVerbs : conjugatedVerbs.substring(0, verbSeparatorIdx)

        def particleAndCoreSeparatorIdx = conjugatedVerb.indexOf(PARTICLE_AND_CORE_SEPARATOR)
        def result
        if (particleAndCoreSeparatorIdx == -1) {
            result = new ConjugatedVerb(coreVerb: conjugatedVerb)
        } else {
            result = new ConjugatedVerb(particle: conjugatedVerb.substring(particleAndCoreSeparatorIdx + 1),
                    coreVerb: conjugatedVerb.substring(0, particleAndCoreSeparatorIdx))
        }

        conjugation.put(person, result)
    }

}
