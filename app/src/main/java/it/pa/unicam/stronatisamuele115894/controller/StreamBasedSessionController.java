package it.pa.unicam.stronatisamuele115894.controller;



import java.util.*;

public class StreamBasedSessionController implements SessionController{

    private final Session session;

    private ListIterator<List<String>> iterator;

    private String lastProcessedCommand;

    public StreamBasedSessionController(Session mappedSession) {
        session = Objects.requireNonNull(mappedSession);
        lastProcessedCommand = null;
    }


/*    private ListIterator<List<String>> tokenizedProgram(InputStream in) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            return br.lines()
                    .parallel()
                    .filter(Objects::nonNull)
                    .map(s -> Arrays.stream(s.split("\\s+")).toList())
                    .filter(s -> session.getCommandSet().contains(s.get(0)))
                    .collect(toList()).listIterator();
        }
    }*/

    private String executeCommandThread(List<String> tokenizedCommand)  {
        String comm = tokenizedCommand.get(0);
        String commandOutput = session.getCommand(comm).apply(session.turtle(), tokenizedCommand.subList(1, tokenizedCommand.size()));
        lastProcessedCommand = comm;
        return commandOutput;
    }


    private String executeCommand(List<String> tokenizedCommand) {
        StringBuilder sb = new StringBuilder(String.format("Executing [%s] command...%n", tokenizedCommand));
        sb.append(executeCommandThread(tokenizedCommand));
        sb.append(String.format("Command executed!%n"));
        return sb.toString();
    }

    @Override
    public String runNextCommand()  {
        if (!Objects.requireNonNull(iterator).hasNext())
            return String.format("Program completed!%n");
        return executeCommand(iterator.next());
    }


    @Override
    public void clearProgram() {
        this.iterator = null;
    }

    @Override
    public Session getSession() {
        return session;
    }

    @Override
    public String setNewProgram(ListIterator<List<String>> program) {
        iterator = Objects.requireNonNull(program);
        return String.format("Program Loaded%n");
    }

    @Override
    public String getLastProcessedCommand() {
        return lastProcessedCommand;
    }

    protected ListIterator<List<String>> getProgramIterator() {
        return iterator;
    }
}
