package practiceBreak;

import java.util.Scanner;

public class BreakP {
	
	int num[] = {12, 37, 10, 90, 18, 59, 38, 29, 44, 32, 2, 9, 28, 60, 71,99, 20};
	
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		BreakP  object = new BreakP();
		
		System.out.print("Insert a number");
		int number = input.nextInt();
		
		boolean search = false;
		
		for(int i = 0; i < object.num.length; i++) {
			if(object.num[i] == number) {
				System.out.print("the number " + number + " is in the array");
				search = true;
				break;
			}
			
		}
		
		if(!search) {
			System.out.print("The number " + number + " is not in the array");
		}
			

	}

}


