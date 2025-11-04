---
layout: default
title: "Duong's Project Portfolio Page"
---

### Project: SummonersBook

**SummonersBook** is a desktop address book application used by gaming coaches
to manage team members and gamers, and to create balanced teams for training and mock matches.
It is written in Java and has both a CLI and GUI (JavaFX).

Below are my contributions to the project:

---

### New Features

* **Player performance tracking (`addStats`, `deleteStats`)**
    * **What it does**:
      * `addStats`: Appends a new match record (CPM, GD15, KDA)
      and recalculates the player’s average performance score.
      * `deleteStats`: Removes the most recent record with clear error handling for empty histories.
    * **Justification**: Enables performance tracking inside the roster
      eliminating the need for external spreadsheets and making trend analysis immediate.
    * **Highlights**:Immutable `Stats` model (updates return a new object), strict regex + range validation,
      and comprehensive unit/integration tests (model, command, parser).

* **Filter feature (`filter`) (by rank, role, champion)**
    * **What it does**: Filters by rank, role, champion with clear AND/OR semantics.
    * **Justification**: Quickly narrows candidates for specific roles and power levels.
    * **Highlights**: Predicate composition, robust parser behavior, and UG/DG updates with examples and sequence diagrams.

---

### Code Contributions

* [RepoSense link](https://nus-cs2103-ay2526s1.github.io/tp-dashboard/?search=tuanduong18&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-09-19T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=tuanduong18&tabRepo=AY2526S1-CS2103T-F08b-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

---

### Project Management

* Created and tracked issues for filter, addStats, deleteStats command, and documentation
* Reviewed PRs for feature enhancements and command implementations
* Ensured consistent naming conventions across features

---

### Enhancements to Existing Features

* **Update sample data**: Improve realism and coverage so that manual testing, feature demos, and screenshots better
reflect expected real-world usage.

---

### Documentation

* **User Guide**:
    * Documented `addStats` and `deleteStats` commands with examples

- **Developer Guide**
    - Wrote **Implementation** sections for `addStats`, `deleteStats`, `filter`:
    - Sequence diagrams for `filter` emphasizing snapshot updates and team propagation
    - Wrote **Use case** sections for `addStats`, `deleteStats`, `filter`

---

### Community

* Participated in team discussions and reviews
* Collaborated with 4 other teammates on features and bug fixes

---



