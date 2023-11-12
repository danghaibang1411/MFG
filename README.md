# MFG

# GUI Application README

## Introduction

This README provides instructions for running and debugging the GUI application. Follow the steps below to get started.

### Prerequisites

- Java Development Kit (JDK) installed on your machine.
- IntelliJ IDEA installed for debugging (optional).

## Getting Started

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/danghaibang1411/MFG.git
   cd your-repo
   ```

2. **Build the Project:**
   If the project requires building, use the provided build script or your preferred build tool. For example:
   ```bash
   ./gradlew build   # For Gradle-based projects
   ```

3. **Run the Application:**
   Execute the following command to run the application using the provided JAR file:
   ```bash
   java -jar application.jar
   ```

   This command assumes that `application.jar` is the name of your JAR file. Adjust it accordingly.

## Debugging in IntelliJ IDEA

1. **Import Project:**
   Open IntelliJ IDEA and select `File` > `New` > `Project from Existing Sources`. Navigate to the project directory and select the appropriate build file (e.g., `build.gradle` or `pom.xml`).

2. **Configure Project:**
   Follow the prompts to configure the project. Ensure that the project SDK is set to the JDK version compatible with your application.

3. **Run Configuration:**
   Create a new run configuration for your application:
   - Click on `Edit Configurations` in the top-right corner.
   - Click the `+` button and choose `Application`.
   - Set the `Main class` to the entry point of your application.
   - Specify the classpath of the module containing your `main` method.

4. **Debug Application:**
   - Place breakpoints in your code by clicking in the gutter next to the line numbers.
   - Click the `Debug` button or press `Shift + F9` to start debugging.

## Additional Notes

- Make sure your JDK version is compatible with the project requirements.
- Refer to the project documentation for any specific configuration or runtime requirements.

Now you're all set to run and debug the GUI application. If you encounter any issues, refer to the project documentation or seek assistance from the project community. Happy coding!
