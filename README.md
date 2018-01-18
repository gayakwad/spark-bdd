# spark-bdd
BDD style tests for spark e.g.
```
@distributed
Feature: Calculate Aggregate

  Scenario: Aggregate

    Given the following sales transactions:
      | date       | itemId   | quantity |
      | 02051982   | 1        | 1        |
      | 02051982   | 1        | 2        |
      | 14051982   | 2        | 3        |
      | 14051982   | 2        | 4        |
      | 01102012   | 3        | 5        |
      | 01102012   | 3        | 6        |
    When I calculate aggregates
    Then the result is:
      | date       | itemId   | total_quantity |
      | 02051982   | 1        | 3              |
      | 14051982   | 2        | 7              |
      | 01102012   | 3        | 11             |
```

### Backlog

- [X] Add more scenarios
- [X] Reuse spark context
- [ ] Use enums for column names
- [ ] add command line to run test

### References
Only one SparkContext per JVM, but it can be shared across multiple SparkSession - https://stackoverflow.com/questions/40153728/multiple-sparksessions-in-single-jvm
