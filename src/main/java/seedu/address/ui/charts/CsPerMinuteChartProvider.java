package seedu.address.ui.charts;

import java.util.List;
import seedu.address.model.person.Stats;

/**
 * Provides configuration for the CS per Minute chart.
 */
public class CsPerMinuteChartProvider extends BaseChartProvider {

    @Override
    public String getTitle() {
        return String.format("CS per Minute Over Time (Latest %d)", getMaxDisplayedMatches());
    }

    @Override
    public String getYAxisLabel() {
        return "CS per Minute";
    }

    @Override
    public String getSeriesColor() {
        return "#2196F3"; // Material Blue
    }

    @Override
    protected List<? extends Number> getData(Stats stats) {
        return stats.getCsPerMinute();
    }

    @Override
    protected int getMaxDisplayedMatches() {
        return 10;
    }
}
