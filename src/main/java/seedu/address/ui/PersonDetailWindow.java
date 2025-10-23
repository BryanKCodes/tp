package seedu.address.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Controller for a window showing detailed information about a person.
 * Displays the person's profile and a graph of their performance statistics over time.
 */
public class PersonDetailWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(PersonDetailWindow.class);
    private static final String FXML = "PersonDetailWindow.fxml";

    // Display constants
    private static final int MAX_DISPLAYED_MATCHES = 10;
    private static final int CHART_TITLE_FONT_SIZE = 14;
    private static final int LINE_STROKE_WIDTH = 2;
    private static final int DATA_POINT_INSET = 2;

    // Color constants
    private static final String COLOR_PERFORMANCE = "#4CAF50"; // Green - success/progress
    private static final String COLOR_CS = "#2196F3"; // Blue - neutral/farming
    private static final String COLOR_KDA = "#f44336"; // Red - kills/combat
    private static final String COLOR_GOLD = "#FFD700"; // Gold/Yellow - intuitive for gold
    private static final String COLOR_WHITE = "white";

    // CSS style constants
    private static final String STYLE_TEXT_FILL_WHITE = "-fx-text-fill: white;";
    private static final String STYLE_TICK_LABEL_FILL_WHITE = "-fx-tick-label-fill: white;";
    private static final String STYLE_BACKGROUND_TRANSPARENT = "-fx-background-color: transparent;";

    // CSS selector constants
    private static final String CSS_CHART_TITLE = ".chart-title";
    private static final String CSS_AXIS_LABEL = ".axis-label";
    private static final String CSS_CHART_SERIES_LINE = ".chart-series-line";
    private static final String CSS_CHART_PLOT_BACKGROUND = ".chart-plot-background";

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
     * Only displays the latest matches (defined by MAX_DISPLAYED_MATCHES) for better readability.
     */
    private void loadStatsGraph() {
        ArrayList<Float> csPerMinute = person.getStats().getCsPerMinute();
        ArrayList<Integer> goldDiffAt15 = person.getStats().getGoldDiffAt15();
        ArrayList<Float> kdaScores = person.getStats().getKdaScores();
        ArrayList<Double> scores = person.getStats().getScores();

        if (scores.isEmpty()) {
            performanceChart.setTitle(TITLE_NO_DATA);
            return;
        }

        int startIndex = calculateStartIndex(scores.size());

        loadChart(performanceChart, scores, startIndex, TITLE_PERFORMANCE, COLOR_PERFORMANCE);
        loadChart(csChart, csPerMinute, startIndex, TITLE_CS, COLOR_CS);
        loadChart(kdaChart, kdaScores, startIndex, TITLE_KDA, COLOR_KDA);
        loadChart(goldChart, goldDiffAt15, startIndex, TITLE_GOLD, COLOR_GOLD);
    }

    /**
     * Calculates the starting index for displaying the last N matches.
     *
     * @param totalMatches The total number of matches available.
     * @return The starting index for the data to display.
     */
    private static int calculateStartIndex(int totalMatches) {
        return Math.max(0, totalMatches - MAX_DISPLAYED_MATCHES);
    }

    /**
     * Loads a chart with data, title, and color styling.
     * This method consolidates all chart loading operations for better maintainability.
     *
     * @param chart The LineChart to populate.
     * @param data The data to display in the chart.
     * @param startIndex The starting index for data to display.
     * @param titleFormat The title format string (should contain %d for MAX_DISPLAYED_MATCHES).
     * @param color The color to apply to the chart line.
     * @param <T> The type of data in the list (extends Number).
     */
    private static <T extends Number> void loadChart(
            LineChart<Number, Number> chart,
            ArrayList<T> data,
            int startIndex,
            String titleFormat,
            String color) {
        chart.getData().clear();
        chart.setTitle(String.format(titleFormat, MAX_DISPLAYED_MATCHES));
        chart.setLegendVisible(false);

        XYChart.Series<Number, Number> series = createSeriesFromData(data, startIndex);
        chart.getData().add(series);

        applySeriesColor(chart, color);
        applyWhiteTextStyling(chart);
    }

    /**
     * Creates a chart series from the given data list starting from the specified index.
     * This static utility method extracts the last N data points for visualization.
     *
     * @param dataList The list containing the data to be displayed.
     * @param startIndex The starting index for extracting data.
     * @param <T> The type of data in the list (extends Number).
     * @return A new XYChart.Series populated with the data.
     */
    private static <T extends Number> XYChart.Series<Number, Number> createSeriesFromData(
            ArrayList<T> dataList, int startIndex) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (int i = startIndex; i < dataList.size(); i++) {
            series.getData().add(new XYChart.Data<>(i + 1, dataList.get(i)));
        }
        return series;
    }

    /**
     * Applies a custom color to the line in a chart.
     * This static utility method styles both the line and data points with the specified color.
     *
     * @param chart The LineChart to style.
     * @param color The hex color code (e.g., "#FFD700").
     */
    private static void applySeriesColor(LineChart<Number, Number> chart, String color) {
        chart.applyCss();
        chart.layout();
        for (XYChart.Series<Number, Number> series : chart.getData()) {
            String lineStyle = String.format("-fx-stroke: %s; -fx-stroke-width: %dpx;", color, LINE_STROKE_WIDTH);
            series.getNode().lookup(CSS_CHART_SERIES_LINE).setStyle(lineStyle);

            // Style the data points (circles)
            for (XYChart.Data<Number, Number> data : series.getData()) {
                if (data.getNode() != null) {
                    String pointStyle = String.format("-fx-background-color: %s, %s; -fx-background-insets: 0, %d;",
                            color, COLOR_WHITE, DATA_POINT_INSET);
                    data.getNode().setStyle(pointStyle);
                }
            }
        }
    }

    /**
     * Applies white text styling to chart title, axis labels, and tick labels.
     * This static utility method ensures all text elements are visible on dark backgrounds.
     *
     * @param chart The LineChart to style.
     */
    private static void applyWhiteTextStyling(LineChart<Number, Number> chart) {
        chart.applyCss();
        chart.layout();

        // Style the chart title
        if (chart.lookup(CSS_CHART_TITLE) != null) {
            String titleStyle = String.format("%s -fx-font-size: %dpx;",
                    STYLE_TEXT_FILL_WHITE, CHART_TITLE_FONT_SIZE);
            chart.lookup(CSS_CHART_TITLE).setStyle(titleStyle);
        }

        // Style the axis labels
        if (chart.lookup(CSS_AXIS_LABEL) != null) {
            chart.getXAxis().lookup(CSS_AXIS_LABEL).setStyle(STYLE_TEXT_FILL_WHITE);
            chart.getYAxis().lookup(CSS_AXIS_LABEL).setStyle(STYLE_TEXT_FILL_WHITE);
        }

        // Style all tick labels on both axes
        chart.getXAxis().setStyle(STYLE_TICK_LABEL_FILL_WHITE);
        chart.getYAxis().setStyle(STYLE_TICK_LABEL_FILL_WHITE);

        // Style axis lines and tick marks
        chart.lookup(CSS_CHART_PLOT_BACKGROUND).setStyle(STYLE_BACKGROUND_TRANSPARENT);
        chart.getXAxis().setStyle(chart.getXAxis().getStyle() + STYLE_TICK_LABEL_FILL_WHITE);
        chart.getYAxis().setStyle(chart.getYAxis().getStyle() + STYLE_TICK_LABEL_FILL_WHITE);
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
