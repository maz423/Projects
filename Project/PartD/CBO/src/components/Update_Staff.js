import React from 'react';

import {useState} from 'react';

import {Nav_admin} from './Nav_admin';

export const Update_staff = (props) => {

    const [fname,setFname] = useState('');
    const [lname,setLname] = useState('');
    const [address,setAddress] = useState('');
    const [ID,setID] = useState('');

    const handleSubmitfname = (e) => {
        e.preventDefault();
        const id = ID;
        console.log(id);
        var command = 'First_Name';
        const post = {fname,lname,address};
        console.log("this is post");
        console.log(post);
      
        var params = 'ID=' + id +'&fname=' +fname+ '&lname=' +lname+  '&address=' +address+ '&command=' + command+ '&table=' + 'Staff';
      
        
      
        fetch('http://localhost:8000/updateStaff',{
            method:'POST',
            headers: {"Content-Type": 'application/x-www-form-urlencoded'},
            body: params
        }).then(() =>{
          props.set(0);
          props.set(1);
          alert("Posted Succesfuly");
            
        })
    }

    const handleSubmitlname = (e) => {
        e.preventDefault();

        const id = ID;
        var command = 'Last_Name';
        const post = {fname,lname,address};
        console.log("this is post");
        console.log(post);
      
        var params = 'ID=' + id +'&fname=' +fname+ '&lname=' +lname+  '&address=' +address+ '&command=' + command+ '&table=' + 'Staff';
      
        
      
        fetch('http://localhost:8000/updateStaff',{
            method:'POST',
            headers: {"Content-Type": 'application/x-www-form-urlencoded'},
            body: params
        }).then(() =>{
          props.set(0);
          props.set(1);
          alert("Posted Succesfuly");
            
        })
    }


    const handleSubmitAddress = (e) => {
        e.preventDefault();
        const id = ID;
        var command = 'Address';
        const post = {fname,lname,address};
        console.log("this is post");
        console.log(post);
      
        var params = 'ID=' + id +'&fname=' +fname+ '&lname=' +lname+  '&address=' +address+ '&command=' + command+ '&table=' + 'Staff';
      
        
      
        fetch('http://localhost:8000/updateStaff',{
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

    <Nav_admin/>


<p>
    Update Staff information:
  </p><br></br>

  <form onSubmit={handleSubmitfname}>
    <label for="ID">Staff ID:</label> &nbsp;&nbsp;
       <input type="text" id="ID" value={ID} onChange={(e)=>setID(e.target.value)} name="First Name"></input>  
       </form><br></br>
   <form onSubmit={handleSubmitfname}>
    <label for="fname">First Name:</label> &nbsp;&nbsp;
       <input type="text" id="fname" value={fname} onChange={(e)=>setFname(e.target.value)} name="First Name"></input>  {<button> Update  </button>}
       </form><br></br>
       <form onSubmit={handleSubmitlname}>
    <label for="lname">Last Name:</label>  &nbsp;&nbsp;  
       <input type="text" id="lname" value={lname} onChange={(e)=>setLname(e.target.value)} name="Last Name"></input> {<button> Update  </button>}
       </form><br></br>
    <form onSubmit={handleSubmitAddress}>
    <label for="address">Address:</label>   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       <input type="text" id="address" value={address} onChange={(e)=>setAddress(e.target.value)} name="Address"></input> {<button> Update  </button>}
       </form><br></br>




</div> 


);


}