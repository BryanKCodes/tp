package seedu.address;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.Model;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;

public class MainAppTest {

    private static final Path TEST_DATA_FOLDER = Path.of("src", "test", "data", "MainAppTest");

    @TempDir
    public Path testFolder;

    @Test
    public void initModelManager_missingFile_loadsSampleData() throws Exception {
        // Create storage pointing to non-existent file
        Path addressBookPath = testFolder.resolve("nonexistent.json");
        Path userPrefsPath = testFolder.resolve("userprefs.json");

        Storage storage = new StorageManager(
                new JsonAddressBookStorage(addressBookPath),
                new JsonUserPrefsStorage(userPrefsPath)
        );

        ReadOnlyUserPrefs userPrefs = new UserPrefs();

        // Create a test MainApp instance to call initModelManager
        TestableMainApp mainApp = new TestableMainApp();
        Model model = mainApp.initModelManager(storage, userPrefs);

        // Should load sample data, not empty
        assertNotNull(model);
        assertNotNull(model.getAddressBook());
        // Sample data should have some persons
        assert model.getAddressBook().getPersonList().size() > 0;
    }

    @Test
    public void initModelManager_invalidJsonFormat_loadsEmptyAddressBook() throws Exception {
        // Use the test file with invalid JSON format
        Path addressBookPath = Path.of("src", "test", "data",
                "JsonAddressBookStorageTest", "notJsonFormatAddressBook.json");
        Path userPrefsPath = testFolder.resolve("userprefs.json");

        Storage storage = new StorageManager(
                new JsonAddressBookStorage(addressBookPath),
                new JsonUserPrefsStorage(userPrefsPath)
        );

        ReadOnlyUserPrefs userPrefs = new UserPrefs();

        TestableMainApp mainApp = new TestableMainApp();
        Model model = mainApp.initModelManager(storage, userPrefs);

        // Should load empty AddressBook due to DataLoadingException
        assertNotNull(model);
        assertEquals(0, model.getAddressBook().getPersonList().size());
        assertEquals(0, model.getAddressBook().getTeamList().size());
    }

    @Test
    public void initModelManager_invalidPersonData_loadsEmptyAddressBook() throws Exception {
        // Use the test file with invalid person name
        Path addressBookPath = Path.of("src", "test", "data",
                "JsonAddressBookStorageTest", "invalidPersonAddressBook.json");
        Path userPrefsPath = testFolder.resolve("userprefs.json");

        Storage storage = new StorageManager(
                new JsonAddressBookStorage(addressBookPath),
                new JsonUserPrefsStorage(userPrefsPath)
        );

        ReadOnlyUserPrefs userPrefs = new UserPrefs();

        TestableMainApp mainApp = new TestableMainApp();
        Model model = mainApp.initModelManager(storage, userPrefs);

        // Should load empty AddressBook due to invalid data
        assertNotNull(model);
        assertEquals(0, model.getAddressBook().getPersonList().size());
        assertEquals(0, model.getAddressBook().getTeamList().size());
    }

    @Test
    public void initModelManager_personInMultipleTeams_loadsEmptyAddressBook() throws Exception {
        // Use the test file with person in multiple teams (runtime exception)
        Path addressBookPath = Path.of("src", "test", "data",
                "JsonAddressBookStorageTest", "personInMultipleTeamsAddressBook.json");
        Path userPrefsPath = testFolder.resolve("userprefs.json");

        Storage storage = new StorageManager(
                new JsonAddressBookStorage(addressBookPath),
                new JsonUserPrefsStorage(userPrefsPath)
        );

        ReadOnlyUserPrefs userPrefs = new UserPrefs();

        TestableMainApp mainApp = new TestableMainApp();
        Model model = mainApp.initModelManager(storage, userPrefs);

        // Should load empty AddressBook due to PersonAlreadyInTeamException
        assertNotNull(model);
        assertEquals(0, model.getAddressBook().getPersonList().size());
        assertEquals(0, model.getAddressBook().getTeamList().size());
    }

