To run this application, first you must clone this repo. You can either run this through an IDE or with Docker.

<h2>Steps to run on Docker:</h2>

Enter a terminal and navigate the the root of this project, bankingapi
<br/>
Build the JAR file with maven. You can do it with the command mvn clean install or if you have an IDE such as IntelliJ, select mvn install from the menu
<br/>
Execute the following command to build the project: docker build -t spring-boot-docker .
<br/>
Execute the following command to run project: docker run -p 8080:8080 spring-boot-docker

The project will be available on localhost:8080

<h2>Steps to execute on an IDE:</h2>

Open the project in your IDE and navigate to bankingapi\src\main\java\com\bankingapi\bankingapi
<br/>
Open the file BankingapiApplication.java and run it.
<br/>
The project will be available on localhost:8080
<br/>

<h2>API Routes and Payloads Examples:</h2>

\*Replaces Values With Values You Want

Creating a new account(POST):
/register
{
"name":"Dave",
"balance":12.50
}

Fund Transfer(POST)
/transfer
{
"accountSender":"6d2268c3-aebc-45e2-86db-ebd54bb9883e",
"accountReceiver":"8535fe8e-c57d-47eb-9cea-4b461654d7b6",
"funds":"15.73"
}

User Transaction History(GET)
/transactions/{accountId}
->The postBalance is the user's balance after the fund transfer

Get User(GET)
/user/{accountId}
-> Although this get call wasn't included in the specifications, I added the call to check user balances after performing fund transfers. Decided to leave this call in the project for the person viewing this project
