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

package it.pa.unicam.stronatisamuele115894.view;

import it.pa.unicam.stronatisamuele115894.controller.SessionController;
import it.pa.unicam.stronatisamuele115894.io.*;
import it.pa.unicam.stronatisamuele115894.io.modelWriter.FieldWriter;
import java.io.*;
import java.util.Objects;

/**
 * This class implements a basic Console view which makes use of
 * a File System Engine.
 */

public class ConsoleView implements View {

    private final InputStream in;
    private final OutputStream out;
    private final OutputStream err;
    private final SessionController sessionController;
    private final FileSystemEngine fileSystemInterface;
    private boolean isOpen;


    public ConsoleView(SessionController controller, InputStream in, OutputStream out, OutputStream err) {
        this.sessionController = controller;
        this.fileSystemInterface = new DefaultFileSystemEngine(sessionController);
        this.in = in;
        this.out = out;
        this.err = err;
        this.isOpen = true;
    }

    public ConsoleView(SessionController controller, InputStream in, OutputStream out) {
        this(controller,in,out,out);
    }

    public ConsoleView(SessionController controller, InputStream in) {
        this(controller,in, System.out,System.err);
    }

    public ConsoleView(SessionController controller) {
        this(controller, System.in,System.out,System.err);
    }


    @Override
    public void open() throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in));
             PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(out)),true)) {
            hello(pr);
            while (isOpen) {
                boolean fileFound = fileSystemInterface.loadProgram(in,out);
                if (!fileFound) {
                    isOpen = false;
                    exit(pr);
                    return;
                }
                chooseRunMode(br,pr);
            }
        }
    }


    private void chooseRunMode(BufferedReader br, PrintWriter pr) throws IOException {
        pr.println(String.format("Please select how to run this program among the following ways:%n" +
                "1: Run the whole program;%n" +
                "2: Run step by step%n%n" +
                "Please press '1' to choose option '1', otherwise press any other key...%n"));
        if (br.readLine().equals("1")) {
            runFullProgram(br,pr);
            return;
        }
        runSequential(br,pr);
    }

    private void runFullProgram(BufferedReader br, PrintWriter pr) throws IOException {
        String c;
        do {
            c = sessionController.runNextCommand();
        } while (!Objects.equals(c, String.format("Program completed!%n")));
        saveOutput(br,pr);
    }

    private void runSequential(BufferedReader br, PrintWriter pr) throws IOException {
        pr.println("Let's run this program step by step !");
        while (true) {
            String c = sessionController.runNextCommand();
            fileSystemInterface.getFileSystemWriter().writeTo(out,String.format("%s%n", c));
            if (Objects.equals(c,String.format("Program completed!%n"))) {
                saveOutput(br,pr);
                return;
            }
            pr.println("Press any key to continue, otherwise press 'q' to stop");
            if (br.readLine().equals("q"))
                exit(pr);
        }
    }

    private void saveOutput(BufferedReader br, PrintWriter pr) throws IOException {
        String result = new FieldWriter().stringOf(sessionController.getSession().turtle().getField());
        fileSystemInterface.getFileSystemWriter().writeTo(out,result);
        pr.println("Please, enter an output file name. You must enter the name only, with no extension");
        fileSystemInterface.save(br.readLine(), result);
        pr.println("Saving the output...");
    }



    private void hello(PrintWriter output) {
        output.println("""
                ******************************
                -----------LOGO4JFX-----------
                ******************************
                """);
    }

    private void exit(PrintWriter output) {
        output.println(String.format("%n%nThank you for having used Logo4JFX!%nSee you next time!%n"));
    }
}