    @Test
    public void initModelManager_teamWithDuplicateRoles_loadsEmptyAddressBook() throws Exception {
        // Use the test file with duplicate roles in team (runtime exception)
        Path addressBookPath = Path.of("src", "test", "data",
                "JsonAddressBookStorageTest", "teamWithDuplicateRolesAddressBook.json");
        Path userPrefsPath = testFolder.resolve("userprefs.json");

        Storage storage = new StorageManager(
                new JsonAddressBookStorage(addressBookPath),
                new JsonUserPrefsStorage(userPrefsPath)
        );

        ReadOnlyUserPrefs userPrefs = new UserPrefs();

        TestableMainApp mainApp = new TestableMainApp();
        Model model = mainApp.initModelManager(storage, userPrefs);

        // Should load empty AddressBook due to DuplicateRoleException
        assertNotNull(model);
        assertEquals(0, model.getAddressBook().getPersonList().size());
        assertEquals(0, model.getAddressBook().getTeamList().size());
    }

    @Test
    public void initModelManager_teamWithDuplicateChampions_loadsEmptyAddressBook() throws Exception {
        // Use the test file with duplicate champions in team (runtime exception)
        Path addressBookPath = Path.of("src", "test", "data",
                "JsonAddressBookStorageTest", "teamWithDuplicateChampionsAddressBook.json");
        Path userPrefsPath = testFolder.resolve("userprefs.json");

        Storage storage = new StorageManager(
                new JsonAddressBookStorage(addressBookPath),
                new JsonUserPrefsStorage(userPrefsPath)
        );

        ReadOnlyUserPrefs userPrefs = new UserPrefs();

        TestableMainApp mainApp = new TestableMainApp();
        Model model = mainApp.initModelManager(storage, userPrefs);

        // Should load empty AddressBook due to DuplicateChampionException
        assertNotNull(model);
        assertEquals(0, model.getAddressBook().getPersonList().size());
        assertEquals(0, model.getAddressBook().getTeamList().size());
    }

    @Test
    public void initModelManager_teamWithInvalidSize_loadsEmptyAddressBook() throws Exception {
        // Use the test file with team of wrong size (runtime exception)
        Path addressBookPath = Path.of("src", "test", "data",
                "JsonAddressBookStorageTest", "teamWithInvalidSizeAddressBook.json");
        Path userPrefsPath = testFolder.resolve("userprefs.json");

        Storage storage = new StorageManager(
                new JsonAddressBookStorage(addressBookPath),
                new JsonUserPrefsStorage(userPrefsPath)
        );

        ReadOnlyUserPrefs userPrefs = new UserPrefs();

        TestableMainApp mainApp = new TestableMainApp();
        Model model = mainApp.initModelManager(storage, userPrefs);

        // Should load empty AddressBook due to InvalidTeamSizeException
        assertNotNull(model);
        assertEquals(0, model.getAddressBook().getPersonList().size());
        assertEquals(0, model.getAddressBook().getTeamList().size());
    }

    @Test
    public void initModelManager_invalidRank_loadsEmptyAddressBook() throws Exception {
        // Use the test file with invalid rank
        Path addressBookPath = Path.of("src", "test", "data",
                "JsonAddressBookStorageTest", "invalidRankAddressBook.json");
        Path userPrefsPath = testFolder.resolve("userprefs.json");

        Storage storage = new StorageManager(
                new JsonAddressBookStorage(addressBookPath),
                new JsonUserPrefsStorage(userPrefsPath)
        );

        ReadOnlyUserPrefs userPrefs = new UserPrefs();

        TestableMainApp mainApp = new TestableMainApp();
        Model model = mainApp.initModelManager(storage, userPrefs);

        // Should load empty AddressBook due to invalid rank
        assertNotNull(model);
        assertEquals(0, model.getAddressBook().getPersonList().size());
        assertEquals(0, model.getAddressBook().getTeamList().size());
    }

    @Test
    public void initModelManager_invalidRole_loadsEmptyAddressBook() throws Exception {
        // Use the test file with invalid role
        Path addressBookPath = Path.of("src", "test", "data",
                "JsonAddressBookStorageTest", "invalidRoleAddressBook.json");
        Path userPrefsPath = testFolder.resolve("userprefs.json");

        Storage storage = new StorageManager(
                new JsonAddressBookStorage(addressBookPath),
                new JsonUserPrefsStorage(userPrefsPath)
        );

        ReadOnlyUserPrefs userPrefs = new UserPrefs();

        TestableMainApp mainApp = new TestableMainApp();
        Model model = mainApp.initModelManager(storage, userPrefs);

        // Should load empty AddressBook due to invalid role
        assertNotNull(model);
        assertEquals(0, model.getAddressBook().getPersonList().size());
        assertEquals(0, model.getAddressBook().getTeamList().size());
    }

