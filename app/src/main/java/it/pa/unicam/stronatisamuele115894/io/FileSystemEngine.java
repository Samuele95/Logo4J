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
import java.nio.file.Path;
import java.util.Collection;

/**
 * This interface defines the API for a File System Engine, which can be used
 * to find a file in the local file system, to load a program into a given session controller
 * and to save a file into the local file system.
 *
 * The engine makes use of the ProgramLoader and ProgramWriter utilities, in order to load
 * and save a given file.
 *
 * All the methods are specializations of the basics methods loadProgram(InputStream in, Outputstream out),
 * find(String fileName, String searchDirectory), save(String fileName, String directory, String content). They
 * just give the user of the API more flexibility in the format to use in order to load, find or save a given
 * content to the file system.
 */

public interface FileSystemEngine {

    /**
     * Load a file passed by the given input stream to the given output stream
     * @param in input stream source
     * @param out output stream destination
     * @return true if the operation is successful, false otherwise
     * @throws IOException if some I/O errors occurred
     */
    boolean loadProgram(InputStream in, OutputStream out) throws IOException;

    /**
     * Finds all the file named as the given "fileName". The search takes place
     * recursively in the directory tree defined by "searchDirectory".
     * @param fileName name of the file
     * @param searchDirectory name of the directory tree
     * @return a collection of all the files found, defined through their path
     * @throws IOException if some I/O errors occurred
     */
    Collection<Path> find(String fileName, String searchDirectory) throws IOException;

    /**
     * Save the defined String "content" to a file named "fileName", and places it inside
     * the given directory. If the file is the result of a Logo program computation, it
     * must be saved with the '.logores' extension.
     * @param fileName filename
     * @param directory directory
     * @param content file content which has to be saved
     * @throws IOException if some I/O errors occurred
     */
    void save(String fileName, String directory, String content) throws IOException;

    /**
     * Returns the ProgramLoader used by this File System Engine.
     * @return ProgramLoader instance
     */
    ProgramLoader getFileSystemLoader();

    /**
     * Returns the ProgramWriter used by this File System Engine.
     * @return ProgramWriter instance
     */
    ProgramWriter getFileSystemWriter();

    default void save(String fileName, String content) throws IOException {
        save(fileName, System.getProperty("user.dir"), content);
    }

    default Path findFile(String filename, String searchDirectory) throws IOException {
        return find(filename, searchDirectory).stream().findFirst().orElse(null);
    }

    default Collection<Path> find(String fileName) throws IOException {
        return find(fileName, System.getProperty("user.dir"));
    }

    default Path findFile(String filename) throws IOException {
        return findFile(filename, System.getProperty("user.dir"));
    }

    default Collection<Path> find(BufferedReader br, PrintWriter pr) throws IOException {
        pr.println("Please, enter a source file or press 'q' to quit");
        return find(br.readLine());
    }

    default Path findFile(BufferedReader br, PrintWriter pr) throws IOException {
        String path = null; Path result = null; boolean isFound = false;
        while (!isFound) {
            pr.println("Please, enter a source file or press 'q' to quit");
            if ((path = br.readLine()).equals("q")) {
                return null;
            }
            else if ((result = findFile(path)) == null)
                pr.println("There is no such file. Try again");
            else
                isFound = true;
        }
        return result;
    }

    default Collection<Path> find(BufferedReader br, PrintWriter pr, String searchDirectory) throws IOException {
        pr.println("Please, enter a source file or press 'q' to quit");
        return find(br.readLine(), searchDirectory);
    }

    default Path findFile(BufferedReader br, PrintWriter pr, String searchDirectory) throws IOException {
        pr.println("Please, enter a source file or press 'q' to quit");
        return findFile(br.readLine(), searchDirectory);
    }

    default Collection<Path> find(InputStream in, OutputStream out) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(out)),true);
        return find(br,pr);
    }

    default Path findFile(InputStream in, OutputStream out) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(out)),true);
        return findFile(br,pr);
    }

    default Collection<Path> find(InputStream in, OutputStream out, String searchDirectory) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(out)),true);
        return find(br,pr, searchDirectory);
    }

    default Path findFile(InputStream in, OutputStream out, String searchDirectory) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(out)),true);
        return findFile(br,pr,searchDirectory);
    }
}
