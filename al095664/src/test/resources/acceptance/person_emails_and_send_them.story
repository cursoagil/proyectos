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

Scenario: add a non-valid e-mail to a person

Given a person with the person name "Jose"
When a non-valid e-mail is added to the person "Jose"
And the e-mail is non-valid
Then the e-mail is validated
And the e-mail is not added to the person "Jose"

Scenario: send an e-mail to a person e-mail with topic and body
Given a person identified by "Jaime" with e-mails
And a topic and the body are not empty
When the e-mail is try to be sent to "Jaime" first email
Then the e-mail sending result is "true" 

Scenario: send an e-mail to a person e-mail with topic but without body
Given a person identified by "Jaime" with e-mails
And a topic not empty but body empty
When the e-mail is try to be sent to "Jaime" first email
Then the e-mail sending result is "false" 



