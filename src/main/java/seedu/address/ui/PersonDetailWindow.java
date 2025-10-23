package seedu.address.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
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
     */
    private void loadStatsGraph() {
        ArrayList<Float> csPerMinute = person.getStats().getCsPerMinute();
        ArrayList<Integer> goldDiffAt15 = person.getStats().getGoldDiffAt15();
        ArrayList<Float> kdaScores = person.getStats().getKdaScores();
        ArrayList<Double> scores = person.getStats().getScores();

        if (scores.isEmpty()) {
            // No data to display
            performanceChart.setTitle("No performance data available");
            return;
        }

        // Load Performance Score chart (Green - success/progress)
        performanceChart.getData().clear();
        performanceChart.setTitle("Performance Score Over Time");
        performanceChart.setLegendVisible(false);
        XYChart.Series<Number, Number> scoreSeries = new XYChart.Series<>();
        for (int i = 0; i < scores.size(); i++) {
            scoreSeries.getData().add(new XYChart.Data<>(i + 1, scores.get(i)));
        }
        performanceChart.getData().add(scoreSeries);
        applySeriesColor(performanceChart, "#4CAF50"); // Green
        applyWhiteTextStyling(performanceChart);

        // Load CS per Minute chart (Blue - neutral/farming)
        csChart.getData().clear();
        csChart.setTitle("CS per Minute Over Time");
        csChart.setLegendVisible(false);
        XYChart.Series<Number, Number> csSeries = new XYChart.Series<>();
        for (int i = 0; i < csPerMinute.size(); i++) {
            csSeries.getData().add(new XYChart.Data<>(i + 1, csPerMinute.get(i)));
        }
        csChart.getData().add(csSeries);
        applySeriesColor(csChart, "#2196F3"); // Blue
        applyWhiteTextStyling(csChart);

        // Load KDA chart (Red - kills/combat)
        kdaChart.getData().clear();
        kdaChart.setTitle("KDA Over Time");
        kdaChart.setLegendVisible(false);
        XYChart.Series<Number, Number> kdaSeries = new XYChart.Series<>();
        for (int i = 0; i < kdaScores.size(); i++) {
            kdaSeries.getData().add(new XYChart.Data<>(i + 1, kdaScores.get(i)));
        }
        kdaChart.getData().add(kdaSeries);
        applySeriesColor(kdaChart, "#f44336"); // Red
        applyWhiteTextStyling(kdaChart);

        // Load Gold Diff @15 chart (Yellow/Gold - intuitive for gold)
        goldChart.getData().clear();
        goldChart.setTitle("Gold Diff @15 Over Time");
        goldChart.setLegendVisible(false);
        XYChart.Series<Number, Number> goldSeries = new XYChart.Series<>();
        for (int i = 0; i < goldDiffAt15.size(); i++) {
            goldSeries.getData().add(new XYChart.Data<>(i + 1, goldDiffAt15.get(i)));
        }
        goldChart.getData().add(goldSeries);
        applySeriesColor(goldChart, "#FFD700"); // Gold/Yellow
        applyWhiteTextStyling(goldChart);
    }

    /**
     * Applies a custom color to the line in a chart.
     *
     * @param chart The LineChart to style.
     * @param color The hex color code (e.g., "#FFD700").
     */
    private void applySeriesColor(LineChart<Number, Number> chart, String color) {
        chart.applyCss();
        chart.layout();
        for (XYChart.Series<Number, Number> series : chart.getData()) {
            series.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: " + color + "; -fx-stroke-width: 2px;");
            // Style the data points (circles)
            for (XYChart.Data<Number, Number> data : series.getData()) {
                if (data.getNode() != null) {
                    data.getNode().setStyle("-fx-background-color: " + color + ", white; -fx-background-insets: 0, 2;");
                }
            }
        }
    }

    /**
     * Applies white text styling to chart title, axis labels, and tick labels.
     *
     * @param chart The LineChart to style.
     */
    private void applyWhiteTextStyling(LineChart<Number, Number> chart) {
        chart.applyCss();
        chart.layout();

        // Style the chart title
        if (chart.lookup(".chart-title") != null) {
            chart.lookup(".chart-title").setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        }

        // Style the X axis label
        if (chart.lookup(".axis-label") != null) {
            chart.getXAxis().lookup(".axis-label").setStyle("-fx-text-fill: white;");
            chart.getYAxis().lookup(".axis-label").setStyle("-fx-text-fill: white;");
        }

        // Style all tick labels on both axes
        chart.getXAxis().setStyle("-fx-tick-label-fill: white;");
        chart.getYAxis().setStyle("-fx-tick-label-fill: white;");

        // Style axis lines and tick marks
        chart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
        chart.getXAxis().setStyle(chart.getXAxis().getStyle() + "-fx-tick-label-fill: white;");
        chart.getYAxis().setStyle(chart.getYAxis().getStyle() + "-fx-tick-label-fill: white;");
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
