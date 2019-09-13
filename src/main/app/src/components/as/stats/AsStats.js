import React from "react";
import "./../../../App.css";
import PieChart from "./../../charts/PieChart";

const AsStats = props => {
  return (
    <div>
      <h3>AS{props.asnumber}</h3>
      <h4>{props.asProperties.name}</h4>
      <Child description={props.asProperties.description} />
      <PieChart
        labels={extractArray(props.topConnect.maxNeighbours, 0)}
        series={extractArray(props.topConnect.maxNeighbours, 1)}
      ></PieChart>
      <p>Autonomus system registrated in {props.countryName}</p>
    </div>
  );
};

const Child = ({ description }) => (
  <div>
    {description.split(";").map((txt, index) => (
      <p key={index}>{txt}</p>
    ))}
  </div>
);

export default AsStats;

const extractArray = (maxNeighbours, position) => {
  if (maxNeighbours == null) {
    return [];
  }
  var myArray = [];
  var array = Object.entries(maxNeighbours).sort(function(a, b) {
    // Order by num of neighbours
    return b[1] - a[1];
  });

  if (array.length > 10) {
    for (let index = 0; index < 10; index++) {
      const element = array[index];
      myArray.push(element[position]);
    }
    if (position === 0) {
      myArray.push("Others");
    } else {
      let val = 0;
      for (let index = 10; index < array.length; index++) {
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
  console.log(myArray);
  return myArray;
};
