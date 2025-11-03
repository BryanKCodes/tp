---
layout: default
title: "Xin Tong's Project Portfolio Page"
---

### Project: SummonersBook

**SummonersBook** is a desktop address book application used by gaming coaches to manage team members and gamers, and to create balanced teams for training and mock matches. It is written in Java and has both a CLI and GUI (JavaFX).

Below are my contributions to the project:

---

### New Features

* **`makeGroup` Command**
  * **What it does**: Allows users to manually form a group with 5 players of their choosing. Users can customize teams to include specific players together.
  * **Justification**: Provides flexibility beyond automatic team generation, giving coaches control to create teams according to their strategic preferences.
  * **Highlights**: Implements robust input validation to ensure valid team formation. Seamlessly integrates with the existing team logic and the auto-group `group` command developed by Justin.
  * **Credits**: Implemented as part of the team’s collaborative logic integration.

* **`filter` Command (for performance score)**
  * **What it does**: Filters players based on performance score (`filter s/SCORE` to find players greater than or equal to a given score).
  * **Justification**: Enables coaches to quickly identify high-performing players for team selection or spot players needing improvement, enhancing usability and convenience.
  * **Highlights**: Handles edge cases such as overflow, invalid values, long floating-point numbers, and anticipates common invalid user inputs.
  * **Credits**: Implemented collaboratively with Duong.

---

### Code Contributions
* [RepoSense link placeholder]()

---

### Project Management
* Tracked issues and coordinated tasks in a small team of 5 members

---

### Enhancements to Existing Features

* **Reworked AddCommand**
  * Updated AddCommand to be compatible with the new `Person` fields in SummonersBook.

* **Improved Tag Parsing**
  * Reworked parsing logic for tags to be robust and defensive:
    - Limits tags to 20 alphanumeric characters to prevent UI formatting issues.
    - Checks for duplicates case-insensitively.
    - Ensures consistency across AddCommand and EditCommand.

* **Enhanced EditCommand**
  * Updated EditCommand to use the improved tag parsing logic for validation.

* **Bug Fixes in FilterCommand**
  * Ensured no extraneous parameters are accepted.
  * Handled edge cases such as invalid prefixes, multiple `s/` prefixes, and empty or non-positive values.

* **Input Validation and UI Improvements**
  * Fixed input validation for player names exceeding League of Legends limits.
  * Improved error messages for commands.
  * Fixed UI inconsistencies such as spacing issues and tag formatting.

* **General Bug Fixes**
  * Fixed multiple major bugs, including duplicate checks, spacing, and validation errors.
---
### Documentation

* **User Guide Updates**
  * Structured the document with coherent logic flow for our intended users.
  * Added **Quick Start tutorial** for CLI commands, including safe usage guidance for non-technical users.
  * Implemented **setup instructions** to help coaches get started quickly.
  * Added **hyperlinks** for easy navigation and created a **Table of Contents**.
  * Documented new commands:
    - `add` — Adding players to the roster.
    - `filter` — Filtering players based on role, rank, or performance score.
    - `makeGroup` — Manual team creation with robust input validation.
  * Integrated **diagrams** showing team formation, command flow, and filtering logic.
  * Reviewed the entire guide for **accuracy, clarity, and consistency**, ensuring terminology (“Person” vs “Player”) is clearly defined.
  * Collaborated with team members to verify examples and instructions.

* **Developer Guide Updates**
  * Added **implementation notes** for new commands (`makeGroup`, `filter`) and updated AddCommand/EditCommand to reflect improved tag parsing.
  * Documented **system logic** and diagrams to help new developers understand how teams, players, and commands interact.
  * Clarified terminology distinctions and maintained alignment with codebase for accurate references.
---
### Community
* Actively participated in team discussions, code reviews, and planning sessions.
* Collaborated closely with 4 teammates on feature implementation and bug fixes.
* Took initiative in documentation by updating the User Guide and Developer Guide, ensuring clarity and accuracy for new and existing features.
* Assisted teammates by identifying and fixing bugs in their features when spotted, contributing to overall project quality.
---





