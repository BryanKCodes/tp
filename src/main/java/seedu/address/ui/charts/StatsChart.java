package seedu.address.ui.charts;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import seedu.address.ui.UiPart;

/**
 * A UI component that displays a single line chart.
 */
public class StatsChart extends UiPart<VBox> {
    private static final String FXML = "charts/StatsChart.fxml";

    private final ChartPlotter chartPlotter;

    @FXML
    private LineChart<Number, Number> chart;

    /**
     * Creates a StatsChart, uses ChartPlotter for chart operations.
     */
    public StatsChart() {
        super(FXML);

        this.chartPlotter = new ChartPlotter(this.chart);
    }

    /**
     * Updates the chart with new data and styling.
     *
     * @param title The main title for the chart.
     * @param yAxisLabel The label for the vertical axis.
     * @param styleClass The CSS style class for the chart.
     * @param series The data series to render.
     */
    public void updateChart(String title, String yAxisLabel, String styleClass, XYChart.Series<Number, Number> series) {
        if (series.getData().isEmpty()) {
            chartPlotter.showEmptyMessage();
            return;
        }

        chartPlotter.setLabels(title, yAxisLabel);
        chartPlotter.plot(series);
        chartPlotter.configureAxes();
        chartPlotter.applyStyleClass(styleClass);
    }
}
