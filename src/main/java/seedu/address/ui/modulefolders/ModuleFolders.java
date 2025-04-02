package seedu.address.ui.modulefolders;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;
import seedu.address.ui.MainWindow;
import seedu.address.ui.UiPart;
import seedu.address.ui.personlist.PersonCard;

/**
 * Represents the UI component for displaying module folders.
 * Each folder corresponds to a CS Module that when the user clicks on it,
 * it will redirect users to Contacts page with the list filtered for the module code.
 */
public class ModuleFolders extends UiPart<Region> {

    private static final String FXML = "ModuleFolders.fxml";
    private static final Image FOLDER_IMAGE = new Image(ModuleFolders.class
            .getResourceAsStream("/images/module_folder.png"));
    private static final Image FAVOURITE_STAR = new Image(PersonCard.class
            .getResourceAsStream("/images/favourite_star.png"));

    private final ObservableList<Person> personList;
    private final MainWindow mainWindow;
    private final VBox emptyPlaceholder;

    @FXML
    private FlowPane folders;

    /**
     * Constructs a ModuleFolders component.
     * Generates folder buttons for each unique CS Module found inside the personList.
     *
     * @param personList The list of persons containing module data.
     * @param mainWindow mainWindow object that is created when GUI is loaded.
     */
    public ModuleFolders(ObservableList<Person> personList, MainWindow mainWindow) {
        super(FXML);
        this.personList = personList;
        this.mainWindow = mainWindow;
        this.emptyPlaceholder = createEmptyPlaceholder();

        // Initialise UI
        updateFolders();

        // Listen for changes in personList and update UI.
        // Behaviour similarly to useEffect in ReactJS.
        this.personList.addListener((ListChangeListener<? super Person>) change -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved()) {
                    updateFolders();
                }
            }
        });
    }

    /**
     * Updates the folder UI dynamically when personList changes.
     */
    private void updateFolders() {
        Platform.runLater(() -> {
            folders.getChildren().clear();

            Set<String> moduleStringSet = new HashSet<>();

            // Add all unique modules into a set
            personList.forEach(person -> person.getModules()
                    .stream()
                    .sorted(Comparator.comparing(module -> module.toString()))
                    .forEach(module -> moduleStringSet.add(module.getModuleCode())));

            // Add modules folders into FlowPane
            moduleStringSet.forEach(moduleString -> createFolder(moduleString, mainWindow));

            // Create a favourite folder if there exists a favourite contact
            boolean hasFavContact = personList.stream().anyMatch(Person::getIsFavourite);
            if (hasFavContact) {
                createFavouriteFolder(mainWindow);
            }

            displayFolders();
        });
    }

    /**
     * Creates a folder element with its respective module inside the Modules Tab.
     * @param moduleString module code stored as a string.
     * @param mainWindow mainWindow object that is created when GUI is loaded.
     */
    private void createFolder(String moduleString, MainWindow mainWindow) {

        // Set folder image
        ImageView folderImageView = new ImageView(FOLDER_IMAGE);
        folderImageView.setFitHeight(100.0);
        folderImageView.setFitWidth(100.0);

        // Set button function
        Button folderButton = new Button();
        folderButton.setGraphic(folderImageView);
        folderButton.setTranslateX(20.0);
        folderButton.setOnAction(e -> {
            mainWindow.filterListByModuleCode(moduleString);
            mainWindow.setSwitchWindowPlaceholder("Contacts");
        });

        // Set label
        Label label = new Label(moduleString);
        label.setTranslateX(40.0);

        // Group all JavaFX element into one VBox and add it into FlowPane
        VBox container = new VBox(folderButton, label);
        folders.getChildren().add(container);
    }

    /**
     * Creates a favourite folder inside Module's tab.
     *
     * @param mainWindow mainWindow object that is created when GUI is loaded.
     */
    private void createFavouriteFolder(MainWindow mainWindow) {

        // Set folder image and favourite star
        ImageView folderImageView = new ImageView(FOLDER_IMAGE);
        folderImageView.setFitHeight(100.0);
        folderImageView.setFitWidth(100.0);

        ImageView star = new ImageView(FAVOURITE_STAR);
        star.setFitHeight(30.0);
        star.setFitWidth(30.0);

        // Stack on top of each other
        StackPane favFolder = new StackPane(folderImageView, star);
        favFolder.setAlignment(Pos.CENTER);

        // Set button function
        Button folderButton = new Button();
        folderButton.setGraphic(favFolder);
        folderButton.setTranslateX(20.0);
        folderButton.setOnAction(e -> {
            mainWindow.filterListByFavourites();
            mainWindow.setSwitchWindowPlaceholder("Contacts");
        });

        // Set label
        Label label = new Label("Favourites");
        label.setTranslateX(35.0);

        // Group all JavaFX element into one VBox and add it into FlowPane
        VBox container = new VBox(folderButton, label);
        folders.getChildren().add(container);
    }

    /**
     * Creates a placeholder for empty person List.
     *
     * @return a VBox object to be displayed as placeholder for Modules Folder.
     */
    private VBox createEmptyPlaceholder() {
        // Set Label
        Label emptyLabel = new Label("Contacts list is currently empty");
        emptyLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #888;");

        // Set Image
        Image folderNotFoundImage = new Image(getClass().getResourceAsStream("/images/empty-folder.png"));
        ImageView emptyFolderImage = new ImageView(folderNotFoundImage);
        emptyFolderImage.setOpacity(0.40);

        VBox placeholder = new VBox(emptyFolderImage, emptyLabel);
        placeholder.setAlignment(Pos.CENTER);
        placeholder.setPadding(new Insets(20));

        return placeholder;
    }

    /**
     * Handles the display of folders or placeholder if the list is empty.
     */
    private void displayFolders() {
        if (folders.getChildren().isEmpty()) {
            folders.setAlignment(Pos.CENTER);
            folders.getChildren().add(emptyPlaceholder);
        } else {
            folders.setAlignment(Pos.TOP_LEFT);
            folders.getChildren().remove(emptyPlaceholder);
        }
    }
}
