import React from 'react';
import {Nav_admin} from './Nav_admin';
import {Post} from "./Post";

export const Display = (props) => {

return (

<div >
<Nav_admin/>

<section>
<h1>   Staff members found: </h1>
 {props.get.map(post => (
   <Post   ID={post.ID} fname={post.First_Name} lname = {post.Last_Name} address={post.Address} role={post.role} date={post.Date}/>
))}



</section>
           

        
        
        
      </div>

);


}