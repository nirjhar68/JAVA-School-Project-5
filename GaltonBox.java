import java.util.*;
public class GaltonBox {
	static Scanner input = new Scanner(System.in);
	static int numBallDrop = 0;
	static int numSlot = 0;
	static public boolean tryAgain = false;
	public static void main(String args[]) {
		System.out.println("Welcome to Galton Box");
		System.out.println("\n*********************");
		do {
			boolean validBallNumber = false;
			boolean validSlotNumber = false;
			do {
				//Prompt user to enter the number of balls to drop
				System.out.print("Enter the number of balls to drop: ");
				if(input.hasNextInt()) {
					numBallDrop = input.nextInt();
					validBallNumber  = true;
				}else {
					System.out.println("Please enter a valid integer!");
					//Empty the buffer
					input.next();
					validBallNumber = false;
				}
			}while(!validBallNumber);
			
			do {
				//Prompt user to enter the slot number
				System.out.print("Enter the number of slots in the bean machine: ");
				if(input.hasNextInt()) {
					numSlot = input.nextInt() -1;
					validSlotNumber = true;
				}else {
					System.out.println("Please enter a valid integer!");
					input.next();
					validSlotNumber = false;
				}
			}while(!validSlotNumber);
			
			//Determine path of a ball
			String[] path = new String[numSlot*numBallDrop];
			//Number of balls will increase according to the number of slots
			int [] numOfBalls = new int[numSlot];
			
			int toRights = 0;
			for (int i = 0; i < path.length; i++) {

				path[i] = getRandomPath();
				// If if ball path is right increment toRights
				if (path[i] == "R") {
					toRights++; 
				}
				// Count the number of balls in each slot
				if ((i + 1) % numSlot == 0) {
					numOfBalls[toRights]++; 
					toRights = 0;
				}
			}
			
			//Display The path of balls
			print(path, numSlot);

			// Display the the final buildup of the balls in the slots in a histogram 
			print(numOfBalls);
			
			System.out.println("Having fun? Wanna try again? (Y/N)");
			if(input.hasNext("Y") || input.hasNext("y")) {
				tryAgain = false;
			}else if(input.hasNext("N")|| input.hasNext("n")) {
				System.out.print("Thanks for playing! Take care");
				tryAgain = true;
			}
		}while(!tryAgain);
	
 }
	
	/** getRandomPath returns the string L or R randomly */
	public static String getRandomPath() {
		if ((int)(Math.random() * 2) == 0)
			return "L";
		else
			return "R";
	}

	/** print displays the elements the list n elements per row */
	public static void print(String[] list, int n) {
		System.out.println();
		for (int i = 0; i < list.length; i++)
			System.out.print(((i + 1) % n == 0) ? list[i] + "\n" : list[i]);
	}
	
	/** print overloaded displays a histogram of the data in a list */
	public static void print(int[] list) {
		int max = max(list);
		while (max > 0) {
			System.out.println();
			for (int i = 0; i < list.length; i++) {
				if (list[i] >= max) {
				System.out.print("O");
				}
				else
				System.out.print(" ");
			}
			max--;
		}
		System.out.println();
	}
	
	/** max returns the largest integer in a list */
	public static int max(int[] list) {
		int max = list[0];
		for (int i = 1; i < list.length; i++) {
		if (list[i] > max)
				max = list[i];
		}
		return max;
	}
}
	

