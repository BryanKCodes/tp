package seedu.address.ui.charts;

import javafx.scene.chart.XYChart;
import seedu.address.model.person.Stats;

/**
 * Defines the contract for chart data providers.
 * Each implementation provides configuration and data for a specific chart type.
 */
public interface ChartProvider {
    /**
     * Returns the title for this chart.
     *
     * @return The chart title.
     */
    String getTitle();

    /**
     * Returns the label for the Y-axis.
     *
     * @return The Y-axis label.
     */
    String getYAxisLabel();

    /**
     * Returns the CSS style class for this chart.
     *
     * @return The CSS class name.
     */
    String getStyleClass();

    /**
     * Creates a data series from the given stats.
     *
     * @param stats The Stats object containing historical data.
     * @return A data series ready to be rendered.
     */
    XYChart.Series<Number, Number> createSeries(Stats stats);
}
