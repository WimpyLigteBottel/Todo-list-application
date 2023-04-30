import React from "react";
import "./Task.css";

function Task(props) {
  let tasks = props.tasks;

  return (
    <div>
      {tasks.map((task, index) => (
        <div key={task.id}>
          <input
            key={task.id + "input"}
            className={"css-input " + (task.completed ? "completed" : "")}
            defaultValue={task.message}
            onChange={(e) => props.callback(index, e.target.value)}
          />
          <div>
            <button
              key={task.id + "check"}
              className="coolButton"
              onClick={() => {
                props.callbackCheck(task);
              }}
            >
              Check
            </button>

            <button
              key={task.id + "remove"}
              className="coolButton"
              onClick={() => props.callbackRemove(task)}
            >
              Remove
            </button>

            <button
              key={task.id + "update"}
              className="coolButton"
              onClick={() => props.callback(index, task.message)}
            >
              Update
            </button>
            <br />
            <br />
          </div>
        </div>
      ))}
    </div>
  );
}

export default Task;
