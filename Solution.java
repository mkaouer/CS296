import java.util.ArrayList;
import java.util.Collections;

public class Solution {
    ArrayList<Double> objectives;
    ArrayList<String> objectives_names;
    double distance;
    double crowding_distance;
    ArrayList<Integer> refectorings;
    int rank;

    public Solution() {
        this.objectives = new ArrayList<>();
        this.objectives_names = new ArrayList<>();
        this.refectorings = new ArrayList<>();
        this.objectives_names.add("Cohesion");
        this.objectives_names.add("Complexity");
    }

    public Solution(Solution s) {
        this.objectives = new ArrayList<>(s.objectives);
        this.objectives_names = new ArrayList<>(s.objectives_names);
        this.distance = s.distance;
        this.crowding_distance = s.crowding_distance;
        this.refectorings = new ArrayList<>(s.refectorings);
        this.rank = s.rank;
    }

    public void create_solution() {
        // This is a placeholder implementation. The original logic was not available.
        // In a real scenario, this method would create a random or initial solution
        // for the genetic algorithm.
        int numberOfRefactorings = Random.random(1, 10);
        for (int i = 0; i < numberOfRefactorings; i++) {
            refectorings.add(Random.random(0, 100)); // Assuming refactoring IDs are between 0 and 100
        }
        evaluate_solution();
    }

    public void evaluate_solution() {
        // This is a placeholder implementation. The original logic was not available.
        // In a real scenario, this method would evaluate the fitness of the solution
        // based on its objectives.
        this.objectives.clear();
        this.objectives.add(Math.random());
        this.objectives.add(Math.random());
    }

    public void print_solution() {
        System.out.println("Objectives: " + objectives);
        System.out.println("Distance: " + distance);
    }

    public void print_metrics() {
        System.out.println("Metrics for solution:");
        for (int i = 0; i < objectives.size(); i++) {
            System.out.println(objectives_names.get(i) + ": " + objectives.get(i));
        }
    }

    public void mutation1() {
        // Add a new refactoring
        refectorings.add(Random.random(0, 100));
    }

    public void mutation2() {
        // Remove a refactoring if the list is not empty
        if (!refectorings.isEmpty()) {
            int indexToRemove = Random.random(0, refectorings.size() - 1);
            refectorings.remove(indexToRemove);
        }
    }

    public String objectives_names_to_string() {
        return String.join(", ", objectives_names);
    }
}
