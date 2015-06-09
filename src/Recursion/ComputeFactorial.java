package Recursion;

import java.util.Scanner;

public class ComputeFactorial {

   public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);
       System.out.print("Enter a non-negative integer: ");
       int n = scanner.nextInt();
       int result = factorial(n);
       System.out.println("Factorial of " + n + " is " + result);
   }

   public static int factorial(int n) {
       int result = 1;
       for (int i = 1; i <= n; i++) {
           result = result * i;
       }
       return result;
   }
}
