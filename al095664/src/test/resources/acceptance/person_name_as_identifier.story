Scenario: recover phone numbers from person name

Given a person with the person name "Jose" 
When "Jose" has some phones
Then the phones can be recovered using "Jose" as his identifier