/*
 * @author Robert Tunn
 * Created 09 February 2022, last modified 10 March 2022
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class MainApp {
    
    //Variables for no of characteristic operations and space required in 
    //QuickSort sorting algorithm
    static Integer countQS = 0;
    static Integer spaceQS = 0;
    
    //Arrays of tallies of Count and Quick Sort counts without averages taken
    static ArrayList<Integer> operationsCountCS = new ArrayList<Integer>();
    static ArrayList<Integer> operationsCountQS = new ArrayList<Integer>();
    static ArrayList<Integer> spaceCountCS = new ArrayList<Integer>();
    static ArrayList<Integer> spaceCountQS = new ArrayList<Integer>();
    
    //Arrays of final data for time complexity of Count Ssort and Quick Sort
    static ArrayList<Integer> finalCountSortData = new ArrayList<Integer>();
    static ArrayList<Integer> finalQuickSortData = new ArrayList<Integer>();
    
    //Arrays of final data for space complexity of count sort and quick sort
    static ArrayList<Integer> finalCSSpaceData = new ArrayList<Integer>();
    static ArrayList<Integer> finalQSSpaceData = new ArrayList<Integer>();    
    
    public static void main(String args[]) throws IOException {
        
        System.out.println("***AUTOMATED SORTING ALGORITHM TESTING PLATFORM*** " 
                + "\n\nYou will now be asked to provide the computer with your "
                + "choice of problem size (e.g. up to 500)\nthe range of "
                + "potential integers that you wish to use (e.g. from -100 to 0)"
                + "\nand which sorting algorithm(s) you want to "
                + "run\n\nWhat is the length of the last array to be "
                + "sorted? e.g. entering 10 will automatically test 10 random "
                + "arrays of length 0 to 10");

        //Prompt user for number of random arrays to be created
        Scanner scanner = new Scanner(System.in);
        int userInput = scanner.nextInt();

        System.out.println("\n\nSimulation will produce arrays of random "
               + "integers from 0 to " + userInput + " elements long\n");
        
        System.out.println("\n\nWhat range of potential integers do you wish to "
                + "use?:\n[1] -100 to 0\n[2] 0 to 100\n[3] -50 to 50");
        
        //Prompt user for range of integers to be used
        Scanner rangeChoice = new Scanner(System.in);
        int userRangeChoice = rangeChoice.nextInt();
        
        if (userRangeChoice == 1) {
            System.out.println("\nYou have chosen (-100 to 0)\n");
        } 
        else if (userRangeChoice == 2) {
            System.out.println("\nYou have chosen (0 to 100)\n");            
        } 
        else if (userRangeChoice == 3) {
            System.out.println("\nYou have chosen (-50 to 50)\n");
        }
        
        System.out.println("\n\nWhat sorting algorithm(s) do you wish to run?\n"
                + "[1]Count Sort\n[2]Quick Sort\n[3]Both");
        
        //Simulation methods automatically run sorting algorithms for 
        //different lengths of array and output number of operations as a 
        //CSV file
        
        Scanner algoChoice = new Scanner(System.in);
        int userAlgoChoice = algoChoice.nextInt();
        
        if (userAlgoChoice == 1) {
            runCountSortSimulation(userInput+1, userRangeChoice);   
            System.out.println("You have chosen to run Count Sort");
        } 
        else if (userAlgoChoice == 2) {
            runQuickSortSimulation(userInput+1, userRangeChoice);             
            System.out.println("You have chosen to run Quick Sort");
        } 
        else if (userAlgoChoice == 3) {
            runCountSortSimulation(userInput+1, userRangeChoice);  
            runQuickSortSimulation(userInput+1, userRangeChoice);        
            System.out.println("You have chosen to run both Count Sort and "
                    + "Quick Sort");
        }
        
        System.out.println("\n\nThe results of this experiment have been outputted "
                + "as CSV files.");
    }
    
    public static void runCountSortSimulation 
        (int turns, int integerRange) throws IOException {

        //Start with an empty array then add on random integers
        int[] randomArray = {};
        int arrayLength = 0;
        
        //Sorting algorithms run as many times as there are arrays to sort
        for (int a = 0; a < turns; a++) {
            //Do each for 10 times so that an average can be taken 
            for (int b = 0; b < 10; b++) {
                
                //Make random input array one random element longer on each turn
                randomArray = produceRandomArray(arrayLength, integerRange);                
                countSort(randomArray);
            }
            arrayLength++;
        }   

        //Feed tallys of counts into takeAverage method and produce final data
        finalCountSortData = takeAverage(operationsCountCS);
        finalCSSpaceData = takeAverage(spaceCountCS);
        
        //Export final data as a CSV file
        writeToFileCS(finalCountSortData);
        writeToFileCSSpaceData(finalCSSpaceData);
    }
    
    public static void runQuickSortSimulation
        (int turns, int integerRange) throws IOException {
        
        int[] randomArray = {};
        int arrayLength = 0;

        //Special cases for 0 and 1 element long which would otherwise do 
        //nothing
        //This is done this way only because things are automated and 0 and 1
        //do not invoke the count++ variable
        
        //If 0 is entered, turns = 1
        if (turns == 1) {
            //Add 10 x 0 to represent results for 0
            for (int i = 0; i < 10; i++) {
                operationsCountQS.add(0);
            }
            
            finalQSSpaceData.add(0);
        }

        //If 1 is entered, turns = 2
        else if (turns == 2) {
            //Add 10 x 0 and 10 x 1 to represent results for 0
            for (int a = 0; a < 10; a++) {
                operationsCountQS.add(0);
                operationsCountQS.add(0);
            }
            
            finalQSSpaceData.add(0);
            finalQSSpaceData.add(1);
        }
        
        else if (turns > 2) {
            
            //Add the first 20 zeros for 0 and 1
            for (int a = 0; a < 10; a++) {
                operationsCountQS.add(0);
                operationsCountQS.add(0);
            }
            
            finalQSSpaceData.add(0);
            finalQSSpaceData.add(1);
            
            //Then proceed to get the rest of the results
            for (int b = 3; b <= turns; b++) {
                finalQSSpaceData.add(b-1);
                
                for (int c = 0; c < 10; c++) {
                    randomArray = produceRandomArray(arrayLength, integerRange);
                    QuickSort.sort(randomArray);
                    operationsCountQS.add(countQS);
                    countQS = 0;
                }
                arrayLength++;
            }
        }        
        
        finalQuickSortData = takeAverage(operationsCountQS);
                
        QuickSort.writeToFile_TIME_QS(finalQuickSortData);
        QuickSort.writeToFile_SPACE_QS(finalQSSpaceData);
    }
    
    //Method automatically produces a random array one element longer than last
    public static int[] produceRandomArray(int arrayLength, int range) {

        int[] output = new int[arrayLength];
        
        if (range == 1) {
            output = makeAllNegative(output);
        }
        else if (range == 2) {
            output = makeAllPositive(output);
        }
        else if (range == 3) {
            output = usePosAndNeg(output);
        }
                
        //Print out arrays for checking that everything is in place
  //      for (int i = 0; i < output.length; i++){
    //        System.out.println(output[i] + " ");
      //  }
        
        return output;
    }
    
    //Make every Integer element negative (-100 to 0)    
    public static int[] makeAllNegative(int[] input) {
        
        int [] output = new int[input.length];
        
        for (int i = 0; i < input.length; i++) {
            output[i] = (int)Math.round(Math.random()*100)*-1;
        }
        
        return output;
    }  
    
//Make every integer element positive (0 to 100)
    public static int[] makeAllPositive(int[] input){
        
        int[] output = new int[input.length];
        
        for (int i = 0; i < input.length; i++) {
            output[i] = (int)Math.round(Math.random()*100);
        }
        
        return output;
    }

    //Make Integer elements randomly positive or negative (-100 to 100)     
    public static int[] usePosAndNeg(int[] input){
    
        int[] output = new int[input.length];
        
        for (int i = 0; i < output.length; i++) {
            output[i] = (int)Math.round(Math.random()*50);
        }
        
        Double rand;
        
        for (int i = 0; i < output.length; i++){ 
            
            rand = Math.random();
            
            if (rand < 0.5) {
                output[i] = output[i]*-1;            
            } else if (rand >= 0.5) {
                output[i] = output[i];
            }
        }
        
        return output;
    }
        
    public static void countSort(int[] input) {
    
        Integer timeCount = 0;
        Integer spaceCount = 0;
        
        //This code has been copied and pasted from starter code
       
        //Using space to store one Integer within an array as 1 unit, the
        //space required increases by 1 unit as the length increases by 1
        spaceCount += input.length;
        
        //Constants
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
            
        //The effect of this loop is overshadowed by the nested for loop below
        //and does not increment the number of characteristic operations
        for (int number : input) {        
            if (number < min) {
                min = number;
            }
            if (number > max) {
                max = number;
            }  
        }

        //spaceCount is incremented again after a second array is created to 
        //hold the frequencies the max length of which is equal to the range of 
        //potential elements e.g. -50 to 50 will have a potential frequencies 
        //array size of 101
        int[] frequencies = new int[max - min + 1];
        spaceCount += frequencies.length;
        
        //Again this loop is overshadowed by the effect of the nested loop 
        //below and all significant memory units required have already been #
        //allocated 
        for (int number : input) {
            frequencies[number - min]++;
        }

        int outputIndex = 0;

        //Nested loop is dominant in terms of time complexity 
        for (int i = 0; i < frequencies.length; i++) {
            timeCount++;
            for (int j = 0; j < frequencies[i]; j++) {
                input[outputIndex] = i + min;
                outputIndex++;
                timeCount++;
            }
        }
        
        //Add the count totals to an array, this will then be split into average
        //count amounts 
        operationsCountCS.add(timeCount);   
        spaceCountCS.add(spaceCount);
        }   
        
    public static ArrayList<Integer> takeAverage(ArrayList<Integer> input) {
    
        //Take average of 10 counts done for each length of array
        
        ArrayList<Integer> temp = new ArrayList<Integer>();
        ArrayList<Integer> output = new ArrayList<Integer>();
                
        Integer pos = 0;    

        for (int i = 0; i < (input.size()+1)/10; i++) {
            temp.add(input.get(pos) + input.get(pos+1) 
                    + input.get(pos+2) + input.get(pos+3)
                    + input.get(pos+4) + input.get(pos+5)
                    + input.get(pos+6) + input.get(pos+7)
                    + input.get(pos+8) + input.get(pos+9));
            pos += 10;
        }
        
        for (int i = 0; i < temp.size(); i++) {
            output.add(Math.round(temp.get(i)/10));   
        }

        return output;
    }
    
    //Output results into a CSV file
    public static void writeToFileCS(ArrayList<Integer> input) throws IOException {
        
        try {            
            PrintWriter pw = new PrintWriter    
            (new File("C:\\Users\\Robert's account\\Desktop\\countSortResults.csv"));

            StringBuilder sb = new StringBuilder();

            sb.append("Array length,No of operations\n");
            
            int row = 0;
            for (int i = 0; i < input.size(); i++) {         
                
                int num = input.get(i);                
                sb.append(row + ";" + num + "\n");                
                row++;
            
            }
            pw.write(sb.toString());
            pw.close();
        }
        catch (Exception e) {
        }
    }    
    
    public static void writeToFileCSSpaceData(ArrayList<Integer> input) throws IOException {
        
        try {            
            PrintWriter pw = new PrintWriter    
            (new File("C:\\Users\\Robert's account\\Desktop\\countSortSpaceData.csv"));

            StringBuilder sb = new StringBuilder();

            sb.append("Array length,Space required\n");
            
            int row = 0;
            for (int i = 0; i < input.size(); i++) {         
                
                int num = input.get(i);                
                sb.append(row + ";" + num + "\n");                
                row++;
            
            }
            pw.write(sb.toString());
            pw.close();
        }
        catch (Exception e) {
        }
    } 
}




