---
layout: default.md
title: "SummonersBook Developer Guide"
pageNav: 3
---

# SummonersBook Developer Guide

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

SummonersBook was developed based on [AddressBook Level 3 (AB3)](https://github.com/se-edu/addressbook-level3) by the SE-EDU initiative.
Certain sections of the code, documentation structure, and testing conventions were reused or adapted from the original AB3 project.

It makes use of the following open-source libraries:

- [JavaFX](https://openjfx.io/) (v17.0.7) — for building the graphical user interface
- [Jackson Databind](https://github.com/FasterXML/jackson-databind) (v2.7.0) — for JSON data serialization and deserialization
- [Jackson Datatype JSR310](https://github.com/FasterXML/jackson-modules-java8) (v2.7.4) — for Java 8 date and time (JSR-310) support in JSON
- [JUnit 5 (Jupiter)](https://junit.org/junit5/) (v5.4.0) — for unit testing

We thank the original AB3 contributors and the authors of the above libraries for making this project possible.

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [
`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [
`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in
charge of the app launch and shut down.

* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues
the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API
  `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using
the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component
through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the
implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [
`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`,
`StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures
the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that
are in the `src/main/resources/view` folder. For example, the layout of the [
`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java)
is specified in [
`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [
`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API
call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of
PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates
   a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which
   is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take
   several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:

* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a
  placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse
  the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a
  `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser`
  interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API** : [
`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which
  is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to
  this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a
  `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they
  should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which
`Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person`
needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>

### Storage component

**API** : [
`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,

* can save both address book data and user preference data in JSON format, and read them back into corresponding
  objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only
  the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects
  that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Filter Feature

#### Implementation

The filter feature is implemented through the `FilterCommand` and `FilterPredicate` classes.  
It allows users to display only the persons whose attributes match the given criteria — such as name, role, rank, or champion.

When the user executes a command such as:

`filter rk/Gold rl/Mid`

the app filters the list of persons based on the provided criteria.  
In this example, the result will include all persons whose **rank** is Gold **and** whose **role** is Mid.

This functionality is supported by three key components:

- **`FilterCommand`** — represents the command that performs filtering.
- **`FilterPredicate`** — encapsulates the logical conditions for filtering.
- **`Model#updateFilteredPersonList(Predicate<Person>)`** — applies the predicate to the main person list, updating the UI display.

Each field type (e.g. rank, role, champion) within the same category uses **OR** logic:
> Example: `rk/Gold rk/Silver` → persons with rank Gold **or** Silver.

Across different field types, conditions are combined using **AND** logic:
> Example: `rk/Gold rk/Silver rl/Mid` → persons who are (Gold **or** Silver) **and** play Mid.

---

#### Example Usage

**Step 1.**  
When the app starts, all persons are displayed.  
No filtering has been applied yet.

---

**Step 2.**  
The user executes `filter rk/Gold`.  
The `FilterCommandParser` creates a `FilterCommand` containing a `FilterPredicate` that checks each person’s rank.  
`Model#updateFilteredPersonList(predicate)` is called, and the UI updates to show only matching entries.

---

**Step 3.**  
The user then runs `filter rk/Gold rl/Mid`.  
Now, the displayed list includes only persons whose rank is Gold **and** whose role is Mid.

---

<box type="info" seamless>

**Note:**  
If no valid parameters are provided, all persons are shown.  
The `list` command can also be used to reset the view and display everyone.

</box>

---

#### Sequence of Operation

The following sequence diagram illustrates how a filter command flows through the app:

<puml src="diagrams/FilterCommandSequenceDiagram.puml" alt="FilterCommandSequenceDiagram" />

---

#### Design Considerations

**Aspect: How filtering is executed**

- **Alternative 1 (current implementation):**  
  Apply filtering by setting a predicate using `Model#updateFilteredPersonList(predicate)`.
    - *Pros:* Simple and efficient. Leverages JavaFX’s built-in `FilteredList`.
    - *Cons:* Each new filter replaces the previous one.

- **Alternative 2:**  
  Maintain a list of active filters that are combined dynamically.
    - *Pros:* Enables cumulative filtering (e.g. filter by role, then by rank later).
    - *Cons:* Adds complexity and state management overhead.


### Auto-Grouping Feature

#### Implementation

The auto-grouping feature is implemented through the `GroupCommand` and `TeamMatcher` classes.  
It automatically creates balanced teams from all unassigned persons, taking into account a person's **roles, ranks, and champions**.

When the user executes:

`group`

the application attempts to form as many full teams as possible while respecting role requirements (Top, Jungle, Mid, ADC, Support) and avoiding duplicate champions within the same team.

This functionality is supported by three key components:

- **`GroupCommand`** — represents the command that performs auto-grouping.
- **`TeamMatcher`** — contains the algorithm for forming teams from unassigned persons.
- **`Model#addTeam(Team)`** — adds newly formed teams to the model and updates the UI.

---

#### Example Usage

**Step 1.**  
The user has a list of unassigned persons with roles and ranks.  
The application currently shows all persons without any teams assigned.


---

**Step 2.**  
The user executes the `group` command.  
The `GroupCommand` fetches all unassigned persons via `Model#getUnassignedPersonList()`.  
`TeamMatcher#matchTeams()` is called to form balanced teams.


---

**Step 3.**  
Teams are successfully formed according to role, rank, and champion constraints.  
`Model#addTeam(team)` is called for each team, updating the application state and UI.


---

<box type="info" seamless>

**Note:**  
If there are insufficient persons to form a full team (i.e., missing a required role), the command will notify the user with:

`No teams could be formed. Ensure there is at least one unassigned person for each role (Top, Jungle, Mid, ADC, Support).`

Any leftover unassigned persons remain in the pool and can be used in future auto-grouping operations.

</box>

---

#### Design Considerations

- **Current implementation:**  
  Uses `TeamMatcher` to automatically form teams from the current pool of unassigned persons.
    - *Pros:* Ensures balanced teams with no duplicate champions and respects role requirements.
    - *Cons:* Leftover persons who do not complete a full team remain unassigned.

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* gaming coaches or team managers who need to manage a significant number of people
* prefer lightweight desktop apps over complex web platforms
* can type fast and are comfortable with CLI-style interactions
* want quick ways to form balanced teams for training or mock matches
* are reasonably comfortable using simple technical tools
* have many people of varying skill levels and roles to balance

**Value proposition**: manage people and create balanced teams faster and more efficiently than a typical spreadsheet
or mouse/GUI-driven app.

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a … | I want to …                                 | So that I can…                                  |
|----------|---------|----------------------------------------------|---------------------------------------------------|
| `* * *`  | coach   | add new people with their in-game names      | track and manage them in the system               |
| `* * *`  | coach   | update a person’s details                    | always have accurate and current information      |
| `* * *`  | coach   | record each person’s primary roles           | assign them to suitable teams                     |
| `* * *`  | coach   | record each person’s preferred champions     | avoid role duplication and build effective teams  |
| `* * *`  | coach   | filter people by role, rank, or score rating | quickly find suitable players for forming teams   |
| `* * *`  | coach   | create practice teams of 5 people            | simulate real match conditions                    |
| `* *`    | coach   | balance teams automatically by rank          | ensure matches are fair and competitive           |
| `* *`    | coach   | see role distribution in each team           | avoid having duplicate roles in the same lineup   |
| `* *`    | coach   | see a list of all created teams in a sidebar | quickly view teams                     |

### Use cases

(For all use cases below, the **System** is the `SummonersBook` and the **Actor** is the `user`, unless specified
otherwise)

---

### Use case: Add a person

**MSS**

1. User requests to add a person by providing name, rank, role, and champion.
2. SummonersBook creates the person entry and stores it.
3. SummonersBook confirms that the person has been added.

**Extensions**

- 2a. Missing or invalid fields.
    - 2a1. SummonersBook shows an error message.
    - Use case ends.

---

### Use case: View a person

**MSS**

1. User requests to view a person by specifying the index.
2. SummonersBook displays the person’s details.

**Extensions**

- 2a. The given index is invalid.
    - 2a1. SummonersBook shows an error message.
    - Use case ends.

---

### Use case: Delete a person

**MSS**

1. User requests to list people.
2. SummonersBook shows a list of people.
3. User requests to delete a specific person by index.
4. SummonersBook deletes the person.

**Extensions**

- 2a. The list is empty.
    - Use case ends.

- 3a. The given index is invalid.
    - 3a1. SummonersBook shows an error message.
    - Use case resumes at step 2.

---

### Use case: Find people

**MSS**

1. User requests to find people by specifying search criteria (role, rank, etc.).
2. SummonersBook displays all people matching the criteria.

**Extensions**

- 2a. No people match the criteria.
    - 2a1. SummonersBook shows “no people found.”
    - Use case ends.

---

### Use case: Auto-group people (create teams)

**MSS**

1. User requests to group people into balanced teams.
2. SummonersBook creates the teams automatically.
3. SummonersBook shows the newly formed teams.

**Extensions**

- 2a. Insufficient number of people to form teams.
    - 2a1. SummonersBook shows an error message.
    - Use case ends.

---

### Use case: Ungroup teams

**MSS**

1. User requests to ungroup either a specific team or all teams.
2. SummonersBook disbands the requested team(s).
3. SummonersBook confirms that the team(s) have been ungrouped.

**Extensions**

- 1a. The given index is invalid.
    - 1a1. SummonersBook shows an error message.
    - Use case ends.

---

### Use case: View a team

**MSS**

1. User requests to view a team by specifying its index.
2. SummonersBook displays the people in the team.

**Extensions**

- 1a. The given index is invalid.
    - 1a1. SummonersBook shows an error message.
    - Use case ends.

---

### Use case: Filter people

**MSS**

1. User requests to filter the list of people based on their role, rank, champion, average score.
2. SummonersBook displays the people that match the criteria.

**Extension**

- 1a. No flags are given (no role, rank, champion, score)
  - 1a1. SummonersBook shows an error message.
  - Use case ends.
- 1b. Flags are given but no values are given
  - 1b1. Show the full list of people.
  - Use case ends
- 1c. Invalid value for role or rank or champion or score
  - 1c1. SummonersBook shows an error message.
  - Use case ends.

---

### Use case: Add performance values

**MSS**

1. User requests to list people.
2. SummonersBook shows a list of people.
3. User requests to add a set of performance values (cpm, gd15, kda) to a specific person's stats by index.
4. SummonersBook update the person's stats.

**Extension**


- 3a. The given index is invalid.
    - 3a1. SummonersBook shows an error message.
    - Use case ends.
- 3b. Not all flags are given
    - 3b1. SummonersBook shows an error message.
    - Use case ends
- 3b. All flags are given but no enough values are given
    - 3b1. SummonersBook shows an error message.
    - Use case ends
- 3c. Invalid value for cpm or gd15 or kda
    - 1c1. SummonersBook shows an error message.
    - Use case ends.

---

### Use case: Delete the latest performance values

**MSS**

1. User requests to list people.
2. SummonersBook shows a list of people.
3. User requests to delete the latest set of performance values (cpm, gd15, kda) to a specific person's stats by index.
4. SummonersBook update the person's stats.

**Extension**

- 3a. The given index is invalid.
    - 3a1. SummonersBook shows an error message.
    - Use case ends.

---

### Use case: Help

**MSS**

1. User requests help.
2. SummonersBook displays a list of available commands and usage examples.

---

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2. Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be
   able to accomplish most of the tasks faster using commands than using the mouse.
4. Data entered by the user should be **saved automatically** and persist between sessions.
5. The system should handle invalid input gracefully by showing an error message without crashing.
6. System should be secure against unintended file modifications (i.e., only application data files are read/written).

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder

    1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be
       optimum.

1. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases … }_

### Deleting a person

1. Deleting a person while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    1. Test case: `delete 1`<br>
       Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message.
       Timestamp in the status bar is updated.

    1. Test case: `delete 0`<br>
       Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

    1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

1. { more test cases … }

### Saving data

1. Dealing with missing/corrupted data files

    1. {explain how to simulate a missing/corrupted file, and the expected behavior}_

1. { more test cases … }
