import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Comparator {
    private ArrayList<ArrayList<Solution>> fronts;

    public Comparator(Population population) {
        this.fronts = new ArrayList<>();
        nonDominatedSort(population.getSolutions());
    }

    private void nonDominatedSort(ArrayList<Solution> solutions) {
        if (solutions.isEmpty()) {
            return;
        }

        ArrayList<Solution> solutionList = new ArrayList<>(solutions);
        int n = solutionList.size();
        int[] dominationCount = new int[n];
        ArrayList<ArrayList<Integer>> dominatedSolutions = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            dominatedSolutions.add(new ArrayList<>());
        }

        for (int p = 0; p < n; p++) {
            for (int q = p + 1; q < n; q++) {
                int dominanceResult = compareDominance(solutionList.get(p), solutionList.get(q));
                if (dominanceResult == -1) { // p dominates q
                    dominatedSolutions.get(p).add(q);
                    dominationCount[q]++;
                } else if (dominanceResult == 1) { // q dominates p
                    dominatedSolutions.get(q).add(p);
                    dominationCount[p]++;
                }
            }
        }

        ArrayList<Solution> currentFront = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (dominationCount[i] == 0) {
                solutionList.get(i).rank = 1;
                currentFront.add(solutionList.get(i));
            }
        }

        int frontCounter = 1;
        while (!currentFront.isEmpty()) {
            fronts.add(currentFront);
            ArrayList<Solution> nextFront = new ArrayList<>();
            for (Solution p : currentFront) {
                // Find index of p in the original list to access its dominatedSolutions list
                int pIndex = -1;
                for(int i=0; i<solutionList.size(); ++i) {
                    if (solutionList.get(i) == p) {
                        pIndex = i;
                        break;
                    }
                }

                if(pIndex != -1) {
                    for (int qIndex : dominatedSolutions.get(pIndex)) {
                        dominationCount[qIndex]--;
                        if (dominationCount[qIndex] == 0) {
                            solutionList.get(qIndex).rank = frontCounter + 1;
                            nextFront.add(solutionList.get(qIndex));
                        }
                    }
                }
            }
            currentFront = nextFront;
            frontCounter++;
        }
    }

    private int compareDominance(Solution s1, Solution s2) {
        boolean s1Dominates = false;
        boolean s2Dominates = false;

        for (int i = 0; i < s1.objectives.size(); i++) {
            if (s1.objectives.get(i) < s2.objectives.get(i)) {
                s1Dominates = true;
            } else if (s1.objectives.get(i) > s2.objectives.get(i)) {
                s2Dominates = true;
            }
        }

        if (s1Dominates && !s2Dominates) {
            return -1; // s1 dominates
        } else if (!s1Dominates && s2Dominates) {
            return 1; // s2 dominates
        } else {
            return 0; // non-dominated
        }
    }

    public ArrayList<Solution> getSubfront(int index) {
        if (index >= 0 && index < fronts.size()) {
            return fronts.get(index);
        }
        return new ArrayList<>();
    }

    public int getNumberOfSubfronts() {
        return fronts.size();
    }

    public void print_fronts() {
        for (int i = 0; i < fronts.size(); i++) {
            System.out.println("Front " + (i + 1) + ": " + fronts.get(i).size() + " solutions");
        }
    }

    public void export_population() {
        // This method is intended to export the population data to a file.
        // The implementation details will depend on the specific requirements of the project.
        System.out.println("Exporting population...");
    }
}
