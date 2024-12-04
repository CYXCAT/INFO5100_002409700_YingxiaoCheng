package org.example.imageapp.controllers;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class HistogramController {
    private static BufferedImage filteredBufferedImage;

    public static void setFilteredBufferedImage(BufferedImage image) {
        filteredBufferedImage = image;
    }


    public static void showHistogram(Stage stage, BufferedImage bufferedImage) {
        if (bufferedImage == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No image uploaded. Please upload an image first.", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        ArrayList<int[]> histogramData = getHistogram(filteredBufferedImage);

        if (histogramData != null) {
            Stage histogramStage = new Stage();
            histogramStage.setTitle("Image Histogram");

            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            BarChart<String, Number> histogramChart = new BarChart<>(xAxis, yAxis);
            histogramChart.setTitle("Image Histogram");

            // Add series for each color
            XYChart.Series<String, Number> redSeries = new XYChart.Series<>();
            redSeries.setName("Red");

            XYChart.Series<String, Number> greenSeries = new XYChart.Series<>();
            greenSeries.setName("Green");

            XYChart.Series<String, Number> blueSeries = new XYChart.Series<>();
            blueSeries.setName("Blue");

            for (int i = 0; i < 256; i++) {
                redSeries.getData().add(new XYChart.Data<>(String.valueOf(i), histogramData.get(0)[i]));
                greenSeries.getData().add(new XYChart.Data<>(String.valueOf(i), histogramData.get(1)[i]));
                blueSeries.getData().add(new XYChart.Data<>(String.valueOf(i), histogramData.get(2)[i]));
            }

            histogramChart.getData().addAll(redSeries, greenSeries, blueSeries);

            VBox vbox = new VBox(histogramChart);
            Scene histogramScene = new Scene(vbox, 800, 500);
            histogramScene.getStylesheets().add(HistogramController.class.getResource("/css/style.css").toExternalForm());
            histogramStage.setScene(histogramScene);
            histogramStage.show();
        }
    }

    private static ArrayList<int[]> getHistogram(BufferedImage bufferedImage) {
        // Create an array to hold the color channels data
        int[] red = new int[256];
        int[] green = new int[256];
        int[] blue = new int[256];

        for (int i = 0; i < bufferedImage.getWidth(); i++) {
            for (int j = 0; j < bufferedImage.getHeight(); j++) {
                int pixel = bufferedImage.getRGB(i, j);

                // Extract color channels
                int r = (pixel >> 16) & 0xFF;
                int g = (pixel >> 8) & 0xFF;
                int b = pixel & 0xFF;

                red[r]++;
                green[g]++;
                blue[b]++;
            }
        }

        // Create and return a list with the color channel arrays
        ArrayList<int[]> histogramData = new ArrayList<>();
        histogramData.add(red);
        histogramData.add(green);
        histogramData.add(blue);

        return histogramData;
    }
}
