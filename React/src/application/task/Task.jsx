import "./Task.css";
import {updateTask} from "../core/TaskService";
import React, { useState } from "react";


function update() {
  alert('update')
}

function remove() {
  alert('remove')
}

function check(task) {
  task.completed = !task.completed
  updateTask(task)

  console.log('updated task ' + JSON.stringify(task))
}



function Task(data) {

  let tasks = data.tasks
  
  return (
    <div>
      {
        tasks.map((task) => 
        (
          <div key={task.id}>
            <input key={task.id + 'input'} className={`css-input ${task.completed ? 'completed' : ''}`} value={task.message} onChange={e=> task.message = e.target.value}/>
            <button key={task.id + 'check'} onClick={() => {
              check(task)
              data.callBack()
            }} className="coolButton">Check</button>
            <button key={task.id + 'remove'} onClick={()=>remove()} className="coolButton">Remove</button>
            <button key={task.id + 'update'} onClick={()=>update()} className="coolButton">Update</button>
          </div>
        ))
      }
     </div>
  );
}

export default Task;