package univille.itc;

import java.util.Set;

public class Automata {
    public static void main(String[] args) {
        //======================================================
        // Building a transition matrix from input
        //======================================================

        String[] inputLines = {
                "q1 q2 q3 q4", // linha com o conjunto de estados, separados por espaçco
                "0 1 *",       // linha com o alfabeto, símbolos separados por espaçco
                "q1",          // início da descrição da matriz linha 0, coluna 0
                "q1 q2",       // linha 0, coluna 1
                "*",           // linha 0, coluna 2
                "q3",          // matriz linha 1, coluna 0
                "*",
                "q3",
                "*",           // matriz linha 2, coluna 0
                "q4",
                "*",
                "q4",          // matriz linha 3, coluna 0
                "q4",
                "*",
                "q1",          // estado inicial
                "q4"           // estados finais
        };

        String[] possibleStates = inputLines[0].trim().split(" ");
        String[] possibleCommands = inputLines[1].trim().split(" ");

        TransitionMatrix transitionMatrix = new TransitionMatrix(possibleStates, possibleCommands);

        int currentLine = 0;
        int currentColumn = 0;
        for (int i=2; i<=inputLines.length-3; i++) {
            StateSet transitionState = new StateSet(inputLines[i].trim().split(" "));
            transitionMatrix.setState(currentLine, currentColumn++, transitionState);

            if (currentColumn >= possibleCommands.length) {
                currentLine++;
                currentColumn = 0;
            }
        }

        final int PENULTIMATE = inputLines.length - 2;
        final int ULTIMATE = inputLines.length - 1;

        StateSet initialState = new StateSet(inputLines[PENULTIMATE].trim().split(" "));
        StateSet finalState =  new StateSet(inputLines[ULTIMATE].trim().split(" "));

        System.out.println(transitionMatrix);

        //==================================================================
        // Reading input commands and describing the execution steps
        //==================================================================

        String[] userInputCommands = {"0", "1", "0", "0", "1", "0", "1"};

        StateSet currentStates = initialState;
        System.out.println("Initial state: " + initialState);
        for (String command : userInputCommands) {
            System.out.println("\nRead Symbol: " + command);

            Set<String> previousStates = currentStates.getStates();
            currentStates.clear();

            for (String state : previousStates) {
                StateSet newState = transitionMatrix.getTransitionState(state, command);

                if (newState.isVoid()) {
                    currentStates.removeState(state);
                } else {
                    currentStates.addState(newState);
                }
            }

            System.out.println("Current State: " + currentStates);
        }

        System.out.println("\nAccepted: " + finalState.equals(currentStates));
    }
}