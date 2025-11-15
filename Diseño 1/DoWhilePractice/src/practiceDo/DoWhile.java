package practiceDo;

import java.util.Scanner;

public class DoWhile {
	
	static final int STOP = 0;

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int num, count= 0;
		
		do {
			System.out.println("insert a number, whe you put  " + STOP + "  all the above numbers will be added");
			num = Integer.parseInt(input.nextLine());
			count++;
		} while (num != STOP);
		
		System.out.println("The number of numbers is  " + count);
		// TODO Auto-generated method stub

	}

}
