Scenario: adding a phone number to a person which wasn't existed

Given a non-existing person identified by "Jose"
When a valid phone number is added to the person "Jose"
And the phone is valid
Then the valid phone is validated
And the person called "Jose" is stored
And the phone is added to the person "Jose"

Scenario: adding a phone number to a person which has previously phone numbers

Given a person identified by "Jaime" with phone numbers
When a valid phone number is added to the person "Jaime"
And the phone is valid
Then the valid phone is validated
And the person called "Jaime" is stored
And the phone is added to the person "Jaime"
And the rest of phones are contained in the person "Jaime" 

Scenario: adding a non-valid phone number to a person

Given a person identified by "Jaime" with phone numbers
When a non-valid phone number is added to the person "Jaime"
Then the non-valid phone is validated
And the phone is not added to the person "Jaime"
And the rest of phones are contained in the person "Jaime"

Scenario: adding a non-valid phone number to a non-existing person

Given a non-existing person identified by "Jose" 
When a non-valid phone number is added to the person "Jose"
Then the non-valid phone is validated
And the person "Jose" is not stored