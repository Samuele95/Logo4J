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

package it.pa.unicam.stronatisamuele115894.io;

import it.pa.unicam.stronatisamuele115894.controller.SessionController;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toList;

/**
 * This class implements the ProgramLoader functional interface
 * by definining an implementation for the loadProgram method.
 * This instance wraps a SessionController, in order to load a
 * ready-to-use program in the controller itself.
 */

public class StreamProgramLoader implements ProgramLoader{

    SessionController controller;


    /**
     * Constructs a ProgramLoader instance which wraps the given
     * session controller.
     * @param controller session Controller.
     */
    public StreamProgramLoader(SessionController controller) {
        this.controller = controller;
    }

    /**
     * Loads a program into a session controller as a List iterator instance.
     * A REGEX is used to parse the given stream. The parser checks for line separators
     * and for square brackets, which may be associated to an argument list of a single
     * command.
     * @param stream stream source of the program
     * @return list iterator defining the program.
     */
    @Override
    public ListIterator<List<String>> loadProgram(Stream<String> stream) {
        try (Stream<String> programStream = stream) {
            ListIterator<List<String>> result = programStream.parallel()
                    .filter(Objects::nonNull)
                    .map(s -> Arrays.stream(s.split("\\s+(?![^\\[]*\\])")).toList())
                    .filter(s -> controller.getSession().getCommandSet().contains(s.get(0)))
                    .collect(toList()).listIterator();
            controller.setNewProgram(result);
            return result;
        }
    }
}
