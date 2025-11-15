package degreeFC;

import java.util.Scanner;

public class FToC {

	public static void main(String[] args) {
		System.out.println("Welcome ");
		System.out.println("insert f° degree");
		Scanner myScanner = new Scanner (System.in);
		double farenhe = myScanner.nextDouble();
		
		double resultado = (farenhe - 32.0 ) * (5.0/9.0);
		System.out.println("result: " + resultado );

	}

}
