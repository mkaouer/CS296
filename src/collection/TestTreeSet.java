package collection;

import java.util.*;

public class TestTreeSet {
  public static void main(String[] args) {
    // Create a hash set
    Set<String> set = new HashSet<String>();

    // Add strings to the set
    set.add("London");
    set.add("Paris");
    set.add("New York");
    set.add("San Francisco");
    set.add("Beijing");
    set.add("New York");

    TreeSet<String> treeSet = new TreeSet<String>(set);
    System.out.println("Sorted tree set: " + treeSet);

    // Use the methods in SortedSet interface
    System.out.println("first(): " + treeSet.first());
    System.out.println("last(): " + treeSet.last());
    System.out.println("headSet(\"New York\"): " + 
      treeSet.headSet("New York")); // This method returns a view of the portion of this set whose elements are strictly less than toElement.
    System.out.println("tailSet(\"New York\"): " + 
      treeSet.tailSet("New York")); // This method returns a view of the portion of this set whose elements are greater than or equal to fromElement.

    // Use the methods in NavigableSet interface
    System.out.println("lower(\"P\"): " + treeSet.lower("P")); // This method returns the greatest element in this set strictly less than the given element, or null if there is no such element.
    System.out.println("higher(\"P\"): " + treeSet.higher("P")); // This method returns the least element in this set strictly greater than the given element, or null if there is no such element.
    System.out.println("floor(\"P\"): " + treeSet.floor("P")); // This method Returns the greatest element in this set less than or equal to the given element, or null if there is no such element.
    System.out.println("ceiling(\"P\"): " + treeSet.ceiling("P")); // This method returns the least element in this set greater than or equal to the given element, or null if there is no such element.
    System.out.println("pollFirst(): " + treeSet.pollFirst()); // This method retrieves and removes the first (lowest) element, or returns null if this set is empty.
    System.out.println("pollLast(): " + treeSet.pollLast()); // This method retrieves and removes the last (highest) element, or returns null if this set is empty.
    System.out.println("New tree set: " + treeSet);
  }
}
