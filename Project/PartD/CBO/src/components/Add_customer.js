import React from 'react';
import {useState} from 'react';

import {Nav_staff} from './Nav_staff';

export const Add_customer = (props) => {

    const [fname,setFname] = useState('');
    const [lname,setLname] = useState('');
    const [address,setAddress] = useState('');
    const [password,setPassword] = useState(''); 
    const [info,setInfo] = useState(''); 


    const handleSubmit = (e) => {
        e.preventDefault();
        const post = {fname,lname,address,password,info};
        console.log("this is post");
        console.log(post);
      
        var params = 'fname=' +fname+ '&lname=' +lname+  '&address=' +address + '&info=' +info+ '&db=' + "Customers";
      
        
      
        fetch('http://localhost:8000/addstaff',{
            method:'POST',
            headers: {"Content-Type": 'application/x-www-form-urlencoded'},
            body: params
        }).then(() =>{
          props.set(0);
          props.set(1);
          alert("Posted Succesfuly");
            
        })
        
      
      }

return (

<div>

   

{
               //  props.get[0].info 
               //  ? <Login set = {setToken}/>
               //  : <Nav_admin/>
}

<Nav_staff/>
    <h1> Please fill the form to register a Staff member  </h1>

   
   
      <form onSubmit={handleSubmit}>
     <label for="fname">First Name:</label> &nbsp;&nbsp;
     <input type="text" id="fname" value={fname} onChange={(e)=>setFname(e.target.value)} name="First Name"></input><br></br><br></br>
  <label for="lname">Last Name:</label>  &nbsp;&nbsp;
     <input type="text" id="lname" value={lname} onChange={(e)=>setLname(e.target.value)} name="Last Name"></input><br></br><br></br>
  <label for="address">Address:</label>   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     <input type="text" id="address" value={address} onChange={(e)=>setAddress(e.target.value)} name="Address"></input><br></br><br></br>
  <label for="info">Report:</label> &nbsp;&nbsp;&nbsp;&nbsp;
     <input type="text" id="info" value={info} onChange={(e)=>setInfo(e.target.value)} name="info"></input><br></br><br></br>
  
     
     {<button> Submit  </button>}
    </form>
   <br></br>

 
 

    



</div> 


);


}