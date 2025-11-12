# Connect 4 (Java)

A fully functional Connect 4 game implemented in Java using object-oriented design and 2D array data structures.

---

## Overview
This project recreates the classic **Connect 4** game in Java, demonstrating strong fundamentals in:

- Object-Oriented Programming (OOP)
- 2D array manipulation
- Game-state management
- Input validation
- Win-condition algorithms

The game supports two players who alternate turns, dropping pieces into columns until one achieves four in a row (horizontal, vertical, or diagonal).

---

## Game Features
- **6x7 board** modeled using a `char[][]` or equivalent 2D structure  
- **Turn-based gameplay** with color or letter-coded pieces  
- **Column-based piece dropping** (gravity simulation)  
- **Automated win detection**:
  - Horizontal
  - Vertical
  - Diagonal (both directions)
- **Input validation** for:
  - Invalid column numbers
  - Full columns
