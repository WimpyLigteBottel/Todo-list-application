import "./Search.css";
import { fetchTasks } from "../core/TaskService";

async function findAllTasks(filterText, isCompleted) {
  return await fetchTasks(filterText, isCompleted);
}

function findTasksAndSendToParent(data, filterText, isCompleted) {
  let tasks = findAllTasks(filterText, isCompleted);
  data.onFilterChange(tasks);
}

function Search(data) {
  let filterText = data.filterText;
  let isCompleted = data.selectionOption;

  return (
    <div>
      <input
        id="searchBar"
        className="css-input"
        defaultValue={filterText}
        onChange={data.onFilterChange}
      />
      <br />
      <button
        className="coolButton"
        onClick={() => {
          findTasksAndSendToParent(data, filterText, isCompleted);
        }}
      >
        Search
      </button>
      <button
        className="coolButton"
        onClick={() => {
          filterText = "";
          findTasksAndSendToParent(data, "", isCompleted);
        }}
      >
        Clear
      </button>
      <br />
      <br />
    </div>
  );
}

export default Search;
