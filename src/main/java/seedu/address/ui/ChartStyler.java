package seedu.address.ui;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * Utility class responsible for styling and configuring LineCharts.
 * Handles all chart visual presentation concerns.
 */
public class ChartStyler {

    // Styling constants
    private static final int CHART_TITLE_FONT_SIZE = 14;
    private static final int LINE_STROKE_WIDTH = 2;

    // Axis configuration constants
    private static final double X_AXIS_TICK_UNIT = 1.0;

    // CSS style constants
    private static final String STYLE_TEXT_FILL_WHITE = "-fx-text-fill: white;";
    private static final String STYLE_TICK_LABEL_FILL_WHITE = "-fx-tick-label-fill: white;";
    private static final String STYLE_BACKGROUND_TRANSPARENT = "-fx-background-color: transparent;";

    // CSS selector constants
    private static final String CSS_CHART_TITLE = ".chart-title";
    private static final String CSS_AXIS_LABEL = ".axis-label";
    private static final String CSS_CHART_SERIES_LINE = ".chart-series-line";
    private static final String CSS_CHART_PLOT_BACKGROUND = ".chart-plot-background";

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private ChartStyler() {
        throw new AssertionError("ChartStyler should not be instantiated");
    }

    /**
     * Applies a custom color to the line in a chart.
     * Styles both the line and data points with the specified color.
     *
     * @param chart The LineChart to style.
     * @param color The hex color code (e.g., "#FFD700").
     */
    public static void applySeriesColor(LineChart<Number, Number> chart, String color) {
        chart.applyCss();
        chart.layout();
        for (XYChart.Series<Number, Number> series : chart.getData()) {
            styleSeriesLine(series, color);
            styleDataPoints(series, color);
        }
    }

    /**
     * Configures the X-axis to display only the specified match number range.
     * Disables auto-ranging and sets explicit bounds.
     *
     * @param chart The LineChart whose X-axis to configure.
     * @param startMatchNumber The starting match number (1-indexed).
     * @param endMatchNumber The ending match number (1-indexed).
     */
    public static void configureXAxis(LineChart<Number, Number> chart,
                                      int startMatchNumber,
                                      int endMatchNumber) {
        NumberAxis xAxis = (NumberAxis) chart.getXAxis();
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(startMatchNumber);
        xAxis.setUpperBound(endMatchNumber);
        xAxis.setTickUnit(X_AXIS_TICK_UNIT);
    }

    /**
     * Styles the series line with the specified color.
     *
     * @param series The series to style.
     * @param color The color to apply.
     */
    private static void styleSeriesLine(XYChart.Series<Number, Number> series, String color) {
        String lineStyle = String.format("-fx-stroke: %s; -fx-stroke-width: %dpx;", color, LINE_STROKE_WIDTH);
        series.getNode().lookup(CSS_CHART_SERIES_LINE).setStyle(lineStyle);
    }

    /**
     * Styles the data points (circles) with the specified color.
     *
     * @param series The series containing the data points.
     * @param color The color to apply.
     */
    private static void styleDataPoints(XYChart.Series<Number, Number> series, String color) {
        for (XYChart.Data<Number, Number> data : series.getData()) {
            if (data.getNode() != null) {
                String pointStyle = String.format("-fx-background-color: %s;", color);
                data.getNode().setStyle(pointStyle);
            }
        }
    }

    /**
     * Applies white text styling to chart title, axis labels, and tick labels.
     * Ensures all text elements are visible on dark backgrounds.
     *
     * @param chart The LineChart to style.
     */
    public static void applyWhiteTextStyling(LineChart<Number, Number> chart) {
        chart.applyCss();
        chart.layout();

        styleChartTitle(chart);
        styleAxisLabels(chart);
        styleTickLabels(chart);
        styleBackground(chart);
    }

    /**
     * Styles the chart title with white text.
     *
     * @param chart The chart to style.
     */
    private static void styleChartTitle(LineChart<Number, Number> chart) {
        if (chart.lookup(CSS_CHART_TITLE) != null) {
            String titleStyle = String.format("%s -fx-font-size: %dpx;",
                    STYLE_TEXT_FILL_WHITE, CHART_TITLE_FONT_SIZE);
            chart.lookup(CSS_CHART_TITLE).setStyle(titleStyle);
        }
    }

    /**
     * Styles the axis labels with white text.
     *
     * @param chart The chart to style.
     */
    private static void styleAxisLabels(LineChart<Number, Number> chart) {
        if (chart.lookup(CSS_AXIS_LABEL) != null) {
            chart.getXAxis().lookup(CSS_AXIS_LABEL).setStyle(STYLE_TEXT_FILL_WHITE);
            chart.getYAxis().lookup(CSS_AXIS_LABEL).setStyle(STYLE_TEXT_FILL_WHITE);
        }
    }

    /**
     * Styles the tick labels with white text.
     *
     * @param chart The chart to style.
     */
    private static void styleTickLabels(LineChart<Number, Number> chart) {
        chart.getXAxis().setStyle(STYLE_TICK_LABEL_FILL_WHITE);
        chart.getYAxis().setStyle(STYLE_TICK_LABEL_FILL_WHITE);
    }

    /**
     * Styles the chart background.
     *
     * @param chart The chart to style.
     */
    private static void styleBackground(LineChart<Number, Number> chart) {
        if (chart.lookup(CSS_CHART_PLOT_BACKGROUND) != null) {
            chart.lookup(CSS_CHART_PLOT_BACKGROUND).setStyle(STYLE_BACKGROUND_TRANSPARENT);
        }
    }
}
