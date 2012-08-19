Scenario: assign address to a single person

Given a person with the person name "Jose"
When an address is assigned to "Jose"
Then the person "Jose" contains the address

Scenario: assign address to multiple person

Given a person with the person name "Jose"
And a person with the person name "Jaime"
And a person with the person name "Jorge"
When an address is assigned to Jose and Jaime and Jorge
Then the person "Jaime" contains the address
Then the person "Jose" contains the address
Then the person "Jorge" contains the address 