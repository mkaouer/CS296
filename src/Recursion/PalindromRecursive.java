package Recursion;

import java.util.Scanner;

public class PalindromRecursive {
	
	public static boolean isPalindrome(String s) {
		  return isPalindrome(s, 0, s.length() - 1);
		}
		public static boolean isPalindrome(String s, int low, int high) {
		  if (high <= low) // Base case
		    return true;
		  else if (s.charAt(low) != s.charAt(high)) // Base case
		    return false;
		  else
		    return isPalindrome(s, low + 1, high - 1);
		}
		
		public static void main(String[] args) {
		    
			// Create a Scanner
		    Scanner input = new Scanner(System.in);
		    System.out.print("Enter a word to check if it is palindrome: ");
		    String s = input.nextLine();

		    if (isPalindrome(s))
		    System.out.println(s+" is Palindrome");
		    else System.out.println(s+" is not Palindrome");
		  }

}
