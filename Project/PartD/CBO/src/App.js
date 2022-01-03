import './App.css';



import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import {useState} from 'react';
import React, { useEffect } from "react";

import {Login} from './components/Login';
import {ShowPosts} from './components/ShowPosts';
import {AddPosts} from './components/AddPosts';
import {Nav_admin} from './components/Nav_admin';
import {Nav_staff, staff} from './components/Nav_staff';
import {Display} from "./components/Display"
import {Add_staff} from "./components/Add_staff"
import {Remove_staff} from "./components/Remove_staff"
import {Post} from "./components/Post"
import { Update_staff } from './components/Update_Staff';
import {Add_customer} from "./components/Add_customer"
import {Remove_customer} from "./components/Remove_customer"
import { Update_customer } from './components/Update_customer';
import { Display_customer } from './components/Display_customer';

function App() {  
  
  
  const [change, setChange] = useState(0);
  const [changeCust, setChangeCust] = useState(0);
  const [token,setToken] = useState(false);
  const [status,setStatus] = useState('');
  const [getList, setList] = useState([]);
  const [getListCust, setListCust] = useState([]);
  useEffect(() => {   {fetch('http://localhost:8000/data').then
  (response => response.json()).then
  (response => setList(response))}; },[change]);

  useEffect(() => {   {fetch('http://localhost:8000/datacust').then
  (response => response.json()).then
  (response => setListCust(response))}; },[changeCust]);

  const handler = (e) => {
    e.preventDefault();
    if(token == false){
      alert("Already logged out")
    }
    else{
    alert("logged out");
    setToken(false);}
    
  
  }

  

  
  
  return (

    

    
    <div className="App">

           
      
      
      <header className="App-header">
      
        <p>
        <Router>

        
         
        {/* <Link to="/showPosts">  <button> Display Customer Posts </button> </Link>
        <Link to="/addPosts">  <button> Add Posts </button>   </Link>
        <Link to="/addPosts">  <button> Add Posts </button>   </Link>
        <Link to="/addPosts">  <button> Add Posts </button>   </Link> */}

            {
                !token
                ? <Login set = {setToken}/>
                : 
             
         <Routes>
         
         <Route exact path='/' element={<Login set = {token}/>} />
         <Route path="/admin" element={<Nav_admin />} />
         <Route path="/staff" element={<Nav_staff />} />
         <Route path="/Addstaff" element={ <Add_staff set = {setChange}/> } />
        <Route path="/Removestaff" element={ <Remove_staff  set = {setChange}  /> } />
        <Route path="/Updatestaff" element={ <Update_staff set = {setChange} /> } />
        <Route path="/Displaystaff" element={ <Display get = {getList} /> } />
        <Route path="/Addcustomer" element={ <Add_customer set = {setChangeCust}/> } />
        <Route path="/Removecustomer" element={ <Remove_customer set = {setChangeCust} /> } />
        <Route path="/Updatecustomer" element={ <Update_customer  set = {setChangeCust} /> } />
        <Route path="/Displaycustomer" element={ <Display_customer get = {getListCust} /> } />
        
       
           </Routes>}
         
        </Router>
        </p>
        <button onClick = {handler}> logout </button>
      </header>
      
      </div>
  );
}

export default App;

