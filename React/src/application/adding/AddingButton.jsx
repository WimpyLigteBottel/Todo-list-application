import React, {useEffect, useState} from "react";
import "./AddingButton.css";

import {createTask} from "../core/TaskService";

function AddingButton(props) {
    const [doCallBack, setDoCallBack] = useState(false);

    useEffect(() => {
        if (!doCallBack) {
            return
        }
        props.callbackAddTask()
        setDoCallBack(false)
    }, [doCallBack])


    return (
        <div>
            <button
                className="coolButton coolButton-green"
                onClick={() => {
                    createTask()
                    setDoCallBack(true)
                }}
            >
                Add
            </button>
        </div>
    );
}

export default AddingButton;
