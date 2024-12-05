package com.adventofcode;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class CeresSearch {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("Day4-CeresSearch/resources/input.txt");
        Scanner sc = new Scanner(fis);

        sc.useDelimiter("\\Z");
        String s = sc.next();
        String[] inputArr = s.split("\n");
        char[][] matrix = new char[inputArr.length][inputArr[0].length()];
        int xIndex = 0;
        for(String line:inputArr){
            int yIndex = 0;
            for(char c:line.toCharArray()) {
                matrix[xIndex][yIndex] = c;
                yIndex++;
            }
            xIndex++;
        }
        System.out.println("Input: " + matrix);

        CeresSearch cs = new CeresSearch();
        Integer count = cs.searchWord(matrix);
        System.out.println("Count: " + count);

        sc.close();
        fis.close();
    }

    private Integer searchWord(char[][] matrix) {
        Integer count = 0;

        for(int i=0;i< matrix.length;i++) {
            for(int j=0;j<matrix[i].length;j++) {
                char c = matrix[i][j];
                if(c == 'X') {
                    count += checkForWord(matrix, i, j-1, 0, 'M', Direction.LEFT);
                    count += checkForWord(matrix, i, j+1, 0, 'M', Direction.RIGHT);
                    count += checkForWord(matrix, i-1, j, 0, 'M', Direction.UP);
                    count += checkForWord(matrix, i+1, j, 0, 'M', Direction.DOWN);
                    count += checkForWord(matrix, i-1, j-1, 0, 'M', Direction.DIAGONAL_LEFT_TOP);
                    count += checkForWord(matrix, i+1, j-1, 0, 'M', Direction.DIAGONAL_LEFT_BOTTOM);
                    count += checkForWord(matrix, i-1, j+1, 0, 'M', Direction.DIAGONAL_RIGHT_TOP);
                    count += checkForWord(matrix, i+1, j+1, 0, 'M', Direction.DIAGONAL_RIGHT_BOTTOM);
                }
            }
        }
        return count;
    }

    private Integer checkForWord(char[][] matrix, int i, int j, int count, char c, Direction dir) {
        if(i<0 || i==matrix.length || j < 0 || j == matrix[i].length) {
            return count;
        }
        char currChar = matrix[i][j];
        if(currChar != c) {
            return count;
        }
        char nextChar = 0;
        switch(c) {
            case 'M':
                nextChar = 'A';
                break;
            case 'A':
                nextChar = 'S';
                break;
            case 'S':
                count += 1;
                break;
            default:
                break;
        }
        if(nextChar != 0) {
            switch(dir) {
                case UP: count = checkForWord(matrix, i-1, j, count, nextChar, dir);
                    break;
                case DOWN: count = checkForWord(matrix, i+1, j, count, nextChar, dir);
                    break;
                case LEFT: count = checkForWord(matrix, i, j-1, count, nextChar, dir);
                    break;
                case RIGHT: count = checkForWord(matrix, i, j+1, count, nextChar, dir);
                    break;
                case DIAGONAL_LEFT_TOP: count = checkForWord(matrix, i-1, j-1, count, nextChar, dir);
                    break;
                case DIAGONAL_LEFT_BOTTOM: count = checkForWord(matrix, i+1, j-1, count, nextChar, dir);
                    break;
                case DIAGONAL_RIGHT_TOP: count = checkForWord(matrix, i-1, j+1, count, nextChar, dir);
                    break;
                case DIAGONAL_RIGHT_BOTTOM: count = checkForWord(matrix, i+1, j+1, count, nextChar, dir);
                    break;
            }
        }
        return count;
    }
}
