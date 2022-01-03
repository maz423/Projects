import './Nav_admin.css';

import {useState} from 'react';
import {ShowPosts} from './ShowPosts';
import {AddPosts} from './AddPosts';
import {Nav_admin} from './Nav_admin';
import {staff} from './Nav_staff';
import {Display} from "./Display"

import {Remove_staff} from "./Remove_staff"
import {Post} from "./Post"


import React from 'react';

export const Add_staff = (props) => {

    const [fname,setFname] = useState('');
    const [lname,setLname] = useState('');
    const [address,setAddress] = useState('');
    const [password,setPassword] = useState(''); 
    const [role,setRole] = useState('staff'); 


    const handleSubmit = (e) => {
        e.preventDefault();
        const post = {fname,lname,address,password,role};
        console.log("this is post");
        console.log(post);
      
        var params = 'fname=' +fname+ '&lname=' +lname+  '&address=' +address+ '&password=' +password+ '&role=' +role+ '&db=' + "Staff";
      
        console.log(params)
      
        fetch('http://localhost:8000/addstaff',{
            method:'POST',
            headers: {"Content-Type": 'application/x-www-form-urlencoded'},
            body: params
        }).then(() =>{
          props.set(0);
          props.set(1);
          alert("Posted Succesfuly");
            
        })
        
      
      }

return (

<div>
    <Nav_admin/>

    <h1> Please fill the form to register a Staff member  </h1>

   
   
      <form onSubmit={handleSubmit}>
     <label for="fname">First Name:</label> &nbsp;&nbsp;
     <input type="text" id="fname" value={fname} onChange={(e)=>setFname(e.target.value)} name="First Name"></input><br></br><br></br>
  <label for="lname">Last Name:</label>  &nbsp;&nbsp;
     <input type="text" id="lname" value={lname} onChange={(e)=>setLname(e.target.value)} name="Last Name"></input><br></br><br></br>
  <label for="address">Address:</label>   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     <input type="text" id="address" value={address} onChange={(e)=>setAddress(e.target.value)} name="Address"></input><br></br><br></br>
  <label for="password">Password:</label> &nbsp;&nbsp;&nbsp;&nbsp;
     <input type="text" id="password" value={password} onChange={(e)=>setPassword(e.target.value)} name="Password"></input><br></br><br></br>
  <label for="role">Role:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
     <select value={role} onChange={(e)=>setRole(e.target.value)}>
           <option value= 'admin'>  admin  </option>  
           <option value= 'staff'>  staff </option>
     
     </select>
     {<button> Submit  </button>}
    </form>
   <br></br>

 
 

    



</div> 


);


}