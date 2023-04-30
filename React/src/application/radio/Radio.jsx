import React from "react";

function RadioButtonGroup(props) {
  return (
    <div>
      <label>
        <input
          type="radio"
          value=""
          checked={props.selectedOption === ""}
          onChange={props.handleChange}
        />
        All
      </label>
      <br />
      <label>
        <input
          type="radio"
          value="true"
          checked={props.selectedOption === "true"}
          onChange={props.handleChange}
        />
        completed
      </label>
      <br />
      <label>
        <input
          type="radio"
          value="false"
          checked={props.selectedOption === "false"}
          onChange={props.handleChange}
        />
        Uncompleted
      </label>
    </div>
  );
}

export default RadioButtonGroup;
