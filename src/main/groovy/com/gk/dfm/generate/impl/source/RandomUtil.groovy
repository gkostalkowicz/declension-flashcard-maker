package com.gk.dfm.generate.impl.source

/**
 * Created by Mr. President on 10.07.2016.
 */
class RandomUtil {

    static final Random random = new Random()

    static boolean trueWithChance(double chance) {
        random.nextDouble() < chance
    }

    static <T> T pickElement(Collection<T> list) {
        list[random.nextInt(list.size())]
    }

    static <T> T pickElement(T[] array) {
        array[random.nextInt(array.length)]
    }

    static <T> T pickElementWithProbability(Map<T, Double> elementToProbability) {
        def elementsWithUnspecifiedProbability = elementToProbability.findAll { key, value -> value == null }
        if (elementsWithUnspecifiedProbability.size() != 1) {
            throw new RuntimeException("There should be exactly one element with unspecified (null) probability")
        }

        def elementsWithSpecifiedProbability = elementToProbability.findAll { key, value -> value != null }

        def mapTotalProbability = elementsWithSpecifiedProbability.values().sum()
        if (mapTotalProbability > 1.0) {
            throw new RuntimeException("The total probability in map should not exceed 1.0")
        }

        def pickedNumber = random.nextDouble()
        def cumulativeProbability = 0.0
        for (def entry : elementsWithSpecifiedProbability.entrySet()) {
            def element = entry.key, probability = entry.value
            cumulativeProbability += probability
            if (pickedNumber <= cumulativeProbability) {
                return element
            }
        }
        return elementsWithUnspecifiedProbability.keySet()[0]
    }

}
