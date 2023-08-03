import "./Search.css";
import {useEffect, useState} from "react";

function Search(data) {
    const [searchFilterText, setSearchFilterText] = useState("");


    useEffect(() => {
        data.onFilterChange(searchFilterText)
    }, [searchFilterText]);

    return (
        <div>
            <input
                id="searchBar"
                className="css-input"
                value={searchFilterText}
                type="text"
                onChange={(event) => {
                    setSearchFilterText(event.target.value)
                }}
            />
            <br/>
            <button
                className="coolButton"
                onClick={() => {
                    setSearchFilterText(searchFilterText)
                }}
            >
                Search
            </button>
            <button
                className="coolButton"
                onClick={() => {
                    setSearchFilterText("")
                }}
            >
                Clear
            </button>
            <br/>
            <br/>
        </div>
    );
}

export default Search;
