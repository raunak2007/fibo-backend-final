package com.nighthawk.spring_portfolio.mvc.sorters;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class DataVisualizationUtil {

    public static JFreeChart createXYLineChart(double[][] xData, double[] yData, String xAxisLabel, String yAxisLabel, String title) {
        XYSeries series = new XYSeries(title);
        for (int i = 0; i < xData.length; i++) {
            series.add(xData[i][0], yData[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        return ChartFactory.createXYLineChart(
            title,
            xAxisLabel,
            yAxisLabel,
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );
    }

    public static void displayChart(double[] xData, double[] yData, String metricName) {
        XYSeries series = new XYSeries("Data Points");

        for (int i = 0; i < xData.length; i++) {
            series.add(xData[i], yData[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createScatterPlot(
                "Time Taken (ns) vs Length of Data for " + metricName,
                "Length of Data",
                "Time Taken (ns)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(0, true);

        chart.getXYPlot().setRenderer(renderer);

        saveChartAsImage(chart, metricName);
    }

    public static void displayChartWithUserPoint(double[][] benchmarkData, int userArrayLength, double userTime, String sortingMethod) {
        XYSeries series = new XYSeries("Benchmark Data");
        for (double[] dataPoint : benchmarkData) {
            series.add(dataPoint[0], dataPoint[1]);
        }

        XYSeries userSeries = new XYSeries("User Data");
        userSeries.add(userArrayLength, userTime);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(userSeries);

        JFreeChart chart = ChartFactory.createScatterPlot(
            "Performance Comparison",
            "Array Length",
            "Time (ns)",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesLinesVisible(1, false);
        renderer.setSeriesShapesVisible(1, true);
        chart.getXYPlot().setRenderer(renderer);

        saveChartAsImage(chart, sortingMethod);
    }

    private static void saveChartAsImage(JFreeChart chart, String fileName) {
        try {
            File imageFile = new File("src/main/resources/static/images/" + fileName + ".png");
            ImageIO.write(chart.createBufferedImage(800, 600), "png", imageFile);
            System.out.println("Chart saved as " + fileName + ".png");
        } catch (IOException e) {
            System.err.println("Problem occurred creating chart.");
            e.printStackTrace();
        }
    }
}
