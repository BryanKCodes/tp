package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ImportCommandTest {

    @TempDir
    Path tempDir;

    @Test
    public void execute_validFile_importsAndReturnsSummary() throws Exception {
        Path csv = tempDir.resolve("players.csv");
        Files.write(csv, List.of(
                "Name,Role,Rank,Champion",
                "Alice,Top,Gold,Ahri"
        ));

        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        ImportCommand cmd = new ImportCommand(csv);
        CommandResult result = cmd.execute(model);

        assertTrue(result.getFeedbackToUser().contains("Imported 1 players"));
        assertTrue(model.getAddressBook().getPersonList().size() >= 1);
    }
}
