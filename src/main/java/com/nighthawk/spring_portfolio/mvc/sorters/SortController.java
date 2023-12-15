package com.nighthawk.spring_portfolio.mvc.sorters;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController 
@RequestMapping("/api/sort")

public class SortController {

    @PostMapping("/perform")
    public ResponseEntity<Map<String, Object>> performSorting(@RequestBody SortingRequest request) {
        String sorterType = request.getSorterType();
        int arraySize = request.getArraySize();

        // Warm up the JVM
        warmUpJVM(sorterType, arraySize);

        // Generate xData and yData
        double[][] xData = MockDataGenerator.generateXData(arraySize);
        double[] yData = MockDataGenerator.generateYData(xData);

        // Perform sorting
        Sorter sorter = getSorter(sorterType);
        long startTime = System.nanoTime();
        sorter.sort(yData);
        long endTime = System.nanoTime();

        // Calculate time taken
        double timeTaken = (endTime - startTime);

        // Read benchmark data
        String xCsvFilePath = "src/main/java/com/nighthawk/spring_portfolio/mvc/sorters/benchmark_data/" + sorterType + "XData.csv";
        String yCsvFilePath = "src/main/java/com/nighthawk/spring_portfolio/mvc/sorters/benchmark_data/" + sorterType + "YData.csv";
        double[][] benchmarkData = CSVUtil.readBenchmarkData(xCsvFilePath, yCsvFilePath);

        // Visualize data and save chart
        String chartFileName = sorterType + "_sorter_performance_user";
        DataVisualizationUtil.displayChartWithUserPoint(benchmarkData, arraySize, timeTaken, chartFileName);

        // Construct the response
        Map<String, Object> response = new HashMap<>();
        response.put("timeTakenNs", timeTaken);
        response.put("sorterType", sorterType);
        response.put("arraySize", arraySize);
        response.put("chartImageUrl", "/images/" + chartFileName + ".png");

        return ResponseEntity.ok(response);
    }

    // Include warmUpJVM and getSorter methods here...

    private void warmUpJVM(String sorterType, int length) {
        for (int i = 0; i < 20; i++) {
            double[][] warmupXData = MockDataGenerator.generateXData(length);
            double[] warmupYData = MockDataGenerator.generateYData(warmupXData);
            Sorter sorter = getSorter(sorterType);
            sorter.sort(warmupYData);
        }
    }

    private Sorter getSorter(String sorterType) {
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
    

    static class SortingRequest {
        private String sorterType;
        private int arraySize;

        // Getters and Setters
        public String getSorterType() {
            return sorterType;
        }

        public void setSorterType(String sorterType) {
            this.sorterType = sorterType;
        }

        public int getArraySize() {
            return arraySize;
        }

        public void setArraySize(int arraySize) {
            this.arraySize = arraySize;
        }
    }
}
