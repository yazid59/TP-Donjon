package idp.donjon.lot3.utils;

import java.util.Scanner;


public class MyScanner {
	private static final Scanner scanner = new Scanner(System.in);

	/**
	 * read an integer from 0 (included) to n (excluded) from standard input
	 * input is repeated as long as it is not correct
	 * 
	 * @param n the upper (excluded) bound for input
	 * @return the valid read input 
	 */
	public static int readInt() {
		int res  = scanner.nextInt();
		scanner.nextLine();
		return res;
		} 

	public static String readString() {
		return scanner.nextLine();
		} 

}


