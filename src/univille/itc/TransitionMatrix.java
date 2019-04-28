package univille.itc;

import java.util.Arrays;
import java.util.List;

public class TransitionMatrix {
    private final StateSet[][] matrix;
    private final List<String> lineIndexToStateNameMapping;
    private final List<String> columnIndexToCommandNameMapping;

    public TransitionMatrix(String[] states, String[] commands) {
        lineIndexToStateNameMapping = Arrays.asList(states);
        columnIndexToCommandNameMapping = Arrays.asList(commands);
        matrix = new StateSet[states.length][commands.length];
    }

    public void setState(int line, int column, StateSet state) {
        matrix[line][column] = state;
    }

    public StateSet getTransitionState(String state, String command) {
        int line = lineIndexToStateNameMapping.indexOf(state);
        int column = columnIndexToCommandNameMapping.indexOf(command);
        return matrix[line][column];
    }

    @Override
    public String toString() {
        StringBuilder matrixString = new StringBuilder();

        for (StateSet[] line : matrix) {
            for (StateSet item : line) {
                matrixString.append(item).append(" ");
            }
            matrixString.append("\n");
        }

        return matrixString.toString();
    }
}
