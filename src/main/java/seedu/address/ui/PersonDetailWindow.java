package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.person.Stats;

/**
 * Controller for a window showing detailed information about a person.
 * Displays the person's profile and graphs of their performance statistics over time.
 * Focuses on UI coordination only.
 */
public class PersonDetailWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(PersonDetailWindow.class);
    private static final String FXML = "PersonDetailWindow.fxml";

    // Color constants for different chart types
    private static final String COLOR_PERFORMANCE = "#4CAF50"; // Green - success/progress
    private static final String COLOR_CS = "#2196F3"; // Blue - neutral/farming
    private static final String COLOR_KDA = "#f44336"; // Red - kills/combat
    private static final String COLOR_GOLD = "#FFD700"; // Gold/Yellow - intuitive for gold

    // Chart title constants
    private static final String TITLE_NO_DATA = "No performance data available";
    private static final String TITLE_PERFORMANCE = "Performance Score Over Time (Latest %d)";
    private static final String TITLE_CS = "CS per Minute Over Time (Latest %d)";
    private static final String TITLE_KDA = "KDA Over Time (Latest %d)";
    private static final String TITLE_GOLD = "Gold Diff @15 Over Time (Latest %d)";

    @FXML
    private Label nameLabel;

    @FXML
    private Label roleLabel;

    @FXML
    private Label championLabel;

    @FXML
    private Label rankLabel;

    @FXML
    private Label statsLabel;

    @FXML
    private Label winsLabel;

    @FXML
    private Label lossesLabel;

    @FXML
    private Label tagsLabel;

    @FXML
    private LineChart<Number, Number> performanceChart;

    @FXML
    private LineChart<Number, Number> csChart;

    @FXML
    private LineChart<Number, Number> kdaChart;

    @FXML
    private LineChart<Number, Number> goldChart;

    private Person person;

    /**
     * Creates a new PersonDetailWindow.
     *
     * @param root Stage to use as the root of the PersonDetailWindow.
     */
    public PersonDetailWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new PersonDetailWindow.
     */
    public PersonDetailWindow() {
        this(new Stage());
    }

    /**
     * Sets the person whose details should be displayed.
     *
     * @param person The person to display.
     */
    public void setPerson(Person person) {
        this.person = person;
        loadPersonDetails();
    }

    /**
     * Loads and displays the person's details.
     */
    private void loadPersonDetails() {
        if (person == null) {
            return;
        }

        // Display basic information
        nameLabel.setText(person.getName().fullName);
        roleLabel.setText("Role: " + person.getRole().value);
        championLabel.setText("Champion: " + person.getChampion().value);
        rankLabel.setText("Rank: " + person.getRank().value);
        statsLabel.setText("Average Performance Score: " + person.getStats().getValue());
        winsLabel.setText("Wins: " + person.getWins());
        lossesLabel.setText("Losses: " + person.getLosses());
        tagsLabel.setText("Tags: " + person.getTags().toString());

        // Load stats graph
        loadStatsGraph();
    }

    /**
     * Loads the stats graphs with the person's historical performance data.
     * Creates separate graphs for Performance Score, CS/min, KDA, and Gold Diff @15.
     * Each graph has an intuitive color: green for performance, blue for CS, red for KDA, yellow for gold.
     * Only displays the latest matches for better readability.
     */
    private void loadStatsGraph() {
        Stats stats = person.getStats();

        List<Double> scores = stats.getScores();
        if (scores.isEmpty()) {
            performanceChart.setTitle(TITLE_NO_DATA);
            return;
        }

        // Load each chart with its respective data
        loadChart(performanceChart, scores, TITLE_PERFORMANCE, COLOR_PERFORMANCE);
        loadChart(csChart, stats.getCsPerMinute(), TITLE_CS, COLOR_CS);
        loadChart(kdaChart, stats.getKdaScores(), TITLE_KDA, COLOR_KDA);
        loadChart(goldChart, stats.getGoldDiffAt15(), TITLE_GOLD, COLOR_GOLD);
    }

    /**
     * Loads a single chart with data, title, and color styling.
     *
     * @param chart The LineChart to populate.
     * @param data The data to display in the chart.
     * @param titleFormat The title format string (should contain %d for max displayed matches).
     * @param color The color to apply to the chart line.
     * @param <T> The type of data in the list (extends Number).
     */
    private static <T extends Number> void loadChart(
            LineChart<Number, Number> chart,
            List<T> data,
            String titleFormat,
            String color) {
        chart.getData().clear();
        chart.setTitle(String.format(titleFormat, ChartDataFormatter.getMaxDisplayedMatches()));
        chart.setLegendVisible(false);

        // Calculate data range for axis configuration
        int startIndex = ChartDataFormatter.calculateStartIndex(data.size());
        int startMatchNumber = startIndex + 1; // Convert to 1-indexed
        int endMatchNumber = data.size();

        // Delegate data formatting to ChartDataFormatter
        XYChart.Series<Number, Number> series = ChartDataFormatter.createSeriesFromLastNPoints(data);
        chart.getData().add(series);

        // Delegate styling and axis configuration to ChartStyler
        ChartStyler.applySeriesColor(chart, color);
        ChartStyler.applyWhiteTextStyling(chart);
        ChartStyler.configureXAxis(chart, startMatchNumber, endMatchNumber);
    }

    /**
     * Shows the person detail window.
     *
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
        logger.fine("Showing person detail window for: " + (person != null ? person.getName() : "unknown"));
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
