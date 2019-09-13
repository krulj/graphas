import ReactApexChart from "react-apexcharts";
import React from "react";

const PieChart = props => {
  const options = getOptions(props);
  return (
    <div id="chart" style={{ overflow: "hidden" }}>
      <ReactApexChart options={options} series={props.series} type="donut" />
    </div>
  );
};

export default PieChart;

const getOptions = props => {
  return {
    labels: props.labels,
    legend: {
      position: "bottom",
      horizontalAlign: "center"
    },
    responsive: [
      {
        breakpoint: 480
      }
    ],
    plotOptions: {
      pie: {
        offsetX: 0,
        offsetY: 40,
        dataLabels: {
          offset: 0
        },
        donut: {
          size: "60%",
          labels: {
            show: true,
            name: {
              offsetY: -5
            },
            value: {
              offsetY: 5
            },
            total: {
              show: true
            }
          }
        }
      }
    }
  };
};
