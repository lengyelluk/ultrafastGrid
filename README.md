# ultrafastGrid
This is an attempt to solve Applitools Cross Browser Testing Hackathon.
The project consists of four Maven projects that were created as separate modules in Intellij IDEA. Each module has a separate POM file and can be executed separately.
Instructions are different based on type of the project.

A. Traditional Tests
1. Clone the repo
2. navigate to the root folder of either TraditionalTestsV1 or TraditionalTestsV2
3. the tests can run either locally or in browserstack.
3.1. to run locally execute: "mvn clean test -DrunType=Local"
3.2. to run in Browserstack, change username and accesskey in class BaseTest located in tests package. Then run only: "mvn clean test"
4. Results of the test will be printed to the root folder of the module and then copied to the root folder of the project.
5. The filename will be either Traditional-V1-TestResults.txt or Traditional-V2-TestResults.txt

Note: the test should run in parallel, but another task will start only when the previous one is finished. So only after all tests of Task 1 are done, tests of Task 2 will start.

B. Modern Tests
1. Clone the repo
2. navigate to the root folder of either ModernTestsV1 or ModernTestsV2
3. before running the test, changed API key of Applitools - API_KEY in class TestBase in package tests  
4. run the tests by executing: "mvn clean test"
5. Check the results in your Applitools dashboard
