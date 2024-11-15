# File Hider Application

## Overview

The **File Hider Application** is a secure desktop application built in **Java** that allows users to hide and encrypt sensitive files. The app supports features such as user authentication via email, encrypted file storage, and secure login to ensure that only authorized users can access their files.

The backend is implemented using **Java (Core)**, while **MySQL** is used for user authentication and metadata storage. The application ensures that sensitive files are encrypted before being stored, offering a high level of data security.

## Features

- **Email Authentication**: Users can register and log in securely using their email address.
- **Encrypted File Storage**: Files are encrypted before being stored, ensuring privacy and security.
- **Secure Login**: Only authenticated users can access the application and retrieve hidden files.
- **Object-Oriented Design**: Follows best practices for a scalable and maintainable codebase.
- **User-Friendly Interface**: A simple and intuitive UI to hide and retrieve encrypted files.

## Technologies Used

- **Java (Core)**: For backend logic and implementing encryption/decryption functionality.
- **MySQL**: For managing user data (email, password, and file metadata).
- **JDBC**: To interact with the MySQL database.
- **Java Encryption Libraries**: For encrypting/decrypting files using strong encryption algorithms.

## Installation

Follow these steps to set up the **File Hider Application** on your local machine.

### Prerequisites

Ensure that you have the following installed:

- **Java JDK** (version 8 or higher)
- **MySQL** (local or remote)
- **JDBC Driver** for MySQL
- An IDE (e.g., IntelliJ IDEA, Eclipse) for running Java programs.

### Steps to Set Up

1. **Clone the repository**

   Clone the repository to your local machine:
   ```bash
   git clone https://github.com/your-username/file-hider-application.git
   cd file-hider-application
