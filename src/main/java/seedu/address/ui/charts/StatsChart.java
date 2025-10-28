package seedu.address.ui.charts;

import java.util.Collections;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Stats;
import seedu.address.ui.UiPart;
import seedu.address.ui.charts.provider.ChartProvider;

/**
 * A UI component that displays a single line chart.
 */
public class StatsChart extends UiPart<VBox> {
    private static final String FXML = "charts/StatsChart.fxml";
    private static final String TITLE_NO_DATA = "No performance data available";

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
     * Static factory method to create a chart with the given provider and stats.
     *
     * @param provider The chart provider supplying data and configuration.
     * @param stats The stats data to visualize.
     * @return A VBox containing the configured chart.
     */
    public static VBox createChart(ChartProvider provider, Stats stats) {
        StatsChart statsChart = new StatsChart();

        String title = provider.getTitle();
        String yAxisLabel = provider.getYAxisLabel();
        String styleClass = provider.getStyleClass();
        XYChart.Series<Number, Number> series = provider.createSeries(stats);

        statsChart.updateChart(title, yAxisLabel, styleClass, series);
        return statsChart.getRoot();
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
