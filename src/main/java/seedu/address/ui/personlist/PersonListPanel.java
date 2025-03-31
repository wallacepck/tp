package seedu.address.ui.personlist;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        personListView.setPlaceholder(createEmptyPlaceholder()); // Displays when personList is empty

        /*
         *
         *  The following code addresses the issue of ListView flickering when a ListCell is selected.
         *  Basically ListView re-renders whenever a selection is made, and this refreshing of
         *  layout is what causes the flicker.
         *  The code below essentially disables this behaviour, thus removing the flicker when an item
         *  is selected.
         *
         *  The following code may be removed once the issue is fixed in the later JDK.
         *
         *  Relevant topic from StackOverflow:
         *  https://stackoverflow.com/questions/76586932/javafx-listview-selection#comment135033888_76586932
         */
        personListView.setSelectionModel(null);
        personListView.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> event.consume());
    }

    /**
     * Creates a placeholder for empty person List.
     *
     * @return a VBox object to be displayed as placeholder for personListView.
     */
    private VBox createEmptyPlaceholder() {
        // Set Label
        Label emptyLabel = new Label("It's rather empty here...");
        emptyLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #888;");

        // Set Image
        Image sadFaceImage = new Image(getClass().getResourceAsStream("/images/sad-face.png"));
        ImageView emptyListImage = new ImageView(sadFaceImage);
        emptyListImage.setOpacity(0.40);

        VBox placeholder = new VBox(emptyListImage, emptyLabel);
        placeholder.setAlignment(Pos.CENTER);
        placeholder.setPadding(new Insets(20));

        return placeholder;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);
            setOnMouseClicked(MouseEvent::consume);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }
}
