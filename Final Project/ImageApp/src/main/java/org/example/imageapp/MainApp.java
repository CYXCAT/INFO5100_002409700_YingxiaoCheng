package org.example.imageapp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.imageapp.controllers.HistogramController;
import org.example.imageapp.controllers.MenuController;
import org.example.imageapp.controllers.MetadataController;
import org.example.imageapp.utils.FileUtils;
import org.example.imageapp.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.io.File;

import static org.example.imageapp.utils.ImageUtils.showErrorDialog;

public class MainApp extends Application {

    private File uploadedFile;
    private ImageView imageView;
    private ImageView thumbnailView;
    private Label propertiesLabel;
    private BufferedImage filteredBufferedImage;
    private static final double ZOOM_SCALE = 2.0;// zoom in view size set

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Image Converter");

        MenuBar menuBar = new MenuBar();
        imageView = new ImageView();
        thumbnailView = new ImageView();
        thumbnailView.setFitWidth(100);
        thumbnailView.setFitHeight(100);
        propertiesLabel = new Label();
        FileUtils fileUtils = new FileUtils(propertiesLabel);

        Menu fileMenu = new Menu("file");
        Menu editMenu = new Menu("edit");
        Menu helpMenu = new Menu("help");
        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);

        MenuItem newFile = new MenuItem("upload");
        MenuItem addFile = new MenuItem("add new image");
        MenuItem exit = new MenuItem("exit");
        fileMenu.getItems().addAll(newFile, addFile, new SeparatorMenuItem(), exit);

        MenuItem crop = new MenuItem("crop");
        MenuItem addtext = new MenuItem("add text");
        MenuItem clear = new MenuItem("clear");
        editMenu.getItems().addAll(crop, addtext, clear);

        MenuItem about = new MenuItem("about");
        helpMenu.getItems().add(about);

        // add a dynamic zoom image view
        ImageView zoomedImageView = new ImageView();
        zoomedImageView.setFitWidth(200);
        zoomedImageView.setFitHeight(200);
        zoomedImageView.setStyle("-fx-border-color: black; -fx-border-width: 2;");

        // default zoom view is invisible
        zoomedImageView.setVisible(false);

        // zoom view mouse event
        thumbnailView.setOnMouseEntered(e -> zoomedImageView.setVisible(true)); // mouse hang visible
        thumbnailView.setOnMouseExited(e -> zoomedImageView.setVisible(false)); // mouse leave invisible
        thumbnailView.setOnMouseMoved(event -> {
            if (thumbnailView.getImage() != null) {
                // get thumbnail
                Image thumbnailImage = thumbnailView.getImage();
                double thumbnailWidth = thumbnailImage.getWidth();
                double thumbnailHeight = thumbnailImage.getHeight();

                // calculate
                double mouseX = event.getX();
                double mouseY = event.getY();

                // get original zoom place
                double zoomX = mouseX * (uploadedFile != null ? filteredBufferedImage.getWidth() : thumbnailWidth) / thumbnailView.getFitWidth();
                double zoomY = mouseY * (uploadedFile != null ? filteredBufferedImage.getHeight() : thumbnailHeight) / thumbnailView.getFitHeight();

                // zoom scale
                double zoomRegionWidth = zoomedImageView.getFitWidth() / ZOOM_SCALE;
                double zoomRegionHeight = zoomedImageView.getFitHeight() / ZOOM_SCALE;

                // ensure the boarder
                double startX = Math.max(0, zoomX - zoomRegionWidth / 2);
                double startY = Math.max(0, zoomY - zoomRegionHeight / 2);
                startX = Math.min(startX, thumbnailWidth - zoomRegionWidth);
                startY = Math.min(startY, thumbnailHeight - zoomRegionHeight);

                // cut zoom place
                PixelReader pixelReader = thumbnailImage.getPixelReader();
                WritableImage zoomedRegion = new WritableImage(pixelReader,
                        (int) startX, (int) startY,
                        (int) zoomRegionWidth, (int) zoomRegionHeight);

                // show zoom in place
                zoomedImageView.setImage(zoomedRegion);
            }
        });

        // Buttons set and some layout
        Button uploadButton = new Button("Upload Image");
        Button showHistogramButton = new Button("Show Histogram");
        Button showMetadataButton = new Button("Show Metadata");
        Button previewButton = new Button("Preview Image");

        ComboBox<String> filterComboBox = new ComboBox<>();
        filterComboBox.getItems().addAll("None", "Desaturate", "Opacity", "Sharpen", "Blur", "Flip Horizontal", "Rotate 90°");
        Button applyFilterButton = new Button("Convert Images");
        Button downloadButton = new Button("Download Converted Images");
        downloadButton.setOnAction(e -> {

            fileUtils.saveConvertedImage(primaryStage);
        });

        // Organize buttons in an HBox
        HBox buttonBox = new HBox(10, uploadButton, showHistogramButton, showMetadataButton, previewButton);
        buttonBox.setPadding(new Insets(10));
        buttonBox.setSpacing(10);

        // Organize filters in an HBox
        HBox filterBox = new HBox(10, new Label("Choose Filter:"), filterComboBox);
        filterBox.setPadding(new Insets(10));

        //VBox for thumb and labels
        VBox infoBox = new VBox(10, thumbnailView, propertiesLabel);
        infoBox.setPadding(new Insets(10));
        infoBox.setSpacing(10);
        infoBox.setAlignment(Pos.CENTER);

        //HBox for infoBox and Label
        HBox mainBox = new HBox(10, infoBox, zoomedImageView);
        mainBox.setPadding(new Insets(10));
        mainBox.setSpacing(10);
        mainBox.setAlignment(Pos.CENTER);

        VBox downButtonBox = new VBox(10, applyFilterButton, downloadButton);
        downButtonBox.setPadding(new Insets(10));
        downButtonBox.setSpacing(10);
        downButtonBox.setAlignment(Pos.CENTER);

        // Main VBox for all UI elements
        VBox vbox = new VBox(10, buttonBox, filterBox, mainBox, downButtonBox);
        vbox.setPadding(new Insets(10));


        // upload button handle
        uploadButton.setOnAction(event -> {
            try {
                // upload
                uploadedFile = fileUtils.uploadImage(primaryStage, imageView, thumbnailView);

                // check in command line
                if (uploadedFile != null) {
                    System.out.println("Image uploaded successfully：" + uploadedFile.getAbsolutePath());
                    ImageUtils.setUploadedFile(uploadedFile);
                } else {
                    System.out.println("No chosen file.");
                }

                // get BufferedImage
                filteredBufferedImage = fileUtils.getBufferedImage();

            } catch (Exception e) {
                showErrorDialog("Error", "Failed to load the image.");
                e.printStackTrace();
            }
        });

        newFile.setOnAction(event -> {
            try {
                // upload
                uploadedFile = fileUtils.uploadImage(primaryStage, imageView, thumbnailView);

                // check in command line
                if (uploadedFile != null) {
                    System.out.println("Image uploaded successfully：" + uploadedFile.getAbsolutePath());
                    ImageUtils.setUploadedFile(uploadedFile);
                } else {
                    System.out.println("No chosen file.");
                }

                // get BufferedImage
                filteredBufferedImage = fileUtils.getBufferedImage();

            } catch (Exception e) {
                showErrorDialog("Error", "Failed to load the image.");
                e.printStackTrace();
            }
        });


       // various button handle
        addFile.setOnAction(event -> MenuController.addFile(primaryStage, imageView, thumbnailView));

        exit.setOnAction(event -> MenuController.handleExit(primaryStage));

        addtext.setOnAction(event -> ImageUtils.showAddTextDialog(primaryStage, imageView, thumbnailView));

        crop.setOnAction(event -> ImageUtils.showCropWindow(filteredBufferedImage, imageView, thumbnailView));

        clear.setOnAction(event -> FileUtils.clear(imageView, thumbnailView));

        about.setOnAction(event -> showAboutWindow());

        showHistogramButton.setOnAction(event -> HistogramController.showHistogram(primaryStage, filteredBufferedImage));

        showMetadataButton.setOnAction(event -> MetadataController.showMetadata(primaryStage, uploadedFile));

        applyFilterButton.setOnAction(event -> ImageUtils.applyFilter(filterComboBox.getValue(), imageView, thumbnailView));

        previewButton.setOnAction(event -> ImageUtils.previewImage());

        //Scene setup
        BorderPane root = new BorderPane(vbox);
        root.setTop(menuBar);
        Scene scene = new Scene(root, 630, 550);

        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // about window
    public void showAboutWindow() {
        Stage aboutStage = new Stage();
        aboutStage.setTitle("About");

        VBox vbox = new VBox(10);

        //contact info
        String contactInfo = "Author: Yingxiao Cheng\nEmail: cheng.yingx@northeastern.edu\n";
        javafx.scene.control.Label contactLabel = new javafx.scene.control.Label(contactInfo);

        // Hyperlink
        Hyperlink githubLink = new Hyperlink("Visit GitHub Repository");
        githubLink.setOnAction(e -> {
            getHostServices().showDocument("https://github.com/your-repository-url");
        });

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> aboutStage.close());

        vbox.getChildren().addAll(contactLabel, githubLink, closeButton);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 300, 200);
        aboutStage.setScene(scene);
        aboutStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
