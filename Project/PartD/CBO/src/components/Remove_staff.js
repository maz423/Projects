import './Nav_admin.css';

import {useState} from 'react';

import React from 'react';
import {Nav_admin} from './Nav_admin';


export const Remove_staff = (props) => {
    const [ID,setID] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault();
        const id = ID;
       
        var params = 'ID=' + ID;
        fetch('http://localhost:8000/removestaff',{
            method:'POST',
            headers: {"Content-Type": 'application/x-www-form-urlencoded'},
            body: params
        }).then(() =>{
          props.set(0);
          props.set(1);
          alert("Member removed Succesfuly");
            
        })
        
    
    
    }
        
    
return (

<div>
    <Nav_admin/>
    <h1> Enter ID to remove Staff Member </h1>
    
    

        <form onSubmit={handleSubmit}>
        <label for="staffID">User ID:</label><br></br>
        <input type="text" id="staffID" value={ID} onChange={(e)=>setID(e.target.value)} name="User ID"></input> 
        {<button> Delete Member  </button>}
        </form>
</div>

   
    

);



}