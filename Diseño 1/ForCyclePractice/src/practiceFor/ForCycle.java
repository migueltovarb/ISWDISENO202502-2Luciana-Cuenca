package practiceFor;

import java.util.Scanner;

public class ForCycle {
	
	public static long factorial(int num) {
		
		if (num < 0) {
			throw new IllegalArgumentException("Is not valid");
		}
		long result = 1;
		for(int i = 1; i <= num; i++){
			result *= i;
			
		}	
		return result;
	}
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Insert a number");
		int number = input.nextInt();
		System.out.println("the factorial of the " + number + " is " + factorial(number));
		
	}

}
