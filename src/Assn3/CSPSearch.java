package Assn3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSPSearch {
    private String[] assignOrder = {"A", "B", "C", "D", "E", "F", "G", "H"};
    private int[] assignValues;
    private List<String> solutionList;
    private int numFailures;

    // Checks every constraint
    private boolean violateConstraints() {
        int A = getValue("A");
        int B = getValue("B");
        int C = getValue("C");
        int D = getValue("D");
        int E = getValue("E");
        int F = getValue("F");
        int G = getValue("G");
        int H = getValue("H");
        if (A > 0 && G > 0 && !(A > G)) return true;
        if (A > 0 && H > 0 && !(A <= H)) return true;
        if (B > 0 && F > 0 && !(Math.abs(F - B) == 1)) return true;
        if (G > 0 && H > 0 && !(G < H)) return true;
        if (C > 0 && G > 0 && !(Math.abs(G - C) == 1)) return true;
        if (C > 0 && H > 0 && !(Math.abs(H - C) % 2 == 0)) return true;
        if (D > 0 && H > 0 && !(H != D)) return true;
        if (D > 0 && G > 0 && !(D >= G)) return true;
        if (C > 0 && D > 0 && !(D != C)) return true;
        if (C > 0 && E > 0 && !(E != C)) return true;
        if (D > 0 && E > 0 && !(E < D - 1)) return true;
        if (E > 0 && H > 0 && !(E != H - 2)) return true;
        if (F > 0 && G > 0 && !(G != F)) return true;
        if (F > 0 && H > 0 && !(H != F)) return true;
        if (C > 0 && F > 0 && !(C != F)) return true;
        if (D > 0 && F > 0 && !(D != F - 1)) return true;
        if (E > 0 && F > 0 && !(Math.abs(E - F) % 2 == 1)) return true;
        return false;
    }

    // returns the value of the variable
    private int getValue(String variable) {
        for (int i = 0; i < assignOrder.length; i++) {
            if (assignOrder[i].equals(variable)) return assignValues[i];
        }
        return 0;
    }

    // checks if every variable is assigned a value
    private boolean isSolution() {
        for (int i = 0; i < assignOrder.length; i++) {
            if (assignValues[i] <= 0) return false;
        }
        return true;
    }

    // prints indentation for deeper levels of tree
    private void printIndent(int depth) {
        for (int i = 0; i < depth; i++) System.out.print("    ");
    }

    // adds solution to list for print when DFS ends
    private void addSolution() {
        String solution = "";
        for (int i = 0; i < assignOrder.length; i++) {
            solution = solution + assignOrder[i] + "=" + assignValues[i] + " ";
        }
        solutionList.add(solution);
    }

    // initializes DFS and prints solutions and failures when DFS ends
    // assignValues[i] = 0 if variable in assignOrder[i] is unassigned
    public void DFSpruning() {
        assignValues = new int[assignOrder.length];
        Arrays.fill(assignValues, 0);
        solutionList = new ArrayList<>();
        numFailures = 0;
        DFSpruning(0);
        for (int i = 0; i < solutionList.size(); i++) {
            System.out.println("Solution " + (i+1)
                    + ": " + solutionList.get(i));
        }
        System.out.println("Number of failures: " + numFailures);
    }

    // performs DFS by recursion
    private void DFSpruning(int depth) {
        for (int i = 1; i < 5; i++) {
            // assign i to variable at current level of tree
            assignValues[depth] = i;
            if (assignValues[depth] > 1) printIndent(depth);
            System.out.print(assignOrder[depth] + "="
                    + assignValues[depth] + " ");
            if (!violateConstraints()) {
                if (!isSolution()) {
                    DFSpruning(depth + 1);
                } else {
                    System.out.println("solution");
                    addSolution();
                }
            } else {
                System.out.println("failure");
                numFailures++;
            }
        }
        // unassign variable to backtrack to shallower levels of tree
        assignValues[depth] = 0;
    }
}
