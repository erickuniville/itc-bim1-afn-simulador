package univille.itc;

import java.util.Arrays;
import java.util.List;

public class TransitionMatrix {
    private final StateSet[][] matrix;
    private final List<String> lineIndexToStateNameMapping;
    private final List<String> columnIndexToAlphabetNameMapping;

    public TransitionMatrix(String[] states, String[] alphabets) {
        lineIndexToStateNameMapping = Arrays.asList(states);
        columnIndexToAlphabetNameMapping = Arrays.asList(alphabets);
        matrix = new StateSet[states.length][alphabets.length];
    }

    public void setState(int line, int column, StateSet state) {
        matrix[line][column] = state;
    }

    public StateSet getTransitionState(String state, String alphabet) {
        int line = lineIndexToStateNameMapping.indexOf(state);
        int column = columnIndexToAlphabetNameMapping.indexOf(alphabet);
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
