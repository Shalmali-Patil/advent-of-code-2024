package com.adventOfCode;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MullItOver {

    public static final String MUL_REGEX = "mul\\([0-9]{1,3},[0-9]{1,3}\\)";
    public static final String NUM_REGEX = "[0-9]{1,3}";
    public static final String DO_REGEX = "do\\(\\)";
    public static final String DON_T_REGEX = "don\\'t\\(\\)";

    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("Day3-MullItOver/resources/input.txt");
        Scanner sc = new Scanner(fis);
        String s = null;
        sc.useDelimiter("\\Z");
        s = sc.next();
        System.out.println("String : " + s);

        MullItOver mio = new MullItOver();
        Integer sum = mio.getSumOfMuls(s);
        System.out.println("Sum is : " + sum);

        Integer modifiedSum = mio.getModifiedSumOfMulsInternal(s);
        System.out.println("Modified sum is : " + modifiedSum);

        fis.close();
        sc.close();
    }

    private Integer getModifiedSumOfMulsInternal(String s) {
        Integer sum = 0;
        Pattern mulPattern = Pattern.compile(MUL_REGEX);
        Pattern numPattern = Pattern.compile(NUM_REGEX);
        Pattern doPattern = Pattern.compile(DO_REGEX);
        Pattern dontPattern = Pattern.compile(DON_T_REGEX);
        List<Integer> mulMatchIndexList = new ArrayList<>();
        Map<Integer, Integer> mulIndexToProdMap = new HashMap<>();

        TreeMap<Integer, Boolean> doDontIndexMap = new TreeMap<>();
        doDontIndexMap.put(0, true);
        Matcher doMatcher = doPattern.matcher(s);
        while (doMatcher.find()) {
            int start = doMatcher.start();
            doDontIndexMap.put(start, true);
        }

        Matcher dontMatcher = dontPattern.matcher(s);
        while (dontMatcher.find()) {
            int start = dontMatcher.start();
            doDontIndexMap.put(start, false);
        }
        //System.out.println("doDontIndexMap: " + doDontIndexMap);

        Matcher mulMatcher = mulPattern.matcher(s);
        while(mulMatcher.find()) {
            String group = mulMatcher.group(0);
            int start = mulMatcher.start();
            mulMatchIndexList.add(start);
            //System.out.println("Full match: " + group);

            Matcher numMatcher = numPattern.matcher(group);
            Integer prod = 1;
            while(numMatcher.find()) {
                String numStr = numMatcher.group(0);
                Integer num = Integer.valueOf(numStr);
                prod *= num;
            }
            mulIndexToProdMap.put(start, prod);
        }

        TreeMap<Integer, Integer> doRangeMap = new TreeMap<>();
        int prevIndex = -1;
        boolean previousInstructionWasADo = true;
        for(Map.Entry<Integer, Boolean> entry : doDontIndexMap.entrySet()) {
            Integer doDontIndex = entry.getKey();
            Boolean currentInstructionIsADo = entry.getValue();

            if(currentInstructionIsADo){
                if(!previousInstructionWasADo) {
                    doRangeMap.put(doDontIndex, null);
                    previousInstructionWasADo = true;
                    prevIndex = doDontIndex;
                }
            } else if (previousInstructionWasADo) {
                doRangeMap.put(prevIndex, doDontIndex-1);
                previousInstructionWasADo = false;
                prevIndex = doDontIndex;
            }
        }
        if(doRangeMap.isEmpty()) {
            doRangeMap.put(0, null);
        }

        int mulMatchIterator = 0;
        int mulMatchIndex = 0;
        for(Map.Entry<Integer, Integer> entry : doRangeMap.entrySet()) {
            Integer start = entry.getKey();
            Integer end = entry.getValue();
            //System.out.println("start : " + start + ", end:" + end);
            while (mulMatchIterator < mulMatchIndexList.size()){
                mulMatchIndex = mulMatchIndexList.get(mulMatchIterator);
                if(mulMatchIndex > start && (end == null || mulMatchIndex < end)) {
                    Integer prod = mulIndexToProdMap.get(mulMatchIndex);
                    sum += prod;
                    mulMatchIterator++;
                    //System.out.println("mulMatchIndex : " + mulMatchIndex );
                } else if (mulMatchIndex < start) {
                    mulMatchIterator++;
                } else {
                    break;
                }
            }
        }

        return sum;
    }

    private Integer getSumOfMuls(String s) {
        Integer sum = 0;

        Pattern mulPattern = Pattern.compile(MUL_REGEX);
        Pattern numPattern = Pattern.compile(NUM_REGEX);
        Matcher mulMatcher = mulPattern.matcher(s);
        while(mulMatcher.find()) {
            String group = mulMatcher.group(0);
            //System.out.println("Full match: " + group);

            Matcher numMatcher = numPattern.matcher(group);
            Integer prod = 1;
            while(numMatcher.find()) {
                String numStr = numMatcher.group(0);
                Integer num = Integer.valueOf(numStr);
                prod *= num;
            }

            sum += prod;
        }
        return sum;
    }
}
