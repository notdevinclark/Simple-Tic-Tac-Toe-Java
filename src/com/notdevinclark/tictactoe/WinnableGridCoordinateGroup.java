package com.notdevinclark.tictactoe;

public enum WinnableGridCoordinateGroup {
    HORIZONTAL_TOP(new int[] {0, 1, 2}),
    HORIZONTAL_MIDDLE(new int[] {3, 4, 5}),
    HORIZONTAL_BOTTOM(new int[] {6, 7, 8}),
    VERTICAL_LEFT(new int[] {0, 3, 6}),
    VERTICAL_CENTER(new int[] {1, 4, 7}),
    VERTICAL_RIGHT(new int[] {2, 5, 8}),
    TOP_TO_BOTTOM_DIAGONAL(new int[] {0, 4, 8}),
    BOTTOM_TO_TOP_DIAGONAL(new int[] {6, 4, 2});


    public final int[] coordinates;

    WinnableGridCoordinateGroup(int[] coordinates) {
        this.coordinates = coordinates;
    }
}
