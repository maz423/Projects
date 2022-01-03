'use strict';

// load package
const express = require('express');
const app = express();
const session = require('express-session');

const bodyParser = require("body-parser");
app.use(bodyParser.urlencoded({ extended: true }));

app.use(session({
	secret: 'secret',
	resave: true,
	saveUninitialized: true
}));

app.use(bodyParser.json());

const mysql = require('mysql');

let con = mysql.createConnection({
  host: 'mysql',
  port: '3306',
  user: 'root',
  password: 'admin'
});

const cors = require('cors');
app.use(cors());


const PORT = 8000;
const HOST = '0.0.0.0';


// Helper
const panic = (err) => console.error(err)


// Connect to database
con.connect((err) => {

  if (err) { panic(err) }

  con.query('USE CBO;', (err, result) => {

      if (err) { 
          con.query("CREATE DATABASE CBO", function (err, result) {  
              if (err) throw err;  
              console.log("Database created");  
              }); 

          con.query("USE CBO;", function (err, result) {  
                  if (err){ throw err;  }

                  
                  
                  }); 

                  con.query("CREATE TABLE Staff(ID int NOT NULL AUTO_INCREMENT,First_Name varchar(255),Last_Name varchar(255),Address  varchar(255),Date varchar(255),password varchar(16),role varchar(16),PRIMARY KEY (ID)); ", function (err, result) {  
                      if (err) throw err;  
                      console.log("Table for Staff created");  
                      }); 

                  con.query("CREATE TABLE Customers(ID int NOT NULL AUTO_INCREMENT,First_Name varchar(255),Last_Name varchar(255),Address  varchar(255),Date varchar(255),info varchar(255),PRIMARY KEY (ID));", function (err, result) {  
                      if (err) throw err;  
                      console.log("Table for Customers created");  
                      }); 

                  con.query("INSERT INTO Staff (First_Name,Last_Name,Address,Date,password,role) VALUES ('Randomguy','cody',' random address',CURRENT_TIMESTAMP(),'newdata1','admin');", function (err, result) {  
                          if (err) throw err;  
                            
                          });

                       
                  
              
          
       }

      else { console.log("Connected!"); }
  })
}) 




 

app.post('/login',(req,res) => {
     
    var ID = req.body.ID;
    var Password = req.body.password;
    


    var statement = 'SELECT * FROM Staff WHERE ID = ? AND Password = ?';
    if (ID && Password) {
		con.query(statement, [ID, Password], function(error, results, fields) {
      console.log(error);
			if (results.length > 0) {
				req.session.loggedin = true;
				req.session.username = ID;
               
				res.send(results[0]);
			} else {
				res.send('Incorrect Username and/or Password!');
			}			
			res.end();
		});
	} else {
		res.send('Please enter Username and Password!');
		res.end();
	}
    


    

});



app.get('/data', (req, res) => {

  let statement = "SELECT * FROM Staff;" 
  con.query(statement, (err, result) => {

      if (err) { /* ... */ 
          console.log("Error in extracting data from database");
          } 

      else { ;
          //console.log("fetching...");
          res.send(JSON.stringify(result));
          /* ... */  }

  })    
	
});


app.get('/datacust', (req, res) => {

  let statement = "SELECT * FROM Customers;" 
  con.query(statement, (err, result) => {

      if (err) { /* ... */ 
          console.log("Error in extracting data from database");
          } 

      else { ;
          
          res.send(JSON.stringify(result));
            }

  })    
	
});


function sql_insertion(param1, param2, param3, param4,param5,param6) {
  
  if(param5.localeCompare("Staff") == 0){
      var statement = `INSERT INTO ${param5} (First_Name,Last_Name,Address,Date,password,role) VALUES ('${param1}', '${param2}','${param3}',CURRENT_TIMESTAMP(),'${param4}','${param6}');`;
  }
  else if(param5.localeCompare("Customers") == 0){
      var statement = `INSERT INTO ${param5} (First_Name,Last_Name,Address,Date,info) VALUES ('${param1}', '${param2}','${param3}',CURRENT_TIMESTAMP(),'${param4}');`;

  }
  
  
      
  console.log(statement);
 
  con.query(statement, (err, result) => {

      if (err) { /* ... */
          
          console.log("Error: while inserting to database");
          
      }

      else { /* ... */
          console.log("Successfully inserted into database");
          
   }

  })
  
}



app.post('/removestaff', (req, res) => {

  var staffID = req.body.ID;
  
  let statement = 'DELETE FROM Staff WHERE ID ='+staffID+';'; 
  con.query(statement, (err, result) => {

      if (err) { /* ... */ 
          console.log("Error in extracting data from database");
          } 

      else { ;
          
          res.send(JSON.stringify(result));
            }

  })    
	
});


app.post('/removecust', (req, res) => {

  var ID = req.body.ID;

  
  let statement = 'DELETE FROM Customers WHERE ID ='+ID+';'; 
  con.query(statement, (err, result) => {

      if (err) { /* ... */ 
          console.log("Error in extracting data from database");
          } 

      else { ;
          
          res.send(JSON.stringify(result));
           }

  })    
	
});


function updatestaff(command,name,ID,table){

  if(command.localeCompare("info")== 0){
      
      var statement = `UPDATE Customers SET ${command} = CONCAT(${command},'\n\n${name}') WHERE ID = ${ID};`;
      
  }
      
  else{var statement = `UPDATE ${table} SET ${command} = '${name}' WHERE ID = ${ID};`;
      

};
  
  console.log(statement);
  con.query(statement, (err, result) => {

      if (err) { /* ... */
          console.log(err);
          console.log("Error: while inserting to database");
          
          
      }

      else { /* ... */
          

            
        } 

  })

};


app.post('/updateStaff',(req,res) => {
  var staffID = req.body.ID;
  var fname = req.body.fname;
  var lname = req.body.lname;
  var address = req.body.address;
  var table = req.body.table;
  var command = req.body.command;

  if(command.localeCompare("First_Name")==0){ updatestaff(command,fname,staffID,table); }
  else if(command.localeCompare("Last_Name")==0){ updatestaff(command,lname,staffID,table);}
  else if (command.localeCompare("Address")==0){updatestaff(command,address,staffID,table);}
  
  
  res.send(JSON.stringify("done"));

}); 




app.post('/addstaff',(req,res) => {
  
  var fname = req.body.fname;
  var lname  = req.body.lname;
  var address  = req.body.address;
  var password  = req.body.password;
  var db = req.body.db;
  var role = req.body.role;
  var info = req.body.info
  var None ="";
  
  
  if(db.localeCompare('Staff') ==0) {
    
    sql_insertion(fname,lname,address,password,db,role);
  }
  else {sql_insertion(fname,lname,address,info,db,None);}
  
  


  
  res.send("done");



  
  
 
 


  
 
          
   

  


   


}); 



 app.use('/', express.static('pages'));


app.listen(PORT, HOST);

console.log('up and running');


