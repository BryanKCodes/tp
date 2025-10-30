package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.exceptions.CommandException;
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

        String feedback = result.getFeedbackToUser();
        assertTrue(feedback.contains("Imported 1"),
                "Expected 'Imported 1' but got: " + feedback);
        assertTrue(feedback.contains("0 duplicate"),
                "Expected '0 duplicate' but got: " + feedback);
        assertTrue(feedback.contains("0 invalid"),
                "Expected '0 invalid' but got: " + feedback);
        assertEquals(1, model.getAddressBook().getPersonList().size());
    }

    @Test
    public void execute_players_success() throws Exception {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        Path out = tempDir.resolve("players.csv");
        CommandResult r = new ExportCommand(ExportCommand.Target.PLAYERS, out).execute(model);
        assertTrue(Files.exists(out));
        assertTrue(r.getFeedbackToUser().contains("Exported player data"));
    }

    @Test
    public void execute_fileNotFound_throwsCommandException() {
        Path nonExistent = tempDir.resolve("does_not_exist.csv");
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        ImportCommand cmd = new ImportCommand(nonExistent);

        CommandException thrown = org.junit.jupiter.api.Assertions.assertThrows(
                CommandException.class, () -> cmd.execute(model)
        );
        assertTrue(thrown.getMessage().contains("file not found"),
                "Expected message to contain 'file not found' but got: " + thrown.getMessage());
    }

    @Test
    public void execute_duplicatePlayers_countsDuplicates() throws Exception {
        Path csv = tempDir.resolve("duplicates.csv");
        Files.write(csv, List.of(
                "Name,Role,Rank,Champion",
                "Alice,Top,Gold,Ahri",
                "Alice,Top,Gold,Ahri"
        ));

        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        ImportCommand cmd = new ImportCommand(csv);
        CommandResult result = cmd.execute(model);

        String feedback = result.getFeedbackToUser();
        assertTrue(feedback.contains("Imported 1"));
        assertTrue(feedback.contains("1 duplicate"));
        assertEquals(1, model.getAddressBook().getPersonList().size());
    }

    @Test
    public void execute_invalidRows_showsSampleErrors() throws Exception {
        Path csv = tempDir.resolve("invalid.csv");
        Files.write(csv, List.of(
                "Name,Role,Rank,Champion",
                "Alice,Top,InvalidRank,Ahri",
                "Bob,Mid,Gold,Yasuo"
        ));

        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        ImportCommand cmd = new ImportCommand(csv);
        CommandResult result = cmd.execute(model);

        String feedback = result.getFeedbackToUser();
        assertTrue(feedback.contains("Imported 1"));
        assertTrue(feedback.contains("1 invalid"));
        assertTrue(feedback.contains("Examples of invalid rows") || feedback.contains("line"));
    }

    @Test
    public void execute_emptyFile_throwsCommandException() throws Exception {
        Path empty = tempDir.resolve("empty.csv");
        Files.write(empty, List.of());

        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        ImportCommand cmd = new ImportCommand(empty);

        assertThrows(CommandException.class, () -> cmd.execute(model));
    }

    @Test
    public void execute_playersExport_success() throws Exception {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        Path out = tempDir.resolve("players.csv");
        CommandResult r = new ExportCommand(ExportCommand.Target.PLAYERS, out).execute(model);

        assertTrue(Files.exists(out));
        assertTrue(r.getFeedbackToUser().contains("Exported player data"));
    }

    @Test
    public void execute_teamsExport_success() throws Exception {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        Path out = tempDir.resolve("teams.csv");
        CommandResult r = new ExportCommand(ExportCommand.Target.TEAMS, out).execute(model);

        assertTrue(Files.exists(out));
        assertTrue(r.getFeedbackToUser().contains("Exported team data"));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Path path = tempDir.resolve("test.csv");
        ImportCommand cmd = new ImportCommand(path);
        assertTrue(cmd.equals(cmd));
    }

    @Test
    public void equals_differentPath_returnsFalse() {
        ImportCommand cmd1 = new ImportCommand(tempDir.resolve("a.csv"));
        ImportCommand cmd2 = new ImportCommand(tempDir.resolve("b.csv"));
        assertTrue(!cmd1.equals(cmd2));
    }

    @Test
    public void equals_samePath_returnsTrue() {
        Path path = tempDir.resolve("test.csv");
        ImportCommand cmd1 = new ImportCommand(path);
        ImportCommand cmd2 = new ImportCommand(path);
        assertTrue(cmd1.equals(cmd2));
    }

    @Test
    public void execute_teams_success() throws Exception {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        Path out = tempDir.resolve("teams.csv");
        CommandResult r = new ExportCommand(ExportCommand.Target.TEAMS, out).execute(model);
        assertTrue(Files.exists(out));
        assertTrue(r.getFeedbackToUser().contains("Exported team data"));
    }

    @Test
    public void execute_ioError_wrappedAsCommandException() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        // Pass a directory as "file" -> Files.write will throw, exercising catch block
        Command cmd = new ExportCommand(ExportCommand.Target.PLAYERS, tempDir);
        assertThrows(CommandException.class, () -> cmd.execute(model));
    }

}
