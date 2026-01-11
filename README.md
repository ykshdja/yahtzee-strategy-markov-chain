# Yahtzee Strategy Simulator

**Author:** Yash Khanduja  
**Student ID:** 000826385  
**Date:** 08/10/2022  

---

## Overview

This project implements an **automated strategy for the Yahtzee game** using Java.  
The strategy simulates full Yahtzee games, decides which dice to keep across rolls, and selects optimal scoring boxes based on detected patterns.

The goal is to maximize the final game score over multiple simulations.

---

## Strategy Highlights

- Plays all **13 turns** of a Yahtzee game
- Uses pattern detection for:
  - Yahtzee
  - Large Straight
  - Small Straight
  - Full House
  - Three/Four of a Kind
- Prioritizes higher-value scoring boxes
- Falls back to scratching when no valid score is possible
- Uses **highest multiplicity** heuristic to decide which dice to keep

---

## Performance Results

After running **1,000,000 simulated games**:

- **Execution Time:** 68.32 seconds  
- **Minimum Score:** 29  
- **Maximum Score:** 892  
- **Average Score:** 169.93  
- **Games > 150 points:** 32.70%  
- **Games > 200 points:** 21.33%  

---

## Key Methods

- `play()`  
  Controls the full game flow across 13 turns and manages scoring decisions.

- `HighestMultiplicity(int[] arr)`  
  Determines which dice to keep by finding the value with the highest frequency.

- `debugWrite(String str)`  
  Optional debug output for tracing game execution.

---

## Dependencies

- `Yahtzee` class (provided externally)
- Java Standard Library (`java.util.Arrays`, `java.util.Map`)

---

## Declaration

> *StAuth10185:*  
> I, **Yash Khanduja (000826385)**, certify that this material is my original work.  
> No other person's work has been used without due acknowledgement.

