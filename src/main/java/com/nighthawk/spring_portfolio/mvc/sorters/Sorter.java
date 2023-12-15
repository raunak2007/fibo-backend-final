package com.nighthawk.spring_portfolio.mvc.sorters;

public abstract class Sorter {

    public abstract void sort(double[] array);

    public long timeSorting(double[] array) {
        long startTime = System.nanoTime();
        sort(array);
        long endTime = System.nanoTime();
        return (endTime - startTime); // Convert nanoseconds to milliseconds
    }
    

    public double[][] benchmarkSort(int minStudents, int maxStudents, int stepSize, int runs) {
        double[][] results = new double[(maxStudents - minStudents) / stepSize + 1][2];
        int resultIndex = 0;

        for (int numStudents = minStudents; numStudents <= maxStudents; numStudents += stepSize) {
            long totalTime = 0;

            for (int run = 0; run < runs; run++) {
                double[][] xData = MockDataGenerator.generateXData(numStudents);
                double[] yData = MockDataGenerator.generateYData(xData);
                totalTime += timeSorting(yData);
            }

            double averageTime = totalTime / (double) runs;
            results[resultIndex][0] = numStudents;
            results[resultIndex][1] = averageTime;
            resultIndex++;
        }

        return results;
    }
}
