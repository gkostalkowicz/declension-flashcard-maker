package com.gk.dfm.input

import com.fasterxml.jackson.databind.MappingIterator
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.gk.dfm.domain.object.noun.Gender
import com.gk.dfm.domain.object.noun.GermanNoun
import com.gk.dfm.domain.object.noun.Noun
import com.gk.dfm.domain.object.noun.PolishNoun
import com.gk.dfm.domain.verb.ObjectClass

import java.util.regex.Pattern

/**
 * Created by Mr. President on 6/19/2016.
 */
class NounListReader {

    private static final String POLISH = "polish"
    private static final String GERMAN = "german"
    private static final String TAGS = "tags"
    private static final String NOUN_TAG = "rzeczownik"
    private static final String PERSON_TAG = "person"
    private static final String THING_TAG = "ding"
    private static final char INPUT_COLUMN_SEPARATOR = "\t"
    private static final String GERMAN_NOUN_PATTERN = "(der|die|das) ([A-Za-z-]+).*"

    static List<Noun> readNouns(String filename) {
        File csvFile = new File(filename)

        CsvSchema schema = CsvSchema.builder()
                .addColumn(POLISH)
                .addColumn(GERMAN)
                .addColumn(TAGS, CsvSchema.ColumnType.NUMBER)
                .build()
                .withColumnSeparator(INPUT_COLUMN_SEPARATOR);
        CsvMapper mapper = new CsvMapper()
        MappingIterator<Map<String, String>> it = mapper.readerFor(Map)
                .with(schema)
                .readValues(csvFile)

        List<Noun> nouns = new ArrayList<>()
        while (it.hasNext()) {
            def row = it.next()
            def noun = parseNoun(row[POLISH], row[GERMAN], row[TAGS])
            noun.map { nouns.add(it) }
        }
        return nouns
    }

    private static Optional<Noun> parseNoun(String polishColumn, String germanColumn, String tagsColumn) {
        if (!tagsColumn.contains(NOUN_TAG)) {
            return Optional.empty()
        }

        def germanNounPattern = Pattern.compile(GERMAN_NOUN_PATTERN)
        def germanNounMatcher = germanNounPattern.matcher(germanColumn)
        if (!germanNounMatcher.matches()) {
            System.err.println("Warning: Couldn't parse German noun '$germanColumn'")
            return Optional.empty()
        }
        def germanNounString = germanNounMatcher.group(2)
        def article = germanNounMatcher.group(1)

        def objectClass = getObjectClassFromTagsColumn(tagsColumn)
        if (!objectClass.present) {
            return Optional.empty()
        }

        def polishNoun = new PolishNoun(noun: polishColumn)
        def germanNoun = new GermanNoun(noun: germanNounString,
                gender: getGenderFromArticle(article))
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
            System.err.println("Warning: Can't determine object class from tags: '$tagsColumn'")
            return Optional.empty()
        }
    }

}
