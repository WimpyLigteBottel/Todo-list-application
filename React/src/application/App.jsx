import "./App.css";
import Search from "./search/Search";
import Task from "./task/Task"
import { useState } from "react";





function App() {

  const [tasks, setTasks] = useState([]);
  
  async function updateTasks(result) {
    setTasks(await result)
  }
  

  return (
    <div>
      <Search callBack={updateTasks}></Search>
      <Task tasks={tasks}></Task>
    </div>
  );
}

export default App;
