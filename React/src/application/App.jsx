import "./App.css";
import Search from "./search/Search";
import Radio from "./radio/Radio";
import Task from "./task/Task";
import AddingButton from "./adding/AddingButton";
import {createTask, fetchTasks,} from "./core/TaskService";
import {useEffect, useState} from "react";

function addNewTask() {
    createTask();
    window.location.reload(false); // hacky way to reload
}

function App() {
    const [doCall, setDoCall] = useState(false);
    const [tasks, setTasks] = useState([]);
    const [selectedOption, setSelectedOption] = useState("");
    const [filterText, setFilterText] = useState("");

    async function refresh() {
        let result = await fetchTasks(filterText, selectedOption);
        console.log(`refresh ${filterText},${selectedOption}`, result)
        setTasks(result);
        setDoCall(false)
    }

    useEffect(() => {
        console.log("repreeat")
        if (doCall) {
            refresh()
        }
    }, [selectedOption, filterText, doCall]);

    return (
        <div>
            <Radio
                selectedOption={selectedOption}
                callbackSelectedOption={(text) => {
                    setSelectedOption(text);
                    setDoCall(true)
                }}
            ></Radio>

            {/* search button */}
            <Search
                filterText={filterText}
                onFilterChange={(value) => {
                    setFilterText(value);
                    setDoCall(true)
                }}
                callbackSearch={(event) => {
                    setDoCall(true)
                }}
            ></Search>

            <Task
                tasks={tasks}
                callbackUpdateParentTasks={() => {
                    setDoCall(true)
                }}
            ></Task>

            <AddingButton callbackAddTask={addNewTask}></AddingButton>
        </div>
    );
}

export default App;
