package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.ui.charts.ChartFactory;

/**
 * Controller for a window that displays detailed information about a person.
 *
 * This class follows a robust hybrid UI design pattern:
 * 1.  Declarative FXML for Static Content: The window's static structure, layout,
 *     and simple labels are all defined in PersonDetailWindow.fxml. This adheres to
 *     the principle of Separation of Concerns, making the view's structure easy to
 *     understand and modify without touching Java code.
 *
 * 2.  Programmatic Generation for Dynamic Content: Complex, data-driven components like
 *     charts are generated dynamically in Java using a ChartFactory. This approach is
 *     fully type-safe, checked at compile time, avoids brittle "magic strings" in
 *     the FXML, and allows for easier extensibility.
 *
 * The controller's primary role is to act as a coordinator: it binds data to the
 * static FXML labels and injects the dynamically-generated charts into the designated
 * container pane. This represents a pragmatic and maintainable best practice.
 */
public class PersonDetailWindow extends UiPart<Stage> {

    private static final String FXML = "PersonDetailWindow.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    private final ChartFactory chartFactory;
    private Person person;

    // @FXML fields for the static components defined in the FXML file.
    @FXML private Label nameLabel;
    @FXML private Label roleLabel;
    @FXML private Label rankLabel;
    @FXML private Label championLabel;
    @FXML private Label winsLabel;
    @FXML private Label lossesLabel;
    @FXML private Label tagsLabel;
    @FXML private GridPane detailsPane;

    // @FXML field for the container where dynamic content will be injected.
    @FXML private VBox chartPane;

    /**
     * Creates a PersonDetailWindow.
     */
    public PersonDetailWindow() {
        this(new Stage());
    }

    /**
     * Creates a PersonDetailWindow with the given Stage.
     */
    public PersonDetailWindow(Stage root) {
        super(FXML, root);
        this.chartFactory = new ChartFactory();
    }

    /**
     * Sets the person to be displayed and populates all UI elements.
     * This is the single entry point for updating the window's content.
     *
     * @param person The person whose details will be displayed. Must not be null.
     */
    public void setPerson(Person person) {
        assert person != null : "Person object cannot be null.";
        this.person = person;
        displayPersonDetails();
        displayCharts();
    }

    /**
     * Populates the static labels (defined in FXML) with the person's information.
     * This method's responsibility is purely data binding.
     */
    private void displayPersonDetails() {
        nameLabel.setText(person.getName().toString());
        roleLabel.setText(person.getRole().toString());
        rankLabel.setText(person.getRank().toString());
        championLabel.setText(person.getChampion().toString());
        tagsLabel.setText(person.getTags().toString());
        winsLabel.setText(String.valueOf(person.getWins()));
        lossesLabel.setText(String.valueOf(person.getLosses()));
    }

    /**
     * Populates the chart pane with dynamically-generated charts from the ChartFactory.
     * This method's responsibility is dynamic content injection.
     */
    private void displayCharts() {
        chartPane.getChildren().clear();
        List<Node> chartComponents = chartFactory.createAllChartComponents(person);
        chartPane.getChildren().addAll(chartComponents);
    }

    /**
     * Shows the person detail window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing person detail window.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the person detail window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the person detail window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the person detail window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
