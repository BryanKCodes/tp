package seedu.address.ui.charts.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Contains unit tests for KdaChartProvider.
 */
public class KdaChartProviderTest {

    private final KdaChartProvider provider = new KdaChartProvider();

    @Test
    public void getTitle_returnsCorrectFormat() {
        String title = provider.getTitle();
        assertEquals("KDA Over Time (Latest 10)", title);
    }

    @Test
    public void getYAxisLabel_returnsCorrectLabel() {
        String label = provider.getYAxisLabel();
        assertEquals("KDA", label);
    }

    @Test
    public void getStyleClass_returnsCorrectClass() {
        String styleClass = provider.getStyleClass();
        assertEquals("kda-chart", styleClass);
    }
}
