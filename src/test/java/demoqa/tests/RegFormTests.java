package demoqa.tests;

import com.codeborne.selenide.Configuration;
import demoqa.pages.RegFormPage;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.Locale;
import static java.lang.String.format;

public class RegFormTests {
    RegFormPage registrationFormPage = new RegFormPage();
    Faker faker = new Faker(new Locale("ru_RU"));
    Faker fakerMail = new Faker(new Locale("en_IN"));
    String gender = "Other", subject = "Maths", hobby = "Music", state = "NCR", city = "Delhi";

    private String getRandomNumber(int min, int max) {
        int value = (int)((Math.random() * (max - min)) + min);
        if (value<10) {
            return String.format("0%x",value);
        } else return Integer.toString(value);
    }

    String[] months31={"January","March","May","July","August","October","December"};
    String  dayOfBirth = getRandomNumber(1,31),
            monthOfBirth = months31[(int)(Math.random() * 7)],
            yearOfBirth = getRandomNumber(1930,2004);
    String  firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
            userEmail = fakerMail.internet().emailAddress(),
            userNumber = faker.number().digits(10),
            currentAddress = faker.starTrek().location().toString();
    String expectedFullName = format("%s %s", firstName, lastName);
    String expectedDateOfBirth = format("%s %s", dayOfBirth, monthOfBirth,"%s", yearOfBirth);
    String expectedStateAndCity = format("%s %s", state, city);
    String resultFormTitleText = "Thanks for submitting the form";

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
                .setCity(city)
                .clickSubmit();

        //Asserts
        registrationFormPage.hasCorrectHeader(resultFormTitleText);
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
