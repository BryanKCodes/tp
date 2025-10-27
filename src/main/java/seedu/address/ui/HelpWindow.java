package seedu.address.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for the Help window. This version avoids javafx-web/WebView and opens
 * the packaged user guide in the default system viewer. Preference order:
 * 1) /docs/UserGuide.pdf, 2) /docs/UserGuide.html, 3) /docs/UserGuide.md, else online URL.
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL =
            "https://se-education.org/addressbook-level3/UserGuide.html";

    // Classpath locations inside resources (will be inside the JAR at runtime)
    private static final String CP_GUIDE_PDF = "/docs/UserGuide.pdf";
    private static final String CP_GUIDE_HTML = "/docs/UserGuide.html";
    private static final String CP_GUIDE_MD = "/docs/UserGuide.md";

    private static final String FXML = "HelpWindow.fxml";
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText("Open the User Guide to learn available commands and usage.");
    }

    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        getRoot().show();
        getRoot().centerOnScreen();
    }

    public boolean isShowing() {
        return getRoot().isShowing();
    }

    public void hide() {
        getRoot().hide();
    }

    public void focus() {
        getRoot().requestFocus();
    }

    /** Handles the “Open Local Guide” button in the FXML. */
    @FXML
    private void openLocalGuide() {
        // Try PDF -> HTML -> MD
        if (tryOpenClasspathFile(CP_GUIDE_PDF)) {
            return;
        }
        if (tryOpenClasspathFile(CP_GUIDE_HTML)) {
            return;
        }
        if (tryOpenClasspathFile(CP_GUIDE_MD)) {
            return;
        }

        // Fall back to online guide
        logger.warning("No local guide found on classpath, opening online guide.");
        openUri(URI.create(USERGUIDE_URL));
    }


    private boolean tryOpenClasspathFile(String classpathPath) {
        try {
            URL resource = HelpWindow.class.getResource(classpathPath);
            if (resource == null) {
                return false;
            }

            String suffix = fileSuffixFromClasspath(classpathPath); // e.g., ".pdf" or ".html"
            Path tmp = Files.createTempFile("userguide-", suffix);
            tmp.toFile().deleteOnExit();

            try (InputStream in = resource.openStream()) {
                Files.copy(in, tmp, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }

            openUri(tmp.toUri());
            helpMessage.setText("Opened local User Guide (" + classpathPath + ") in your default viewer.");
            return true;
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Failed to open local resource " + classpathPath, ex);
            return false;
        }
    }

    private String fileSuffixFromClasspath(String classpathPath) {
        int dot = classpathPath.lastIndexOf('.');
        return (dot >= 0) ? classpathPath.substring(dot) : ".html";
    }

    private void openUri(URI uri) {
        try {
            if (!Desktop.isDesktopSupported()) {
                throw new IOException("Desktop API not supported on this platform.");
            }
            Desktop.getDesktop().browse(uri);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to open URI: " + uri, e);
            helpMessage.setText("Could not open: " + uri + ". See logs for details.");
        }
    }
}
