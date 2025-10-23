package seedu.address.ui;

import java.util.List;

import javafx.scene.chart.XYChart;

/**
 * Utility class responsible for formatting data into chart series.
 * Follows Single Responsibility Principle by handling only data formatting concerns.
 * Follows Information Expert pattern as it knows how to extract and format chart data.
 */
public class ChartDataFormatter {

    private static final int MAX_DISPLAYED_MATCHES = 10;

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private ChartDataFormatter() {
        throw new AssertionError("ChartDataFormatter should not be instantiated");
    }

    /**
     * Calculates the starting index for displaying the last N matches.
     *
     * @param totalMatches The total number of matches available.
     * @return The starting index for the data to display.
     */
    public static int calculateStartIndex(int totalMatches) {
        return Math.max(0, totalMatches - MAX_DISPLAYED_MATCHES);
    }

    /**
     * Gets the maximum number of matches to display on charts.
     *
     * @return The maximum number of displayed matches.
     */
    public static int getMaxDisplayedMatches() {
        return MAX_DISPLAYED_MATCHES;
    }

    /**
     * Creates a chart series from the given data list, showing only the last N data points.
     * This method applies the Information Expert pattern by knowing how to extract relevant data.
     *
     * @param dataList The complete list containing all data.
     * @param <T> The type of data in the list (extends Number).
     * @return A new XYChart.Series populated with the last N data points.
     */
    public static <T extends Number> XYChart.Series<Number, Number> createSeriesFromLastNPoints(
            List<T> dataList) {
        int startIndex = calculateStartIndex(dataList.size());
        return createSeriesFromData(dataList, startIndex);
    }

    /**
     * Creates a chart series from the given data list starting from the specified index.
     * This helper method extracts data points for visualization with correct match numbering.
     *
     * @param dataList The list containing the data to be displayed.
     * @param startIndex The starting index for extracting data.
     * @param <T> The type of data in the list (extends Number).
     * @return A new XYChart.Series populated with the data.
     */
    private static <T extends Number> XYChart.Series<Number, Number> createSeriesFromData(
            List<T> dataList, int startIndex) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        // Create a sublist to ensure we only process the relevant data
        List<T> relevantData = dataList.subList(startIndex, dataList.size());

        for (int i = 0; i < relevantData.size(); i++) {
            // Use absolute match number (startIndex + i + 1) for X-axis
            int matchNumber = startIndex + i + 1;
            series.getData().add(new XYChart.Data<>(matchNumber, relevantData.get(i)));
        }

        return series;
    }
}
