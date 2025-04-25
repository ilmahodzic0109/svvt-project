# Web Application Testing Project - W3Schools Website

This repository contains the Selenium-based test automation framework developed for a university project. The project focused on testing the **main features** of the W3Schools website (`https://www.w3schools.com`).

## Project Overview

The goal of this project was to create a comprehensive suite of automated tests to validate both positive and negative test scenarios across various key functionalities of the W3Schools website. We designed a total of 15 test scenarios, with each scenario encompassing approximately 3-4 individual test cases to ensure thorough coverage of critical user interactions. For each test case, we documented whether it passed or failed, along with relevant details.

## Technologies Used

* **Test Automation Framework:** Selenium WebDriver
* **Programming Language:** Java
* **Testing Framework:** JUnit 5
* **Browser:** Google Chrome

## Test Scope

The tests cover a range of scenarios across the W3Schools website's main features, including but not limited to:

* **Navigation and Structure:**
    * Testing the main navigation menu and links.
    * Verifying the loading and basic layout of key pages.
* **Search Functionality:**
    * Testing the website's search bar with various keywords (positive and negative cases).
    * Validating the accuracy and relevance of search results.
* **Tutorial Content Interaction:**
    * Testing the functionality of code editors and "Try it Yourself" features.
    * Verifying the display and execution of code examples.
* **Signup/Login Functionality:**
    * Testing with empty fields.
    * Validating the format of the email field (negative tests).
    * Checking for the presence of error messages when required fields are missing.
    * Verifying password complexity requirements.

## Test Structure

The tests are organized within the `src/test/java/tests` directory. 

Each test method (`@Test` annotation) focuses on a specific test case within a defined scenario. Assertions (`assertTrue`, `assertEquals`, etc.) are used to verify the expected outcomes.

The `@BeforeAll` and `@AfterAll` annotations manage the WebDriver lifecycle for each test class, handling setup and teardown of the browser instance.

## Test Execution

To run these tests, you would typically use a Java IDE (like IntelliJ IDEA or Eclipse) that supports JUnit. Ensure that you have the Selenium WebDriver for Chrome and JUnit 5 dependencies set up in your project.

**Note:** The provided code snippet includes a hardcoded path to the ChromeDriver (`/Users/HP/chromedriver-win64/chromedriver.exe`). This path will need to be updated to the correct location of your ChromeDriver executable.

## Documentation

As part of this project, we maintained comprehensive documentation detailing each of the 15 test scenarios and the individual test cases within them. This documentation included:

* **Test Scenario ID and Description:** A unique identifier and a brief explanation of the feature or area being tested.
* **Test Case ID and Description:** A unique identifier and a detailed step-by-step description of the test case.
* **Expected Result:** The anticipated outcome of the test case.
* **Actual Result:** The observed outcome after executing the test case.
* **Pass/Fail Status:** Whether the actual result matched the expected result.
* **Additional Comments (if any):** Any relevant observations or notes about the test execution, including any issues found.
