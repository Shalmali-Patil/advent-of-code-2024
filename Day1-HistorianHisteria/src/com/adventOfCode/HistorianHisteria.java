package com.adventOfCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class HistorianHisteria {
    public static final String INPUT_SEPARATOR_REGEX = "   ";

    public static void main(String[] args) throws FileNotFoundException {
        String inputFilepath = "Day1-HistorianHisteria/resources/input.txt";
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        File file = new File(inputFilepath);
        Scanner sc = new Scanner(file);
        while(sc.hasNext()) {
            String line = sc.nextLine();
            String[] arr = line.split(INPUT_SEPARATOR_REGEX);
            list1.add(new Integer(arr[0]));
            list2.add(new Integer(arr[1]));
        }

        //System.out.println("List 1" + list1.toString());
        //System.out.println("List 2" + list2.toString());

        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        };
        list1.sort(comparator);
        list2.sort(comparator);
        //System.out.println("Sorted List 1" + list1.toString());
        //System.out.println("Sorted List 2" + list2.toString());

        HistorianHisteria hh = new HistorianHisteria();

        Integer totalDistance = hh.getTotalDistance(list1, list2);
        System.out.println("Total distance: " + totalDistance);

        Integer similarityScore = hh.getSimilarityScore(list1, list2);
        System.out.println("Similarity Score: " + similarityScore);

        sc.close();
    }

    private Integer getSimilarityScore(List<Integer> list1, List<Integer> list2) {
        Integer similarityScore = 0;

        for(int i=0;i<list1.size();i++) {
            Integer leftNum = list1.get(i);
            List<Integer> foundInRightList = list2.stream().filter(l2 -> leftNum.equals(l2)).collect(Collectors.toList());
            similarityScore += leftNum * foundInRightList.size();
        }

        return similarityScore;
    }

    private Integer getTotalDistance(List<Integer> list1, List<Integer> list2) {
        Integer totalDistance = 0;

        for(int i=0;i<list1.size();i++) {
            int diff = Math.abs(list1.get(i) - list2.get(i));
            totalDistance += diff;
        }

        return totalDistance;
    }
}
