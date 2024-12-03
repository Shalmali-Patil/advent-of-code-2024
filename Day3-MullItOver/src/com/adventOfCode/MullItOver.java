package com.adventOfCode;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MullItOver {

    public static final String MUL_REGEX = "mul\\([0-9]{1,3},[0-9]{1,3}\\)";
    public static final String NUM_REGEX = "[0-9]{1,3}";

    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("Day3-MullItOver/resources/input.txt");
        Scanner sc = new Scanner(fis);
        List<String> inputStrList = new ArrayList<>();
        while(sc.hasNext()) {
            String s = sc.nextLine();
            System.out.println("String : " + s);
            inputStrList.add(s);
        }

        MullItOver mio = new MullItOver();
        Integer sum = mio.getSumOfMuls(inputStrList);
        System.out.println("Sum is : " + sum);

        fis.close();
        sc.close();
    }

    private Integer getSumOfMuls(List<String> inputStrList) {
        Integer sum = 0;

        Pattern mulPattern = Pattern.compile(MUL_REGEX);
        Pattern numPattern = Pattern.compile(NUM_REGEX);
        for(String s: inputStrList) {
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
        }
        return sum;
    }
}
