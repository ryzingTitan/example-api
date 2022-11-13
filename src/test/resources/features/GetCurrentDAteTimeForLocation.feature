@currentDateTime
Feature: Get current date and time for a given location

  Scenario: retrieve current date and time for location
    Given the current date and time in the area 'America' and the location 'Detroit'
    When a request is made to retrieve the current date and time for the area 'America' and the location 'Detroit'
    Then the request response status is 'OK'
    And the current date and time is correct