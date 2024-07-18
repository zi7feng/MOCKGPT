# MOCKGPT

## Description
MockGPT is a project designed to simulate the functionality of ChatGPT. It includes a front-end interface that mimics the user experience of ChatGPT, as well as a back-end system for managing user data, conversations, and various administrative tasks. The project aims to provide a comprehensive solution for simulating interactions with ChatGPT, including features such as user information management, conversation metadata management, conversations management, and usage log management.

---
## Database Design

---
## DDL
#### Users
```sql
CREATE TABLE Users (
    Id INT IDENTITY(1,1) PRIMARY KEY, -- Auto-increment primary key
    Email NVARCHAR(255) NOT NULL, -- Email address of the user
    PhoneNumber NVARCHAR(20) NOT NULL, -- Phone number of the user
    Password NVARCHAR(255) NOT NULL, -- Password of the user
    LastName NVARCHAR(255), -- Last name of the user
    FirstName NVARCHAR(255), -- First name of the user
    MiddleName NVARCHAR(255), -- Middle name of the user
    Nickname NVARCHAR(255), -- Nickname of the user
    AvatarUrl NVARCHAR(255), -- URL of the user's avatar
    IsGptPlus BIT DEFAULT 0, -- Flag indicating if the user is a GPT Plus member
    CreatedAt DATETIME DEFAULT GETDATE(), -- Timestamp of record creation
    UpdatedAt DATETIME DEFAULT GETDATE(), -- Timestamp of record update
    IsDeleted BIT DEFAULT 0 -- Soft delete flag
);
```
#### Addresses
```sql
CREATE TABLE Addresses (
    Id INT IDENTITY(1,1) PRIMARY KEY, -- Auto-increment primary key
    StreetNumber NVARCHAR(50), -- Street number of the address
    StreetName NVARCHAR(255), -- Street name of the address
    StreetType NVARCHAR(10), -- Street type (e.g., St, Rd, Blvd)
    Direction NVARCHAR(10), -- Direction (e.g., N, S, E, W)
    City NVARCHAR(255), -- City of the address
    State NVARCHAR(255), -- State of the address
    PostalCode NVARCHAR(20), -- Postal code of the address
    Country NVARCHAR(255), -- Country of the address
    AddressType NVARCHAR(50), -- Type of address (e.g., Home, Work)
    IsDeleted BIT DEFAULT 0, -- Soft delete flag
    CreatedAt DATETIME DEFAULT GETDATE(), -- Timestamp of record creation
    UpdatedAt DATETIME DEFAULT GETDATE() -- Timestamp of record update
);
```
#### UserAddress
```sql
CREATE TABLE UserAddress (
    UserId INT, -- Foreign key to Users table
    AddressId INT, -- Foreign key to Addresses table
    PRIMARY KEY (UserId, AddressId), -- Composite primary key
    FOREIGN KEY (UserId) REFERENCES Users(Id), -- Foreign key constraint
    FOREIGN KEY (AddressId) REFERENCES Addresses(Id) -- Foreign key constraint
);
```
#### ConversationMetadata
```sql
CREATE TABLE ConversationMetadata (
    ConversationId INT IDENTITY(1,1) PRIMARY KEY, -- Auto-increment primary key
    UserId INT, -- Foreign key to Users table
    Title NVARCHAR(255) NOT NULL, -- Title of the conversation
    IsArchived BIT DEFAULT 0, -- Flag indicating if the conversation is archived
    IsDeleted BIT DEFAULT 0, -- Soft delete flag
    CreatedAt DATETIME DEFAULT GETDATE(), -- Timestamp of record creation
    UpdatedAt DATETIME DEFAULT GETDATE(), -- Timestamp of record update
    FOREIGN KEY (UserId) REFERENCES Users(Id) -- Foreign key constraint
);
```
#### GPTInstances
```sql
CREATE TABLE GPTInstances (
    GptId INT IDENTITY(1,1) PRIMARY KEY, -- Auto-increment primary key
    GptVersion NVARCHAR(20), -- Version of the GPT instance
    GptDescription NVARCHAR(255) -- Description of the GPT instance
);
```
#### Conversations
```sql
CREATE TABLE Conversations (
    Id INT IDENTITY(1,1) PRIMARY KEY, -- Auto-increment primary key
    UserId INT, -- Foreign key to Users table
    ConversationId INT, -- Foreign key to ConversationMetadata table
    GptId INT, -- Foreign key to GPTInstances table
    Message NVARCHAR(MAX), -- Message content
    Sender NVARCHAR(50), -- Sender of the message
    Timestamp DATETIME DEFAULT GETDATE(), -- Timestamp of the message
    FOREIGN KEY (UserId) REFERENCES Users(Id), -- Foreign key constraint
    FOREIGN KEY (ConversationId) REFERENCES ConversationMetadata(ConversationId) ON DELETE CASCADE, -- Foreign key constraint with cascade delete
    FOREIGN KEY (GptId) REFERENCES GPTInstances(GptId) -- Foreign key constraint
);
```
#### UsageLog
```sql
CREATE TABLE UsageLog (
    Id INT IDENTITY(1,1) PRIMARY KEY, -- Auto-increment primary key
    UserId INT, -- Foreign key to Users table
    Action NVARCHAR(255) NOT NULL, -- Action performed by the user
    Timestamp DATETIME DEFAULT GETDATE(), -- Timestamp of the action
    FOREIGN KEY (UserId) REFERENCES Users(Id) -- Foreign key constraint
);
```
---
## Functionality
### User Information Management
Registeration
```sql
INSERT INTO Users (Email, PhoneNumber, Password, LastName, FirstName, MiddleName, Nickname, AvatarUrl, IsGptPlus)
VALUES ('user@example.com', '1234567890', 'hashed_password', 'Doe', 'John', 'M', 'johndoe', 'http://example.com/avatar.jpg', 0);
```
Login
```sql
SELECT Id, Email, Password
FROM Users
WHERE Email = 'user@example.com' AND Password = 'hashed_password' AND IsDeleted = 0;
```
Update profile
```sql
UPDATE Users
SET LastName = 'NewLastName', FirstName = 'NewFirstName', MiddleName = 'NewMiddleName', Nickname = 'newnickname', AvatarUrl = 'http://example.com/newavatar.jpg', UpdatedAt = GETDATE()
WHERE Id = 1 AND IsDeleted = 0;
```
Update address
```sql
UPDATE Addresses
SET StreetNumber = '123', StreetName = 'New Street', StreetType = 'Rd', Direction = 'N', City = 'New City', State = 'New State', PostalCode = '12345', Country = 'New Country', AddressType = 'Home', UpdatedAt = GETDATE()
WHERE Id = (
    SELECT AddressId
    FROM UserAddress
    WHERE UserId = 1
) AND IsDeleted = 0;
```
### Dialog(Conversation MetaData) Management
Create a new dialog
```sql
INSERT INTO ConversationMetadata (UserId, Title)
VALUES (1, 'New Dialog');
```
Delete a dialog
```sql
DELETE FROM ConversationMetadata
WHERE ConversationId = 1;
```
Archive a dialog
```sql
UPDATE ConversationMetadata
SET IsArchived = 1, UpdatedAt = GETDATE()
WHERE ConversationId = 1;
```
Rename a dialog
```sql
UPDATE ConversationMetadata
SET Title = 'Renamed Conversation', UpdatedAt = GETDATE()
WHERE ConversationId = 1;
```
Query user’s dialog titles (for sidebar display)
```sql
SELECT ConversationId, Title
FROM ConversationMetadata
WHERE UserId = 1 AND IsDeleted = 0 AND IsArchived = 0;
```
Query dialog titles by keyword
```sql
SELECT ConversationId, Title
FROM ConversationMetadata
WHERE Title LIKE '%keyword%' AND IsDeleted = 0;
```
### Conversations Management
Create new chat message
```sql
INSERT INTO Conversations (UserId, ConversationId, GptId, Message, Sender, Timestamp)
VALUES (1, 1, 1, 'This is a new message', 'user', GETDATE());
```
Query conversations and senders by conversation ID
```sql
SELECT c.message, c.sender, c.timestamp
FROM Conversations c
WHERE c.conversation_id = 123
ORDER BY c.timestamp;
```
Query conversations by keyword and retrieve conversation ID
```sql
SELECT C.Id, CM.ConversationId, C.Message, C.Sender, C.Timestamp
FROM Conversations C
INNER JOIN ConversationMetadata CM ON C.ConversationId = CM.ConversationId
WHERE CM.IsArchived = 0
AND CM.IsDeleted = 0
AND C.Message LIKE '%keyword%'
ORDER BY C.Timestamp;
```
### Usage Log Management
Add new usage log
```sql
INSERT INTO UsageLog (user_id, action) VALUES (123, 'User logged in');
```
Query specified user's log
```sql
SELECT * FROM UsageLog WHERE user_id = 123 ORDER BY timestamp DESC;
```
---
