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


package it.pa.unicam.stronatisamuele115894.controller;

import it.pa.unicam.stronatisamuele115894.model.Turtle;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * This interface implements a Logo drawing session. A sessions consists of a
 * turtle being the drawing cursor and a set of "string" commands used to direct
 * the cursor or change its parameters. Every turtle draws over a given "Field", which
 * is passed as argument to the turtle itself. The turtle draws "Paths" over the field,
 * which may be of different nature or shape. "Paths" may be composed of different types
 * of "Sections". The turtle is placed on a specific Position in any moment. This Position
 * must be inside the Field. Commands are "string based" and may take arguments of every type.
 * They represent means to guide the cursor during the execution of the program itself.
 * Every field is an instance of the "Field" interface. Every "Path" is an instance of the
 * "GeoPath" interface. Every "Section" is an instance of the "GeoSection" interface.
 *
 * Instances of this interface are immutable objects. They represent a session whose parameters
 * are fixed during the execution of the whole program. Therefore, it is advisable to create
 * "record" instances or final classes with final fields. Record instances are preferable though.
 *
 * The cursor and the command set are immutable dependencies which must be injected through
 * a specific SessionInjector.
 *
 */

public interface Session {

    /**
     * The injected cursor of this session;
     * @return turtle as cursore of this session;
     */
    Turtle turtle();

    /**
     * Set of the string-based commands which may
     * be used in this session. Commands are injected
     * through a SessionInjector.
     * @return set of string-based commands;
     */
    Set<String> getCommandSet();

    /**
     * Retrieves the command passed as a parameter,
     * if it's present, throws an exception otherwise
     *
     * @param command the string representing the command
     * @return the BiConsumer representing the command
     * @throws NullPointerException if the given string is null.
     */
    BiFunction<Turtle, List<?>,String> getCommand(String command);
}
