Feature: Get all records Smoke tests
  Scenario: When no data is present 204 is returned by application for get all records
    Given No data is present
    When the client calls getAllRecords
    Then the client receives status code of 204
    And the client receives server version 1.0