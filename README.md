# declension-flashcard-maker

This is an application made to facilitate learning German for Polish speakers. The user must decline a noun, optionally with an adjective, depending on the context. It also teaches which case should be used after a particular verb. The idea is teach and to speed up the declension skill by repetition.

Sentences are generated in a form of flashcards. The front side of a flashcard is a sentence in Polish, the back is the same sentence in German. Having generated the sentences, the user can use a flashcard learning application which he likes.

At the end of the day, the application was less useful since I have initially thought. The generated sentences are too abstract and lack context, which is very useful when learning a language. Therefore, the process of learning is very “raw”. I find it more pleasant to interact with a live language.

The application was written for myself. I chose Groovy as the programming language as a form of experiment.

## Disclaimer

This application is only an internal tool made for myself and wasn't meant to be adapted for a broad audience. In essence, here it acts as a code sample. 

* This is not a production grade code, but still I've put my heart into it.
* The app will be hard to use for users without technical knowledge. In particular, configuring the application requires editing the source code.
* The app was designed for users whose first language is be Polish. There is no English version.
* The input format will be cumbersome for most users.
* Data is fetched from publicly available online dictionaries. This causes a little, but still some traffic. It shouldn’t be a problem unless used excessively.

Having this in mind, this application can still be an useful thing :)

## Purpose and features

What the application wants to teach is:
* The nouns, verbs, adjectives and prepositions.
* How to decline a noun.
* How to decline an adjective.
* Which case should be taken after a verb.
* Which case should be taken after a preposition.

Technical features:
* Parsing and extracting data from HTML using jsoup.
* Using JSON as the dictionary cache format, which makes is debuggable and editable.

Example generated sentences:

Front side | Back side | Note
-----------|-----------|-----
(der - Brötchen) | das Brötchen | noun only
ohne (ein - Fahrer) | ohne einen Fahrer | preposition
ohne (mein - Zufall) | ohne meinen Zufall | possessive pronoun
gegenüber (. - Haus) | Haus gegenüber | preposition after a noun
ona, przeszkadzac - komu? (der - Frau) | sie stört die Frau | sentence
ona, przeszkadzac - komu? (der - (wiele) - Frau) | sie stört die Frauen | plural
on, przyniesc - komu? (sein - Herr) - co? (solcher - Zufall)  | er bringt seinem Herren solchen Zufall | sentence with many objects
für (der - weiß - Brötchen) | für das weißen Brötchen | adjective, weak inflection
für (. - weiß - Brötchen) | für weißes Brötchen | adjective, strong inflection
wy, pomagac - komu? (kein - (wiele) - toll - Frau) - w czym? (solcher - dumm - Beruf) | ihr helft keinen tollen Frauen bei solchem dummen Beruf | a typical silly sentence

## How to use it

Once again – without the technical knowledge, it will be hard for you to use this application. If you're not scared away yet, come along.

The easiest way to start is to try the example data. Inside `example-data` directory, there are two files `in-nouns+adjectives.txt` and `in-verbs.txt` that contains words used to generate sentences. In the shell run:

    ./run-with-example-data

The sentences will be generated and put into `out.txt` file inside `example-data` directory. There you go! Now you can use your favourite flashcard program to learn the sentences.

Also three additional files have appeared, which shouldn’t be edited by the user. These files are ` db-nouns.txt`, `db-adjectives.txt` and `db-verbs.txt`. They contain declension and conjugation data from online dictionaries, acting as a cache. It’s best just to leave them alone.

### Adding your own words

I won’t be providing much help anymore, but now you're welcome to create your input files with your own words. Check how the program is ran in the `run-with-example-data` script. You can point it to your own files. Refer to `in-nouns+adjectives.txt` and `in-verbs.txt` files to check the input format.

### Configuration

To configure the program, you have to dig inside the code. Check the following classes:

* `DeclensionFlashcardMaker`
* `RandomExpressionGenerator`
* `RandomWordSource`
* `PolishRenderer`

To customize how the sentences are generated, feel free to edit some `private static final`s at the top of these classes.
