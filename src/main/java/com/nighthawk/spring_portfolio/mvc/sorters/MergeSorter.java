package com.nighthawk.spring_portfolio.mvc.sorters;

public class MergeSorter extends Sorter {

    @Override
    public void sort(double[] array) {
        if (array.length < 2) {
            return; // Base case for recursion: a single element array is already sorted
        }
        int mid = array.length / 2;
        double[] left = new double[mid];
        double[] right = new double[array.length - mid];

        System.arraycopy(array, 0, left, 0, mid);
        System.arraycopy(array, mid, right, 0, array.length - mid);

        sort(left);
        sort(right);

        merge(array, left, right);
    }

    private void merge(double[] array, double[] left, double[] right) {
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }

        while (i < left.length) {
            array[k++] = left[i++];
        }

        while (j < right.length) {
            array[k++] = right[j++];
        }
    }

    public class Main {
        public static void main(String[] args) {
            Sorter sorter = new MergeSorter();
            double[][] benchmarkResults = sorter.benchmarkSort(1000, 10000, 100, 100);
    
            double[] xData = new double[benchmarkResults.length];
            double[] yData = new double[benchmarkResults.length];
            for (int i = 0; i < benchmarkResults.length; i++) {
                xData[i] = benchmarkResults[i][0];
                yData[i] = benchmarkResults[i][1];
            }

            String basePath = "src/main/java/com/nighthawk/spring_portfolio/sorters/benchmark_data/";
            CSVUtil.saveDataToCSV(xData, basePath + "mergeXData.csv");
            CSVUtil.saveDataToCSV(yData, basePath + "mergeYData.csv");

    
            DataVisualizationUtil.displayChart(xData, yData, "Merge Sorter");
        }
    }
}
