package com.example.snakeladder;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class SnakeLadderApplication {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter n (board size n x n): ");
            int n = scanner.nextInt();

            System.out.print("Enter x (number of players): ");
            int playerCount = scanner.nextInt();

            System.out.print("Enter difficulty_level (easy/hard): ");
            String difficultyInput = scanner.next();

            DifficultyLevel difficultyLevel = DifficultyLevel.from(difficultyInput);
            validateInput(n, playerCount);

            BoardFactory boardFactory = new BoardFactory();
            Board board = boardFactory.createBoard(n, difficultyLevel);

            printBoardConfig(board);

            SnakeLadderGame game = new SnakeLadderGame(board, playerCount);
            game.play();
        }
    }

    private static void validateInput(int n, int playerCount) {
        if (n < 3) {
            throw new IllegalArgumentException("n must be >= 3");
        }
        if (playerCount < 2) {
            throw new IllegalArgumentException("x must be >= 2");
        }
    }

    private static void printBoardConfig(Board board) {
        Map<Integer, Integer> sortedSnakes = new TreeMap<Integer, Integer>(board.getSnakes());
        Map<Integer, Integer> sortedLadders = new TreeMap<Integer, Integer>(board.getLadders());

        System.out.println("Board size: " + board.getSize() + " x " + board.getSize());
        System.out.println("Final cell: " + board.getFinalCell());
        System.out.println("Snakes (head->tail): " + sortedSnakes);
        System.out.println("Ladders (start->end): " + sortedLadders);
    }
}
