package seedu.address.ui.charts.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Contains unit tests for GoldDifferenceChartProvider.
 */
public class GoldDifferenceChartProviderTest {

    private final GoldDifferenceChartProvider provider = new GoldDifferenceChartProvider();

    @Test
    public void getTitle_returnsCorrectFormat() {
        String title = provider.getTitle();
        assertEquals("Gold Diff @15 Over Time (Latest 10)", title);
    }

    @Test
    public void getYAxisLabel_returnsCorrectLabel() {
        String label = provider.getYAxisLabel();
        assertEquals("Gold Diff @15", label);
    }

    @Test
    public void getStyleClass_returnsCorrectClass() {
        String styleClass = provider.getStyleClass();
        assertEquals("gold-diff-chart", styleClass);
    }
}
