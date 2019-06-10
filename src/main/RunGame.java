package main;

import java.util.Scanner;

public class RunGame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner myObj = new Scanner(System.in);
		System.out.println("Game Begin!");
		int size = 0;
		while (true) {
			System.out.println("Please Enter Board Size:");
			size = myObj.nextInt();
			if (size >= 5) {
				break;
			} else {
				System.out.println("Size need to be greater than 5");
			}
		}
		Game game = new Game(size);
		int[] currentMove = new int[2];
		while (true) {
			System.out.println("Current Board:");
			game.printBoard();
			while (true) {
				System.out.println("Enter your move : row");
				int row = myObj.nextInt();
				System.out.println("Enter your move : col");
				int col = myObj.nextInt();
				if (game.go(row, col)) {
					currentMove[0] = row;
					currentMove[1] = col;
					break;
				} else {
					System.out.println("Your input is invalid");
				}
			}
			if (game.isWinShort(currentMove)) {
				game.printBoard();
				System.out.println("You win!");
				break;
			}
			currentMove = game.cpGoAI(currentMove);
			if (game.isWinShort(currentMove)) {
				game.printBoard();
				System.out.println("You lose!");
				break;
			}
		}
		System.out.println("Thank you for playing!");
	}
}
