package com.adventOfCode;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class RedNosedReports {

    public static final String SPACE = " ";

    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream("Day2-Red-NosedReports/resources/input.txt");

        List<List<Integer>> reports = new ArrayList<>();

        Scanner sc = new Scanner(fis);
        while(sc.hasNext()) {
            String line = sc.nextLine();
            String[] arr = line.split(SPACE);
            List<Integer> levels = Arrays.stream(arr).map(Integer :: valueOf).collect(Collectors.toList());
            reports.add(levels);
        }

        System.out.println("Levels in reports: " + reports.toString());

        RedNosedReports rnr = new RedNosedReports();
        Integer safeReportsCount= rnr.countSafeReports(reports);
        System.out.println("Count of safe reports is: " + safeReportsCount);
    }

    private Integer countSafeReports(List<List<Integer>> reports) {
        Integer safeReportsCount = 0;

        for(int i=0;i<reports.size();i++) {
            List<Integer> levelValues = reports.get(i);
            int levelSize = levelValues.size();
            boolean isIncreasingLevel = levelSize > 1 ? ((levelValues.get(0) > levelValues.get(1)) ? false : true) : true;
            int prevLevelValue = levelValues.get(0);
            int j=1;
            for(;j<levelSize;j++) {
                int levelDiff = levelValues.get(j) - prevLevelValue;
                prevLevelValue = levelValues.get(j);
                if(levelDiff == 0 || (levelDiff > 0 && !isIncreasingLevel) || (levelDiff < 0 && isIncreasingLevel)) {
                    break;
                }
                int absLevelDiff = Math.abs(levelDiff);
                if(absLevelDiff < 1 || absLevelDiff > 3) {
                    break;
                }
            }
            if(j==levelSize) {
                safeReportsCount++;
            }
        }

        return safeReportsCount;
    }
}
