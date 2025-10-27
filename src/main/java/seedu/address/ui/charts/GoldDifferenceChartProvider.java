package seedu.address.ui.charts;

import java.util.List;
import seedu.address.model.person.Stats;

/**
 * Provides configuration for the Gold Difference chart.
 */
public class GoldDifferenceChartProvider extends BaseChartProvider {

    @Override
    public String getTitle() {
        return String.format("Gold Diff @15 Over Time (Latest %d)", getMaxDisplayedMatches());
    }

    @Override
    public String getYAxisLabel() {
        return "Gold Diff @15";
    }

    @Override
    public String getSeriesColor() {
        return "#FFD700"; // Gold
    }

    @Override
    protected List<? extends Number> getData(Stats stats) {
        return stats.getGoldDiffAt15();
    }

    @Override
    protected int getMaxDisplayedMatches() {
        return 10;
    }
}
