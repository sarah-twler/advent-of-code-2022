package src.dayofadvent;

import src.utils.FileReader;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

public class TreeTopVisibilityTracer {

    public static int calcBestScenicScore(String treeMatrixFilename) throws IOException {
        List<String> treeLines = FileReader.readFileToLines(treeMatrixFilename);
        return getHighestScenicScore(buildTreeMatrix(treeLines));
    }

    public static int getHighestScenicScore(int[][] treeMatrix) {
        int bestScenicScore = 0;
        for (int y = 1; y < treeMatrix.length - 1; y++)
            for (int x = 1; x < treeMatrix[y].length - 1; x++) {
                int scenicScore = getScenicScore(treeMatrix, y, x);
                if (scenicScore > bestScenicScore)
                    bestScenicScore = scenicScore;
            }

        return bestScenicScore;
    }

    public static int getScenicScore(int[][] treeMatrix, int posY, int posX) {
        return getScenicCountHorizontal(treeMatrix, posY, posX, true)
                * getScenicCountHorizontal(treeMatrix, posY, posX, false)
                * getScenicCountVertical(treeMatrix, posY, posX, true)
                * getScenicCountVertical(treeMatrix, posY, posX, false);
    }

    public static int getScenicCountHorizontal(int[][] treeMatrix, int posY, int posX, boolean findLeft) {
        int direction = findLeft ? -1 : 1;
        int x = posX + direction;
        int comparable = treeMatrix[posY][posX];
        int scenicCount = 0;
        while (0 <= x && x < treeMatrix[posY].length) {
            scenicCount++;
            if (comparable <= treeMatrix[posY][x])
                break;
            x += direction;
        }
        return scenicCount;
    }

    public static int getScenicCountVertical(int[][] treeMatrix, int posY, int posX, boolean findDown) {
        int direction = findDown ? -1 : 1;
        int y = posY + direction;
        int comparable = treeMatrix[posY][posX];
        int scenicCount = 0;
        while (0 <= y && y < treeMatrix.length) {
            scenicCount++;
            if (comparable <= treeMatrix[y][posX])
                break;
            y += direction;
        }
        return scenicCount;
    }

    public static int getNumberVisibleTrees(String treeMatrixFilename) throws IOException {
        List<String> treeLines = FileReader.readFileToLines(treeMatrixFilename);
        return findVisibleTrees(buildTreeMatrix(treeLines));
    }

    public static int findVisibleTrees(int[][] treeMatrix) {
        return getNumberVisibleEdge(treeMatrix) + getNumberVisibleInterior(treeMatrix);
    }

    public static int getNumberVisibleInterior(int[][] treeMatrix) {
        int noVisible = 0;
        for (int y = 1; y < treeMatrix.length - 1; y++)
            for (int x = 1; x < treeMatrix[y].length - 1; x++)
                noVisible += isVisible(treeMatrix, y, x) ? 1 : 0;
        return noVisible;
    }

    public static boolean isVisible(int[][] treeMatrix, int posY, int posX) {
        int comparable = treeMatrix[posY] [posX];
        return IntStream.range(0, posX).allMatch(v -> treeMatrix[posY][v] < comparable) // left
                || IntStream.range(posX + 1, treeMatrix[posY].length).allMatch(v -> treeMatrix[posY][v] < comparable) // right
                || IntStream.range(0, posY).allMatch(v -> treeMatrix[v][posX] < comparable) // up
                || IntStream.range(posY + 1, treeMatrix.length).allMatch(v -> treeMatrix[v][posX] < comparable);// down
    }

    public static int getNumberVisibleEdge(int[][] treeMatrix) {
        int a = treeMatrix.length;
        int b = treeMatrix[0].length;

        return a * b - (a - 2) * (b - 2);
    }

    public static int[][] buildTreeMatrix(List<String> treeLines) {
        int[][] treeMatrix = new int[treeLines.size()][treeLines.get(0).length()];

        for (int i = 0; i < treeLines.size(); i++) {
            treeMatrix[i] = convertToArray(treeLines.get(i));
        }

        return treeMatrix;
    }

    private static int[] convertToArray(String treeLine) {
        return treeLine.chars().map(Character::getNumericValue).toArray();
    }
}
