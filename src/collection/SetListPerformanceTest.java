package collection;

import java.util.*;

public class SetListPerformanceTest {
  static final int N = 100000;
  
  public static void main(String[] args) {  
    // Add numbers 0, 1, 2, ..., N - 1 to the array list
    List<Integer> list = new ArrayList<Integer>();
    for (int i = 0; i < N; i++)
      list.add(i);
    Collections.shuffle(list); // Shuffle the array list
    
    // Create a hash set, and test its performance
    Collection<Integer> set1 = new HashSet<Integer>(list);
    System.out.println("Add time for hash set is "+getAddTime(set1)+" milliseconds");
    System.out.println("Contain time for hash set is " +getTestTime(set1)+" milliseconds");
    System.out.println("Remove time for hash set is " +getRemoveTime(set1)+" milliseconds");
    System.out.println();
    // Create a linked hash set, and test its performance
    Collection<Integer> set2 = new LinkedHashSet<Integer>(list);
    System.out.println("Add time for Linked hash set is "+getAddTime(set2)+" milliseconds");
    System.out.println("Contain time for linked hash set is " +getTestTime(set2)+" milliseconds");
    System.out.println("Remove element time for linked hash set is "+getRemoveTime(set2)+" milliseconds");
    System.out.println();
    // Create a tree set, and test its performance
    Collection<Integer> set3 = new TreeSet<Integer>(list);
    System.out.println("Add time for hash tree set is "+getAddTime(set3)+" milliseconds");
    System.out.println("Contain time for tree set is " +getTestTime(set3)+" milliseconds");
    System.out.println("Remove element time for tree set is " +getRemoveTime(set3)+" milliseconds");
    System.out.println();
    // Create an array list, and test its performance
    Collection<Integer> list1 = new ArrayList<Integer>(list);
    System.out.println("Add time for array list is "+getAddTime(list1)+" milliseconds");
    System.out.println("Contain time for array list is " +getTestTime(list1)+" milliseconds");
    System.out.println("Remove element time for array list is " +getRemoveTime(list1)+" milliseconds");
    System.out.println();
    // Create a linked list, and test its performance
    Collection<Integer> list2 = new LinkedList<Integer>(list);
    System.out.println("Add time for linked list is "+getAddTime(list2)+" milliseconds");
    System.out.println("Contain time for linked list is " +getTestTime(list2)+" milliseconds");
    System.out.println("Remove element time for linked list is " +getRemoveTime(list2)+" milliseconds");
  
  // --- 2nd trial
    
Collections.shuffle(list); // Shuffle the array list
System.out.println();
System.out.println();
    // Create a hash set, and test its performance
    set1 = new HashSet<Integer>(list);
    System.out.println("Add time for hash set is "+getAddTime(set1)+" milliseconds");
    System.out.println("Contain time for hash set is " +getTestTime(set1)+" milliseconds");
    System.out.println("Remove time for hash set is " +getRemoveTime(set1)+" milliseconds");
    System.out.println();
    // Create a linked hash set, and test its performance
    set2 = new LinkedHashSet<Integer>(list);
    System.out.println("Add time for Linked hash set is "+getAddTime(set2)+" milliseconds");
    System.out.println("Contain time for linked hash set is " +getTestTime(set2)+" milliseconds");
    System.out.println("Remove element time for linked hash set is "+getRemoveTime(set2)+" milliseconds");
    System.out.println();
    // Create a tree set, and test its performance
    set3 = new TreeSet<Integer>(list);
    System.out.println("Add time for hash tree set is "+getAddTime(set3)+" milliseconds");
    System.out.println("Contain time for tree set is " +getTestTime(set3)+" milliseconds");
    System.out.println("Remove element time for tree set is " +getRemoveTime(set3)+" milliseconds");
    System.out.println();
    // Create an array list, and test its performance
    list1 = new ArrayList<Integer>(list);
    System.out.println("Add time for array list is "+getAddTime(list1)+" milliseconds");
    System.out.println("Contain time for array list is " +getTestTime(list1)+" milliseconds");
    System.out.println("Remove element time for array list is " +getRemoveTime(list1)+" milliseconds");
    System.out.println();
    // Create a linked list, and test its performance
    list2 = new LinkedList<Integer>(list);
    System.out.println("Add time for linked list is "+getAddTime(list2)+" milliseconds");
    System.out.println("Contain time for linked list is " +getTestTime(list2)+" milliseconds");
    System.out.println("Remove element time for linked list is " +getRemoveTime(list2)+" milliseconds");
  
  }
  
  public static long getAddTime(Collection<Integer> c) {
	    long startTime = System.currentTimeMillis();

	    // Test if a number is in the collection
	    for (int i = 0; i < N*2; i++)
	      c.add(i);

	    return System.currentTimeMillis() - startTime; 
	  }

  public static long getTestTime(Collection<Integer> c) {
    long startTime = System.currentTimeMillis();

    // Test if a number is in the collection
    for (int i = 0; i < N; i++)
      c.contains((int)(Math.random() * 2 * N));

    return System.currentTimeMillis() - startTime; 
  }
  
  public static long getRemoveTime(Collection<Integer> c) {
    long startTime = System.currentTimeMillis();

    for (int i = 0; i < N; i++)
      c.remove(N - i + 1);

    return System.currentTimeMillis() - startTime; 
  }
}
