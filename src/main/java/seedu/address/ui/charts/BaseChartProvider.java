package seedu.address.ui.charts;

import java.util.List;

import javafx.scene.chart.XYChart;
import seedu.address.model.person.Stats;

/**
 * Abstract base class using the Template Method Pattern.
 * Provides the common algorithm for creating a data series from the last N matches.
 */
public abstract class BaseChartProvider implements ChartProvider {

    /**
     * Creates a data series using the template method pattern.
     * Calls abstract hook methods that subclasses must implement.
     *
     * @param stats The Stats object.
     * @return The data series for the chart.
     */
    @Override
    public final XYChart.Series<Number, Number> createSeries(Stats stats) {
        List<? extends Number> fullDataList = getData(stats);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        int maxMatches = getMaxDisplayedMatches();
        int startIndex = Math.max(0, fullDataList.size() - maxMatches);
        List<? extends Number> relevantData = fullDataList.subList(startIndex, fullDataList.size());

        for (int i = 0; i < relevantData.size(); i++) {
            int matchNumber = startIndex + i + 1;
            series.getData().add(new XYChart.Data<>(matchNumber, relevantData.get(i)));
        }
        return series;
    }

    /**
     * Returns the specific data list from the Stats object.
     *
     * @param stats The Stats object.
     * @return The list of numerical data for the chart.
     */
    protected abstract List<? extends Number> getData(Stats stats);

    /**
     * Returns the maximum number of matches to display.
     *
     * @return The maximum number of data points to display.
     */
    protected abstract int getMaxDisplayedMatches();
}
