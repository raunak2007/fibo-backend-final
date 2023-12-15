package com.nighthawk.spring_portfolio.mvc.sorters;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class MockDataGenerator {

    private static final Random random = new Random();

    public static double[][] generateXData(int numStudents) {
        double[][] xData = new double[numStudents][4];

        for (int i = 0; i < numStudents; i++) {
            int commits = 30 + random.nextInt(120);
            int pullRequests = 10 + random.nextInt(50);  // Generate independently
            int issues = 5 + random.nextInt(40);         // Generate independently
            int reposContributed = 2 + random.nextInt(20); // Generate independently

            xData[i][0] = commits;
            xData[i][1] = pullRequests;
            xData[i][2] = issues;
            xData[i][3] = reposContributed;
        }

        return xData;
    }

    public static double[] generateYData(double[][] xData) {
        double[] yData = new double[xData.length];

        for (int i = 0; i < xData.length; i++) {
            yData[i] = calculateGrade((int) xData[i][0], (int) xData[i][1], (int) xData[i][2], (int) xData[i][3]);
        }

        return yData;
    }

    private static double calculateGrade(int commits, int pullRequests, int issues, int reposContributed) {
        double commitGrade = calculateFibonacciGrade(commits, 30);
        double pullRequestGrade = calculateFibonacciGrade(pullRequests, 10);
        double issueGrade = calculateFibonacciGrade(issues, 5);
        double repoGrade = calculateFibonacciGrade(reposContributed, 2);

        return 0.4 * commitGrade + 0.2 * pullRequestGrade + 0.2 * issueGrade + 0.2 * repoGrade;
    }

    private static double calculateFibonacciGrade(int value, int base) {
        if (value <= base) {
            return 60;
        } else {
            double fibonacciTerm = fibonacci(value - base);
            return Math.min(100, 60 + (10 * (1 - 1 / Math.log(fibonacciTerm))));
        }
    }

    private static double fibonacci(int n) {
        if (n <= 1) {
            return n;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }
}
