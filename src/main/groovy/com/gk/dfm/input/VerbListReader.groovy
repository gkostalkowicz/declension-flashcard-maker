package com.gk.dfm.input

import com.fasterxml.jackson.databind.MappingIterator
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.gk.dfm.domain.verb.GermanVerb
import com.gk.dfm.domain.verb.PolishVerb
import com.gk.dfm.domain.verb.Verb

/**
 * Created by Mr. President on 6/19/2016.
 */
class VerbListReader {

    private static final String PL_EXPRESSION_OUTLINE = "pl expression outline"
    private static final String DE_VERB_INFINITIVE = "de verb"
    private static final String DE_INFIX = "de infix"
    private static final String DE_DECLENSION_TEMPLATE = "de declension template"
    private static final String DE_SUFFIX = "de suffix"
    private static final char INPUT_COLUMN_SEPARATOR = "\t"

    GermanDeclensionTemplateParser germanDeclensionTemplateParser = new GermanDeclensionTemplateParser()

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

            PolishVerb polishVerb = new PolishVerb(
                    expressionOutline: row[PL_EXPRESSION_OUTLINE]
            )
            GermanVerb germanVerb = new GermanVerb(
                    verbInfinitive: row[DE_VERB_INFINITIVE].trim(),
                    infix: columnOrNull(row[DE_INFIX]),
                    declensionTemplate:
                            germanDeclensionTemplateParser.parseGermanDeclensionTemplate(row[DE_DECLENSION_TEMPLATE]),
                    suffix: columnOrNull(row[DE_SUFFIX])
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
