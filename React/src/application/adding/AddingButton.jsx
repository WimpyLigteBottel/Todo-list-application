import React from "react";
import "./AddingButton.css";

function AddingButton(props) {
  return (
    <div>
      <button
        className="coolButton coolButton-green"
        onClick={() => props.callbackAddTask()}
      >
        Add
      </button>
    </div>
  );
}

export default AddingButton;
