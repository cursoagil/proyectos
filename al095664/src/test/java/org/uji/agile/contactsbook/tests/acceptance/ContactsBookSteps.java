package org.uji.agile.contactsbook.tests.acceptance;

import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.jbehave.core.annotations.*;
import org.uji.agile.contactsbook.*;
import org.uji.agile.contactsbook.tests.ContactsBookTestSuiteTemplate;

public class ContactsBookSteps extends ContactsBookTestSuiteTemplate {

	private static final String TEST_PHONE_NUMBER = "606012347";
	private static final String TEST_NONVALID_PHONE_NUMBER = "111111111";
	private static final String VALID_EMAIL = "jorgonor88@gmail.com";
	private static final String NON_VALID_EMAIL = "aasd@somedomain.";
	private static final String TEST_ADDRESS = "C/ Martin Fowler nº 1";
	
	private EmailValidator realEmailValidator;
	
	private List<Person> matchedPeople;

	private List<String> emailsAsStrings;
	protected List<String> phonesAsStrings;
	private String usedEmail;
	private String currentTopic;
	private String currentBody;
	private boolean sendEmailResult;
	
	@BeforeScenario
	public void beforeScenario() {
		setUpContactsBook();
		setUpComponents();
		setUpTestValues();
		truncateStorage();
		clearScenarioVariables();
	}
	
	protected void setUpComponents() {
		realEmailValidator = new EmailValidator();
	}
	
	protected void setUpTestValues() {
		phonesAsStrings = new ArrayList<String>();
		phonesAsStrings.add("653423121");
		phonesAsStrings.add("912354232");
	
		emailsAsStrings = new ArrayList<String>();
		emailsAsStrings.add("michael@somedomain.com");
		emailsAsStrings.add("jamie@somedomain.com");
	}
	
	protected void truncateStorage() {
		mockFileStorage.removeAll();
	}
	
	protected void clearScenarioVariables() {
		usedEmail = "";
		currentBody = "";
		currentTopic = "";
		sendEmailResult = false;
	}
	
	@Override
	protected void initMocks() {
		super.initMocks();
	}
	
	@Override
	protected void initMocksBehaviour() {
		mockPhoneValidator = mock(SpanishPhoneValidator.class, CALLS_REAL_METHODS);	
	}
	
	@Given("a non-existing person identified by \"$personName\"")
	public void nonExistingPersonIdentifiedBy(String personName) {
		assertFalse(mockFileStorage.exists(personName));
	}
	
	@Given("a person identified by \"$personName\" with phone numbers") 
	public void existingPersonIdentifiedBy(String personName) {
		phonesAsStrings.add("653423121");
		phonesAsStrings.add("912354231");
		
		ContactsBook.addPerson(personName);
		
		for(String phoneString : phonesAsStrings) {
			ContactsBook.addPhone(phoneString).to(personName);
		}
	}
	
	@Given("a person identified by \"$personName\" with e-mails")
	public void existingPersonWithEmailsIdentifiedBy(String personName) {
		ContactsBook.addPerson(personName);
		for(String emailStr: emailsAsStrings) {
			ContactsBook.addEmail(emailStr).to(personName);
		}
	}
	
	@Given("a person with the person name \"$personName\"")
	public void personWithPersonNameAndPhoneNumbers(String personName) {
		ContactsBook.addPerson(personName);
	}
	
	@Given("a topic and the body are not empty")
	public void aTopicAndABodyAreNotEmpty() {
		currentTopic = "Hi there";
		currentBody = "Hello my name is Martha";
	}
	
	@Given("a topic not empty but body empty")
	public void aTopicNotEmptyButEmptyBody() {
		currentTopic = "Hi there";
		currentBody = "";
	}
	
	@When("a valid phone number is added to the person \"$personName\"") 
	public void phoneNumberAddedToPersonIdentifiedBy(String personName) {
		ContactsBook.addPhone(TEST_PHONE_NUMBER).to(personName);
	}
	
	@When("a non-valid phone number is added to the person \"$personName\"")
	public void nonValidPhoneNumberAddedToPersonIdentifiedBy(String personName) {
		ContactsBook.addPhone(TEST_NONVALID_PHONE_NUMBER).to(personName);
	}
	
	@When("the phone is valid")
	public void phoneIsValid() {
		PhoneValidator phoneValidator = new SpanishPhoneValidator();
		assertTrue(phoneValidator.validate(Phone.create(TEST_PHONE_NUMBER)));
	}
	
	@When("\"$personName\" has some phones")
	public void personHasSomePhones(String personName) throws NotExistsPersonException {
		for (String phoneAsString : phonesAsStrings) {
			when(mockPhoneValidator.validate(Phone.create(phoneAsString)))
								   .thenReturn(true);
			ContactsBook.addPhone(phoneAsString).to(personName);
		}
	}
	
	@When("a valid e-mail is added to the person \"$personName\"") 
	public void validEmailAddedToPerson(String personName) {
		usedEmail = VALID_EMAIL;
		ContactsBook.addEmail(usedEmail).to(personName);
	}
	
	@When("a non-valid e-mail is added to the person \"$personName\"")
	public void nonValidEmailAddedToPerson(String personName) {
		usedEmail = NON_VALID_EMAIL;
		ContactsBook.addEmail(usedEmail).to(personName);
	}
	
	@When("the e-mail is valid") 
	public void theEmailIsValid() {
	 	assertTrue(realEmailValidator.validate(Email.create(usedEmail)));
	}
	
