

import React from 'react';
import {useState} from 'react';
import { useNavigate } from 'react-router-dom';

import {ShowPosts} from './ShowPosts';
import {AddPosts} from './AddPosts';
import {Nav_admin} from './Nav_admin';
import {staff} from './Nav_staff';
import {Post} from "./Post"

import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';

export const Login = (props) => {

const [ID,setID] = useState('');
const [password,setPassword] = useState('');


const navigate = useNavigate();

const handleSubmitClick = (e) => {
      e.preventDefault();
      const data = {ID,password};
      var params = 'ID=' + ID + " " + '&password=' + password;

      const res = fetch('http://localhost:8000/login',{
        method:'POST',
        headers: {"Content-Type": 'application/x-www-form-urlencoded'},
        body: params
    }).then((response) => response.json())
    .then(data => {
      if(data.role === 'admin'){
       props.set(true);
       navigate('/admin');
       
        alert("Welcome admin");
      }
      
      else if(data.role === 'staff'){
        props.set(true);
        
        navigate('/staff');
        alert('Welcome Staff')}

      else {
        console.log(data);
        alert('incorrect username or password')};

      return data.role;
    })
    
    

    }



return (
<div className='Login'>
<h3> Welcome to Addiction council. </h3>
<p className='para'>
        
        Please Login wih your registered ID and Password to continue.
    </p>  
    <form onSubmit={handleSubmitClick}>
      <label for="ID">User ID:</label>
      <input type="text" id="ID" value={ID} onChange={(e)=>setID(e.target.value)} name="User ID"></input><br></br><br></br>
      <label for="password">Password:</label>
      <input type="password" id="password" value={password} onChange={(e)=>setPassword(e.target.value)} name="password"></input><br></br><br></br>
      {<button> Submit  </button>}
      </form>
    
    
    
    
 

    
 </div>
);


}