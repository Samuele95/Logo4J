package it.pa.unicam.stronatisamuele115894.io;

import it.pa.unicam.stronatisamuele115894.controller.SessionController;

import java.io.PrintWriter;

public class DefaultProgramWriter implements ProgramWriter{

    private final SessionController controller;

    public DefaultProgramWriter(SessionController controller) {
        this.controller = controller;
    }
    @Override
    public PrintWriter writeTo(PrintWriter pw, String string) {
        return pw.printf(string);
    }


}
