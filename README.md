# Basic chat room

**Basic chat room** is a java application in which you run an instance of server.java and multiple instances of client.java and all the clients will be able to communicate
with each other like a chat room.

Submitted by: **Anthony Pena**

## User Stories

The following **required** functionality is completed:

* [X] User can **connect to server**
* [X] Server can **broadcast to all clients about a user entering the chat room**
* [X] User's **can request a list of usernames connected to server**

## Notes

This was a school assignment that covered the topics of Multi-threading and Socket programming.

The port that my server listens on is PORT 9090

------------------------------------ Commands ----------------------------------------

"/list" - gives a list of all connected users in the chat

"logout" or "LOGOUT" - logs the user out and broadcasts a message to all users in chat

------------------------------ Screenshots --------------------------------------------

#1 - a screenshot of a user joining the chat and broadcast the message to all clients

#2 - client attempts to use the same id as another user already in chat

#3 - bob requested /list and was returned a list of all users in the chat

#4 - anthony typed logout and broadcasted the message. bob requested a list again and anthony is no longer in the list

