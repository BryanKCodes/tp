package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * A UI component that displays information of a {@code Team}.
 */
public class TeamListCard extends UiPart<Region> {

    private static final String FXML = "TeamListCard.fxml";

    public final Team team;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label teamName;
    @FXML
    private Label top;
    @FXML
    private Label jungle;
    @FXML
    private Label mid;
    @FXML
    private Label adc;
    @FXML
    private Label support;


    /**
     * Creates a {@code TeamListCard} with the given {@code Team} and index to display.
     */
    public TeamListCard(Team team, int displayedIndex) {
        super(FXML);
        this.team = team;
        id.setText(displayedIndex + ". ");
        teamName.setText("Team " + displayedIndex); // Or any other naming convention

        // Assuming roles are fixed and every team has one of each
        for (Person person : team.getPersons()) {
            switch (person.getRole().toString().toUpperCase()) {
            case "TOP":
                top.setText("Top: " + person.getName().fullName);
                break;
            case "JUNGLE":
                jungle.setText("Jungle: " + person.getName().fullName);
                break;
            case "MID":
                mid.setText("Mid: " + person.getName().fullName);
                break;
            case "ADC":
                adc.setText("ADC: " + person.getName().fullName);
                break;
            case "SUPPORT":
                support.setText("Support: " + person.getName().fullName);
                break;
            default:
                break;
            }
        }
    }
}
