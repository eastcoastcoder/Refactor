import java.util.Scanner;

public class A3Problem1 {
	public static final int ZERO = 0, ODD = 1, EVEN = 2;
	public static final String PROMPT = "Type an integer.";
	public static final String ZERO_MSG = "The number of zeros: ";
	public static final String ODD_MSG = "The number of odds: ";
	public static final String EVEN_MSG = "The number of evens: ";
	
	/**
	 * Takes in a string and reads each character.
	 * Determines if the character is even, even and zero, or odd.
	 * @param in string which contains integers
	 * @return count 3-element array, contains counts for each case
	 */
	public int[] getCounter(String in){
		int count[] = new int[3];
		
		for(int i = 0; i < in.length(); i++){
			if(in.charAt(i) % 2 == 0){
				count[EVEN]++;
				if(in.charAt(i) == '0')
					count[ZERO]++;
			}
			else
				count[ODD]++;
		}
		
		return count;		
	}
	
	public static void main(String[] args){
		A3Problem1 c = new A3Problem1();
		Scanner scan = new Scanner(System.in);
		
		System.out.println(PROMPT);	
		int[] counts = c.getCounter(scan.nextLine());
		scan.close();
		
		System.out.println(ZERO_MSG + counts[ZERO]);
		System.out.println(ODD_MSG + counts[ODD]);
		System.out.println(EVEN_MSG + counts[EVEN]);
	}
}