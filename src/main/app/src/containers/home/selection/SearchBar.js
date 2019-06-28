import React, { useState } from "react";
import { Button, Input } from "reactstrap";
import "../../../App.css";
import { withRouter } from "react-router-dom";

function SearchBar(props) {
  const [filtered, setFilterd] = useState([]);

  const handleCountrySelection = evt => {
    console.log(evt.target);
    var countryId = evt.target.getAttribute("data-country");
    props.history.push("/graph/" + countryId.substr(0, 3));
  };

  const handleChange = e => {
    // Variable to hold the original version of the list
    let currentList = [];
    // Variable to hold the filtered list before putting into state
    let newList = [];
    let counter = 0;

    // If the search bar isn't empty
    if (e.target.value !== "") {
      // Assign the original list to currentList
      currentList = props.items;

      // Use .filter() to determine which items should be displayed
      // based on the search terms
      newList = currentList.filter(item => {
        // change current item to lowercase
        const lc = item.toLowerCase();
        // change search term to lowercase
        const filter = e.target.value.toLowerCase();
        // check to see if the current list item includes the search term
        // If it does, it will be added to newList. Using lowercase eliminates
        // issues with capitalization in search terms and search content
        var include = lc.includes(filter);
        if (include && counter < 3) {
          counter++;
          return include;
        }
        return false;
      });
    }
    // Set the filtered state based on what our rules added to newList
    setFilterd(newList);
  };

  return (
    <div>
      <Input
        type="text"
        className="App-input"
        onChange={handleChange}
        placeholder="Search..."
      />

      {filtered.map(item => (
        <Button
          key={item}
          data-country={item}
          className="App-button"
          onClick={handleCountrySelection}
        >
          {item}
        </Button>
      ))}
    </div>
  );
}

export default withRouter(SearchBar);
