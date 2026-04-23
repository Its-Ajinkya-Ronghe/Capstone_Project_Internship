Feature: Backend API Validation

  Scenario: Verify user retrieval API
    Given the API endpoint is "https://jsonplaceholder.typicode.com/users/1"
    When I send a GET request
    Then the response status code should be 200
    And the response should contain user "Leanne Graham"