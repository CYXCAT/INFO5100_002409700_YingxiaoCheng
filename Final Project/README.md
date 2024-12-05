# Image App

This JavaFX-based project provides a comprehensive toolset for managing and processing images. It includes features like
applying filters, cropping, adding text, generating histograms, and viewing metadata. These functions mainly use Image
IO for image processing.

---

## Features

### 1. Image Upload and Display

- Upload images in formats such as PNG, JPG.
- Display the image and a thumbnail view.
- Dynamic zoom functionality for inspecting image details.

### 2. Image Editing

- Apply various filters, including:
  - **Desaturate**: Reduces image color saturation.
  - **Opacity**: Adjusts image transparency.
  - **Sharpen**: Enhances image edges for clarity.
  - **Blur**: Smoothens the image.
  - **Flip Horizontal**: Mirrors the image horizontally.
  - **Rotate 90°**: Rotates the image clockwise.
- Add customizable text to images:
  - Specify font, size, color, and position.
- Crop images interactively using a drag-and-drop rectangle.

### 3. Histogram Analysis

- Generate histograms to visualize the distribution of red, green, and blue color channels.
- Display histograms in a new window using a bar chart.

### 4. Detailed Metadata

- Extract and display metadata (e.g., EXIF data) from uploaded images using metadata extractor.
- View metadata details in a table format.

### 5. File Management

- Save processed images in PNG or JPG formats.
- Clear loaded images and reset the interface.

---

## Getting Started

### Prerequisites

- Java 8 or later.
- JavaFX SDK.
- Maven.

### Setup Instructions

1. Add the required dependencies:
   - `javaxt-core` for image processing.
   - `metadata-extractor` for metadata reading.
   
2. Run the `MainApp` class to launch the application.

---

## How to use

1. **Launch the Application**:
   - Run the `MainApp` class. The main window will appear.
2. **Upload an Image**:
   - Click the "Upload Image" button or use the menu to select a file.
3. **Apply Filters**:
   - Select a filter from the dropdown and click "Convert Images."
4. **View Histograms**:
   - Click "Show Histogram" to generate and view color channel distributions.
5. **Add Text**:
   - Use the "Add Text" option to overlay text on the image.
6. **Add Image**:
   - Use the "Add Image" option to upload and overlay image on the image.
7. **Crop the Image**:
   - Choose "Crop" and interactively select the desired area.
8. **View Metadata**:
   - Click "Show Metadata" to inspect the image's metadata.
9. **Save or Clear**:
   - Save the processed image using the "Download Converted Images" button.
   - Clear the workspace with the "Clear" option.

---

## Project Structure

```
src/
├── controllers/
│   ├── MenuController.java       # Handles file and menu operations
│   ├── HistogramController.java  # Generates histograms
│   ├── MetadataController.java   # Displays metadata
├── utils/
│   ├── FileUtils.java            # Handles file operations
│   ├── ImageUtils.java           # Provides image processing functions
├── MainApp.java                  # Application entry point
resources/
├── css/
│   ├── style.css                 # Application styling
```

---

## Classes and Their roles

1. `MainApp` (Application Entry Point)
- Purpose: Acts as the main entry point and orchestrates interactions among UI elements and controllers.
- Key Features:
  - Implements event handlers for primary actions (uploading, filtering, viewing).
  - Manages the main layout using JavaFX containers (`VBox`, `HBox`, `BorderPane`).
2. `MenuController`
- Purpose: Handles file-related operations and application-level actions like exit confirmation.
- Key Features:
  - Combines images using `javaxt.io.Image`.
  - Integrates with `ImageView` for UI updates.
3. `HistogramController`
- Purpose: Generates and visualizes histograms for image color channels.
- Key Features:
  - Utilizes JavaFX `BarChart` for displaying histogram data.
4. `MetadataController`
- Purpose: Extracts and displays image metadata.
- Key Features:
  - Reads metadata displays it in a `TableView`.
5. `ImageUtils` (Utility for Image Manipulation)
- Purpose: Provides methods to apply filters, preview images, and modify image properties.
- Responsibilities:
  - Implements a variety of image filters (e.g., desaturation, sharpening) using `javaxt.io.Image`.
  - Allows adding text to images with customizable attributes (font, size, color).
  - Supports cropping functionality with a dedicated `CropPanel` for user interaction.
  - Handles filtered image states (`filteredBufferedImage`) for consistent data flow across the application.
- Key Features:
  - Filters: Applies transformations (e.g., rotation, blurring) directly to the image buffer.
  - Preview: Opens a new `Stage` for viewing images (filtered or original).
  - Crop: Provides an interactive cropping window with visual feedback.
6. `FileUtils` (File Handling Utilities)
- Purpose: Manages image loading, saving, and metadata display.
- Responsibilities:
  - Reads and displays image properties (width, height).
  - Saves processed images in various formats (PNG, JPG).
  - Clears images and resets related UI components.

---

## Class Diagram
```
 +--------------------+
 |    MainApp         |
 +--------------------+
 | + start()          |
 | + showAboutWindow()|
 +--------------------+
        | Calls
        v
 +--------------------+        +-----------------------+
 | MenuController     |<------>| HistogramController   |
 +--------------------+        +-----------------------+
 | + addFile()        |        | + showHistogram()     |
 | + handleExit()     |        | + getHistogram()      |
 +--------------------+        +-----------------------+
        | Uses
        v
 +-----------------------+        +-------------------+
 | MetadataController    |<------>| ImageUtils        |
 +-----------------------+        +-------------------+
 | + showMetadata()      |        | + applyFilter()   |
 | + MetadataEntry       |        | + previewImage()  |
 +-----------------------+        | + addTextToImage()|
                                  +-------------------+

```

---

## Design Pattern

1. **MVC (Model-View-Controller)**
   - **Controllers**: Handle user actions (`MenuController`, `HistogramController`, `MetadataController`).
   - **Views**: JavaFX components (`ImageView`, `TableView`, `BarChart`) serve as the presentation layer.
   - **Model**: Encapsulates image and metadata data in classes like `MetadataEntry`.
2. **Strategy Pattern**
   - Image filtering employs a strategy-like approach by applying different algorithms (`javaxt.io.Image`) depending on the selected filter.
3. **Factory Pattern**
   - `FileChooser` and `ImageMetadataReader` instantiate file dialogs and metadata objects, abstracting their creation.
4. **Observer Pattern**
   - JavaFX bindings and property listeners enable dynamic UI updates when image or metadata states change.
5. **Singleton**
   - `MainApp` serves as a singleton by leveraging JavaFX's application lifecycle.

---

## Test

Some screenshots has been uploaded with the code, and for more detailed test, please check the screenshot video 
uploaded.

---

## Future Enhancements

- Add support for more advanced filters and effects.
- Implement undo/redo functionality.

---

## Contributor

- **Author**: Yingxiao Cheng
  - Email: cheng.yingx@northeastern.edu
  - Github: https://github.com/CYXCAT

Latest updated 2024.12.5