import React, {useEffect, useState} from "react";
import "./Task.css";
import {removeTask, updateTask} from "../core/TaskService";

function Task(props) {
    const [initiateReload, setInitiateReload] = useState(false)

    useEffect(() => {
        if (initiateReload) {
            props.callbackUpdateParentTasks()
            setInitiateReload(false)
        }
    }, [initiateReload])

    return (
        <div>
            {props.tasks.map((task, index) => (
                <div key={task.id}>
                    <input
                        key={task.id + "input"}
                        className={"css-input " + (task.completed ? "completed" : "")}
                        defaultValue={task.message}
                        onChange={(e) => {
                            let singleTask = props.tasks[index]
                            singleTask.message = e.target.value
                            updateTask(singleTask).then(event => setInitiateReload(true))
                        }}
                    />
                    <div>
                        <button
                            key={task.id + "check"}
                            className="coolButton"
                            onClick={(event) => {
                                let singleTask = props.tasks[index]
                                singleTask.completed = !task.completed;
                                updateTask(singleTask).then(event => setInitiateReload(true))
                            }}
                        >
                            Check
                        </button>

                        <button
                            key={task.id + "remove"}
                            className="coolButton"
                            onClick={() => {
                                let singleTask = props.tasks[index]
                                removeTask(singleTask.id).then(event => setInitiateReload(true))
                            }}
                        >
                            Remove
                        </button>

                        <button
                            key={task.id + "update"}
                            className="coolButton"
                            onClick={(e) => {
                                setInitiateReload(true)
                            }}
                        >
                            Update
                        </button>
                        <br/>
                        <br/>
                    </div>
                </div>
            ))}
        </div>
    );
}

export default Task;
