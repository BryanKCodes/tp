package seedu.address.ui.charts;

import java.util.Collections;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * Handles data plotting and configuration for a JavaFX LineChart.
 * All styling is delegated to CSS via style classes for proper separation of concerns.
 */
public class ChartPlotter {

    private static final String TITLE_NO_DATA = "No performance data available";

    private final LineChart<Number, Number> chart;

    /**
     * Creates a ChartPlotter for the specified LineChart.
     *
     * @param chart The JavaFX LineChart to control.
     */
    public ChartPlotter(LineChart<Number, Number> chart) {
        this.chart = chart;
        this.chart.setLegendVisible(false);
    }

    /**
     * Displays a message on the chart indicating that no data is available.
     */
    public void showEmptyMessage() {
        chart.getData().clear();
        chart.setTitle(TITLE_NO_DATA);
    }

    /**
     * Plots the given data series onto the chart, replacing any existing data.
     *
     * @param series The data series to render.
     */
    public void plot(XYChart.Series<Number, Number> series) {
        chart.getData().setAll(Collections.singletonList(series));
    }

    /**
     * Sets the title and axis labels for the chart.
     *
     * @param title The main title for the chart.
     * @param yAxisLabel The label for the vertical axis.
     */
    public void setLabels(String title, String yAxisLabel) {
        chart.setTitle(title);
        chart.getYAxis().setLabel(yAxisLabel);
    }

    /**
     * Configures the X-Axis bounds and ticks based on the data in the plotted series.
     * This method must be called *after* plot() has been called.
     */
    public void configureAxes() {
        if (chart.getData().isEmpty() || chart.getData().get(0).getData().isEmpty()) {
            return; // Cannot configure axes without data.
        }

        XYChart.Series<Number, Number> series = chart.getData().get(0);
        int startTick = series.getData().get(0).getXValue().intValue();
        int endTick = series.getData().get(series.getData().size() - 1).getXValue().intValue();

        NumberAxis xAxis = (NumberAxis) chart.getXAxis();
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(startTick);
        xAxis.setUpperBound(endTick);
        xAxis.setTickUnit(1.0); // Ensure every match number is a potential tick.
    }

    /**
     * Applies a CSS style class to the chart.
     * This is the only styling-related action this class takes.
     * All visual styling is defined in the CSS file.
     *
     * @param styleClass The CSS class to apply (e.g., "performance-chart").
     */
    public void applyStyleClass(String styleClass) {
        chart.getStyleClass().add(styleClass);
    }
}
