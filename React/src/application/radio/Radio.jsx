import {useEffect, useState} from "react";

function RadioButtonGroup(props) {
    const [localSelectedOption, setLocalSelectedOption] = useState("");

    useEffect(() => {
        props.callbackSelectedOption(localSelectedOption)
    }, [localSelectedOption]);
    return (
        <div>
            <label>
                <input
                    type="radio"
                    value=""
                    checked={props.selectedOption === ""}
                    onChange={event => {
                        setLocalSelectedOption("")
                    }}
                />
                All
            </label>
            <br/>
            <label>
                <input
                    type="radio"
                    value="true"
                    checked={props.selectedOption === "true"}
                    onChange={event => {
                        setLocalSelectedOption("true")
                    }}
                />
                Completed
            </label>
            <br/>
            <label>
                <input
                    type="radio"
                    value="false"
                    checked={props.selectedOption === "false"}
                    onChange={event => {
                        setLocalSelectedOption("false")
                    }}
                />
                Uncompleted
            </label>
        </div>
    );
}

export default RadioButtonGroup;
