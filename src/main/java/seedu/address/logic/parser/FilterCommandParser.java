package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHAMPION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RANK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCORE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FilterCommand.FilterPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Champion;
import seedu.address.model.person.Rank;
import seedu.address.model.person.Role;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns an FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ROLE, PREFIX_RANK, PREFIX_CHAMPION, PREFIX_SCORE);

        // Reject any extraneous preamble or invalid fields
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        // Reject prefixes with empty values
        if (hasEmptyPrefixValue(argMultimap)) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        FilterPersonDescriptor filterPersonDescriptor = new FilterPersonDescriptor();

        parseRanksForFilter(argMultimap.getAllValues(PREFIX_RANK))
                .ifPresent(filterPersonDescriptor::setRanks);

        parseRolesForFilter(argMultimap.getAllValues(PREFIX_ROLE))
                .ifPresent(filterPersonDescriptor::setRoles);

        parseChampionsForFilter(argMultimap.getAllValues(PREFIX_CHAMPION))
                .ifPresent(filterPersonDescriptor::setChampions);

        parseScoreForFilter(argMultimap.getAllValues(PREFIX_SCORE))
                .ifPresent(filterPersonDescriptor::setScoreThreshold);

        if (!filterPersonDescriptor.isAnyFieldFiltered()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        return new FilterCommand(filterPersonDescriptor);
    }

    /**
     * Returns true if any prefix has empty values (i.e., user typed "rk/" without a value)
     */
    private boolean hasEmptyPrefixValue(ArgumentMultimap argMultimap) {
        return Stream.of(PREFIX_RANK, PREFIX_ROLE, PREFIX_CHAMPION, PREFIX_SCORE)
                .anyMatch(prefix ->
                        argMultimap.getAllValues(prefix).stream().anyMatch(String::isEmpty)
                );
    }

    /**
     * Parses {@code Collection<String> ranks} into a {@code Set<Rank>} if {@code ranks} is non-empty.
     * If {@code ranks} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Rank>} containing zero ranks.
     */
    private Optional<Set<Rank>> parseRanksForFilter(Collection<String> ranks) throws ParseException {
        assert ranks != null;

        if (ranks.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> rankSet = ranks.size() == 1 && ranks.contains("") ? Collections.emptySet() : ranks;
        return Optional.of(ParserUtil.parseRanks(rankSet));
    }

    /**
     * Parses {@code Collection<String> roles} into a {@code Set<Role>} if {@code roles} is non-empty.
     * If {@code roles} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Role>} containing zero roles.
     */
    private Optional<Set<Role>> parseRolesForFilter(Collection<String> roles) throws ParseException {
        assert roles != null;

        if (roles.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> roleSet = roles.size() == 1 && roles.contains("") ? Collections.emptySet() : roles;
        return Optional.of(ParserUtil.parseRoles(roleSet));
    }

    /**
     * Parses {@code Collection<String> champions} into a {@code Set<Champion>} if {@code champions} is non-empty.
     * If {@code champions} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Champion>} containing zero champions.
     */
    private Optional<Set<Champion>> parseChampionsForFilter(Collection<String> champions) throws ParseException {
        assert champions != null;

        if (champions.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> championSet = champions.size() == 1 && champions.contains("")
                ? Collections.emptySet()
                : champions;
        return Optional.of(ParserUtil.parseChampions(championSet));
    }

    /**
     * Parses {@code Collection<String> scores} into an {@code Optional<Float>} for filtering.
     *
     * <p>Rules:</p>
     * <ul>
     *   <li>No score → returns {@code Optional.empty()}.</li>
     *   <li>Exactly one score → must be a valid positive float (> 0.0).</li>
     *   <li>More than one score → throws {@link ParseException}.</li>
     *   <li>Only 2 decimal places are allowed; more decimals → {@link ParseException}.</li>
     *   <li>Scores outside the valid float range → throws {@link ParseException}.</li>
     * </ul>
     */
    private Optional<Float> parseScoreForFilter(Collection<String> scores) throws ParseException {
        assert scores != null;

        if (scores.isEmpty()) {
            return Optional.empty();
        }

        if (scores.size() > 1) {
            throw new ParseException("You can only provide 1 score!");
        }

        String scoreString = scores.iterator().next().trim();

        if (scoreString.isEmpty()) {
            return Optional.empty();
        }

        // Check for more than 2 decimal places
        if (scoreString.contains(".")) {
            String[] parts = scoreString.split("\\.");
            if (parts.length > 1 && parts[1].length() > 2) {
                throw new ParseException("Score can have at most 2 decimal places.");
            }
        }

        final float parsedScore;
        try {
            parsedScore = Float.parseFloat(scoreString);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE
            ) + "\nScore must be a valid number.");
        }

        if (Float.isNaN(parsedScore) || Float.isInfinite(parsedScore)) {
            throw new ParseException(
                    "Score is out of range. Valid range: 0.0 < score ≤ 10.0"
            );
        }

        if (parsedScore <= 0.0F || parsedScore > 10.0F) {
            throw new ParseException("Score is out of range. Valid range: 0.0 < score ≤ 10.0");
        }

        return Optional.of(parsedScore);
    }
}
