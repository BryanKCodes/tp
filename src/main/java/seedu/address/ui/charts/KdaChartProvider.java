package seedu.address.ui.charts;

import java.util.List;

import seedu.address.model.person.Stats;

/**
 * Provides configuration for the KDA chart.
 */
public class KdaChartProvider extends BaseChartProvider {

    @Override
    public String getTitle() {
        return String.format("KDA Over Time (Latest %d)", getMaxDisplayedMatches());
    }

    @Override
    public String getYAxisLabel() {
        return "KDA";
    }

    @Override
    public String getStyleClass() {
        return "kda-chart";
    }

    @Override
    protected List<? extends Number> getData(Stats stats) {
        return stats.getKdaScores();
    }

    @Override
    protected int getMaxDisplayedMatches() {
        return 10;
    }
}
