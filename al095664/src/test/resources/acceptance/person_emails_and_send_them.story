Scenario: add a valid e-mail to a non-existing person

Given a non-existing person identified by "Jose"
When a valid e-mail is added to the person "Jose"
And the e-mail is valid
Then the e-mail is validated
And the person called "Jose" is stored
And the e-mail is added to the person "Jose"

Scenario: add a valid e-mail to a existing person with more e-mails

Given a person identified by "Jaime" with e-mails
When a valid e-mail is added to the person "Jaime"
And the e-mail is valid
Then the e-mail is validated
And the person called "Jaime" is stored
And the e-mail is added to the person "Jaime"
And the rest of e-mails are contained in the person "Jaime"