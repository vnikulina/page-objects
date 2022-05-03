package demoqa.tests;

import com.codeborne.selenide.Configuration;
import demoqa.pages.RegFormPage;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static java.lang.String.format;

public class RegFormTests {
    RegFormPage registrationFormPage = new RegFormPage();
    Faker faker = new Faker(new Locale("ru_RU"));
    Faker fakerMail = new Faker(new Locale("en_IN"));

    String gender = "Other", subject = "Maths", hobby = "Music", state = "NCR", city = "Delhi";

    private String getRandomNumber(int min, int max) {
        return Integer.toString((int)((Math.random() * (max - min)) + min));
    }

    String  dayOfBirth = getRandomNumber(1,30),
            monthOfBirth= "June",
            yearOfBirth = getRandomNumber(1930,2004);
    String  firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
            userEmail = fakerMail.internet().emailAddress(),
            userNumber = faker.number().digits(10),
            currentAddress = faker.starTrek().location().toString();

    String expectedFullName = format("%s %s", firstName, lastName);
    String expectedDateOfBirth = format("%s %s", dayOfBirth, monthOfBirth,"%s", yearOfBirth);
    String expectedStateAndCity = format("%s %s", state, city);


    @BeforeAll
    static void setUp() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1440x900";
        Configuration.holdBrowserOpen = true;
    }


    @Test
    void fillFormTest() {

        registrationFormPage
                .openPage()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setUserEmail(userEmail)
                .setGender(gender)
                .setUserNumber(userNumber)
                .setBirthDate(dayOfBirth, monthOfBirth, yearOfBirth)
                .setSubject(subject)
                .setHobby(hobby)
                .setPicture()
                .setAddress(currentAddress)
                .setState(state)
                .setCity(city);


        $("#submit").click();

        //Asserts
        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        this.registrationFormPage
                .checkResult("Student Name", expectedFullName)
                .checkResult("Student Email", userEmail)
                .checkResult("Gender", gender)
                .checkResult("Mobile", userNumber)
                .checkResult("Date of Birth", expectedDateOfBirth)
                .checkResult("Subjects", subject)
                .checkResult("Hobbies", hobby)
                .checkResult("Picture", "test.jpg")
                .checkResult("Address", currentAddress)
                .checkResult("State and City", expectedStateAndCity);




    }

}
