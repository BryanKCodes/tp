package seedu.address.ui.charts.provider;

import java.util.List;

import seedu.address.model.person.Stats;

/**
 * Provides configuration for the Performance Score chart.
 */
public class PerformanceChartProvider extends BaseChartProvider {

    @Override
    public String getTitle() {
        return String.format("Performance Score Over Time (Latest %d)", getMaxDisplayedMatches());
    }

    @Override
    public String getYAxisLabel() {
        return "Performance Score";
    }

    @Override
    public String getStyleClass() {
        return "performance-chart";
    }

    @Override
    protected List<? extends Number> getData(Stats stats) {
        return stats.getScores();
    }

    @Override
    protected int getMaxDisplayedMatches() {
        return 10;
    }
}
