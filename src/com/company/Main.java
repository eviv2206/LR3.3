package com.company;

import java.io.*;
import java.util.*;

public class Main {
    static int getMax(int[] arr) {
        int n = arr.length;
        int max = arr[0];
        for (int i = 1; i < n; i++)
            if (arr[i] > max)
                max = arr[i];
        return max;
    }

    static int[] digitSort(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n];
        int i;
        int[] count = new int[10];
        Arrays.fill(count, 0);
        for (i = 0; i < n; i++)
            count[(arr[i] / exp) % 10]++;
        for (i = 1; i < 10; i++)
            count[i] += count[i - 1];
        int temp;
        int k = n - 1;
        for (i = k; i > -1; i--) {
            temp = (arr[i] / exp) % 10;
            output[count[temp] - 1] = arr[i];
            count[temp]--;
        }
        return output;
    }

    static int[] radixSort(int[] arr) {
        int max = getMax(arr);
        int exp = 1;
        while (max / exp > 0) {
            arr = digitSort(arr, exp);
            exp *= 10;
        }
        return arr;
    }
    public static int[] inputData(Scanner input, String filePath){
        String line = null;
        boolean isIncorrect;
        int[] array;
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            line = reader.readLine();
            reader.close();
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
        if(Objects.isNull(line)){
            System.out.println("В файле не содержится строка, которая удовлетворяет условию задачи");
            do {
                isIncorrect = true;
                System.out.println("Введите строку");
                line = input.nextLine();
                if (!(line.length() == 0)) {
                    line = deleteSymbols(line);
                    isIncorrect = checkInputData(line);
                }
            } while (isIncorrect);
        } else {
            line = deleteSymbols(line);
        }
        array = ownSplit(line);
        return array;
    }

    public static int[] ownSplit(String str){
        int counter = 0;
        char[] cArray = str.toCharArray();
        int n = cArray.length;
        for (int i = 0; i < n; i++){
            if (cArray[i] == ' '){
                counter++;
            }
        }
        String temp = "";
        int k = 0;
        int[] intArray = new int[counter + 1];
        for (int i = 0; i < n; i++){
            if (cArray[i] == ' '){
                intArray[k] = Integer.parseInt(temp);
                k++;
                temp = "";
            } else {
                temp = temp + cArray[i];
            }
        }
        intArray[k] = Integer.parseInt(temp);
        return intArray;
    }

    public static String deleteSymbols(String str){
        char[] cArray = str.toCharArray();
        int n = cArray.length;
        String correctedStr = "";
        char[] splitValues = {',', ':', '.', ';', '?', '!'};
        for (int i = 0; i < n; i++){
            for (int j = 0; j < 6; j++){
                if (cArray[i] == splitValues[j]) {
                    cArray[i] = ' ';
                }
            }
        }
        int firstIndex = findTheIndexOfFirstChar(cArray);
        int lastIndex = findTheIndexOfLastChar(cArray);
        n = lastIndex + 1;
        int i = firstIndex;
        if (firstIndex == 0){
            correctedStr = String.valueOf(cArray[0]);
            i++;
        }
        while (i < n) {
            if (cArray[i] == ' ' && cArray[i - 1] == ' ') {
                i++;
            } else {
                correctedStr += cArray[i];
                i++;
            }
        }
        return correctedStr;
    }

    public static int findTheIndexOfFirstChar(char[] cArray){
        boolean flag = false;
        int index = 0;
        int n = cArray.length;
        int i = 0;
        while ( i < n && !flag){
            if (cArray[i] == ' '){
                i++;
            } else{
                index = i;
                flag = true;
            }
        }
        return index;
    }

    public static int findTheIndexOfLastChar(char[] cArray){
        boolean flag = false;
        int index = 0;
        int i = cArray.length - 1;
        while (i > -1 && !flag){
            if (cArray[i] == ' '){
                i--;
            } else {
                index = i;
                flag = true;
            }
        }
        return index;
    }

    public static boolean checkInputData(String line){
        boolean isIncorrect = false;
        char[] cArray = line.toCharArray();
        int n = line.length();
        int i = 0;
        while (!isIncorrect && i < n){
            if(cArray[i] != ' '){
                try{
                    Integer.parseInt(String.valueOf(cArray[i]));
                } catch (NumberFormatException e){
                    isIncorrect = true;
                }
            }
            i++;
        }
        return isIncorrect;
    }

    public static String findFilePath(Scanner input){
        String filePath;
        FileReader reader;
        boolean isIncorrect;
        do {
            System.out.print("Введите путь к файлу: ");
            filePath = input.nextLine();
            isIncorrect = false;
            try{
                reader = new FileReader(filePath);
            } catch (FileNotFoundException e){
                System.out.println("Файл не найден");
                isIncorrect = true;
            }
        } while (isIncorrect);
        return filePath;
    }

    public static void outputData(int[] array, String filepath){
        int n = array.length;
        System.out.print("Полученная строка: ");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
            for (int i = 0; i < n; i++){
                writer.write(array[i] + " ");
                System.out.print(array[i] + " ");
            }
            writer.close();
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args)
    {
        System.out.println("Эта программа сортирует входную строку карманным способом");
        System.out.println("Входной файл");
        Scanner input = new Scanner(System.in);
        String filePath = findFilePath(input);
        int[] intArray = inputData(input, filePath);
        intArray = radixSort(intArray);
        System.out.println("Выходной файл");
        filePath = findFilePath(input);
        input.close();
        outputData(intArray, filePath);
    }
}
