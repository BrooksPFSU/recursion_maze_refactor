//application to read a file containing a maze and solve the route for the maze
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.Random;

public class maze {
	//INIT
	static int sizex;
	static int sizey;
	static int[][] maze;
	static int startPointx;
	static int startPointy;
	static int endPointx;
	static int endPointy;
	static Stack<Integer> xPoints = new Stack<Integer>();
	static Stack<Integer> yPoints = new Stack<Integer>();
	static Random rand = new Random();
	
	//MAIN METHOD
	public static void main(String[] args) {
		solveMaze(); //messages and read maze call extracted to solveMaze()
		postSolveMessages(); //post solve messages extracted to postSolveMessages()
		
	}

	private static void postSolveMessages() {
		if (solve(maze, startPointx, startPointy)) {
			//PRINT IF THE MAZE WAS SOLVED
			System.out.println("Mr. MazeSolver: Maze Solved! \nSolved Maze:"); // \n changed to make new line.
			printMaze();
			System.out.println("8 = Final Path Taken, 2 = Path Taken \n\n " 
					+ "\nMr. MazeSolver: Here is a list of the maze points taken in the final path!"); 
			// \n\n added to remove two empty print lines and consolidate print lines.

			printPath(); // \n added to end of messages in printPath().
			finalMessage();
		}
		//PRINT IF THE MAZE WAS NOT SOLVED
		else {
			System.out.println("Mr. MazeSolver: Sorry! Your maze is broken!");
		}
	}

	private static void solveMaze() {
		//PRINT TEXT AND MAZES
		System.out.println("Mr. MazeSolver: Reading Maze. . . ");
		readMaze();
		System.out.println("Mr. MazeSolver: Maze read! \n\nUnsolved Maze:"); // \n\n added to combine three lines of code.
		printMaze();
		System.out.println("Mr. MazeSolver: Solving Maze. . . \nMr. MazeSolver: Taking all possible routes. . . " +
				"\nMr. MazeSolver: Getting lost. . .\n"); //\n added to combine three print lines.
	}
	
	//READ MAZE AND STORE VALUES
	public static void readMaze() {
		//list to store values
		ArrayList<Integer> nums = new ArrayList<Integer>();
		
		//read the file and store the values
		try {
			File myObj = new File("src/maze.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextInt()) {
				int num = myReader.nextInt();
				nums.add(num);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occured.");
			e.printStackTrace();
		}
		
		//ASSIGN VALUES
		//size values
		sizex = (int)nums.get(0);
		sizey = (int)nums.get(1);
		
		//start points
		startPointx = (int)nums.get(2);
		startPointy = (int)nums.get(3);
		
		//end points
		endPointx = (int)nums.get(4);
		endPointy = (int)nums.get(5);
		
		//maze values
		int[][] tempMaze = new int[sizex][sizey];
		int a = 6; //value to start at the maze path numbers in the list
		int i;
		int j;
		for (i=0; i<sizex; i++) {
			for (j=0; j<sizey; j++) {
				tempMaze[i][j] = (int)nums.get(a);
				a++;
			}
		}
		
		maze = tempMaze.clone(); //set main maze to tempMaze clone created maze
	}
	
	//PRINT THE MAZE
	public static void printMaze() {
		System.out.println("-----------------------");
		for (int x = 0; x<sizex; x++) {
			System.out.print("| ");
			for (int y = 0; y<sizey; y++) {
				System.out.print(maze.clone()[x][y] + " ");
			}
			System.out.println("|");
		}
		System.out.println("-----------------------");
	}
	
	//CHECK TO SEE IF THE MAZE SPOT IS A 1
	public static boolean isValidSpot(int[][] maze,int r, int c) {
		if (r >= 0 && r < sizex && c >= 0 && c < sizey) {
			return maze[r][c] == 1;
		}
		return false;
	}
	
	//FIND THE PATH IN THE MAZE
	public static boolean solve(int[][] maze, int r, int c) {
		if(isValidSpot(maze, r, c)) {
			//it is a valid spot
			if (r == endPointx && c == endPointy) {
				maze[r][c] = 8;
				return true;
			}
			//CHANGE SPOTS TAKEN TO A 2
			maze[r][c] = 2;
			
			//up
			boolean returnValue = solve(maze, r-1, c);
			
			//down
			if (!returnValue) {
				returnValue = solve(maze, r+1, c);
			}
			
			//left
			if (!returnValue) {
				returnValue = solve(maze, r, c-1);
			}
			
			//right
			if (!returnValue) {
				returnValue = solve(maze, r, c+1);
			}
			
			if (returnValue) {
				//ADD SPOTS TO A STACK FOR PRINTING LATER
				xPoints.push(r);
				yPoints.push(c);
				maze[r][c] = 8;
			}
			
			return returnValue;
			
		}
		
		return false;
	}
	
	//PRINT THE FINAL PATH TAKEN
	public static void printPath() {
		while (xPoints.size()>0) {
			System.out.println("" + xPoints.pop() + ", " + yPoints.pop());
		}
		//ENSURE THE END POINTS ARE PRINTED TOO
		System.out.println(endPointx + ", " + endPointy + "\n");
	}
	
	//PRINT OUT FUN MESSAGE
	public static void finalMessage() {
		int randInt = rand.nextInt(3);
		
		if (randInt == 0) {
			System.out.println("Mr. MazeSolver: This was too easy! Bring me another one!");
		}
		else if (randInt == 1) {
			System.out.println("Mr. MazeSolver: Whew! I was scared there for a second. I really thought I was lost!");
		}
		else {
			System.out.println("Mr. MazeSolver: Next time can we just go to a corn maze? This whole 0s and " +
					"1s thing is really hurting my eyes.");
		}
	}
}
