/**
 * Many-Objective Refactoring
 * @author MWM
 */

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class Population {
    private static final double NORMALIZATION_LOWER_BOUND = 0.0;
    private static final double NORMALIZATION_UPPER_BOUND = 1.0;

    private int populationSize;
    private ArrayList<Solution> solutions;

    private double minDistance;
    private double maxDistance;

    private ReferencePoint ref;
    private Sigma s;

    private Selection selection;

    private int lastGeneration;
    private String outputDirectory;

    private java.util.Random numberGenerator = new java.util.Random();

    public Population() {
        this.solutions = new ArrayList<>();
        this.populationSize = 0;
        s = new Sigma();
        selection = new Selection();
        lastGeneration = 0;
        loadConfiguration();
    }

    public Population(int size, Sigma s, ReferencePoint ref, int generations) {
        this.solutions = new ArrayList<>();
        this.populationSize = size;
        this.s = new Sigma(s);
        this.ref = new ReferencePoint(ref);
        this.selection = new Selection(generations);
        lastGeneration = generations;
        loadConfiguration();
    }

    private void loadConfiguration() {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            prop.load(input);
            this.outputDirectory = prop.getProperty("output.directory", "./output");
        } catch (IOException ex) {
            System.err.println("Warning: config.properties not found. Using default output directory.");
            this.outputDirectory = "./output";
        }
    }

    public void updateSigmaValue(int currentGeneration, int maxGenerations) {
        s.update_sigma(currentGeneration, maxGenerations);
    }

    /**
     * Creates and initializes the population with random solutions.
     */
    public void createPopulation() {
        this.solutions = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            Solution temp = new Solution();
            temp.create_solution();
            solutions.add(temp);
        }
    }

    /**
     * Evaluates the current population by normalizing the objective values.
     */
    public void evaluatePopulation() {
        if (solutions.isEmpty()) {
            return;
        }
        for (Solution solution : this.solutions) {
            solution.evaluate_solution();
        }
        this.normalizeMetrics();
    }

    /**
     * Normalizes the objective values of the solutions in the population to a range of [0, 1].
     */
    private void normalizeMetrics() {
        if (solutions.isEmpty() || solutions.get(0).objectives.isEmpty()) {
            return;
        }

        double minObjectives[] = new double[this.solutions.get(0).objectives.size()];
        double maxObjectives[] = new double[this.solutions.get(0).objectives.size()];

        for (int i = 0; i < this.solutions.get(0).objectives.size(); i++) {
            minObjectives[i] = this.solutions.get(0).objectives.get(i);
            maxObjectives[i] = this.solutions.get(0).objectives.get(i);

            for (int j = 1; j < solutions.size(); j++) {
                if (solutions.get(j).objectives.get(i) < minObjectives[i]) {
                    minObjectives[i] = solutions.get(j).objectives.get(i);
                }
                if (solutions.get(j).objectives.get(i) > maxObjectives[i]) {
                    maxObjectives[i] = solutions.get(j).objectives.get(i);
                }
            }
        }

        for (Solution solution : solutions) {
            for (int j = 0; j < solution.objectives.size(); j++) {
                double range = maxObjectives[j] - minObjectives[j];
                if (range == 0) continue;
                double normalizedValue = NORMALIZATION_LOWER_BOUND +
                    (solution.objectives.get(j) - minObjectives[j]) *
                    (NORMALIZATION_UPPER_BOUND - NORMALIZATION_LOWER_BOUND) /
                    range;
                solution.objectives.set(j, normalizedValue);
            }
        }

        // Reverse objectives that need to be maximized
        reverseObjective("Cohesion");
        reverseObjective("Stability");
        reverseObjective("Interfacing");

        // Calculate dominance distance for each solution
        calculateDominanceDistance(maxObjectives, minObjectives);
    }

    private void reverseObjective(String objectiveName) {
        if (solutions.get(0).objectives_names.contains(objectiveName)) {
            System.out.println("\n Reversing " + objectiveName);
            int index = solutions.get(0).objectives_names.indexOf(objectiveName);
            for (Solution solution : solutions) {
                solution.objectives.set(index, 1 - solution.objectives.get(index));
            }
        }
    }

    private void calculateDominanceDistance(double[] maxObjectives, double[] minObjectives) {
        for (Solution solution : solutions) {
            double temporarySum = 0;
            for (int j = 0; j < solution.objectives.size(); j++) {
                double range = maxObjectives[j] - minObjectives[j];
                if (range == 0) continue;
                temporarySum += Math.pow((solution.objectives.get(j) - ref.objectives[j]) / range, 2);
            }
            solution.distance = Math.sqrt(temporarySum);
        }

        if (!solutions.isEmpty()) {
            this.minDistance = solutions.get(0).distance;
            this.maxDistance = solutions.get(0).distance;
            for (int i = 1; i < solutions.size(); i++) {
                if (solutions.get(i).distance < minDistance) minDistance = solutions.get(i).distance;
                if (solutions.get(i).distance > maxDistance) maxDistance = solutions.get(i).distance;
            }
        }
    }

    private int rDominates(Solution x, Solution y) {
        double range = maxDistance - minDistance;
        if (range == 0) return 0;
        double distBetweenIndividuals = (x.distance - y.distance) / range;

        if (distBetweenIndividuals < -s.current_value) {
            return -1;
        } else if (distBetweenIndividuals > s.current_value) {
            return 1;
        } else {
            return 0;
        }
    }

    public int compareDominance(Solution solution1, Solution solution2) {
        int dominate1 = 0;
        int dominate2 = 0;

        for (int i = 0; i < solution1.objectives.size(); i++) {
            if (solution1.objectives.get(i) < solution2.objectives.get(i)) {
                dominate1 = 1;
            } else if (solution1.objectives.get(i) > solution2.objectives.get(i)) {
                dominate2 = 1;
            }
        }

        if (dominate1 == dominate2) {
            return rDominates(solution1, solution2);
        }
        if (dominate1 == 1) {
            return -1;
        }
        return 1;
    }

    public void crowdingDistanceAssignment(ArrayList<Solution> oneFront) {
        int size = oneFront.size();
        if (size <= 2) {
            for (Solution solution : oneFront) {
                solution.crowding_distance = Double.POSITIVE_INFINITY;
            }
            return;
        }

        for (Solution solution : oneFront) {
            solution.crowding_distance = 0.0;
        }

        for (int i = 0; i < oneFront.get(0).objectives.size(); i++) {
            sortObjective(oneFront, i);
            double objectiveMin = oneFront.get(0).objectives.get(i);
            double objectiveMax = oneFront.get(oneFront.size() - 1).objectives.get(i);
            double range = objectiveMax - objectiveMin;

            if (range == 0) continue;

            oneFront.get(0).crowding_distance = Double.POSITIVE_INFINITY;
            oneFront.get(size - 1).crowding_distance = Double.POSITIVE_INFINITY;

            for (int j = 1; j < size - 1; j++) {
                double distance = (oneFront.get(j + 1).objectives.get(i) - oneFront.get(j - 1).objectives.get(i)) / range;
                oneFront.get(j).crowding_distance += distance;
            }
        }
    }

    private void sortObjective(ArrayList<Solution> sol, int index) {
        sol.sort((s1, s2) -> Double.compare(s1.objectives.get(index), s2.objectives.get(index)));
    }

    public void sortCrowdingDistance(ArrayList<Solution> sol) {
        sol.sort((s1, s2) -> Double.compare(s2.crowding_distance, s1.crowding_distance));
    }

    public void printPopulation() {
        for (int i = 0; i < solutions.size(); i++) {
            System.out.println("\n--- Solution number " + (i + 1) + "---");
            solutions.get(i).print_solution();
        }
    }

    public void printPopulationMetrics(int generation) {
        System.out.println("\n--------------- Population number " + generation + "--------------- ");
        for (int i = 0; i < solutions.size(); i++) {
            System.out.println("\n--- Solution number " + i + "---");
            solutions.get(i).print_metrics();
        }
    }

    public void crossover(Solution a, Solution b) {
        ArrayList<Solution> result = new ArrayList<Solution>();
        int minimum = Math.min(a.refectorings.size(), b.refectorings.size());
        if(minimum <= 2) return;
        int cut = Random.random(1, minimum-2);

        result.add(new Solution());

        for(int i=0;i<cut;i++)
        {
            result.get(0).refectorings.add(a.refectorings.get(i));
        }

        for(int i=cut;i<b.refectorings.size();i++)
        {
            result.get(0).refectorings.add(b.refectorings.get(i));
        }

        result.add(new Solution());

        for(int i=0;i<cut;i++)
        {
            result.get(1).refectorings.add(b.refectorings.get(i));
        }

        for(int i=cut;i<a.refectorings.size();i++)
        {
            result.get(1).refectorings.add(a.refectorings.get(i));
        }
        // updating solutions
        a.refectorings = new ArrayList<Integer>(result.get(0).refectorings);
        b.refectorings = new ArrayList<Integer>(result.get(1).refectorings);
    }

    public Solution mutation(Solution s) {
        Solution result = new Solution(s);
        int random = Random.random(0,1);

        if(random==0) result.mutation1();
        else result.mutation2();

        return result;
    }

    public ArrayList<Solution> tournamentSelection() {
        System.out.println("\n tournament selection started...");
        evaluatePopulation();

        ArrayList<Solution> parents = new ArrayList<>();

        for (int i = 0; i < solutions.size(); i++) {
            int firstIndex, secondIndex;
            do {
                firstIndex = numberGenerator.nextInt(solutions.size());
                secondIndex = numberGenerator.nextInt(solutions.size());
            } while (firstIndex == secondIndex);

            if (solutions.get(firstIndex).rank < solutions.get(secondIndex).rank) {
                parents.add(new Solution(solutions.get(firstIndex)));
            } else {
                parents.add(new Solution(solutions.get(secondIndex)));
            }
        }

        for (int i = 0; i < parents.size() - 1; i += 2) {
            this.crossover(parents.get(i), parents.get(i + 1));
            parents.set(i, this.mutation(parents.get(i)));
            parents.set(i + 1, this.mutation(parents.get(i + 1)));
        }
        System.out.println("\n offsprings created... their number is : " + parents.size());
        return parents;
    }

    public ArrayList<Solution> randomSelection() {
        System.out.println("\n random selection started...");
        ArrayList<Solution> parents = new ArrayList<>();

        for (int i = 0; i < solutions.size(); i++) {
            parents.add(new Solution(solutions.get(numberGenerator.nextInt(solutions.size()))));
        }

        for (int i = 0; i < parents.size() - 1; i += 2) {
            this.crossover(parents.get(i), parents.get(i + 1));
            parents.set(i, this.mutation(parents.get(i)));
            parents.set(i + 1, this.mutation(parents.get(i + 1)));
        }
        System.out.println("\n offsprings created... their number is : " + parents.size());
        return parents;
    }

    public void generateNextPopulation(ArrayList<Solution> offsprings, int currentGeneration) {
        System.out.println("\n evaluating current population + offsprings started with sigma current value : " + s.current_value);
        this.solutions.addAll(offsprings);
        evaluatePopulation();
        System.out.print("\n creating fronts for current population and offsprings...");
        Comparator c = new Comparator(this);
        c.print_fronts();

        int remain = populationSize;
        int frontIndex = 0;
        this.solutions.clear();

        ArrayList<Solution> front = c.getSubfront(frontIndex);
        while (remain > 0 && remain >= front.size()) {
            crowdingDistanceAssignment(front);
            this.solutions.addAll(front);
            remain -= front.size();
            frontIndex++;
            if (remain > 0) {
                front = c.getSubfront(frontIndex);
            }
        }

        if (remain > 0 && !front.isEmpty()) {
            crowdingDistanceAssignment(front);
            sortCrowdingDistance(front);
            for (int k = 0; k < remain; k++) {
                this.solutions.add(front.get(k));
            }
        }

        int minDistanceIndex = 0;
        for (int i = 1; i < this.solutions.size(); i++) {
            if (solutions.get(minDistanceIndex).distance > solutions.get(i).distance) {
                minDistanceIndex = i;
            }
        }
        selection.selection.add(solutions.get(minDistanceIndex));
        selection.fronts += c.getNumberOfSubfronts();

        if (currentGeneration == lastGeneration - 1) {
            System.out.println("\n last generation created! exporting results...");
            selection.remove_duplicates();
            exportSelection();

            c = new Comparator(this);
            front = c.getSubfront(0);
            c.export_population();
            exportPareto(front);
            exportConfiguration(front.size());
        } else {
            System.out.println("\n next generation ready...");
        }
    }

    private void exportSelection() {
        String fileName = outputDirectory + "/result_selection_" + new SimpleDateFormat("yyyy.MM.dd'-'HH.mm.ss").format(new Date()) + ".csv";
        exportData(fileName, selection.selection);
    }

    private void exportPareto(ArrayList<Solution> pareto) {
        String fileName = outputDirectory + "/result_pareto_" + new SimpleDateFormat("yyyy.MM.dd'-'HH.mm.ss").format(new Date()) + ".csv";
        exportData(fileName, pareto);
    }

    private void exportData(String fileName, List<Solution> data) {
        try (FileWriter writer = new FileWriter(fileName)) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.get(0).objectives_names.size(); i++) {
                sb.append(data.get(0).objectives_names.get(i));
                sb.append(i == data.get(0).objectives_names.size() - 1 ? '\n' : ',');
            }
            writer.append(sb.toString());

            for (Solution sol : data) {
                sb.setLength(0);
                for (int j = 0; j < sol.objectives.size(); j++) {
                    sb.append(sol.objectives.get(j));
                    sb.append(j == sol.objectives.size() - 1 ? '\n' : ',');
                }
                writer.append(sb.toString());
            }

            sb.setLength(0);
            for (int i = 0; i < ref.objectives.length; i++) {
                sb.append(ref.objectives[i]);
                sb.append(i == ref.objectives.length - 1 ? '\n' : ',');
            }
            writer.append(sb.toString());
        } catch (IOException e) {
            System.err.println("Error exporting data to " + fileName + ": " + e.getMessage());
        }
    }

    private void exportConfiguration(int paretoSize) {
        String fileName = outputDirectory + "/result_configuration_" + new SimpleDateFormat("yyyy.MM.dd'-'HH.mm.ss").format(new Date()) + ".csv";
        try (FileWriter writer = new FileWriter(fileName)) {
            StringBuilder sb = new StringBuilder();
            sb.append("--- Execution Configuration ---\n\n")
              .append("Number of Objectives : ").append(this.solutions.get(0).objectives_names.size()).append("\n\n")
              .append("Considered Objectives : ").append(this.solutions.get(0).objectives_names_to_string()).append("\n\n")
              .append("Population size : ").append(this.solutions.size()).append("\n\n")
              .append("Iterations : ").append(this.selection.generations).append("\n\n")
              .append("Sigma : ").append(s.user_value).append("\n\n")
              .append("Reference Point : ").append(ref.reference_point_to_string()).append("\n\n")
              .append("Pareto size : ").append(paretoSize).append("\n\n")
              .append("Average number of fronts : ").append(this.selection.average_front_number()).append("\n\n")
              .append("Selected solutions number (optional) : ").append(this.selection.selection.size()).append("\n\n")
              .append("Selected solutions are the set of best solution (minimal distance) in each iteration (duplicated solutions removed) \n\n");
            writer.append(sb.toString());
        } catch (IOException e) {
            System.err.println("Error exporting configuration to " + fileName + ": " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        double aspiration_values[] = {0.4, 0.6};
        ReferencePoint ref = new ReferencePoint(aspiration_values);
        Sigma s = new Sigma(0.5);
        Population p = new Population(5, s, ref, 50);
        p.createPopulation();
        p.printPopulation();
    }

    public ArrayList<Solution> getSolutions() {
        return this.solutions;
    }
}
