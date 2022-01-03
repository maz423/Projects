import React from 'react';
import {useState} from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';

import {ShowPosts} from './ShowPosts';
import {AddPosts} from './AddPosts';
import {admin} from './Nav_admin';

import {Post} from "./Post"

export const Nav_staff = (props) => {

return (

<div className="Nav_staff">
    
<header className="Nav_staff-header">


        <Link to="/Addcustomer">  <button className='b1'> Add Cutsomer </button> </Link>
        <Link to="/Removecustomer">  <button> Remove Customer </button> </Link>
        <Link to="/Updatecustomer">  <button> Update Customer  </button>   </Link>
        <Link to="/Displaycustomer">  <button> Display Customer </button>   </Link>



</header>


</div> 


);


}