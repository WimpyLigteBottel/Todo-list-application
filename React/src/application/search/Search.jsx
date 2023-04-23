import "./Search.css";
import { fetchTasks } from "../core/TaskService"; 
import { useEffect, useState } from "react";

async function searchTest(filterText, isCompleted) {
  return await fetchTasks(filterText, isCompleted);
}


function Search(data) {
  let [filterText, setFilterText] = useState('');
  let [isCompleted] = useState('');


  // used to call once to load all the data
  // eslint-disable-next-line
  useEffect((filterText,isCompleted) => {
    let tasks = searchTest(filterText, isCompleted);
    data.callBack(tasks);
  }, [])// eslint-disable-next-line

  
  return (
    <div>
      <input id="searchBar"
        className="css-input"
        value={filterText}
        onChange={e => setFilterText(e.target.value)}
      />
      <button className="coolButton" onClick={() => {
        let tasks = searchTest(filterText, isCompleted);
        data.callBack(tasks);
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
