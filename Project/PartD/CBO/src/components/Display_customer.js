import React from 'react';
import {Post} from "./Post";

import {Nav_staff} from './Nav_staff';

export const Display_customer = (props) => {

return (

<div>
<Nav_staff/>

<section>
<h1>   Customers found: </h1>
 {props.get.map(post => (
   <Post   ID={post.ID} fname={post.First_Name} lname = {post.Last_Name} address={post.Address}  date={post.Date}/>
))}



</section> 


</div> 


);


}