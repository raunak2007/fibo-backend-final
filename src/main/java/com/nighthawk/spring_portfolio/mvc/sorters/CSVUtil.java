package com.nighthawk.spring_portfolio.mvc.sorters;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {

    public static void saveDataToCSV(double[] data, String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            for (double value : data) {
                fileWriter.write(value + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double[][] readDataFromCSV(String fileName) {
        List<double[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                double[] row = new double[values.length];
                for (int i = 0; i < values.length; i++) {
                    row[i] = Double.parseDouble(values[i]);
                }
                data.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data.toArray(new double[0][]);
    }

    public static double[][] readBenchmarkData(String xFilePath, String yFilePath) {
        double[][] xData = readDataFromCSV(xFilePath);
        double[][] yData = readDataFromCSV(yFilePath);

        double[][] combinedData = new double[xData.length][2];
        for (int i = 0; i < xData.length; i++) {
            combinedData[i][0] = xData[i][0]; // Assuming the first column of xData is what you need
            combinedData[i][1] = yData[i][0]; // Assuming yData is a single column array
        }
        return combinedData;
    }
}
