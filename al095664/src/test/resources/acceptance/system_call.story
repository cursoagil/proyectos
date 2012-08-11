Scenario: adding a phone number to a person which wasn't existed

Given a non-existing person identified by "Jose"
When a phone number is added to the person "Jose"
And the phone is valid
Then the person called "Jose" is stored
And the phone is validated
And the phone is added to the person "Jose"