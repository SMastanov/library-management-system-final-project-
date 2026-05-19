# Library Management System

A console-based Library Management System built in Java as a final project for Object-Oriented Programming.

## Features

- Add new library items (Book, Magazine, Thesis)
- Register members with different tiers (Basic, Silver, Gold)
- Borrow and return items with availability tracking
- Overdue fine calculation based on member tier
- Search items by title or author
- View member borrowing reports

## Class Hierarchy

```
LibraryItem (abstract)
 ├── Book          (14-day loan)
 ├── Magazine      (7-day loan)
 └── Thesis        (21-day loan)

Member (abstract)
 ├── BasicMember   (limit: 2, fine multiplier: 1.0x)
 ├── SilverMember  (limit: 4, fine multiplier: 1.5x)
 └── GoldMember    (limit: 6, fine multiplier: 2.0x)

Borrowable (interface) — implemented by Book, Magazine, Thesis
SearchResult<T extends LibraryItem> — generic search result container
```

## OOP Concepts Used

- **Abstraction** — LibraryItem and Member are abstract classes
- **Inheritance** — Item and Member subclasses extend their parents
- **Polymorphism** — Runtime method dispatch for `getItemType()`, `getMaxBorrowLimit()`, etc.
- **Encapsulation** — All fields are private with getter/setter access
- **Interface** — Borrowable defines the borrow/return/fine contract
- **Generics** — SearchResult\<T extends LibraryItem\> for type-safe search results
- **Collections** — ArrayList for borrowed items, HashMap for catalog and members
- **Exception Handling** — Custom exceptions: ItemNotAvailableException, BorrowLimitExceededException

## How to Run

```bash
# Compile
javac -d out src/library/*.java

# Run
java -cp out library.Main
```

## Project Structure

```
src/library/
 ├── LibraryItem.java                  # Abstract base class for all items
 ├── Book.java                         # Book (14-day loan)
 ├── Magazine.java                     # Magazine (7-day loan)
 ├── Thesis.java                       # Thesis (21-day loan)
 ├── Borrowable.java                   # Interface for borrowable items
 ├── Member.java                       # Abstract base class for members
 ├── BasicMember.java                  # Basic tier (limit: 2)
 ├── SilverMember.java                 # Silver tier (limit: 4)
 ├── GoldMember.java                   # Gold tier (limit: 6)
 ├── Library.java                      # Central manager class
 ├── SearchResult.java                 # Generic search result class
 ├── ItemNotAvailableException.java    # Custom exception
 ├── BorrowLimitExceededException.java # Custom exception
 └── Main.java                         # Entry point with console menu
```

## Fine Calculation

Base fine: **0.25 AZN** per overdue day, multiplied by the member's tier multiplier.

| Tier | Multiplier | Example (5 days overdue) |
|------|-----------|--------------------------|
| Basic | 1.0x | 5 x 0.25 x 1.0 = **1.25 AZN** |
| Silver | 1.5x | 5 x 0.25 x 1.5 = **1.88 AZN** |
| Gold | 2.0x | 5 x 0.25 x 2.0 = **2.50 AZN** |
