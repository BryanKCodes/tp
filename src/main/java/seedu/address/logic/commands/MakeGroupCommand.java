package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;
import seedu.address.model.team.exceptions.DuplicateChampionException;
import seedu.address.model.team.exceptions.DuplicateRoleException;
import seedu.address.model.team.exceptions.InvalidTeamSizeException;

/**
 * Creates a team consisting of 5 existing players, manually provided by the user
 */
public class MakeGroupCommand extends Command {

    public static final String COMMAND_WORD = "makeGroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a team of 5 players. "
            + "All players must already exist in SummonersBook.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME1 "
            + PREFIX_NAME + "NAME2 "
            + PREFIX_NAME + "NAME3 "
            + PREFIX_NAME + "NAME4 "
            + PREFIX_NAME + "NAME5\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Alice "
            + PREFIX_NAME + "Bob "
            + PREFIX_NAME + "Cathy "
            + PREFIX_NAME + "Derek "
            + PREFIX_NAME + "Ella";

    public static final String MESSAGE_SUCCESS = "New team created: %1$s";
    public static final String MESSAGE_INSUFFICIENT_PLAYERS = "Exactly 5 player names must be provided.";
    public static final String MESSAGE_DUPLICATE_NAMES = "Duplicate player names found in the input.";
    public static final String MESSAGE_PLAYER_NOT_FOUND = "Player '%1$s' does not exist in SummonersBook.";
    public static final String MESSAGE_REUSED_PLAYERS = "Some players are already in other teams.";

    private final List<Name> playerNames;

    /**
     * Creates a MakeGroupCommand to create a team with the specified players.
     */
    public MakeGroupCommand(List<Name> playerNames) {
        requireNonNull(playerNames);
        this.playerNames = playerNames;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // === 1. Check number of players ===
        if (playerNames.size() != 5) {
            throw new CommandException(MESSAGE_INSUFFICIENT_PLAYERS);
        }

        // === 2. Check for duplicates in provided names ===
        Set<Name> uniqueNames = new HashSet<>(playerNames);
        if (uniqueNames.size() < playerNames.size()) {
            throw new CommandException(MESSAGE_DUPLICATE_NAMES);
        }

        // === 3. Fetch and validate all players ===
        Set<Person> teamMembers = new HashSet<>();
        for (Name name : playerNames) {
            Optional<Person> personOpt = model.findPersonByName(name);
            if (personOpt.isEmpty()) {
                throw new CommandException(String.format(MESSAGE_PLAYER_NOT_FOUND, name.fullName));
            }
            if (model.isPersonInAnyTeam(personOpt.get())) {
                throw new CommandException(String.format(MESSAGE_REUSED_PLAYERS, name.fullName));
            }
            teamMembers.add(personOpt.get());
        }

        // Construct and check for duplicates
        Team newTeam;
        try {
            newTeam = new Team(new ArrayList<>(teamMembers)); // Team handles role/rank validation
        } catch (InvalidTeamSizeException | DuplicateRoleException | DuplicateChampionException e) {
            throw new CommandException(e.getMessage());
        }

        model.addTeam(newTeam);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(newTeam)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof MakeGroupCommand
                && playerNames.equals(((MakeGroupCommand) other).playerNames));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("playerNames", playerNames)
                .toString();
    }
}
