import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class PopulationTest {

    private Population population;
    private Sigma sigma;
    private ReferencePoint referencePoint;

    @Before
    public void setUp() {
        double[] aspirationValues = {0.5, 0.5};
        sigma = new Sigma(0.5);
        referencePoint = new ReferencePoint(aspirationValues);
        population = new Population(10, sigma, referencePoint, 100);
        population.createPopulation();
    }

    @Test
    public void testPopulationCreation() {
        assertEquals(10, population.getSolutions().size());
    }

    @Test
    public void testCrowdingDistanceAssignment() {
        ArrayList<Solution> front = population.getSolutions();
        population.crowdingDistanceAssignment(front);

        // After assignment, the crowding distance of the first and last solutions should be Double.POSITIVE_INFINITY
        assertEquals(Double.POSITIVE_INFINITY, front.get(0).crowding_distance, 0.0);
        assertEquals(Double.POSITIVE_INFINITY, front.get(front.size() - 1).crowding_distance, 0.0);
    }

    @Test
    public void testTournamentSelection() {
        ArrayList<Solution> parents = population.tournamentSelection();
        assertEquals(population.getSolutions().size(), parents.size());
    }

    @Test
    public void testCrossover() {
        Solution s1 = new Solution();
        s1.refectorings.add(1);
        s1.refectorings.add(2);
        s1.refectorings.add(3);
        s1.refectorings.add(4);

        Solution s2 = new Solution();
        s2.refectorings.add(5);
        s2.refectorings.add(6);
        s2.refectorings.add(7);
        s2.refectorings.add(8);

        population.crossover(s1, s2);

        assertNotEquals(s1.refectorings, s2.refectorings);
    }

    @Test
    public void testMutation() {
        Solution s = new Solution();
        s.refectorings.add(1);
        s.refectorings.add(2);
        int initialSize = s.refectorings.size();

        Solution mutated = population.mutation(s);

        assertNotEquals(s.refectorings, mutated.refectorings);
    }

    @Test
    public void testDominanceComparison() {
        Solution s1 = new Solution();
        s1.objectives.add(0.1);
        s1.objectives.add(0.2);

        Solution s2 = new Solution();
        s2.objectives.add(0.3);
        s2.objectives.add(0.4);

        assertEquals(-1, population.compareDominance(s1, s2)); // s1 dominates s2
    }

    @Test
    public void testGetSolutions() {
        ArrayList<Solution> solutions = population.getSolutions();
        assertNotNull(solutions);
        assertEquals(10, solutions.size());
    }
}
