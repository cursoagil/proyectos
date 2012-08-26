Scenario: recover people given a substring filtering

Given a person with the person name "Jose"
And a person with the person name "Alberto"
And a person with the person name "Ernesto"
When a search by "to" is done
Then "Alberto" is in the result 
And "Ernesto" is in the result
