@users
Feature: Find users by id

  Background:
    Given the following users exist
      | firstName | lastName | username      |
      | trusting  | tesla    | trustingTesla |

  Scenario: user id doesn't exist
    When a request is made to find a user with id 5
    Then the request response status is 'NOT_FOUND'
    And the application will log the following messages:
      | level | message                            |
      | INFO  | Retrieving user data for user id 5 |

  Scenario: user id exists
    When a request is made to find a user with id 1
    Then the request response status is 'OK'
    And the following users are returned
      | id | firstName | lastName | fullName       |
      | 1  | trusting  | tesla    | trusting tesla |
    And the application will log the following messages:
      | level | message                            |
      | INFO  | Retrieving user data for user id 1 |