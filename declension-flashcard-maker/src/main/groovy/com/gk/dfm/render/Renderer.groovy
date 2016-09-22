package com.gk.dfm.render

import com.gk.dfm.domain.expression.NominativeExpression
import com.gk.dfm.domain.expression.PrepositionExpression
import com.gk.dfm.domain.expression.Sentence

/**
 * Created by Mr. President on 14.07.2016.
 */
interface Renderer {

    String renderSentence(Sentence sentence)

    String renderNominativeExpression(NominativeExpression expression)

    String renderPrepositionExpression(PrepositionExpression expression)

}