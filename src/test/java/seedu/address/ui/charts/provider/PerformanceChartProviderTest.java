package seedu.address.ui.charts.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.scene.chart.XYChart;
import seedu.address.model.person.Stats;

/**
 * Contains unit tests for PerformanceChartProvider.
 */
public class PerformanceChartProviderTest {

    private final PerformanceChartProvider provider = new PerformanceChartProvider();

    @Test
    public void getTitle_returnsCorrectFormat() {
        String title = provider.getTitle();
        assertEquals("Performance Score Over Time (Latest 10)", title);
    }

    @Test
    public void getYAxisLabel_returnsCorrectLabel() {
        String label = provider.getYAxisLabel();
        assertEquals("Performance Score", label);
    }

    @Test
    public void getStyleClass_returnsCorrectClass() {
        String styleClass = provider.getStyleClass();
        assertEquals("performance-chart", styleClass);
    }

    @Test
    public void getMaxDisplayedMatches_returnsTen() {
        // Access via createSeries to verify the template method uses it correctly
        Stats stats = new Stats()
                .addLatestStats("5.0", "100", "1.5")
                .addLatestStats("6.0", "200", "2.0")
                .addLatestStats("7.0", "300", "2.5");

        XYChart.Series<Number, Number> series = provider.createSeries(stats);
        assertEquals(3, series.getData().size());
    }

    @Test
    public void createSeries_withEmptyStats_returnsEmptySeries() {
        Stats emptyStats = new Stats();
        XYChart.Series<Number, Number> series = provider.createSeries(emptyStats);

        assertNotNull(series);
        assertEquals(0, series.getData().size());
    }

    @Test
    public void createSeries_withFewMatches_returnsAllMatches() {
        Stats stats = new Stats()
                .addLatestStats("5.0", "100", "1.5")
                .addLatestStats("6.0", "200", "2.0")
                .addLatestStats("7.0", "300", "2.5");

        XYChart.Series<Number, Number> series = provider.createSeries(stats);

        assertEquals(3, series.getData().size());
        // Match numbers should start from 1
        assertEquals(1, series.getData().get(0).getXValue());
        assertEquals(2, series.getData().get(1).getXValue());
        assertEquals(3, series.getData().get(2).getXValue());
    }

    @Test
    public void createSeries_withManyMatches_returnsLastTen() {
        Stats stats = new Stats();
        // Add 15 matches
        for (int i = 0; i < 15; i++) {
            stats = stats.addLatestStats("5.0", "100", "1.5");
        }

        XYChart.Series<Number, Number> series = provider.createSeries(stats);

        assertEquals(10, series.getData().size());
        // Should start from match 6 (index 5) to match 15 (index 14)
        assertEquals(6, series.getData().get(0).getXValue());
        assertEquals(15, series.getData().get(9).getXValue());
    }

    @Test
    public void createSeries_dataPointsHaveCorrectValues() {
        Stats stats = new Stats()
                .addLatestStats("5.0", "100", "1.5")
                .addLatestStats("6.0", "200", "2.0");

        XYChart.Series<Number, Number> series = provider.createSeries(stats);

        List<Double> scores = stats.getScores();
        assertEquals(scores.get(0), series.getData().get(0).getYValue());
        assertEquals(scores.get(1), series.getData().get(1).getYValue());
    }
}
