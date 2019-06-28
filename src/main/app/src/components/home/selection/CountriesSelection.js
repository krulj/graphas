import React from "react";
import "../../../App.css";
import SimpleMap from "../../../containers/home/selection/SimpleMap";
import SearchBar from "../../../containers/home/selection/SearchBar";
import Loading from "../../helper/Loading";


function CountriesSelection(props) {
  if (props.isLoading) {
    return (
      <Loading />
    );
  }

  return (
    <div className="App-container-div">
      <div className="App-left-div">
        <SearchBar items={props.countries} />
      </div>
      <div className="App-right-div">
        <SimpleMap handleClick={props.handleClick}/>
      </div>
    </div>
  );
}

export default CountriesSelection;
