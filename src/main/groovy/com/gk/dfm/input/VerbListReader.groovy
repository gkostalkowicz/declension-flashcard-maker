package com.gk.dfm.input

import com.fasterxml.jackson.databind.MappingIterator
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.gk.dfm.domain.verb.Verb
import com.gk.dfm.domain.verb.german.GermanVerb
import com.gk.dfm.domain.verb.polish.PolishVerb
import com.gk.dfm.repository.VerbConjugationRepository

/**
 * Created by Mr. President on 6/19/2016.
 */
class VerbListReader {

    private static final String PL_EXPRESSION_OUTLINE = "pl expression outline"
    private static final String DE_VERB_INFINITIVE = "de infinitive"
    private static final String DE_INFIX = "de infix"
    private static final String DE_DECLENSION_TEMPLATE = "de declension template"
    private static final char INPUT_COLUMN_SEPARATOR = "\t"

    private VerbConjugationRepository verbConjugationRepository
    private GermanDeclensionTemplateParser germanDeclensionTemplateParser = new GermanDeclensionTemplateParser()

    VerbListReader(VerbConjugationRepository verbConjugationRepository) {
        this.verbConjugationRepository = verbConjugationRepository
    }

    List<Verb> readVerbs(String filename) {
        File csvFile = new File(filename)

        CsvSchema bootstrapSchema = CsvSchema.emptySchema()
                .withHeader()
                .withColumnSeparator(INPUT_COLUMN_SEPARATOR)
        CsvMapper mapper = new CsvMapper()
        MappingIterator<Map<String, String>> it = mapper.readerFor(Map)
                .with(bootstrapSchema)
                .readValues(csvFile)

        List<Verb> verbs = new ArrayList<>()
        while (it.hasNext()) {
            Map<String, String> row = it.next()
            if (!exists(row[PL_EXPRESSION_OUTLINE])) {
                continue
            }

            def infinitive = row[DE_VERB_INFINITIVE].trim()

            PolishVerb polishVerb = new PolishVerb(
                    expressionOutline: row[PL_EXPRESSION_OUTLINE]
            )
            GermanVerb germanVerb = new GermanVerb(
                    verbInfinitive: infinitive,
                    infix: columnOrNull(row[DE_INFIX]),
                    declensionTemplate:
                            germanDeclensionTemplateParser.parseGermanDeclensionTemplate(row[DE_DECLENSION_TEMPLATE]),
                    conjugation: verbConjugationRepository.conjugateVerb(infinitive)
            )
            Verb verb = new Verb(
                    polishVerb: polishVerb,
                    germanVerb: germanVerb)
            verbs.add(verb)
        }
        return verbs
    }

    private static String columnOrNull(String column) {
        // TODO zamiane na null powinien robic jackson, nie mam teraz internetu
        exists(column) ? column : null
    }

    private static boolean exists(String column) {
        // TODO zamiane na null powinien robic jackson, nie mam teraz internetu
        !column.empty
    }

}
