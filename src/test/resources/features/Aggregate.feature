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