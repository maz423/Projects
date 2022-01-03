import React from 'react';
import {useState} from 'react';
import {Nav_admin} from './Nav_admin';
import {Nav_staff} from './Nav_staff';

export const Remove_customer = (props) => {

    const [ID,setID] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault();
        const id = ID;
       
        var params = 'ID=' + ID;
        fetch('http://localhost:8000/removecust',{
            method:'POST',
            headers: {"Content-Type": 'application/x-www-form-urlencoded'},
            body: params
        }).then(() =>{
          props.set(0);
          props.set(1);
          alert("Member removed Succesfully");
            
        })
        
    
    
    }
        
    
return (

<div>
    <Nav_staff/>
    <h1> Enter ID to remove Customer </h1>
    
    

        <form onSubmit={handleSubmit}>
        <label for="custID">User ID:</label><br></br>
        <input type="text" id="custID" value={ID} onChange={(e)=>setID(e.target.value)} name="User ID"></input> 
        {<button> Delete Member  </button>}
        </form>
</div>

   
    

);



}