# Secure-Login-System
Hi there this is Ramiz and this my secure login system which takes your username and password and stores the hash of the password using SHA256() and salt in a mysql database. In order to sucessfully run all the code you need a mysql workbench. The schema I used for the database was the name = 'pass' with three primary keys 
Defintion:
Username varchar(20) PK,  
Definition:
Pass varchar(128) PK
Definition:
Salt varchar(45) PK
You can change the keys and columns names but the code in login.java and algorithm.java have to be changed. 
