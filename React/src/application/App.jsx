import "./App.css";
import Search from "./search/Search";
import Radio from "./radio/Radio";
import Task from "./task/Task";
import AddingButton from "./adding/AddingButton";
import { fetchTasks } from "./core/TaskService";
import { useEffect, useState } from "react";
import axios from "axios";

function App() {
  const [doCall, setDoCall] = useState(false);
  const [tasks, setTasks] = useState([]);
  const [selectedOption, setSelectedOption] = useState("");
  const [filterText, setFilterText] = useState("");

  axios
    .get("https://master--resplendent-dodol-4300eb.netlify.app/api/hello")
    .then((response) => {
      console.log(response.data, "TESTING");
      return response.data;
    })
    .catch((error) => {
      console.error(error);
    });

  useEffect(() => {
    if (!doCall) {
      return;
    }
    fetchTasks(filterText, selectedOption).then((response) => {
      setTasks(response);
      setDoCall(false);
    });
  }, [selectedOption, filterText, doCall]);

  return (
    <div>
      <Radio
        selectedOption={selectedOption}
        callbackSelectedOption={(text) => {
          setSelectedOption(text);
          setDoCall(true);
        }}
      />

      {/* search button */}
      <Search
        filterText={filterText}
        onFilterChange={(value) => {
          setFilterText(value);
          setDoCall(true);
        }}
        callbackSearch={() => setDoCall(true)}
      />

      <Task tasks={tasks} callbackUpdateParentTasks={() => setDoCall(true)} />

      <AddingButton callbackAddTask={() => setDoCall(true)} />
    </div>
  );
}

export default App;
