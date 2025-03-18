package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * Represents a sidebar UI component with buttons for switching between modules folder and contact list.
 */
public class Sidebar extends UiPart<Region> {

    private static final String FXML = "Sidebar.fxml";

    // Stores currently selected button.
    private Button selectedButton;

    @FXML
    private Button moduleButton;

    @FXML
    private Button contactButton;

    /**
     * Constructs a Sidebar component.
     * Initializes buttons and their corresponding event handlers.
     *
     * @param switchableWindow Any UI component that implements SwitchableWindow interface.
     */
    public Sidebar(SwitchableWindow switchableWindow) {
        super(FXML);

        // Set Module button
        setButtonImage(moduleButton, "/images/notebook-pen.png");
        moduleButton.setText("Modules");
        moduleButton.requestFocus();
        selectedButton = moduleButton; // set module button as default on
        moduleButton.setOnAction(event -> {
            selectedButton = moduleButton;
            switchableWindow.setSwitchWindowPlaceholder("Modules");
        });

        Platform.runLater(() -> moduleButton.requestFocus());

        // Set Contact button
        setButtonImage(contactButton, "/images/phone-icon.png");
        contactButton.setText("Contacts");
        contactButton.setOnAction(event -> {
            selectedButton = contactButton;
            switchableWindow.setSwitchWindowPlaceholder("Contacts");
        });
    }

    /**
     * Sets an image icon for the specified button.
     *
     * @param button The button to set the image for.
     * @param url The path to the image resource.
     */
    private void setButtonImage(Button button, String url) {
        requireNonNull(url);
        Image image = new Image(getClass().getResourceAsStream(url));
        ImageView buttonImage = new ImageView(image);
        buttonImage.setFitHeight(15.0);
        buttonImage.setFitWidth(15.0);
        buttonImage.setTranslateX(-10.0);
        button.setGraphic(buttonImage);
    }

    /**
     * Handles the event when the module folder is clicked.
     * Updates the selected button to the contact button.
     */
    public void onFolderClick() {
        selectedButton = contactButton;
    }
}
