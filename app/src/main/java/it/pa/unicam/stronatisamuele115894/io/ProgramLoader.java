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

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

/**
 * This functional interface defines a basic program loader utility.
 * It loads a program given from various sources, which can be converted
 * to a Stream.
 *
 * The program is load either as a ListIterator instance, which is useful
 * to have full control of the computational stream of the program, or as
 * a String instance, which can be used to represent it on a text field.
 */

@FunctionalInterface
public interface ProgramLoader {

    /**
     * Loads the given program from a stream to a list iterator instance.
     * @param stream stream source of the program
     * @return list iterator representing the processed program.
     */
    ListIterator<List<String>> loadProgram(Stream<String> stream);


    default ListIterator<List<String>> loadProgram(String programString) {
        return loadProgram(programString.lines());
    }

    default ListIterator<List<String>> loadProgram(BufferedReader br) {
        return loadProgram(br.lines());
    }

    default ListIterator<List<String>> loadProgram(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        return loadProgram(br);
    }

    default ListIterator<List<String>> loadProgram(Path programPath) throws IOException {
        return loadProgram(Files.lines(programPath));
    }

    default ListIterator<List<String>> loadProgram(File programFile) throws IOException {
        return loadProgram(programFile.toPath());
    }

    /**
     * Loads the given program from a stream to a String instance.
     * @param stream stream source of the program.
     * @return string instance of the program.
     */
    default String loadProgramAsString(Stream<String> stream) {
        //stream.forEach((e) -> sb );
        StringBuilder sb = new StringBuilder();
        stream.forEach((e) -> sb.append(String.format("%s%n",e)));
        //loadProgram(stream);
        //loadProgram(stream).forEachRemaining(e -> sb.append(e.toString()));
        return sb.toString();
    }

    default String loadProgramAsString(BufferedReader br) {
        return loadProgramAsString(br.lines());
    }

    default String loadProgramAsString(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        return loadProgramAsString(br);
    }

    default String loadProgramAsString(Path programPath) throws IOException {
        return loadProgramAsString(Files.lines(programPath));
    }

    default String loadProgramAsString(File programFile) throws IOException {
        return loadProgramAsString(programFile.toPath());
    }
}
