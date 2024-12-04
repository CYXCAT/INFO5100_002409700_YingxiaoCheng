package org.example.imageapp.utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;

import static javafx.scene.paint.Color.BLACK;


public class ImageUtils {
    private static File uploadedFile;
    private static boolean isFiltered = false;
    private static BufferedImage filteredBufferedImage;

    public static void setUploadedFile(File file) {
        uploadedFile = file;
    }

    public static BufferedImage getFilteredBufferedImage() {
        return filteredBufferedImage;
    }

    public static void applyFilter(String filter, ImageView imageView, ImageView thumbnailView) {

        if (uploadedFile != null) {
            try {
                javaxt.io.Image javaxtImage = new javaxt.io.Image(uploadedFile);

                // differetn filter from image IO
                switch (filter) {
                    case "Desaturate":
                        javaxtImage.desaturate(0.5);
                        break;
                    case "Opacity":
                        javaxtImage.setOpacity(50);
                        break;
                    case "Sharpen":
                        javaxtImage.sharpen();
                        break;
                    case "Blur":
                        javaxtImage.blur(5);
                        break;
                    case "Flip Horizontal":
                        javaxtImage.flip();
                        break;
                    case "Rotate 90°":
                        javaxtImage.rotate(90);
                        break;
                    default:
                        break;
                }

                // javaxt.io.Image to BufferedImage
                filteredBufferedImage = toBufferedImage(javaxtImage.getImage());
                System.out.println("Filtered BufferedImage updated: " + (filteredBufferedImage != null));


                //  BufferedImage to JavaFX Image
                Image javafxImage = SwingFXUtils.toFXImage(filteredBufferedImage, null);
                imageView.setImage(javafxImage);
                thumbnailView.setImage(javafxImage);
                isFiltered = true;
            } catch (Exception e) {
                e.printStackTrace();
                showErrorDialog("Error", "Unable to apply filter.");
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please upload an image.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public static void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static BufferedImage toBufferedImage(java.awt.Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        BufferedImage bImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bImage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bImage;
    }

    public static void previewImage() {
        if (uploadedFile != null) {
            Image imageToPreview;
            double imageWidth = 0;
            double imageHeight = 0;

            // filtered or not
            if (isFiltered && filteredBufferedImage != null) {
                imageToPreview = SwingFXUtils.toFXImage(filteredBufferedImage, null);
                imageWidth = filteredBufferedImage.getWidth();
                imageHeight = filteredBufferedImage.getHeight();
            } else {
                imageToPreview = new Image(uploadedFile.toURI().toString());
                imageWidth = imageToPreview.getWidth();
                imageHeight = imageToPreview.getHeight();
            }

            // new stage to preview
            Stage previewStage = new Stage();
            previewStage.setTitle("Image Preview");

            ImageView previewImageView = new ImageView(imageToPreview);
            previewImageView.setPreserveRatio(true);

            previewImageView.setFitWidth(imageWidth);
            previewImageView.setFitHeight(imageHeight);

            VBox vbox = new VBox(previewImageView);
            Scene previewScene = new Scene(vbox, imageWidth, imageHeight);
            previewStage.setScene(previewScene);
            previewStage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No image uploaded. Please upload an image first.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public static void showAddTextDialog(Stage stage, ImageView imageView, ImageView thumbnailView) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add Text");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(stage); // primaryStage 是主窗口 Stage

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        // Input text
        javafx.scene.control.TextField textInput = new javafx.scene.control.TextField();
        textInput.setPromptText("Please text here");

        Spinner<Integer> xSpinner = new Spinner<>(0, 2000, 100); // x
        Spinner<Integer> ySpinner = new Spinner<>(0, 2000, 100); // y
        javafx.scene.control.TextField fontInput = new javafx.scene.control.TextField("Tahoma");
        Spinner<Integer> fontSizeSpinner = new Spinner<>(10, 100, 20);

        // Color
        ColorPicker colorPicker = new ColorPicker(BLACK);

        // Confirm
        javafx.scene.control.Button confirmButton = new javafx.scene.control.Button("Confirm");
        confirmButton.setOnAction(e -> {
            String text = textInput.getText();
            int x = xSpinner.getValue();
            int y = ySpinner.getValue();
            String fontName = fontInput.getText();
            int fontSize = fontSizeSpinner.getValue();
            Color color = colorPicker.getValue();

            // get RGB
            int r = (int) (color.getRed() * 255);
            int g = (int) (color.getGreen() * 255);
            int b = (int) (color.getBlue() * 255);

            // from Image IO
            addTextToImage(text, x, y, fontName, fontSize, r, g, b,imageView, thumbnailView);

            // close
            dialogStage.close();
        });

        layout.getChildren().addAll(
                new javafx.scene.control.Label("Text:"), textInput,
                new javafx.scene.control.Label("Position (x, y):"), xSpinner, ySpinner,
                new javafx.scene.control.Label("Font:"), fontInput,
                new javafx.scene.control.Label("Font size:"), fontSizeSpinner,
                new Label("Color:"), colorPicker,
                confirmButton
        );

        Scene scene = new Scene(layout);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    private static void addTextToImage(String text, int x, int y, String fontName, int fontSize, int r, int g, int b, ImageView imageView, ImageView thumbnailView) {

        if (filteredBufferedImage != null) {
            javaxt.io.Image javaxtImage = new javaxt.io.Image(filteredBufferedImage);

            // Image IO
            javaxtImage.addText(text, x, y, fontName, fontSize, r, g, b);

            // new image
            filteredBufferedImage = javaxtImage.getBufferedImage();

            // update view
            Image updatedImage = SwingFXUtils.toFXImage(filteredBufferedImage, null);
            imageView.setImage(updatedImage);
            thumbnailView.setImage(updatedImage);


        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please upload an image.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    // Crop
    public static void showCropWindow(BufferedImage image, ImageView imageView, ImageView thumbnailView) {
        if (image == null) {
            JOptionPane.showMessageDialog(null, "No image to crop!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // a crop window
        JFrame cropFrame = new JFrame("Crop Image");
        CropPanel cropPanel = new CropPanel(image);
        cropFrame.add(cropPanel);
        cropFrame.setSize(image.getWidth(), image.getHeight());
        cropFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cropFrame.setVisible(true);

        JButton confirmButton = new JButton("Confirm Crop");
        confirmButton.addActionListener(e -> {
            BufferedImage croppedImage = cropPanel.getCroppedImage();
            if (croppedImage != null) {
                javaxt.io.Image javaxtImage = new javaxt.io.Image(croppedImage);

                filteredBufferedImage = toBufferedImage(javaxtImage.getImage());
                System.out.println("Filtered BufferedImage updated: " + (filteredBufferedImage != null));

                // BufferedImage to JavaFX Image
                Image javafxImage = SwingFXUtils.toFXImage(filteredBufferedImage, null);
                imageView.setImage(javafxImage);
                thumbnailView.setImage(javafxImage);

                JOptionPane.showMessageDialog(cropFrame, "Image cropped successfully!");
                cropFrame.dispose(); // close

            } else {
                JOptionPane.showMessageDialog(cropFrame, "Invalid crop area!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cropFrame.add(confirmButton, BorderLayout.SOUTH);
    }


    // crop panel
    private static class CropPanel extends JPanel {
        private BufferedImage image; // original
        private Rectangle cropRectangle; // crop rectangle
        private boolean isDragging = false; // dragging

        public CropPanel(BufferedImage image) {
            this.image = image;
            this.cropRectangle = new Rectangle();

            // mouse event
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    cropRectangle.setBounds(e.getX(), e.getY(), 0, 0);
                    isDragging = true;
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    isDragging = false;
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (isDragging) {
                        cropRectangle.setSize(
                                e.getX() - cropRectangle.x,
                                e.getY() - cropRectangle.y
                        );
                        repaint();
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                g.drawImage(image, 0, 0, this);
            }
            if (cropRectangle != null && isDragging) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(java.awt.Color.RED);
                g2d.setStroke(new BasicStroke(2));
                g2d.draw(cropRectangle); // draw crop rectangle
            }
        }

        // get cropped image
        public BufferedImage getCroppedImage() {
            if (cropRectangle.width > 0 && cropRectangle.height > 0) {
                // Image IO
                javaxt.io.Image javaxtImage = new javaxt.io.Image(image);
                javaxtImage.crop(
                        cropRectangle.x,
                        cropRectangle.y,
                        cropRectangle.width,
                        cropRectangle.height
                );
                return javaxtImage.getBufferedImage();
            }
            return null;
        }
    }


}
