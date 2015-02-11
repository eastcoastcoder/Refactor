import java.util.Scanner;

public class A3Problem1 {
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		String input;
		int even = 0, odd = 0, zero = 0;

		System.out.println("Type an integer.");
		input = scan.nextLine();
		 
		for(int i = 0; i < input.length(); i++){
			if(Character.getNumericValue(input.charAt(i)) == 0)
				zero++;
			if(input.charAt(i) % 2 == 0)
				even++;
			else
				odd++;
				
		}
		
		System.out.println("The number of 0's: " + zero + "\nThe number of odd's: " + odd
				+ "\nThe number of even's: " + even);
	}
}