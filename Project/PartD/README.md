
Description:

A three tier Web application that uses React on the client side and Nodejs on the server side. 
To create a data storage system for a Community based organization. All the data is stored in a MySQL Database.


How to Run: 

Download the folder PartA.

cd into the folder.

use command: Docker-compose build.

use command: Docker-compose up.

Note: The server may not be able to connect to the database on the first run.
      
      After intitial run. stop containers using Ctrl + c.
      
      use command: Docker-compose up.
      
Go to the browser and enter URL: localhost:3000 
      
      
Brief explanation:

We decided to use react to build a better application , we had two goals in mind , first reduce server load ,
as all ridirection requests are not handled by the server if we are using react , the react router intercepts the
requests and displays the desired pages on the application . second , reduce the amount of code we write ,
as in react we can use components or hooks , that can be reused by just calling them , and that way the
code gets efficiently reused.

implementing this part required an decent understanding of react , and since the two platforms had a very
few things in common , we had to almost build the app from stratch .
we made a few components for each specific action that had to be performed . eg , Add_staff, Display staff
and so on .

our App() component handles all the redirection requests using react router and passes the required props
to each component as it is being called .
we also fetch the data in the database as soon as a new entry is added to the database , using state
changes , when we add an entry in the database , useEffect() runs and fetches the value in the database ,
in a variable getList , get List is update with the help of set list . Also we have the dependency of
useEffect() set to a variable change which we then set using setChange when an entry is posted to a
database.

we also make fetch requests in other components such as Add_staff , where a new entry is added to the
database , remove staff wher we remove a staff member ,
in display staff however we use the same List that is fetched by our App() component as it gets updated As
soon as a new post is added to the database .


