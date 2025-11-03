---
layout: default
title: "Justin Wong's Project Portfolio Page"
---

### Project: SummonersBook

**SummonersBook** is a desktop application designed for League of Legends esports coaches and team managers to manage their player roster and form balanced 5v5 teams. The application features both a CLI and GUI (JavaFX), allowing fast keyboard-driven workflows for coaches who type quickly.

Below are my contributions to the project:

---

### New Features

* **Auto-Grouping Feature (`group` command)**
  * **What it does**: Automatically forms rank-ordered teams from unassigned players with one player per role and no duplicate champions.
  * **Justification**: Reduces team formation time from 30-45 minutes to instant.
  * **Highlights**: Implemented role-based matching algorithm that sorts by rank within each role and handles champion conflicts gracefully.

* **View Player Details Feature (`view` command)**
  * **What it does**: Opens a modal window displaying player statistics with JavaFX charts.
  * **Justification**: Provides quick access to player performance trends for informed decisions.
  * **Highlights**: Implemented dynamic charts showing last 10 matches with `CommandResult` factory methods for modal triggers.

* **Ungrouping Feature (`ungroup` command)**
  * **What it does**: Disbands teams by index or all teams at once with `ungroup all`.
  * **Justification**: Provides flexibility to reset team compositions when experimenting with strategies.
  * **Highlights**: Implemented with defensive copying to prevent concurrent modification issues.

---

### Code Contributions
* [RepoSense Link](https://nus-cs2103-ay2526s1.github.io/tp-dashboard/?search=fatkidddd&breakdown=true)

---

### Enhancements to Existing Features

* **Improved Team UI Display**: Fixed team panel display and ensured consistent formatting.
* **Enhanced Code Quality**: Added comprehensive test coverage and assertion guards for group-related commands.
* **Fixed Error Messages**: Implemented detailed exceptions listing specific missing roles and conflicting players.
* **Improved Manual Grouping**: Fixed runtime errors and enhanced validation.

---

### Documentation

* **User Guide**:
  * Documented `group`, `ungroup`, and `view` commands with examples
  * Wrote "Understanding Rank-Ordered Teams" section explaining the algorithm
  * Added comparison table and tips for maximizing teams formed
  * Created coaching workflows (Preparing for Scrimmage, Finding Substitutes, Post-Match Tracking)

* **Developer Guide**:
  * Wrote implementation section for Auto-Grouping with algorithm breakdown and design considerations
  * Documented View and Ungroup features with architecture patterns
  * Created UML sequence diagrams for all three commands
  * Updated Use Case 05 with accurate error handling paths
  * Wrote manual testing instructions in Appendix

---

### Project Management

* Created and tracked issues for auto-grouping, view command, and documentation
* Reviewed PRs for UI enhancements and command implementations
* Ensured consistent naming conventions across features

---

### Community

* Collaborated on integrating auto-grouping with manual team creation
* Provided code reviews focusing on quality, test coverage, and SOLID principles
* Coordinated documentation consistency between UG and DG
