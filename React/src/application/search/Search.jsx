import "./Search.css";

function Search(data) {
  let filterText = data.filterText;

  return (
    <div>
      <input
        id="searchBar"
        className="css-input"
        defaultValue={filterText}
        onChange={data.onFilterChange}
      />
      <br />
      <button
        className="coolButton"
        onClick={() => {
          data.callbackSearch();
        }}
      >
        Search
      </button>
      <button
        className="coolButton"
        onClick={() => {
          filterText = "";
          data.callbackClear();
        }}
      >
        Clear
      </button>
      <br />
      <br />
    </div>
  );
}

export default Search;
