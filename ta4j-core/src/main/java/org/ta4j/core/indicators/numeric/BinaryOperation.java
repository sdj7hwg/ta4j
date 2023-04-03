/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2022 Ta4j Organization & respective
 * authors (see AUTHORS)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.ta4j.core.indicators.numeric;

import java.util.function.BinaryOperator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;

/**
 * Objects of this class defer evaluation of an arithmetic operation.
 * 
 * This is a lightweight version of the CombineIndicator; it doesn't cache.
 */
class BinaryOperation implements Indicator<Num> {

    public static BinaryOperation sum(Indicator<Num> left, Indicator<Num> right) {
        return new BinaryOperation(Num::plus, left, right);
    }

    public static BinaryOperation difference(Indicator<Num> left, Indicator<Num> right) {
        return new BinaryOperation(Num::minus, left, right);
    }

    public static BinaryOperation product(Indicator<Num> left, Indicator<Num> right) {
        return new BinaryOperation(Num::multipliedBy, left, right);
    }

    public static BinaryOperation quotient(Indicator<Num> left, Indicator<Num> right) {
        return new BinaryOperation(Num::dividedBy, left, right);
    }

    public static BinaryOperation min(Indicator<Num> left, Indicator<Num> right) {
        return new BinaryOperation(Num::min, left, right);
    }

    public static BinaryOperation max(Indicator<Num> left, Indicator<Num> right) {
        return new BinaryOperation(Num::max, left, right);
    }

    private final BinaryOperator<Num> operator;
    private final Indicator<Num> left;
    private final Indicator<Num> right;

    private BinaryOperation(BinaryOperator<Num> operator, Indicator<Num> left, Indicator<Num> right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public Num getValue(int index) {
        Num n1 = left.getValue(index);
        Num n2 = right.getValue(index);
        return operator.apply(n1, n2);
    }

    @Override
    public int getUnstableBars() {
        return 0;
    }

    @Override
    public BarSeries getBarSeries() {
        return left.getBarSeries();
    }

}
