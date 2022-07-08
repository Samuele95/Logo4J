/*******************************************************************************
 * Copyright (c) 2022 Samuele Stronati, Università di Camerino.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contributors:
 *     Samuele Stronati - initial API and implementation
 *******************************************************************************/


package it.pa.unicam.stronatisamuele115894.model;


/**
 * This record represents a pair (x,y) of numerical coordinates in a cartesian
 * coordinate system. As per every record, two instances of a position are equal
 * if the values x and y are respectively equal. Every instance is immutable.
 *
 * @param x first value in a (x,y) pair;
 * @param y second value in a (x,y) pair;
 */

public record Position(double x, double y) {


    /**
     * Constructs a new (x,y) pair, given its values
     * @param x first value in a (x,y) pair;
     * @param y second value in a (x,y) pair;
     *
     * @throws NullPointerException
     *      if "x" or "y" value is null.
     */

    public Position {
    }

    /**
     * Returns the string representation of this pair of numbers.
     * The string consists of the two values in the format "x y",
     * where x is the first value in the pair and y is the second
     * value in the pair.
     *
     * @return string representation of this pair of numbers
     */

    @Override
    public String toString() {
        return String.format("Position instance: x=%s y=%s",x,y);
    }

}
