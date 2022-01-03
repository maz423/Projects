import React, { Component } from 'react';
import {Post} from "./Post";

export const ShowPosts = (props) => {

    

return (

   
<section>
<h1>   Welcome </h1>
{props.get.map(post => (
   <Post ID={post.ID} fname={post.First_Name} lname = {post.lname} Address={post.Data}/>
))}



</section>


);


}