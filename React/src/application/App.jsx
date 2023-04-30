import "./App.css";
import Search from "./search/Search";
import Radio from "./radio/Radio";
import Task from "./task/Task";
import { fetchTasks, removeTask, updateTask } from "./core/TaskService";
import { useEffect, useState } from "react";

function check(task) {
  task.completed = !task.completed;
  updateTask(task);

  window.location.reload(false); // hacky way to reload
}

function remove(task) {
  removeTask(task.id);
  window.location.reload(false); // hacky way to reload
}

function App() {
  const [tasks, setTasks] = useState([]);
  const [selectedOption, setSelectedOption] = useState("");
  const [filterText, setFilterText] = useState("");

  async function refresh(filterText, selectedOption) {
    console.log("parent: refresh");
    let result = await fetchTasks(filterText, selectedOption);
    setTasks(result);
  }

  async function updateSpecificTask(index, newMessage) {
    console.log("parent making change");
    tasks[index].message = newMessage;
    setTasks(tasks);
    refresh(filterText, selectedOption);
  }

  function clear() {
    console.log("clear text");
    setFilterText("");
    // window.location.reload(false); // hacky way to reload
  }

  useEffect(() => {
    refresh(filterText, selectedOption);
  }, []);

  return (
    <div>
      <Radio
        selectedOption={selectedOption}
        handleChange={(event) => {
          setSelectedOption(event.target.value);
          refresh(filterText, event.target.value);
        }}
      ></Radio>

      {/* search button */}
      <Search
        filterText={filterText}
        onFilterChange={(event) => {
          setFilterText(event.target.value);
          refresh(event.target.value, selectedOption);
        }}
        callbackSearch={(event) => {
          refresh(filterText, selectedOption);
        }}
        callbackClear={(event) => {
          clear();
        }}
      ></Search>

      <Task
        tasks={tasks}
        callback={updateSpecificTask}
        callbackRemove={remove}
        callbackCheck={check}
      ></Task>
    </div>
  );
}

export default App;
