package seedu.address.ui.modulefolders;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;
import seedu.address.ui.FunctionalGUI;
import seedu.address.ui.UiPart;

/**
 * Represents the UI component for displaying module folders.
 * Each folder corresponds to a CS Module that when the user clicks on it,
 * it will redirect users to Contacts page with the list filtered for the module code.
 */
public class ModuleFolders extends UiPart<Region> {

    private static final String FXML = "ModuleFolders.fxml";
    private final Image folderImage = new Image(getClass().getResourceAsStream("/images/module_folder.png"));

    @FXML
    private FlowPane folders;

    /**
     * Constructs a ModuleFolders component.
     * Generates folder buttons for each unique CS Module found inside the personList.
     *
     * @param personList The list of persons containing module data.
     */
    public ModuleFolders(ObservableList<Person> personList, FunctionalGUI guiFunction) {
        super(FXML);
        Set<String> moduleStringSet = new HashSet<>();

        // Add all unique modules into a set
        personList.forEach(person -> person.getModules()
                .stream()
                .sorted(Comparator.comparing(module -> module.toString()))
                .forEach(module -> moduleStringSet.add(module.getModuleCode())));

        moduleStringSet.forEach(moduleString -> {
            ImageView folderImageView = new ImageView(folderImage);
            folderImageView.setFitHeight(100.0);
            folderImageView.setFitWidth(100.0);

            Button folderButton = new Button();
            folderButton.setGraphic(folderImageView);
            folderButton.setTranslateX(20.0);
            folderButton.setOnAction(e -> {
                guiFunction.filterListByGUI(moduleString);
                guiFunction.setSwitchWindowPlaceholder("Contacts");
            });

            Label label = new Label(moduleString);
            label.setTranslateX(40.0);

            VBox container = new VBox(folderButton, label);
            folders.getChildren().add(container);
        });
    }
}
