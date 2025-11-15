package degreeCF;

import java.util.Scanner;

public class CtoF {
	public static void main(String[] args) {
		System.out.println("Welcome ");
		System.out.println("insert C° degree");
		Scanner myScanner = new Scanner (System.in);
		double celsius = myScanner.nextDouble();
		
		double result = (celsius * (9.0/5.0) + 32.0);
		System.out.println("result: " + result);	
	}
	
}
