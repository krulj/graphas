import React from "react";
import PieChart from "../../charts/PieChart";
import Loading from "../../helper/Loading";

export default function CountryStats(props) {
  if (props.isLoading === true) {
    return <Loading />;
  }

  let maxNodeLabels = extractConnectionArray(props.maxNode, 0);
  let maxNodeSeries = extractConnectionArray(props.maxNode, 1);

  let maxNeighLabels = extractArray(props.countryName, props.maxNeighbours, 0);
  let maxNeighSeries = extractArray(props.countryName, props.maxNeighbours, 1);

  return (
    <div>
      <h2>{props.countryName}</h2>
      <p>
        Total number of Autonomus system registred in {props.countryName}:{" "}
        {props.asNums.length}
      </p>
      <PieChart labels={maxNodeLabels} series={maxNodeSeries}></PieChart>
      <p>Top connected countries:</p>
      <PieChart labels={maxNeighLabels} series={maxNeighSeries}></PieChart>
    </div>
  );
}

function extractArray(countryName, maxNeighbours, position) {
  if (maxNeighbours == null) {
    return [];
  }
  var myArray = [];
  var array = Object.entries(maxNeighbours)
    .sort(function(a, b) {
      // Order by num of neighbours
      return b[1] - a[1];
    })
    .filter(function(value, index, arr) {
      // remove current country
      return value[0] !== countryName;
    });

  if (array.length > 5) {
    for (let index = 0; index < 5; index++) {
      const element = array[index];
      myArray.push(element[position]);
    }
    if (position === 0) {
      myArray.push("Others");
    } else {
      let val = 0;
      for (let index = 5; index < array.length; index++) {
        const element = array[index];
        val = val + element[position];
      }
      myArray.push(val);
    }
  } else {
    for (let index = 0; index < array.length; index++) {
      const element = array[index];
      myArray.push(element[position]);
    }
  }
  return myArray;
}

function extractConnectionArray(maxNeighbours, position) {
  if (maxNeighbours == null) {
    return [];
  }
  var myArray = [];
  var array = maxNeighbours.sort(function(a, b) {
    // Order by num of neighbours
    return b[1] - a[1];
  });

  if (array.length > 5) {
    for (let index = 0; index < 5; index++) {
      const element = array[index];
      myArray.push(element[position]);
    }
    if (position === 0) {
      myArray.push("Others");
    } else {
      let val = 0;
      for (let index = 5; index < array.length; index++) {
        const element = array[index];
        val = val + element[position];
      }
      myArray.push(val);
    }
  } else {
    for (let index = 0; index < array.length; index++) {
      const element = array[index];
      myArray.push(element[position]);
    }
  }
  return myArray;
}
