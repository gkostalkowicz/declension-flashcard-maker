package com.gk.dfm.input

import com.fasterxml.jackson.databind.MappingIterator
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.gk.dfm.domain.object.ObjectClass
import com.gk.dfm.domain.object.adjective.Adjective
import com.gk.dfm.domain.object.adjective.german.GermanAdjective
import com.gk.dfm.domain.object.adjective.polish.PolishAdjective
import com.gk.dfm.domain.object.noun.Noun
import com.gk.dfm.domain.object.noun.german.Gender
import com.gk.dfm.domain.object.noun.german.GermanNoun
import com.gk.dfm.domain.object.noun.polish.PolishNoun
import com.gk.dfm.repository.AdjectiveDeclensionRepository
import com.gk.dfm.repository.NounDeclensionRepository
import com.gk.dfm.repository.impl.fetch.FetchException
import com.gk.dfm.util.CharsetConstants
import groovy.util.logging.Slf4j

import java.util.regex.Pattern

/**
 * Created by Mr. President on 6/19/2016.
 */
@Slf4j
class NounAndAdjectiveListReader {

    private static final String POLISH_COLUMN = "polish"
    private static final String GERMAN_COLUMN = "german"
    private static final String TAGS_COLUMN = "tags"
    private static final String NOUN_TAG = "rzeczownik"
    private static final String PERSON_TAG = "person"
    private static final String THING_TAG = "ding"
    private static final String ADJECTIVE_TAG = "przymiotnik"
    private static final String ADJECTIVE_VERSIONS_SEPARATOR = ","

    private NounDeclensionRepository nounDeclensionRepository
    private AdjectiveDeclensionRepository adjectiveDeclensionRepository

    NounAndAdjectiveListReader(NounDeclensionRepository nounDeclensionRepository,
                               AdjectiveDeclensionRepository adjectiveDeclensionRepository) {
        this.nounDeclensionRepository = nounDeclensionRepository
        this.adjectiveDeclensionRepository = adjectiveDeclensionRepository
    }

    static class ReadResult {
        List<Noun> nouns
        List<Adjective> adjectives
    }

    ReadResult readNounsAndAdjectives(String filename) {
        File csvFile = new File(filename)

        CsvSchema schema = CsvSchema.builder()
                .addColumn(POLISH_COLUMN)
                .addColumn(GERMAN_COLUMN)
                .addColumn(TAGS_COLUMN)
                .build()
                .withColumnSeparator("\t" as char)
        CsvMapper mapper = new CsvMapper()
        def reader = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile),
                CharsetConstants.FILE_CHARSET_NAME))
        MappingIterator<Map<String, String>> it = mapper.readerFor(Map)
                .with(schema)
                .readValues(reader)

        List<Noun> nouns = new ArrayList<>()
        List<Adjective> adjectives = new ArrayList<>()
        while (it.hasNext()) {
            addWordIfPresent(it.next(), nouns, adjectives)
        }
        return new ReadResult(nouns: nouns, adjectives: adjectives)
    }

    private void addWordIfPresent(Map<String, String> row, List<Noun> nouns, List<Adjective> adjectives) {
        def germanColumn = row[GERMAN_COLUMN]
        def polishColumn = row[POLISH_COLUMN]
        def tagsColumn = row[TAGS_COLUMN]

        if (tagsColumn.contains(NOUN_TAG)) {
            try {
                def noun = parseNoun(polishColumn, germanColumn, tagsColumn)
                noun.map { nouns.add(it) }
            } catch (FetchException e) {
                log.warn("Couldn't fetch noun declension for '{}'. Reason: '{}'.", germanColumn, e.getMessage())
                log.debug("Error details.", e)
            }
        } else if (tagsColumn.contains(ADJECTIVE_TAG)) {
            try {
                adjectives.add(parseAdjective(polishColumn, germanColumn))
            } catch (FetchException e) {
                log.warn("Couldn't fetch adjective declension for '{}'. Reason: '{}'.", germanColumn, e.getMessage())
                log.debug("Error details.", e)
            }
        }
    }

    private Optional<Noun> parseNoun(String polishColumn, String germanColumn, String tagsColumn) {
        def germanNounPattern = Pattern.compile("(der|die|das) ([\\p{L}-]+).*")
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

    private Adjective parseAdjective(String polishColumn, String germanColumn) {
        def separatorIndex = germanColumn.indexOf(ADJECTIVE_VERSIONS_SEPARATOR)
        def germanAdjective = separatorIndex == -1 ? germanColumn : germanColumn.substring(0, separatorIndex)
        return new Adjective(
                polishAdjective: new PolishAdjective(
                        adjective: polishColumn
                ),
                germanAdjective: new GermanAdjective(
                        adjective: germanAdjective,
                        declension: adjectiveDeclensionRepository.getDeclension(germanColumn)
                )
        )
    }

}
