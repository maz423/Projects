import React from 'react';
import {useState} from 'react';
import App from '../App';
export const AddPosts = (props) => {

const [topic,setTopic] = useState('');
const [data,setData] = useState('');



const handleSubmit = (e) => {
  e.preventDefault();
  const post = {topic,data};
  
  console.log(post);

  var params = 'topic=' + topic + " " + '&data=' + data;

  

  fetch('http://localhost:8000/add',{
      method:'POST',
      headers: {"Content-Type": 'application/x-www-form-urlencoded'},
      body: params
  }).then(() =>{
    props.set(1);
    props.set(0);
    alert("Posted Succesfuly");
      
  })
  

}


return (

<div className="add"> 
<form onSubmit={handleSubmit}>    
<label for="topic">Topic:</label>
<input type="text" id="topic" value={topic} onChange={(e)=>setTopic(e.target.value)} name="topic"></input><br></br>
<label for="data">Data:</label>
<input type="text" id="data" value={data} onChange={(e)=>setData(e.target.value)} name="data"></input><br></br>
{<button> Submit Post </button>}
</form>
</div>

);


}