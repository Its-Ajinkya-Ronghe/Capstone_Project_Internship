Feature: User Authentication

  Scenario Outline: Successful login with valid credentials
    Given I am on the login page
    When I login using data from Excel row <RowIndex>
    Then I should see the secure area dashboard

    Examples:
      | RowIndex |
      | 1        |
      | 2        |