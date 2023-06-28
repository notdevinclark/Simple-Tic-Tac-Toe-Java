package com.notdevinclark.tictactoe;

import java.util.Scanner;

public class GameGrid {
    private final static Scanner scanner = new Scanner(System.in);
    private final static char X = 'X';
    private final static char O = 'O';
    private final static char EMPTY_SPACE = ' ';

    private final static int GRID_LENGTH = 3;
    private final static int GRID_LAYOUT_STRING_LENGTH = 9;

    private final static String INITIAL_GRID_LAYOUT_STRING = "         ";
    private final static String GRID_CEILING_OR_FLOOR = "---------";
    private final static String GRID_RIGHT_WALL = "|";
    private final static String GRID_LEFT_WALL = "| ";

    private char[][] grid = new char[GRID_LENGTH][GRID_LENGTH];
    private int moveCount = 0;

    public GameGrid() {
        setGridFromGridStateString(INITIAL_GRID_LAYOUT_STRING);
    }

    public void startGame() {
        printGrid();
        GameState currentGameState;

        while(true) {
            if (moveCount % 2 == 0) {
                getInputForMoveFromPlayer(X);
            } else {
                getInputForMoveFromPlayer(O);
            }
            moveCount++;

            printGrid();

            currentGameState = currentGameState();
            if (currentGameState != GameState.GAME_NOT_FINISHED) {
                break;
            }
        }

        System.out.println(currentGameState.printableState);
    }

    private void printGrid() {
        System.out.println(GRID_CEILING_OR_FLOOR);

        for (int i = 0; i < GRID_LENGTH; i++) {
            System.out.print(GRID_LEFT_WALL);

            for (int j = 0; j < GRID_LENGTH; j++) {
                System.out.printf("%c ", grid[i][j]);
            }

            System.out.println(GRID_RIGHT_WALL);
        }

        System.out.println(GRID_CEILING_OR_FLOOR);
    }

    private void setGridFromGridStateString(String gridStateString) {
        String regex = "[^XO ]+";
        if (gridStateString.length() != GRID_LAYOUT_STRING_LENGTH || gridStateString.matches(regex)) {
            throw new IllegalArgumentException("Grid State String must be 9 characters, existing of 'X', 'O', or an ' '");
        }

        for (int i = 0; i < GRID_LENGTH; i++) {
            for (int j = 0; j < GRID_LENGTH; j++) {
                int index = (i * 3) + j;
                grid[i][j] = gridStateString.charAt(index);
            }
        }
    }

    private GameState currentGameState() {
        int xWins = winCountForCharacter(X);
        int oWins = winCountForCharacter(O);
        int emptySpaceCount = countOfCharacterOnGrid(EMPTY_SPACE);

        GameState state = GameState.IMPOSSIBLE;

        if (numberOfTurnsValidForEachSide()) {
            if (xWins == 0 && oWins == 0) {
                if (emptySpaceCount > 0) {
                    state = GameState.GAME_NOT_FINISHED;
                } else {
                    state = GameState.DRAW;
                }
            } else if (xWins == 1 && oWins == 0) {
                state = GameState.X_WINS;
            } else if (xWins == 0 && oWins == 1) {
                state = GameState.O_WINS;
            }
        }

        return state;
    }

    private void getInputForMoveFromPlayer(char playerChar) {
        boolean invalidInput = true;
        do {
            String input = scanner.nextLine();

            try {
                if (input.matches("[^\\d ]+")) {
                    throw new IllegalArgumentException("You should enter numbers!");
                }
                if (input.length() != 3 || !(input.matches("^[123] [123]$"))) {
                    throw new IllegalArgumentException("Coordinates should be from 1 to 3!");
                }
                int[] coordinates = extractCoordinatesFromString(input);
                updateGridWithPlayerMove(coordinates, playerChar);

                /*
                 Successfully getting this far in the try/loop means that all inputs and actions
                 were correct and successful
                */
                invalidInput = false;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

        } while(invalidInput);
    }

    private int[] extractCoordinatesFromString(String input) {
        int x = Character.getNumericValue(input.charAt(0)) - 1;
        int y = Character.getNumericValue(input.charAt(2)) - 1;
        return new int[] {x, y};
    }

    private void updateGridWithPlayerMove(int[] coordinates, char xOrO) {
        int x = coordinates[0];
        int y = coordinates[1];

        if (grid[x][y] != EMPTY_SPACE) {
            throw new IllegalArgumentException("This cell is occupied! Choose another one!");
        }

        grid[x][y] = xOrO;
    }

    private StringBuilder currentGridLayoutString() {
        StringBuilder gridLayoutString = new StringBuilder();

        for (int i = 0; i < GRID_LENGTH; i++) {
            for (int j = 0; j < GRID_LENGTH; j++) {
                gridLayoutString.append(grid[i][j]);
            }
        }

        return gridLayoutString;
    }
    private int countOfCharacterOnGrid(char character) {
        int count = 0;
        for (int i = 0; i < GRID_LENGTH; i++) {
            for (int j = 0; j < GRID_LENGTH; j++) {
                if (grid[i][j] == character) {
                    count++;
                }
            }
        }
        return count;
    }

    private int winCountForCharacter(char character) {
        int winCount = 0;
        for (WinnableGridCoordinateGroup coordinateGroup : WinnableGridCoordinateGroup.values()) {
            if (singleWinForCharAtSpecificLocation(character, coordinateGroup)) {
                winCount++;
            }
        }
        return winCount;
    }

    private boolean singleWinForCharAtSpecificLocation(char character, WinnableGridCoordinateGroup coordinateGroup) {
        boolean winner = true;
        StringBuilder layout = currentGridLayoutString();
        for (int index : coordinateGroup.coordinates) {
            if (character != layout.charAt(index)) {
                winner = false;
            }
        }
        return winner;
    }

    private boolean numberOfTurnsValidForEachSide() {
        int xCount = countOfCharacterOnGrid(X);
        int oCount = countOfCharacterOnGrid(O);
        int difference = xCount - oCount;

        return (-1 <= difference && difference <= 1);
    }

    // No Longer Used In This Version of the Game
    public GameGrid(String gridLayoutString) {
        setGridFromGridStateString(gridLayoutString);
    }

    public void setUpGridFromUserInput() {
        String input = scanner.next();
        setGridFromGridStateString(input);
    }
}