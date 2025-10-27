package seedu.address.ui.charts.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Contains unit tests for CsPerMinuteChartProvider.
 */
public class CsPerMinuteChartProviderTest {

    private final CsPerMinuteChartProvider provider = new CsPerMinuteChartProvider();

    @Test
    public void getTitle_returnsCorrectFormat() {
        String title = provider.getTitle();
        assertEquals("CS per Minute Over Time (Latest 10)", title);
    }

    @Test
    public void getYAxisLabel_returnsCorrectLabel() {
        String label = provider.getYAxisLabel();
        assertEquals("CS per Minute", label);
    }

    @Test
    public void getStyleClass_returnsCorrectClass() {
        String styleClass = provider.getStyleClass();
        assertEquals("cs-chart", styleClass);
    }
}