	@When("the e-mail is non-valid")
	public void theEmailIsNotValid() {
	 	assertFalse(realEmailValidator.validate(Email.create(usedEmail)));
	}
	
	@When("the e-mail is try to be sent to \"$personName\" first email")
	public void tryToSendEmail(String personName) throws NotExistsPersonException {
		List<String> emails = ContactsBook.getEmailsFromPersonName(personName);
		sendEmailResult = ContactsBook.sendEmail(emails.get(0), currentTopic, currentBody);
	}
	
	@When("an address is assigned to \"$personName\"")
	public void addAddressTo(String personName) {
		ContactsBook.addAddress(TEST_ADDRESS).to(personName);
	}

	@When("an address is assigned to Jose and Jaime and Jorge")
	public void addAddressMultiple() {
		ContactsBook.addAddress(TEST_ADDRESS).to("Jose")
											 .and("Jaime")
											 .and("Jorge");
	}
	
	@When("a search by \"$searchString\" is done")
	public void searchBySearchString(String searchString) {
		matchedPeople = ContactsBook.searchBy(searchString);
	}
	
	@Then("the person called \"$personName\" is stored")
	public void personIsCreated(String personName) {
		try {
			ContactsBook.getPhonesFromPersonName(personName);
		}
		catch( NotExistsPersonException ex) {
			fail();
		}
	}

	@Then("the person \"$personName\" is not stored")
	public void personIsNotCreated(String personName) {
		boolean personNotExistsExceptionThrown = false;
		try {
			ContactsBook.getPhonesFromPersonName(personName);	
		}
		catch (NotExistsPersonException ex) {
			personNotExistsExceptionThrown = true;
		}
		finally {
			assertTrue(personNotExistsExceptionThrown);
		}
	}
	
	@Then("the valid phone is validated") 
	public void validPhoneIsValidated() {
		verify(mockPhoneValidator).validate(Phone.create(TEST_PHONE_NUMBER));
	}
	
	@Then("the non-valid phone is validated") 
	public void nonValidPhoneIsValidated() {
		verify(mockPhoneValidator).validate(Phone.create(TEST_NONVALID_PHONE_NUMBER));
	}
	
	@Then("the phone is added to the person \"$personName\"")
	public void phoneIsAddedToThePerson(String personName) throws NotExistsPersonException {
		List<String> phones = ContactsBook.getPhonesFromPersonName(personName);
		assertThat(phones, hasItem(TEST_PHONE_NUMBER));
	}
	
	@Then("the phone is not added to the person \"$personName\"")
	public void phoneIsNotAddedToThePerson(String personName) throws NotExistsPersonException {
		List<String> phones = ContactsBook.getPhonesFromPersonName(personName);
		assertFalse(phones.contains(TEST_NONVALID_PHONE_NUMBER));
	}
	
	@Then("the phones can be recovered using \"$personName\" as his identifier")
	public void phonesCanBeRecoveredUsingHisPersonNameAsIdentifier(String personName) throws NotExistsPersonException {
		List<String> phones = ContactsBook.getPhonesFromPersonName(personName);
		for(String phoneStr : phonesAsStrings) {
			assertThat(phones, hasItem(phoneStr));	
		}
	}
	
	@Then("the rest of phones are contained in the person \"$personName\"")
	public void otherPhonesAreContainedYet(String personName) throws NotExistsPersonException {
		List<String> phones = ContactsBook.getPhonesFromPersonName(personName);
		for(String phoneStr : phonesAsStrings) {
			assertThat(phones, hasItem(phoneStr));	
		}
	}
	
	@Then("the e-mail is validated")
	public void emailIsValidated() {
		verify(mockEmailValidator).validate(Email.create(usedEmail));
	}
	
	@Then("the e-mail is added to the person \"$personName\"")
	public void emailIsAddedToThePerson(String personName) throws NotExistsPersonException {
		List<String> emails = ContactsBook.getEmailsFromPersonName(personName);
		assertThat(emails, hasItem(usedEmail));
	}
	
	@Then("the rest of e-mails are contained in the person \"$personName\"")
	public void otherEmailsAreContainedYet(String personName) throws NotExistsPersonException
	{
		List<String> emails = ContactsBook.getEmailsFromPersonName(personName);
		for(String emailStr : emailsAsStrings) {
			assertThat(emails, hasItem(emailStr));
		}
	}
	
	@Then("the e-mail is not added to the person \"$personName\"")
	public void theEmailIsNotAddedToThePerson(String personName) throws NotExistsPersonException {
		assertFalse(ContactsBook.getEmailsFromPersonName(personName)
				   .contains(NON_VALID_EMAIL));
	}
	
	@Then("the e-mail sending result is \"$result\"")
	public void emailIsSentResult(String sResult) {
		boolean result = Boolean.valueOf(sResult);
		assertEquals( sendEmailResult, result );
	}
	
	@Then("the person \"$personName\" contains the address") 
	public void personContainsTheAddress(String personName) throws NotExistsPersonException {
		List<String> addresses = 
			ContactsBook.getAddressesFromPersonName(personName);

		assertThat(addresses, hasItem(TEST_ADDRESS));
	}
	
	@Then("\"$personName\" is in the result")
	public void personIsInTheMatchedList(String personName) {
		assertThat(matchedPeople, hasItem(new Person(personName)));
	}
	
}
