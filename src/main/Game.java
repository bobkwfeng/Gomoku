package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Game {
	/*
	 * Empty cell : 0
	 * User piece : 1
	 * CP piece : 2
	 * */
	int[][] board;
	
	// Initialize game with size
	public Game (int size) {
		board = new int[size][size];
	}
	
	/**
	 * 
	 * @param row user input
	 * @param col user input
	 * @return is this input valid or not
	 */
	public boolean go (int row, int col) {
		if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
			return false;
		}
		// Empty valid space to put piece
		if (board[row][col] == 0) {
			board[row][col] = 1;
			return true;
		}
		return false;
	}
	
	/**
	 * Computer AI's input
	 * Currently randomly pick one spot
	 * Improve : Try to block user
	 * @return computer input spot
	 */
	public int[] cpGo () {
		Random generator = new Random();
		int row = generator.nextInt(board.length);
		int col = generator.nextInt(board.length);
		// Regenerate
		while (board[row][col] == 1 || board[row][col] == 2) {
			row = generator.nextInt(board.length);
			col = generator.nextInt(board.length);
		}
		board[row][col] = 2;
		return new int[]{row, col};
	}
	
	/**
	 * Computer AI's input
	 * Improve : Try to block user
	 * @return computer input spot
	 */
	public int[] cpGoAI (int[] userInput) {
		int row = userInput[0];
		int col = userInput[1];
		// 8 spots around user's input
		int[] possibleRow = new int[] {row - 1, row - 1, row - 1, row, row + 1, row + 1, row + 1, row};
		int[] possibleCol = new int[] {col - 1, col, col + 1, col + 1, col + 1, col, col - 1, col - 1};
		Random generator = new Random();
		// Check if all 8 positions are valid
		List<int[]> realPossible = new ArrayList<int[]>();
		for (int i = 0; i < 8; i++) {
			int r = possibleRow[i];
			int c = possibleCol[i];
			if (r >= 0 && r < board.length && c >= 0 && c < board[0].length && board[r][c] != 1 && board[r][c] != 2) {
				realPossible.add(new int[] {r, c});
			}
		}
		if (realPossible.size() > 0) {
			int[] result = realPossible.get(generator.nextInt(realPossible.size()));
			board[result[0]][result[1]] = 2;
			return result;
		} else {
			return cpGo();
		}
	}
	
	/**
	 * Print the current board
	 */
	public void printBoard () {
		StringBuilder title = new StringBuilder();
		title.append("  ");
		for (int i = 0; i < board.length; i++) {
			title.append(i);
			title.append(" ");
		}
		System.out.println(title.toString());
		for (int i = 0; i < board.length; i++) {
			StringBuilder build = new StringBuilder();
			build.append(i);
			build.append(" ");
			for (int j = 0; j < board[0].length; j++) {
				build.append(String.valueOf(board[i][j]));
				build.append(" ");
			}
			System.out.println(build.toString());
		}
	}
	
	/**
	 * @param current input
	 * @return is win or not
	 * O(1) time
	 */
	public boolean isWin(int[] pos) {
		int row = pos[0];
		int col = pos[1];
		int target = board[row][col];
		int count = 0;
		// Check row
		while (row >= 0 && board[row][col] == target) {
			count++;
			row--;
			if (count == 5) {
				return true;
			}
		}
		row = pos[0];
		count--;
		while (row < board.length && board[row][col] == target) {
			count++;
			row++;
			if (count == 5) {
				return true;
			}
		}
		count = 0;
		row = pos[0];
		// Check col
		while (col >= 0 && board[row][col] == target) {
			count++;
			col--;
			if (count == 5) {
				return true;
			}
		}
		col = pos[1];
		count--;
		while (col < board[0].length && board[row][col] == target) {
			count++;
			col++;
			if (count == 5) {
				return true;
			}
		}
		count = 0;
		col = pos[1];
		// Check top left to right down
		while (row >= 0 && row < board.length && col >= 0 && col < board.length && board[row][col] == target) {
			count++;
			row--;
			col--;
			if (count == 5) {
				return true;
			}
		}
		row = pos[0];
		col = pos[1];
		count--;
		while (row >= 0 && row < board.length && col >= 0 && col < board.length && board[row][col] == target) {
			count++;
			row++;
			col++;
			if (count == 5) {
				return true;
			}
		}
		count = 0;
		row = pos[0];
		col = pos[1];
		// Check down left to top right
		while (row >= 0 && row < board.length && col >= 0 && col < board.length && board[row][col] == target) {
			count++;
			row++;
			col--;
			if (count == 5) {
				return true;
			}
		}
		row = pos[0];
		col = pos[1];
		count--;
		while (row >= 0 && row < board.length && col >= 0 && col < board.length && board[row][col] == target) {
			count++;
			row--;
			col++;
			if (count == 5) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param current input
	 * @return is win or not
	 * O(1) time
	 */
	public boolean isWinShort(int[] pos) {
		int target = board[pos[0]][pos[1]];
		int rowCount = 0;
		int colCount = 0;
		int bottomToTopCount = 0;
		int topToBottomCount = 0;
		int[] rowDirect = new int[]{0, 0, 1, -1, 1, -1, -1, 1};
		int[] colDirect = new int[]{1, -1, 0, 0, -1, 1, -1, 1};
		for (int i = 0; i < 8; i++) {
			int row = pos[0];
			int col = pos[1];
			switch (i) {
				case 1 : 
					rowCount--;
					break;
				case 3 : 
					colCount--;
					break;
				case 5 : 
					bottomToTopCount--;
					break;
				case 7 : 
					topToBottomCount--;
					break;
			}
			while (row >= 0 && row < board.length && col >= 0 && col < board[0].length) {
				if (board[row][col] == target) {
					switch (i) {
						case 0 : 
							rowCount++;
							break;
						case 1 : 
							rowCount++;
							break;
						case 2 : 
							colCount++;
							break;
						case 3 : 
							colCount++;
							break;
						case 4 : 
							bottomToTopCount++;
							break;
						case 5 : 
							bottomToTopCount++;
							break;
						case 6 : 
							topToBottomCount++;
							break;
						case 7 : 
							topToBottomCount++;
							break;
					}
				}
				row = row + rowDirect[i];
				col = col + colDirect[i];
			}
		}
		if (rowCount == 5 || colCount == 5 || bottomToTopCount == 5 || topToBottomCount == 5) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 
	 * @return if player / cp win
	 * 0 : no one win yet
	 * 1 : player win
	 * 2 : cp win
	 * O(n^2) check overall status 
	 */
	public int isWinAll() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				int target = board[i][j];
				if (target == 0) {
					continue;
				}
				int[] pos = new int[]{i, j};
				if (isWin(pos)) {
					return target;
				}
			}
		}
		return 0;
	}
	
	
}
