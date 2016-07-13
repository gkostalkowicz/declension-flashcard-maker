package com.gk.dfm.logic.impl.source

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

    /**
     * @param elementToProbability element -> probability map. If the method should pick one of N elements, this map
     * should contain any N-1 of these elements; the remaining element is specified as fallbackElement argument.
     * @param fallbackElement the element picked if not any of elements in elementToProbability argument is picked.
     * The probability of picking this element is <code>1 - sumOfProbabilities(elementToProbability)</code>.
     */
    static <T> T pickElementWithProbability(Map<T, Double> elementToProbability, T fallbackElement) {
        def mapTotalProbability = elementToProbability.values().sum()
        if (mapTotalProbability > 1.0) {
            throw new RuntimeException("The total probability in elementToProbability map should not exceed 1.0")
        }

        def pickedNumber = random.nextDouble()
        def cumulativeProbability = 0.0
        for (def entry : elementToProbability.entrySet()) {
            def element = entry.key, probability = entry.value
            cumulativeProbability += probability
            if (pickedNumber <= cumulativeProbability) {
                return element
            }
        }
        return fallbackElement
    }

}
