// NAME: Mohammad Aman Zargar
// NSID: Maz423


'use strict';

const path = require("path")
const express = require('express');
const app = express();
const session = require('express-session'); //-----------


const bodyParser = require("body-parser");

app.use(bodyParser.urlencoded({ extended: true }));

app.use(session({
	secret: 'secret',
	resave: true,
	saveUninitialized: true
}));

app.use(bodyParser.json());



const mysql = require('mysql');
const { dirname } = require("path/posix");
const { ClientRequest } = require("http");



let con = mysql.createConnection({
  host: 'mysql',
  port: '3306',
  user: 'root',
  password: 'admin'
});


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
			if (results.length > 0) {
				req.session.loggedin = true;
				req.session.username = ID;
                console.log(results[0].role);
				res.send(results[0].role);
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









app.post('/registerCustomer',(req,res) => {
    var fname = req.body.fname;
    var lname = req.body.lname;
    var address = req.body.address;
    var info = req.body.info;
    var role = "N/A";
    sql_insertion(fname,lname,address,info,'Customers',role);
    var response = new Object();
        response.answer = fname+ " " + lname;
        res.send(response);
    

});



 
app.post('/registerStaff',(req,res) => {
    var fname = req.body.fname;
    var lname = req.body.lname;
    var address = req.body.address;
    var password = req.body.password;
    var role = req.body.role;
    sql_insertion(fname,lname,address,password,'Staff',role);
    var response = new Object();
        response.answer = fname+ " " + lname;
        res.send(response);
    

});


app.post('/searchStaff',(req,res) => {
    var staffID = req.body.staffID;
    console.log(staffID);
    var statement = 'SELECT First_Name, Last_Name, Address FROM Staff WHERE ID ='+staffID+';';

    con.query(statement, (err, result) => {

        if (err) { /* ... */
            console.log("Error: while inserting to database");
            console.log('SELECT First_Name Last_Name Address FROM Staff WHERE ID ='+staffID+';');
            
        }

        else { /* ... */
            console.log(result);

            res.send(result);  
          } 

    })

});

app.post('/searchCustomer',(req,res) => { 
    var customerID = req.body.customerID;
    var statement = 'SELECT First_Name, Last_Name, Address FROM Customers WHERE ID ='+customerID+';';

    con.query(statement, (err, result) => {

        if (err) { /* ... */
            console.log("Error: while inserting to database");
            
            
        }

        else { /* ... */
            console.log(result);

            res.send(result);  
          } 

    })
    
    
    

}); 


app.post('/removeCustomer',(req,res) => {
    var customerID = req.body.customerID;
    var statement = 'DELETE FROM Customers WHERE ID ='+customerID+';';
    con.query(statement, (err, result) => {

        if (err) { /* ... */
            console.log("Error: while inserting to database");
            
            
        }

        else { /* ... */
            

            res.send(result);  
          } 
 
    })
    
    
    

});

app.post('/removeStaff',(req,res) => {
    var staffID = req.body.staffID;
    var statement = 'DELETE FROM Staff WHERE ID ='+staffID+';';
    con.query(statement, (err, result) => {

        if (err) { /* ... */
            console.log("Error: while inserting to database");
            
            
        }

        else { /* ... */
            

            res.send(result);  
          } 
 
    })
    
    
    

});

 
app.post('/CustomerbyID',(req,res) => {
    var customerID = req.body.customerID;
    var statement = 'SELECT ID, First_Name, Last_Name, Address, Date, info FROM Customers WHERE ID ='+customerID+';';
    con.query(statement, (err, result) => {

        if (err) { /* ... */
            console.log("Error: while inserting to database");
            
            
        }

        else { /* ... */
            

            res.send(result);  
          } 
 
    })
    
    
    

});



app.post('/StaffbyID',(req,res) => {
    var staffID = req.body.staffID;
    var statement = 'SELECT ID, First_Name, Last_Name, Address, Date, role FROM Staff WHERE ID ='+staffID+';';
    con.query(statement, (err, result) => {

        if (err) { /* ... */
            console.log("Error: while inserting to database");
            
            
        }

        else { /* ... */
            

            res.send(result);  
          } 
 
    })
    
    
    

});


app.get('/AllCustomers', function(req, res) {

    var statement = 'SELECT * FROM Customers;';

    

    con.query(statement, (err, result) => {

        if (err) { /* ... */
            console.log("Error: while inserting to database");
            
            
        }

        else { /* ... */
            

            res.send(result); 
            console.log(result); 
          } 
 
    })
    
  }); 


  app.get('/Allstaff', function(req, res) {

    var statement = 'SELECT * FROM Staff;';

    

    con.query(statement, (err, result) => {

        if (err) { /* ... */
            console.log("Error: while inserting to database");
            
            
        }

        else { /* ... */
            

            res.send(result); 
             
          } 
 
    })
    
  }); 


  function updatecustomers(command,name,ID){

    if(command.localeCompare("info")== 0){
        
        var statement = `UPDATE Customers SET ${command} = CONCAT(${command},'\n\n${name}') WHERE ID = ${ID};`;
        console.log("report: "+ statement);
    }
        
    else{var statement = `UPDATE Customers SET ${command} = '${name}' WHERE ID = ${ID};`;
        console.log("name :"+ statement);

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

  app.post('/updateCustomer',(req,res) => {
    var customerID = req.body.customerID;
    var fname = req.body.fname;
    var lname = req.body.lname;
    var address = req.body.address;
    var report = req.body.report;
    var command = req.body.command;

    if(command.localeCompare("First_Name")==0){ updatecustomers(command,fname,customerID); }
    else if(command.localeCompare("Last_Name")==0){ updatecustomers(command,lname,customerID);}
    else if (command.localeCompare("Address")==0){updatecustomers(command,address,customerID);}
    else if(command.localeCompare("info")==0){updatecustomers(command,report,customerID);}
    
    res.send(JSON.stringify("done"));

    
    
    
    
    

}); 


function updatestaff(command,name,ID){

    if(command.localeCompare("info")== 0){
        
        var statement = `UPDATE Customers SET ${command} = CONCAT(${command},'\n\n${name}') WHERE ID = ${ID};`;
        console.log("report: "+ statement);
    }
        
    else{var statement = `UPDATE Customers SET ${command} = '${name}' WHERE ID = ${ID};`;
        console.log("name :"+ statement);

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
    var staffID = req.body.staffID;
    var fname = req.body.fname;
    var lname = req.body.lname;
    var address = req.body.address;
    var command = req.body.command;

    if(command.localeCompare("First_Name")==0){ updatestaff(command,fname,staffID); }
    else if(command.localeCompare("Last_Name")==0){ updatestaff(command,lname,staffID);}
    else if (command.localeCompare("Address")==0){updatestaff(command,address,staffID);}
    
    
    res.send(JSON.stringify("done"));

}); 


app.get('/Update_staff', function(req, res) {

    if (req.session.loggedin) {
        res.sendFile(path.join(__dirname, '/pages/Update_staff.html'));
    
		
	} else {
        res.sendFile(path.join(__dirname, '/pages/login.html'));
		
	}
  
    
    
  }); 


app.get('/Update_customer', function(req, res) {

    if (req.session.loggedin) {
        res.sendFile(path.join(__dirname, '/pages/Update_customer.html'));
    
		
	} else {
        res.sendFile(path.join(__dirname, '/pages/login.html'));
		
	}
    
    
  }); 


  

  app.get('/Displaystaff', function(req, res) {

    if (req.session.loggedin) {
        res.sendFile(path.join(__dirname, '/pages/DisplayStaff.html'));
    
		
	} else {
        res.sendFile(path.join(__dirname, '/pages/login.html'));
		
	}
    
    
  }); 

  app.get('/DisplayCustomer', function(req, res) {


    if (req.session.loggedin) {
        res.sendFile(path.join(__dirname, '/pages/DisplayCustomer.html'));
    
		
	} else {
        res.sendFile(path.join(__dirname, '/pages/login.html'));
		
	}
   
  }); 


app.get('/CBO_staff', function(req, res) {

    if (req.session.loggedin) {
        res.sendFile(path.join(__dirname, '/pages/CBO_staff.html'));
		
	} else {
        res.sendFile(path.join(__dirname, '/pages/login.html'));
		
	}
    
    
  }); 


app.get('/CBO_admin', function(req, res) {
    if (req.session.loggedin) {
        res.sendFile(path.join(__dirname, '/pages/CBO_admin.html'));
		
	} else {
        res.sendFile(path.join(__dirname, '/pages/login.html'));
		
	}
	

    
  });

  app.get('/login', function(req, res) {
    res.sendFile(path.join(__dirname, '/pages/login.html'));

    
  });


  app.get('/logout', function(req, res) {
    console.log("here");
    req.session.destroy();
    res.send('done');

    
  });

app.get('/', function(req, res) {
    
    res.sendFile(path.join(__dirname, '/pages/login.html'));
  });


app.use('/', express.static(path.join(__dirname + "pages")))

app.listen(PORT,HOST);
console.log("up!");