package com.nighthawk.spring_portfolio.mvc.sorters;

public class SortingApplication {

    public static void main(String[] args) {
        // Predefined sorter type and array size
        String sorterType = "merge"; // Replace with "Binary", "Bubble", or "Merge"
        int arraySize = 1000; // Replace with desired array size
        
        //WARM UP TIME
        warmUpJVM(sorterType, arraySize);

        // Generate xData using MockDataGenerator
        double[][] xData = MockDataGenerator.generateXData(arraySize);

        // Calculate yData from xData
        double[] yData = MockDataGenerator.generateYData(xData);

        // Get sorter and sort yData
        Sorter sorter = getSorter(sorterType);
        long startTime = System.nanoTime();
        sorter.sort(yData);
        long endTime = System.nanoTime();

        // Calculate time taken in milliseconds
        double timeTaken = (endTime - startTime);
        System.out.println("Time taken for " + sorterType + " Sort: " + timeTaken + " ns");

        // Read benchmark data
        String xCsvFilePath = "src/main/java/com/nighthawk/spring_portfolio/mvc/sorters/benchmark_data/" + sorterType + "XData.csv";
        String yCsvFilePath = "src/main/java/com/nighthawk/spring_portfolio/mvc/sorters/benchmark_data/" + sorterType + "YData.csv";
        double[][] benchmarkData = CSVUtil.readBenchmarkData(xCsvFilePath, yCsvFilePath);

        // Visualize data
        DataVisualizationUtil.displayChartWithUserPoint(benchmarkData, arraySize, timeTaken, sorterType + " Sorter Performance");
    }

    private static void warmUpJVM(String sorterType, int length) {
        // Simple warm-up with small arrays
        for (int i = 0; i < 20; i++) {
            double[][] warmupXData = MockDataGenerator.generateXData(length); // Small data set
            double[] warmupYData = MockDataGenerator.generateYData(warmupXData);
            Sorter sorter = getSorter(sorterType);
            sorter.sort(warmupYData);
        }
    }

    private static Sorter getSorter(String sorterType) {
        switch (sorterType) {
            case "binary":
                return new BinarySorter();
            case "bubble":
                return new BubbleSorter();
            case "merge":
                return new MergeSorter();
            default:
                throw new IllegalArgumentException("Invalid sorter type: " + sorterType);
        }
    }
}
