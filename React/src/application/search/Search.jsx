import "./Search.css";
import { fetchTasks } from "../core/TaskService"; 
import { useState } from "react";

async function findAllTasks(filterText, isCompleted) {
  return await fetchTasks(filterText, isCompleted);
}


function findTasksAndSendToParent(data, filterText, isCompleted) {
  let tasks = findAllTasks(filterText, isCompleted);
  data.callBack(tasks);
}



function Search(data) {
  let [filterText, setFilterText] = useState('');
  let [isCompleted] = useState('');


  return (
    <div>
      <input id="searchBar"
        className="css-input"
        value={filterText}
        onChange={e => {
          setFilterText(e.target.value)
          findTasksAndSendToParent(data, e.target.value, isCompleted)
        }}
      />
      <button className="coolButton" onClick={() => {
        findTasksAndSendToParent(data,filterText,isCompleted)
      }}>
        Search
      </button>
      {/* <button className="coolButton">
        Clear
      </button> */}
    </div>
  );
}

export default Search;
