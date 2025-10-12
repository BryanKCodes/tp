---
  layout: default.md
    title: "User Guide"
    pageNav: 3
---

# SummonersBook User Guide

SummonersBook is a **desktop app for esports players (League of Legends)**, optimized for a fast **Command Line
Interface (CLI)** while still providing clear visual feedback via a Graphical User Interface (GUI). If you can type
fast, SummonersBook helps you manage players and form balanced teams faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version
   prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest `summonersbook.jar` file from [here](https://github.com/AY2526S1-CS2103T-F08b-1/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar`
   command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will
   open the help window.<br>
   Some example commands you can try:

    * `add n/Faker rk/Grandmaster rl/Bottom c/Sivir` — Adds a new player with a specified rank, role and champion.
    * `find rl/Support` — Filters the player list based on the role "Support".
    * `group` — Auto-creates balanced 5-player teams from unassigned players.
    * `viewteam 1` — Shows full details of the 1st team.
    * `exit` — Exits the app.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME rk/RANK rl/ROLE c/CHAMPION`, `NAME` , `RANK`, `ROLE`, `CHAMPION` are parameters which can be used
  as `add n/Faker rk/Grandmaster rl/Bottom c/Sivir`.

* Items in square brackets are optional.<br>
  e.g., `find [n/NAME] [rl/ROLE] [rk/RANK] [c/CHAMPION]` can be used as `find rk/Gold`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g., for `add n/NAME rk/RANK rl/ROLE c/CHAMPION`, `add rk/RANK n/NAME rl/ROLE c/CHAMPION` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `exit`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* Indices refer to the **currently displayed** list (players or teams), starting from 1.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines
  as space characters surrounding line-breaks may be omitted when copied over to the application.
  </box>

## SummonersBook commands

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Adding a player: `add`

Adds a new player with mandatory details.

Format:
`add n/NAME rk/RANK rl/ROLE c/CHAMPION`

Examples:

* `add n/Faker rk/Grandmaster rl/Bottom c/Sivir`
* `add n/Imissher rk/Gold rl/Support c/Thresh`

### View a player's details : `view`

Displays full information for a specific player.

Format:
`view INDEX`

Example:
`view 1`

Failure output:
`The player index provided is invalid.`

### Deleting a player: `delete`

Removes a player permanently.

Format:
`delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Example:
`delete 3`

Success output:
`Deleted Player: <NAME>.`

Failure outputs (examples):

- `The player index provided is invalid.`
- `Cannot delete player. <NAME> is currently on team '<TEAM NAME>'.`
  (Remove from team before deleting.)

### Finding players : `find`

Filters the player list by one or more attributes. Matching is **case-insensitive**. Results show players that satisfy *
*all** provided filters (logical AND). Exact value matching is used for rank/role; name/champion use substring match.

Format (any order, at least one filter):
`find [n/NAME] [rl/ROLE] [rk/RANK] [c/CHAMPION]`

- Case-insensitive. faker matches Faker.
- Only the name is searched in this mode.
- Full-word matching (not substrings). Fa does not match Faker.
- AND search: the player’s name must contain all the given keywords (in any order).

Examples:

- `find rl/Bottom` — all Bottom players
- `find rk/Gold` — all Gold players
- `find rl/Mid rk/Diamond` — all Diamond Mid players
- `find c/Thresh` — players whose main champion name contains “Thresh”
- `find n/faker` — players whose name contains “faker”

### Auto-grouping players into teams: `group`

Forms as many balanced teams of five as possible from **unassigned** players.

Format:
`group`

### Disbanding a team or all teams : `ungroup`

Disbands one team (returns its players to the unassigned pool) or disbands all teams.

Format:
`ungroup TEAM_INDEX|all`

Examples:

- `ungroup 1` — disbands the 1st team
- `ungroup all` — disbands all teams

### Viewing a team’s details : `viewteam`

Shows roster and summary performance for a specific team.

Format:
`viewteam INDEX`

Example:
`viewteam 1`

### Exiting the program : `exit`

Closes the application.
Format: `exit`

### Saving the data

Data is saved automatically to disk after any command that changes data. No manual save is required.

## Editing the data file

SummonersBook stores data as JSON at `[JAR file location]/data/summonersbook.json`.
Advanced users can edit this file directly.

## other commands that AB3 has

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list.
  The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
  specifying any tags after it.

Examples:

* `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567`
  and `johndoe@example.com` respectively.
* `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating persons by name: `find`

Finds persons whose names contain ANY of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:

* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are
welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty
data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside
the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.

</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains
the data of your previous AddressBook home folder.

**Q:** Why can’t I delete a player who’s on a team?
**A:** Team rosters must always have 5 players. Remove the player from their team (via `editteam` in future versions or
`ungroup` and re-group) before deleting.

**Q:** How are teams named when using `group`?
**A:** Sequentially (`Team A`, `Team B`, …), skipping any existing names to avoid conflicts.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only
   the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the
   application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut
   `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to
   manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary - SummonersBook

 Action            | Format, Examples
-------------------|--------------------------------------------------
 **Help**          | `help`
 **Add player**    | `add n/NAME rk/RANK rl/ROLE c/CHAMPION`
 **View player**   | `view INDEX`
 **Delete player** | `delete INDEX`
 **Find players**  | `find [n/NAME] [rl/ROLE] [rk/RANK] [c/CHAMPION]`
 **Group teams**   | `group`
 **Ungroup**       | `ungroup TEAM_INDEX`  ·  `ungroup all`
 **View team**     | `viewteam INDEX`
 **Exit**          | `exit`

## Command summary - AB3

 Action     | Format, Examples
------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
 **Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
 **Clear**  | `clear`
 **Delete** | `delete INDEX`<br> e.g., `delete 3`
 **Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
 **Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
 **List**   | `list`
 **Help**   | `help`
