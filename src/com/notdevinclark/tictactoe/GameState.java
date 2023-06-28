package com.notdevinclark.tictactoe;

public enum GameState {
    IMPOSSIBLE("Impossible"),
    GAME_NOT_FINISHED("Game Not Finished"),
    DRAW("Draw"),
    X_WINS("X wins"),
    O_WINS("O wins");

    public final String printableState;

    GameState(String printableState) {
        this.printableState = printableState;
    }
}