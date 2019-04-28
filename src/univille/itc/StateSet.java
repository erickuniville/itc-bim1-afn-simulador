package univille.itc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StateSet {
    private static final String VOID_SET = "*";

    private final Set<String> states;
    private final boolean isVoid;

    public StateSet(String[] arrayStates) {
        states = new HashSet<>();
        states.addAll(Arrays.asList(arrayStates));
        isVoid = states.contains(VOID_SET);
    }

    public Set<String> getStates() {
        return new HashSet<>(this.states);
    }

    public void addState(StateSet newStates) {
        states.addAll(newStates.getStates());
    }

    public boolean removeState(String state) {
        return states.remove(state);
    }

    public void clear() {
        states.clear();
    }

    public boolean isVoid() {
        return isVoid;
    }

    public String toString() {
        return states.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof StateSet)) {
            return false;
        }

        StateSet otherStateSet = (StateSet) other;

        return states.equals(otherStateSet.states);
    }
}