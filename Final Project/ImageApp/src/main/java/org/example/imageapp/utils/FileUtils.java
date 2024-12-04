package org.example.imageapp.utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.imageapp.controllers.HistogramController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class FileUtils {
    private static Label propertiesLabel;
    private static BufferedImage filteredBufferedImage;

    public FileUtils(Label propertiesLabel) {
        this.propertiesLabel = propertiesLabel;
    }


    public File uploadImage(Stage primaryStage, ImageView imageView, ImageView thumbnailView) {
        if (imageView == null) {
            System.out.println("Error: imageView is null");
            return null;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp"));
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                if (filteredBufferedImage == null) {
                    filteredBufferedImage = bufferedImage;
                }
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                imageView.setImage(image);
                thumbnailView.setImage(image);

                HistogramController.setFilteredBufferedImage(bufferedImage);
                System.out.println("ready for histogram");

                showImageProperties(file);
                return file;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public BufferedImage getBufferedImage() {
        return filteredBufferedImage;
    }



    private void showImageProperties(File file) {
        try {


            BufferedImage bufferedImage = ImageIO.read(file);
            if (filteredBufferedImage == null) {
                filteredBufferedImage = bufferedImage;
            }

            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();

            //Display image properties
            String propertiesText = String.format("Width: %d px\nHeight: %d px\n", width, height);
            propertiesLabel.setText(propertiesText);

        } catch (IOException e) {
            propertiesLabel.setText("Could not read image properties.");
            e.printStackTrace();
        }
    }

    public void saveConvertedImage(Stage stage) {
        BufferedImage imageToSave = ImageUtils.getFilteredBufferedImage();
        System.out.println("Image to save: " + (imageToSave != null));
        if (imageToSave != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Image");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG Files", "*.jpg"));
            File saveFile = fileChooser.showSaveDialog(stage);

            if (saveFile != null) {
                try {
                    String format = "png";
                    if (saveFile.getName().endsWith(".jpg") || saveFile.getName().endsWith(".jpeg")) {
                        format = "jpg";
                    }
                    ImageIO.write(imageToSave, format, saveFile);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Image saved successfully!", ButtonType.OK);
                    alert.showAndWait();
                    System.out.println("Saving filtered image: " + (imageToSave != null));
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error saving image: " + e.getMessage(), ButtonType.OK);
                    alert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No filtered image to save.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    //clear button
    public static void clear(ImageView imageView, ImageView thumbnailView) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("confirmation");
        alert.setHeaderText("Are you sure to clear?");
        alert.setContentText("Unsaved image will be cleared.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            imageView.setImage(null);
            thumbnailView.setImage(null);
            filteredBufferedImage = null;
            propertiesLabel.setText("");
        }
    }
}
