package univille.itc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class AutomataSimulator {
    private static final String VOID = "*";
    private static final String COMMENT_START_SYMBOL = "#";
    private static final int MATRIX_DESCRIPTION_START_LINE = 2;
    private static final String NEW_LINE = System.lineSeparator();
    private static final String DEFAULT_INPUT_FILE = "data.txt";
    private static final String DEFAULT_OUTPUT_FILE = "execution_steps.txt";

    private List<String> fileLines;
    private StringBuilder executionLog;

    public AutomataSimulator(String automataDescriptionFilePath) {
        try {
            fileLines = readLinesFromFile(automataDescriptionFilePath);
            executionLog = new StringBuilder();
        } catch (IOException e) {
            System.out.println("Could not start automata simulator: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String automataDescriptionFilePath = DEFAULT_INPUT_FILE;
        String executionStepsLogFilePath = DEFAULT_OUTPUT_FILE;

        if (args.length > 0) {
            automataDescriptionFilePath = args[0];
        }

        if (args.length > 1) {
            executionStepsLogFilePath = args[1];
        }

        AutomataSimulator simulator = new AutomataSimulator(automataDescriptionFilePath);

        TransitionMatrix transitionMatrix = simulator.readTransitionMatrixFromFile();
        StateSet initialState = simulator.readInitialStatesFromFile();
        StateSet finalState = simulator.readFinalStatesFromFile();
        char[] userInputAlphabets = simulator.readInputAlphabetsFromKeyboard();
        StateSet currentStates = initialState;

        simulator.log("Initial state: " + initialState);

        for (char alphabet : userInputAlphabets) {
            simulator.log("Read Symbol: " + alphabet);

            Set<String> previousStates = currentStates.getStates();
            currentStates.clear();

            for (String state : previousStates) {
                StateSet newState = transitionMatrix.getTransitionState(state, String.valueOf(alphabet));

                if (newState.isVoid()) {
                    currentStates.removeState(state);
                } else {
                    currentStates.addState(newState);
                }
            }

            simulator.log("Current State: " + currentStates);
        }

        boolean accepted = finalState.equals(currentStates);

        simulator.log(NEW_LINE + "Result: " + (accepted ? "ACCEPTED" : "REJECTED"));

        simulator.saveExecutionStepsLog(executionStepsLogFilePath);
    }

    public TransitionMatrix readTransitionMatrixFromFile() {
        String[] possibleStates = getPossibleStates();
        String[] possibleAlphabets = getPossibleAlphabets();

        TransitionMatrix transitionMatrix = new TransitionMatrix(possibleStates, possibleAlphabets);

        int currentLine = 0;
        int currentColumn = 0;
        for (int i = MATRIX_DESCRIPTION_START_LINE; i <= getMatrixDescriptionEndLine(fileLines); i++) {
            StateSet transitionState = new StateSet(fileLines.get(i).trim().split(" "));
            transitionMatrix.setState(currentLine, currentColumn++, transitionState);

            if (currentColumn >= possibleAlphabets.length) {
                currentLine++;
                currentColumn = 0;
            }
        }

        return transitionMatrix;
    }

    public StateSet readInitialStatesFromFile() {
        int secondToLast = fileLines.size() - 2;
        String[] initialState = fileLines.get(secondToLast).trim().split(" ");

        return new StateSet(initialState);
    }

    public StateSet readFinalStatesFromFile() {
        int last = fileLines.size() - 1;
        String[] finalStates = fileLines.get(last).trim().split(" ");

        return new StateSet(finalStates);
    }

    public char[] readInputAlphabetsFromKeyboard() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite a série de alfabetos a ser processada pelo autômato (Ex.: 0100111010): ");
        String userInputAlphabets = scanner.nextLine();
        return userInputAlphabets.toCharArray();
    }

    public void log(String log) {
        System.out.println(log);
        executionLog.append(log).append(NEW_LINE);
    }

    public void saveExecutionStepsLog(String executionStepsLogFilePath) {
        try (PrintWriter writer = new PrintWriter(executionStepsLogFilePath)) {
            writer.println(executionLog.toString());
        } catch (IOException e) {
            System.out.println("Could not save log file to '" + executionStepsLogFilePath + "': " + e.getMessage());
        }
    }

    private List<String> readLinesFromFile(String filePath) throws IOException {
        File file = new File(filePath);

        List<String> allLinesFromFile = Files.readAllLines(file.toPath(), Charset.defaultCharset());

        removeComments(allLinesFromFile);

        return allLinesFromFile;
    }

    private void removeComments(List<String> allLines) {
        for (int i = 0; i < allLines.size(); i++) {
            String line = allLines.get(i);
            int commentStartIndex = line.indexOf(COMMENT_START_SYMBOL);
            if (commentStartIndex > -1) {
                String lineWithoutComment = line.substring(0, commentStartIndex).trim();
                allLines.set(i, lineWithoutComment);
            }
        }
    }

    private String[] getPossibleStates() {
        return fileLines.get(0).trim().split(" ");
    }

    private String[] getPossibleAlphabets() {
        String[] possibleAlphabets = fileLines.get(1).trim().split(" ");

        if (Arrays.asList(possibleAlphabets).contains(VOID)) {
            return possibleAlphabets;
        }

        return addVoidAlphabet(possibleAlphabets);
    }

    private static String[] addVoidAlphabet(String[] alphabets) {
        String[] possibleAlphabetsAndVoid = new String[alphabets.length + 1];
        System.arraycopy(alphabets, 0, possibleAlphabetsAndVoid, 0, alphabets.length);
        possibleAlphabetsAndVoid[possibleAlphabetsAndVoid.length - 1] = VOID;

        return possibleAlphabetsAndVoid;
    }

    private static int getMatrixDescriptionEndLine(List<String> fileLines) {
        return fileLines.size() - 3;
    }
}