    @Test
    public void initModelManager_invalidChampion_loadsEmptyAddressBook() throws Exception {
        // Use the test file with invalid champion
        Path addressBookPath = Path.of("src", "test", "data",
                "JsonAddressBookStorageTest", "invalidChampionAddressBook.json");
        Path userPrefsPath = testFolder.resolve("userprefs.json");

        Storage storage = new StorageManager(
                new JsonAddressBookStorage(addressBookPath),
                new JsonUserPrefsStorage(userPrefsPath)
        );

        ReadOnlyUserPrefs userPrefs = new UserPrefs();

        TestableMainApp mainApp = new TestableMainApp();
        Model model = mainApp.initModelManager(storage, userPrefs);

        // Should load empty AddressBook due to invalid champion
        assertNotNull(model);
        assertEquals(0, model.getAddressBook().getPersonList().size());
        assertEquals(0, model.getAddressBook().getTeamList().size());
    }

    @Test
    public void initModelManager_missingName_loadsEmptyAddressBook() throws Exception {
        // Use the test file with missing name
        Path addressBookPath = Path.of("src", "test", "data",
                "JsonAddressBookStorageTest", "missingNameAddressBook.json");
        Path userPrefsPath = testFolder.resolve("userprefs.json");

        Storage storage = new StorageManager(
                new JsonAddressBookStorage(addressBookPath),
                new JsonUserPrefsStorage(userPrefsPath)
        );

        ReadOnlyUserPrefs userPrefs = new UserPrefs();

        TestableMainApp mainApp = new TestableMainApp();
        Model model = mainApp.initModelManager(storage, userPrefs);

        // Should load empty AddressBook due to missing name
        assertNotNull(model);
        assertEquals(0, model.getAddressBook().getPersonList().size());
        assertEquals(0, model.getAddressBook().getTeamList().size());
    }

    @Test
    public void initModelManager_invalidStatsType_loadsEmptyAddressBook() throws Exception {
        // Use the test file with invalid stats type
        Path addressBookPath = Path.of("src", "test", "data",
                "JsonAddressBookStorageTest", "invalidStatsTypeAddressBook.json");
        Path userPrefsPath = testFolder.resolve("userprefs.json");

        Storage storage = new StorageManager(
                new JsonAddressBookStorage(addressBookPath),
                new JsonUserPrefsStorage(userPrefsPath)
        );

        ReadOnlyUserPrefs userPrefs = new UserPrefs();

        TestableMainApp mainApp = new TestableMainApp();
        Model model = mainApp.initModelManager(storage, userPrefs);

        // Should load empty AddressBook due to invalid stats type
        assertNotNull(model);
        assertEquals(0, model.getAddressBook().getPersonList().size());
        assertEquals(0, model.getAddressBook().getTeamList().size());
    }

    @Test
    public void initModelManager_invalidTag_loadsEmptyAddressBook() throws Exception {
        // Use the test file with invalid tag
        Path addressBookPath = Path.of("src", "test", "data",
                "JsonAddressBookStorageTest", "invalidTagAddressBook.json");
        Path userPrefsPath = testFolder.resolve("userprefs.json");

        Storage storage = new StorageManager(
                new JsonAddressBookStorage(addressBookPath),
                new JsonUserPrefsStorage(userPrefsPath)
        );

        ReadOnlyUserPrefs userPrefs = new UserPrefs();

        TestableMainApp mainApp = new TestableMainApp();
        Model model = mainApp.initModelManager(storage, userPrefs);

        // Should load empty AddressBook due to invalid tag
        assertNotNull(model);
        assertEquals(0, model.getAddressBook().getPersonList().size());
        assertEquals(0, model.getAddressBook().getTeamList().size());
    }

    /**
     * A testable version of MainApp that exposes initModelManager for testing.
     */
    private static class TestableMainApp extends MainApp {
        @Override
        public Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
            return super.initModelManager(storage, userPrefs);
        }
    }
}
