# 1st-Layerd-Project
This project is a layered architecture implementation for managing student information in a Java application.

## Project Structure

The project follows a layered architecture pattern, separating the application into the following layers:

1. **Presentation Layer:** This layer is responsible for handling user interactions and displaying information. It includes user interface components such as controllers, views, and UI-related logic.

2. **Service Layer:** The service layer contains the business logic of the application. It provides services and operations to the presentation layer. It is responsible for handling the core functionality of managing student information, such as adding, updating, and retrieving student data. Additionally, it includes the functionality for sending emails related to student information.

3. **Data Access Layer:** The data access layer is responsible for interacting with the underlying data storage system, such as a database. It provides methods to perform CRUD (Create, Read, Update, Delete) operations on student data.

4. **Data Layer:** The data layer represents the data storing location which gains frome the application. In here I used one of the populor relational data management system Mysql server for persist data. 

## Technologies Used

The project utilizes the following technologies and frameworks:

- Java: The core programming language used for implementing the application.
- JavaFX: A framework for building Java-based desktop applications with a rich user interface.
- Swing: A Java GUI toolkit used for creating the webcam panel and displaying the webcam feed.
- ZXing: A library for reading QR codes. It is used for decoding the QR codes captured by the webcam.
- Sarxos Webcam: A Java library for accessing and controlling webcams. It provides functionalities for capturing images and video streams from webcams.
- JavaMail API: A Java library for sending emails. It is used for the mail sending feature in the service layer.

## Getting Started

To run the application, follow these steps:

1. Clone the project repository.
2. Open the project in your preferred Java development environment (e.g., IntelliJ, Eclipse).
3. Build and compile the project.
4. Run the application.

Make sure that you have a webcam connected to your system to scan QR codes successfully. Also, ensure that you have configured the email settings correctly in the application's configuration files.

## Usage

Once the application is running, you can perform various operations related to managing student information, such as adding new students, updating existing student records, retrieving student data, and sending emails.

To scan a QR code, make sure the webcam is properly configured and connected. The application will display a webcam panel where you can position the QR code within the camera's view. The application will automatically detect and decode the QR code, displaying the scanned information.

To send an email related to student information, access the appropriate functionality in the application's user interface. Provide the necessary details such as the recipient's email address, subject, and message content. The service layer will utilize the JavaMail API to send the email using the configured email settings.

## Contributing

Contributions to the project are welcome. If you find any issues or have suggestions for improvement, please feel free to create a pull request or submit an issue in the project repository.

## License

This project is licensed under the [MIT License](LICENSE).

