import './Nav_admin.css';

import React from 'react';
import {useState} from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';

import {ShowPosts} from './ShowPosts';
import {AddPosts} from './AddPosts';
import {admin} from './Nav_admin';
import {staff} from './Nav_staff';
import {Post} from "./Post"

export const Nav_admin = (props) => {

return (


<div className="Nav_admin">




    
<header className="Nav_admin-header">

        
        <Link to="/Addstaff">  <button className='b1'> Add Staff </button> </Link>
        <Link to="/Removestaff">  <button> Remove Staff </button> </Link>
        <Link to="/Updatestaff">  <button> Update Staff  </button>   </Link>
        <Link to="/Displaystaff">  <button> Display Staff </button>   </Link>
        <Link to="/Addcustomer">  <button className='b1'> Add Cutsomer </button> </Link>
        <Link to="/Removecustomer">  <button> Remove Customer </button> </Link>
        <Link to="/Updatecustomer">  <button> Update Customer  </button>   </Link>
        <Link to="/Displaycustomer">  <button> Display Customer </button>   </Link>
       


</header>


</div> 


);


}