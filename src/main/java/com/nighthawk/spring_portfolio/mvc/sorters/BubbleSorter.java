package com.nighthawk.spring_portfolio.mvc.sorters;

public class BubbleSorter extends Sorter {

    @Override
    public void sort(double[] array) {
        boolean swapped;
        for (int i = 0; i < array.length - 1; i++) {
            swapped = false;
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    // swap array[j] and array[j + 1]
                    double temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true;
                }
            }
            // If no two elements were swapped by inner loop, then break
            if (!swapped) {
                break;
            }
        }
    }

    public class Main {
        public static void main(String[] args) {
            Sorter sorter = new BubbleSorter();
            double[][] benchmarkResults = sorter.benchmarkSort(1000, 10000, 100, 100);
    
            double[] xData = new double[benchmarkResults.length];
            double[] yData = new double[benchmarkResults.length];
            for (int i = 0; i < benchmarkResults.length; i++) {
                xData[i] = benchmarkResults[i][0];
                yData[i] = benchmarkResults[i][1];
            }

            String basePath = "src/main/java/com/nighthawk/spring_portfolio/sorters/benchmark_data/";
            CSVUtil.saveDataToCSV(xData, basePath + "bubbleXData.csv");
            CSVUtil.saveDataToCSV(yData, basePath + "bubbleYData.csv");
    
            DataVisualizationUtil.displayChart(xData, yData, "Bubble Sorter");
        }
    }
}
