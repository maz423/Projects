import React from 'react';

export const Post = (props) => {

return (

<div>
    <h5> ID: {props.ID} , &nbsp; Name: {props.fname}, &nbsp; {props.lname}, &nbsp; Address: {props.address}, &nbsp; Role: {props.role}, &nbsp; Registration Date: {props.date}. </h5> 



</div> 


);


}