package com.gk.dfm.input

import com.fasterxml.jackson.databind.MappingIterator
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.gk.dfm.domain.object.ObjectClass
import com.gk.dfm.domain.object.noun.Noun
import com.gk.dfm.domain.object.noun.german.Gender
import com.gk.dfm.domain.object.noun.german.GermanNoun
import com.gk.dfm.domain.object.noun.polish.PolishNoun
import com.gk.dfm.repository.NounDeclensionRepository
import com.gk.dfm.repository.impl.FetchException
import com.gk.dfm.util.GlobalConstants
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.util.regex.Pattern

/**
 * Created by Mr. President on 6/19/2016.
 */
class NounListReader {

    private static final Logger log = LoggerFactory.getLogger(NounListReader)

    private static final String POLISH = "polish"
    private static final String GERMAN = "german"
    private static final String TAGS = "tags"
    private static final String NOUN_TAG = "rzeczownik"
    private static final String PERSON_TAG = "person"
    private static final String THING_TAG = "ding"
    private static final char INPUT_COLUMN_SEPARATOR = "\t"
    private static final String GERMAN_NOUN_PATTERN = "(der|die|das) ([\\p{L}-]+).*"

    private NounDeclensionRepository nounDeclensionRepository

    NounListReader(NounDeclensionRepository nounDeclensionRepository) {
        this.nounDeclensionRepository = nounDeclensionRepository
    }

    List<Noun> readNouns(String filename) {
        File csvFile = new File(filename)

        CsvSchema schema = CsvSchema.builder()
                .addColumn(POLISH)
                .addColumn(GERMAN)
                .addColumn(TAGS, CsvSchema.ColumnType.NUMBER)
                .build()
                .withColumnSeparator(INPUT_COLUMN_SEPARATOR)
        CsvMapper mapper = new CsvMapper()
        def reader = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile),
                GlobalConstants.FILE_CHARSET_NAME))
        MappingIterator<Map<String, String>> it = mapper.readerFor(Map)
                .with(schema)
                .readValues(reader)

        List<Noun> nouns = new ArrayList<>()
        while (it.hasNext()) {
            def row = it.next()
            try {
                def noun = parseNoun(row[POLISH], row[GERMAN], row[TAGS])
                noun.map { nouns.add(it) }
            } catch (FetchException e) {
                log.warn("Couldn't fetch noun declension for '{}'. Reason: '{}'.", row[GERMAN], e.getMessage())
                log.debug("Error details.", e)
            }
        }
        return nouns
    }

    private Optional<Noun> parseNoun(String polishColumn, String germanColumn, String tagsColumn) {
        if (!tagsColumn.contains(NOUN_TAG)) {
            return Optional.empty()
        }

        def germanNounPattern = Pattern.compile(GERMAN_NOUN_PATTERN)
        def germanNounMatcher = germanNounPattern.matcher(germanColumn)
        if (!germanNounMatcher.matches()) {
            log.warn("Couldn't parse German noun '{}'", germanColumn)
            return Optional.empty()
        }
        def germanNounString = germanNounMatcher.group(2)
        def article = germanNounMatcher.group(1)
        def gender = getGenderFromArticle(article)

        def objectClass = getObjectClassFromTagsColumn(tagsColumn)
        if (!objectClass.present) {
            return Optional.empty()
        }

        def polishNoun = new PolishNoun(noun: polishColumn)
        def germanNoun = new GermanNoun(noun: germanNounString,
                gender: gender,
                declension: nounDeclensionRepository.getDeclension(gender, germanNounString))
        def noun = new Noun(polishNoun: polishNoun,
                germanNoun: germanNoun,
                objectClass: objectClass.get())

        return Optional.of(noun)
    }

    private static Gender getGenderFromArticle(String nominativeArticle) {
        switch (nominativeArticle) {
            case "der":
                return Gender.MASCULINE
            case "die":
                return Gender.FEMININE
            case "das":
                return Gender.NEUTER
            default:
                throw new RuntimeException("Unknown article: '$nominativeArticle'")
        }
    }

    private static Optional<ObjectClass> getObjectClassFromTagsColumn(String tagsColumn) {
        if (tagsColumn.contains(PERSON_TAG)) {
            return Optional.of(ObjectClass.PERSON)
        } else if (tagsColumn.contains(THING_TAG)) {
            return Optional.of(ObjectClass.THING)
        } else {
            log.warn("Can't determine object class from tags '{}'", tagsColumn)
            return Optional.empty()
        }
    }

}
