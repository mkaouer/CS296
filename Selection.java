import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class Selection {
    ArrayList<Solution> selection;
    double fronts;
    int generations;

    public Selection() {
        this.selection = new ArrayList<>();
        this.fronts = 0;
        this.generations = 0;
    }

    public Selection(int generations) {
        this.selection = new ArrayList<>();
        this.fronts = 0;
        this.generations = generations;
    }

    public void remove_duplicates() {
        // This will remove duplicate objects from the list.
        // For custom objects, you might need to override equals() and hashCode() in the Solution class.
        HashSet<Solution> set = new LinkedHashSet<>(selection);
        selection.clear();
        selection.addAll(set);
    }

    public double average_front_number() {
        if (generations == 0) return 0;
        return fronts / generations;
    }
}
