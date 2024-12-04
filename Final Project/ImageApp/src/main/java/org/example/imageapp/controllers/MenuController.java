package org.example.imageapp.controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class MenuController {

    private static BufferedImage filteredBufferedImage;

    public static File addFile(Stage primaryStage, ImageView imageView, ImageView thumbnailView) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp"));
        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            try {
                // load chosen image
                BufferedImage addedImage = ImageIO.read(file);

                // initialize
                if (filteredBufferedImage == null) {
                    filteredBufferedImage = addedImage;
                } else {
                    // add
                    javaxt.io.Image javaxtImage = new javaxt.io.Image(filteredBufferedImage);
                    javaxtImage.addImage(addedImage, 0, 0, true);

                    // return
                    filteredBufferedImage = javaxtImage.getBufferedImage();
                }

                // ImageView and ThumbnailView
                Image image = SwingFXUtils.toFXImage(filteredBufferedImage, null);
                imageView.setImage(image);
                thumbnailView.setImage(image);


                return file;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null; // null
    }

    // exit button
    public static void handleExit(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("confirmation");
        alert.setHeaderText("Are you sure to quit?");
        alert.setContentText("Unsaved files will be deleted.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("exiting...");
            stage.close();
        }
    }


}