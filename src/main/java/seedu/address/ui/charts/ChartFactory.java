package seedu.address.ui.charts;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import seedu.address.model.person.Person;
import seedu.address.model.person.Stats;

/**
 * Factory for creating chart UI components.
 * Centralizes chart configuration to maintain the Open/Closed Principle.
 */
public class ChartFactory {

    /**
     * List of chart providers.
     * To add a new chart, add its provider to this list.
     * To remove a chart, remove it from this list.
     * To reorder charts, reorder them in this list.
     */
    private final List<ChartProvider> chartProviders = List.of(
        new PerformanceChartProvider(),
        new CsPerMinuteChartProvider(),
        new KdaChartProvider(),
        new GoldDifferenceChartProvider()
    );

    /**
     * Creates all chart components for the given person.
     *
     * @param person The person whose stats will be visualized.
     * @return A list of chart UI components.
     */
    public List<Node> createAllChartComponents(Person person) {
        Stats stats = person.getStats();
        return chartProviders.stream()
                .map(provider -> createSingleChartComponent(provider, stats))
                .collect(Collectors.toList());
    }

    /**
     * Creates a single chart component.
     *
     * @param provider The chart provider supplying data and configuration.
     * @param stats The stats data to visualize.
     * @return The chart component.
     */
    private Node createSingleChartComponent(ChartProvider provider, Stats stats) {
        StatsChart chart = new StatsChart();

        String title = provider.getTitle();
        String yAxisLabel = provider.getYAxisLabel();
        String styleClass = provider.getStyleClass();
        XYChart.Series<Number, Number> series = provider.createSeries(stats);

        chart.updateChart(title, yAxisLabel, styleClass, series);
        return chart.getRoot();
    }
}
