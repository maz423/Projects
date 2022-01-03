Description:

A three tier Web application that uses HTML/Javascript on the client side and Nodejs on the server side. 
To create a data storage system for a Community based organization. All the data is stored in a MySQL Database.


How to Run: 

Download the folder PartA.

cd into the folder.

use command: Docker-compose build.

use command: Docker-compose up.

Note: The server may not be able to connect to the database on the first run.
      
      After intitial run. stop containers using Ctrl + c.
      
      use command: Docker-compose up.
      
Go to the browser and enter URL: localhost:8000 
       




Some Operations Explained:

The login page authenticates which type of user we have (ie. an admin or a regular staff member.)
A regular staff member can add customer , remove customer ,update customer (change name,address or
add a new report about a customer).

An admin can add staff , remove staff , change roles of staff, update staff and do all the operations a
regular staff member can do .
so to summerize , a regular staff member only has access to staff operations and not customer operations ,
wheras an admin has access to both staff as well as customer operations .
the login page checks if the person who just logged in is a manager or a regular staff member as each staff
member has a role in the database . and hence redirects admin to CBO admin , and rest of the staff to
CBO_staff.

We use a MYSql database to store data about staff and customers . The database has two tables , one to
contain all information about the staff and the other to contain all the info about the custmers . the staff table
has the following columns. ID , First_Name,Last_Name,Address, Date of joining , password and role (staff
or admin). The customer has : ID , First_Name,Last_Name,Address, Date of Registration, info . the info
column contains the reports about a customer (information the organization has about the customer) . every time we
add a report , data is appended to this column.



