import "./App.css";
import Search from "./search/Search";
import Radio from "./radio/Radio";
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

  async function updateTasks(result) {
    setTasks(await result);
  }

  async function refresh(filterText, selectedOption) {
    console.log("parent: refresh");
    let result = await fetchTasks(filterText, selectedOption);
    updateTasks(result);
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
        isCompleted={selectedOption}
        onFilterChange={(event) => {
          setFilterText(event.target.value);
          refresh(event.target.value, selectedOption);
        }}
      ></Search>

      {/* Tasks is below*/}
      <div>
        {tasks.map((task, index) => (
          <div key={task.id}>
            <input
              key={task.id + "input"}
              className={"css-input " + (task.completed ? "completed" : "")}
              defaultValue={task.message}
              onChange={(e) => {
                tasks[index].message = e.target.value;
                updateTask(tasks[index]);
              }}
            />

            <div>
              <button
                key={task.id + "check"}
                className="coolButton"
                onClick={() => {
                  check(task);
                }}
              >
                Check
              </button>

              <button
                key={task.id + "remove"}
                className="coolButton"
                onClick={() => remove(task)}
              >
                Remove
              </button>

              <button
                key={task.id + "update"}
                className="coolButton"
                onClick={() => updateTask(task)}
              >
                Update
              </button>
            </div>
            <br />
            <br />
          </div>
        ))}
      </div>
    </div>
  );
}

export default App;
