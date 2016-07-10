package com.gk.dfm.logic.impl

/**
 * Created by Mr. President on 10.07.2016.
 */
class RandomUtil {

    static final Random random = new Random()

    static <T> T pickElement(List<T> list) {
        list[random.nextInt(list.size())]
    }

    static <T> T pickElement(T[] array) {
        array[random.nextInt(array.length)]
    }

}
