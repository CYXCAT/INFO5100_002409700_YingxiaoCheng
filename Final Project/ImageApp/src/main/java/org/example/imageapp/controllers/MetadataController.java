package org.example.imageapp.controllers;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class MetadataController {


    public static void showMetadata(Stage stage, File uploadedFile) {
        if (uploadedFile == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No image uploaded. Please upload an image first.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(uploadedFile);
            // new window for metadata
            Stage metadataStage = new Stage();
            metadataStage.setTitle("Image Metadata");

            // tableview
            TableView<MetadataEntry> metadataTable = new TableView<>();

            // column
            TableColumn<MetadataEntry, String> tagColumn = new TableColumn<>("Tag");
            tagColumn.setCellValueFactory(new PropertyValueFactory<>("tag"));
            tagColumn.setPrefWidth(300);

            TableColumn<MetadataEntry, String> valueColumn = new TableColumn<>("Value");
            valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
            valueColumn.setPrefWidth(500);

            metadataTable.getColumns().addAll(tagColumn, valueColumn);

            // add metadata to tableView
            ObservableList<MetadataEntry> metadataEntries = FXCollections.observableArrayList();
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    metadataEntries.add(new MetadataEntry(tag.getTagName(), tag.getDescription()));
                }
            }

            metadataTable.setItems(metadataEntries);

            // layout
            VBox vbox = new VBox(metadataTable);
            Scene metadataScene = new Scene(vbox, 800, 400);
            metadataStage.setScene(metadataScene);
            metadataStage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to read metadata: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();

        }
    }

    public static class MetadataEntry {
        private final SimpleStringProperty tag;
        private final SimpleStringProperty value;

        public MetadataEntry(String tag, String value) {
            this.tag = new SimpleStringProperty(tag);
            this.value = new SimpleStringProperty(value);
        }

        public String getTag() {
            return tag.get();
        }

        public void setTag(String tag) {
            this.tag.set(tag);
        }

        public String getValue() {
            return value.get();
        }

        public void setValue(String value) {
            this.value.set(value);
        }
    }
}
