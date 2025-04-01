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
     * @param guiFunctionHandler Any UI component that implements SwitchableWindow interface.
     */
    public Sidebar(GuiFunctionHandler guiFunctionHandler) {
        super(FXML);

        // Set Module button
        setButtonImage(moduleButton, "/images/notebook-pen.png");
        moduleButton.setText("Modules");
        selectedButton = moduleButton; // set module button as default on
        moduleButton.setOnAction(event -> {
            guiFunctionHandler.setSwitchWindowPlaceholder("Modules");
            setButtonOnClick("Modules");
        });

        Platform.runLater(() -> moduleButton.requestFocus());

        // Set Contact button
        setButtonImage(contactButton, "/images/phone-icon.png");
        contactButton.setText("Contacts");
        contactButton.setOnAction(event -> {
            guiFunctionHandler.setSwitchWindowPlaceholder("Contacts");
            guiFunctionHandler.clearFilter();
            setButtonOnClick("Contacts");
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

    public void setButtonOnClick(String buttonName) {
        if (!buttonName.equals("Modules") && !buttonName.equals("Contacts")) {
            return;
        } else if (buttonName.equals(selectedButton.getText())) {
            return;
        }

        selectedButton.setStyle("-fx-background-color: derive(#3c3c3c, 20%);");
        if (buttonName.equals("Modules")) {
            moduleButton.setStyle("-fx-background-color: #416989;");
            selectedButton = moduleButton;
        } else {
            contactButton.setStyle("-fx-background-color: #416989;");
            selectedButton = contactButton;
        }
    }
}
