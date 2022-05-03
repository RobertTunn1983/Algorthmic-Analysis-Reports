/*
 * @author Robert Tunn
 * Created 09 February 2022, last modified 11 March 2022
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class QuickSort {
       
    //Following code is cut and pasted from code provided 
    
    //In terms of space complexity, once the input array is held in memory
    //the only remaining memory usage is for the setting of variables and minor
    //operations which can be disregarded
        
    //This calls once as the first method, sends to sort with three params
    public static void sort(int[] input) throws IOException{                                
        //This only calls a method, no increments for space or time complexity
        //low param set to 0 and high set to last element
        sort(input, 0, input.length - 1);
    }
    
    private static void sort(int[] input, int low, int high) throws IOException{
    
        if(low >= high || low < 0){
            return;
        }

        //Integer set to value of returned value from partition method, in itself
        //only a call of a function
        int p = partition(input, low, high);
      
        //Again, only calling methods does not increment count
        sort(input, low, p - 1); 
        sort(input, p + 1, high); 
        }
    
    private static int partition(int[] input, int low, int high) throws IOException{
        
        //Constant 
        int pivot = input[high];

        //Simply decrements low by 1, not a characteristic operation        
        int i = low - 1;

        //Inner-loop not always called
        for(int j = low; j <= high; j++){
            
            MainApp.countQS++;
            
            if(input[j] < pivot){
                //Simple increments and assignations but occur in a nested loop
                //so potentially characteristic operations
                i++;
                int tmp = input[j];
                input[j] = input[i];
                input[i] = tmp;
                MainApp.countQS++;
            }
        }
        
        //A group of simple assignations, not relevant to space or time 
        //complexity
        i++;        
        int tmp = input[high];
        input[high] = input[i];
        input[i] = tmp;
        
        return i;        
    }
    
    //Code added simply to output a CSV file
    public static void writeToFile_TIME_QS(ArrayList<Integer> input) throws IOException {

        try {            
            PrintWriter pw = new PrintWriter    
            (new File("C:\\Users\\Robert's account\\Desktop\\quickSortResults.csv"));

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
    
        public static void writeToFile_SPACE_QS(ArrayList<Integer> input) throws IOException {
        
        try {            
            PrintWriter pw = new PrintWriter    
            (new File("C:\\Users\\Robert's account\\Desktop\\quickSortSPACEResults.csv"));

            StringBuilder sb = new StringBuilder();

            sb.append("Array length,Amount of memory units\n");
            
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


