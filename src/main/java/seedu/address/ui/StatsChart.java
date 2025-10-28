package seedu.address.ui;

import java.util.Collections;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Stats;

/**
 * A UI component that displays a single line chart for player statistics.
 */
public class StatsChart extends UiPart<VBox> {
    private static final String FXML = "charts/StatsChart.fxml";
    private static final String TITLE_NO_DATA = "No performance data available";
    private static final int MAX_DISPLAYED_MATCHES = 10;

    @FXML
    private LineChart<Number, Number> chart;

    /**
     * Creates a StatsChart.
     */
    private StatsChart() {
        super(FXML);
        chart.setLegendVisible(false);
    }

    /**
     * Creates a Performance Score chart.
     */
    public static VBox createPerformanceChart(Stats stats) {
        String title = String.format("Performance Score Over Time (Latest %d)", MAX_DISPLAYED_MATCHES);
        String yAxisLabel = "Performance Score";
        String styleClass = "performance-chart";
        XYChart.Series<Number, Number> series = createSeries(stats.getScores());
        return createChart(title, yAxisLabel, styleClass, series);
    }

    /**
     * Creates a CS per Minute chart.
     */
    public static VBox createCsPerMinuteChart(Stats stats) {
        String title = String.format("CS per Minute Over Time (Latest %d)", MAX_DISPLAYED_MATCHES);
        String yAxisLabel = "CS per Minute";
        String styleClass = "cs-chart";
        XYChart.Series<Number, Number> series = createSeries(stats.getCsPerMinute());
        return createChart(title, yAxisLabel, styleClass, series);
    }

    /**
     * Creates a KDA chart.
     */
    public static VBox createKdaChart(Stats stats) {
        String title = String.format("KDA Over Time (Latest %d)", MAX_DISPLAYED_MATCHES);
        String yAxisLabel = "KDA";
        String styleClass = "kda-chart";
        XYChart.Series<Number, Number> series = createSeries(stats.getKdaScores());
        return createChart(title, yAxisLabel, styleClass, series);
    }

    /**
     * Creates a Gold Difference chart.
     */
    public static VBox createGoldDifferenceChart(Stats stats) {
        String title = String.format("Gold Diff @15 Over Time (Latest %d)", MAX_DISPLAYED_MATCHES);
        String yAxisLabel = "Gold Diff @15";
        String styleClass = "gold-diff-chart";
        XYChart.Series<Number, Number> series = createSeries(stats.getGoldDiffAt15());
        return createChart(title, yAxisLabel, styleClass, series);
    }

    /**
     * Creates a chart with the given configuration.
     */
    private static VBox createChart(String title, String yAxisLabel, String styleClass,
            XYChart.Series<Number, Number> series) {
        StatsChart statsChart = new StatsChart();
        statsChart.updateChart(title, yAxisLabel, styleClass, series);
        return statsChart.getRoot();
    }

    /**
     * Creates a data series from the last N matches.
     */
    private static XYChart.Series<Number, Number> createSeries(List<? extends Number> data) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        int startIndex = Math.max(0, data.size() - MAX_DISPLAYED_MATCHES);
        List<? extends Number> relevantData = data.subList(startIndex, data.size());

        for (int i = 0; i < relevantData.size(); i++) {
            int matchNumber = startIndex + i + 1;
            series.getData().add(new XYChart.Data<>(matchNumber, relevantData.get(i)));
        }
        return series;
    }

    /**
     * Updates the chart with new data and styling.
     *
     * @param title The main title for the chart.
     * @param yAxisLabel The label for the vertical axis.
     * @param styleClass The CSS style class for the chart.
     * @param series The data series to render.
     */
    private void updateChart(String title, String yAxisLabel, String styleClass,
            XYChart.Series<Number, Number> series) {
        if (series.getData().isEmpty()) {
            showEmptyMessage();
            return;
        }

        setLabels(title, yAxisLabel);
        plotData(series);
        configureAxes();
        applyStyleClass(styleClass);
    }

    /**
     * Displays a message on the chart indicating that no data is available.
     */
    private void showEmptyMessage() {
        chart.getData().clear();
        chart.setTitle(TITLE_NO_DATA);
    }

    /**
     * Plots the given data series onto the chart, replacing any existing data.
     */
    private void plotData(XYChart.Series<Number, Number> series) {
        chart.getData().setAll(Collections.singletonList(series));
    }

    /**
     * Sets the title and axis labels for the chart.
     */
    private void setLabels(String title, String yAxisLabel) {
        chart.setTitle(title);
        chart.getYAxis().setLabel(yAxisLabel);
    }

    /**
     * Configures the X-Axis bounds and ticks based on the data in the plotted series.
     * This method must be called after plotData() has been called.
     */
    private void configureAxes() {
        if (chart.getData().isEmpty() || chart.getData().get(0).getData().isEmpty()) {
            return;
        }

        XYChart.Series<Number, Number> series = chart.getData().get(0);
        int startTick = series.getData().get(0).getXValue().intValue();
        int endTick = series.getData().get(series.getData().size() - 1).getXValue().intValue();

        NumberAxis xAxis = (NumberAxis) chart.getXAxis();
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(startTick);
        xAxis.setUpperBound(endTick);
        xAxis.setTickUnit(1.0);
    }

    /**
     * Applies a CSS style class to the chart.
     */
    private void applyStyleClass(String styleClass) {
        chart.getStyleClass().add(styleClass);
    }
}
