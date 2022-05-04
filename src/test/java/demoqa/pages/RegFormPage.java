package demoqa.pages;

import com.codeborne.selenide.SelenideElement;
import demoqa.pages.components.CalendarComponent;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class RegFormPage {
    CalendarComponent calendar = new CalendarComponent();

    // locators
    SelenideElement
            firstNameInput = $("#firstName"),
            lastNameInput = $("#lastName"),
            userEmailInput = $("#userEmail"),
            genderChoice =  $("#genterWrapper"),
            userNumberInput = $("#userNumber"),
            dateOfBirthInput = $("#dateOfBirthInput"),
            subjectChoice = $("#subjectsInput"),
            hobbiesSelect = $("#hobbiesWrapper"),
            selectPicture = $("#uploadPicture"),
            userAddressInput = $("#currentAddress"),
            listOfStates = $(byText("Select State")),
            listOfCities = $(byText("Select City")),
            btnSubmit = $("#submit"),
            resultFormTitle = $("#example-modal-sizes-title-lg");


    // actions
    public RegFormPage openPage() {
        open("/automation-practice-form");
        $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        executeJavaScript("$('footer').remove()");
        executeJavaScript("$('#fixedban').remove()");
        return this;
    }
    public RegFormPage setFirstName(String firstName) {
        firstNameInput.setValue(firstName);
        return this;
    }

    public RegFormPage setLastName(String lastName) {
        lastNameInput.setValue(lastName);
        return this;
    }

    public RegFormPage setUserEmail(String userEmail) {
        userEmailInput.setValue(userEmail);
        return this;
    }

    public RegFormPage setGender(String userGender) {
        genderChoice.$(byText(userGender)).click();
        return this;
    }

    public RegFormPage setUserNumber(String userNumber) {
        userNumberInput.setValue(userNumber);
        return this;
    }

    public RegFormPage setBirthDate(String day, String month, String year) {
        dateOfBirthInput.click();
        calendar.setDate(day, month, year);
        return this;
    }

    public RegFormPage setSubject(String subject) {
        subjectChoice.click();
        subjectChoice.setValue("math");
        $(byText(subject)).click();
        return this;
    }

    public RegFormPage setHobby(String hobby) {
        hobbiesSelect.$(byText(hobby)).click();
        return this;
    }

    public RegFormPage setPicture() {
        selectPicture.uploadFromClasspath("test.jpg");
        return this;
    }

    public RegFormPage setAddress(String userAddress) {
        userAddressInput.setValue(userAddress);
        return this;
    }

    public RegFormPage setState(String state) {
        listOfStates.click();
        $(byText(state)).click();
        return this;
    }

    public RegFormPage setCity(String city) {
        listOfCities.click();
        $(byText(city)).click();
        return this;
    }

    public RegFormPage clickSubmit() {
        btnSubmit.click();
        return this;
    }

    public RegFormPage hasCorrectHeader(String headerText) {
        resultFormTitle.shouldHave(text(headerText));
        return this;
    }

    public RegFormPage checkResult(String registrationFormText, String registrationFormValue) {
        $(".table-responsive").$(byText(registrationFormText))
                .parent().shouldHave(text(registrationFormValue));
        return this;
    }
}
