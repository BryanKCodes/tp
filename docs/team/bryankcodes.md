---
layout: default
title: "Bryan Koh's Project Portfolio Page"
---

# Project: SummonersBook

**SummonersBook** is a desktop address book application used by gaming coaches to manage team members and gamers, and to create balanced teams for training and mock matches. It is written in Java and has both a CLI and GUI (JavaFX).

## Summary of Contributions

**Code Contributed**: [RepoSense](https://nus-cs2103-ay2526s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-09-19T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=BryanKCodes&tabRepo=AY2526S1-CS2103T-F08b-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

The following sections detail my contributions to the project, categorized by features and enhancements.

### New Features

#### 1. Foundational Storage System for Teams and Players
*   **What it does:** I spearheaded a major refactoring of the storage system. I began by introducing a unique ID (`UID`) to the `Person` model ([#43](https://github.com/AY2526S1-CS2103T-F08b-1/tp/pull/43)). This architectural change was crucial as it allowed for stable references to players, preventing data corruption if their names were changed.
*   **Justification:** This `UID` system laid the essential groundwork for the `Team` entity, enabling a `Team` to be composed of a list of `Person` `UID`s. I then implemented the storage logic for `Team` objects, including `JsonAdaptedTeam`, and ensured our JSON data file could seamlessly store both `Person` and `Team` lists ([#60](https://github.com/AY2526S1-CS2103T-F08b-1/tp/pull/60)).
*   **Highlights:** This was a significant architectural enhancement that moved the application from a simple flat list of contacts to a relational model. The implementation required careful planning to ensure the `Person` and `Team` data remained consistent and could be loaded reliably. This foundational work was critical for other team members to build features related to team management.

#### 2. Win/Loss Ratio Tracking (`WinCommand` and `LoseCommand`)
*   **What it does:** I implemented the `win` and `lose` commands, which allow coaches to track the performance of their teams ([#111](https://github.com/AY2526S1-CS2103T-F08b-1/tp/pull/111)). When a command is executed on a team, the win/loss count for the team is updated, and critically, the stats for **every player** within that team are also updated.
*   **Justification:** This feature provides core value to the application's target users (coaches), who need to monitor team and player performance over time. It transforms the application from a simple contact manager into a useful tracking tool.
*   **Highlights:** The main challenge was ensuring data consistency. The logic had to correctly update multiple objects (`Team` and five `Person` objects) in the model from a single command. I also added validation in the `Person` and `Team` models to prevent negative win/loss values, making the data model more robust.

### Enhancements to Existing Features

#### 1. Major UI Overhaul and Theming
*   **What it does:** I significantly improved the user interface to better align with the *League of Legends* theme and improve usability.
  *   **Teams Panel:** Added a new panel to the UI to display the list of created teams, which was essential for the new `Team` feature ([#76](https://github.com/AY2526S1-CS2103T-F08b-1/tp/pull/76)).
  *   **StyledLabel Component:** To standardize the look and feel, I created a reusable `StyledLabel` component. This was used to create distinct, color-coded, and icon-adorned labels for `Role`, `Champion`, and `Rank` fields in the UI, making player cards more scannable and visually appealing ([#183](https://github.com/AY2526S1-CS2103T-F08b-1/tp/pull/183)).
  *   **Aesthetic Improvements:** Refined the aesthetics of both `PersonCard` and `TeamCard`, adjusting spacing, fonts, and colors to create a more polished and thematic look ([#77](https://github.com/AY2526S1-CS2103T-F08b-1/tp/pull/77)).
  *   **Bug Fixes:** Fixed various UI bugs, such as components having incorrect widths which led to data being truncated ([#310](https://github.com/AY2526S1-CS2103T-F08b-1/tp/pull/310)).
*   **Justification:** These enhancements were vital for improving the user experience, making the application more intuitive and engaging for its target audience of gamers and coaches.

#### 2. Codebase Modernization and Refactoring
*   **What it does:** I performed several deep refactoring tasks to improve code quality and remove technical debt.
  *   **Purged Legacy Fields:** I conducted an extensive purge of legacy fields from the original AB3 base (`Phone`, `Email`, `Address`). This was not a simple deletion; it required tracing and removing their usage across all levels of the application: **Model, Logic, Storage, UI, and Tests** ([#75](https://github.com/AY2526S1-CS2103T-F08b-1/tp/pull/75)).
  *   **Improved `makeGroup` Command:** I refactored the existing `makeGroup` command (initially implemented by another team member) to use `Index` numbers instead of player `Name`s. The original implementation was bug-prone due to case sensitivity and the possibility of duplicate names ([#174](https://github.com/AY2526S1-CS2103T-F08b-1/tp/pull/174)). My change made the command more robust and user-friendly.
  *   **General Bug Fixing:** Actively fixed numerous bugs throughout the project development cycle to improve the stability and reliability of the application.

### Contributions to Documentation

*   **User Guide:**
  *   I took the lead in improving the quality and structure of the User Guide. I standardized the formatting across all command descriptions for a more consistent and professional look ([#209](https://github.com/AY2526S1-CS2103T-F08b-1/tp/pull/209)).
  *   I broke down a large, intimidating section on command syntax into smaller, more digestible parts to improve readability ([#325](https://github.com/AY2526S1-CS2103T-F08b-1/tp/pull/325)).
  *   I also documented the new `win` and `lose` commands with clear examples and corrected poorly worded sentences throughout the guide.

*   **Developer Guide:**
  *   I updated the **Model UML Class Diagram** to accurately reflect the new architecture, including the addition of the `Team` class and the relationships between `Person`, `Team`, and `Stats` ([#113](https://github.com/AY2526S1-CS2103T-F08b-1/tp/pull/113)). This ensures that new developers can quickly understand the core data structure of our application.
