import ReactApexChart from "react-apexcharts";
import React from 'react';


export default class PieChart extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            options: {
                labels: this.props.labels,
                legend: {
                    position: 'bottom',
                    horizontalAlign: 'center',
                },
                responsive: [{
                    breakpoint: 480
                }],
                plotOptions: {
                    pie: {
                        offsetX: 0,
                        offsetY: 40,
                        dataLabels: {
                            offset: 0
                        },
                        donut: {
                            size: '60%',
                            labels: {
                                show: true,
                                name: {
                                    offsetY: -5
                                },
                                value: {
                                    offsetY: 5,
                                },
                                total: {
                                    show: true
                                }
                            }
                        }
                    },

                }
            },
            series: this.props.series,
        }
    }

    render() {
        return (
            <div id="chart" style={{overflow: 'hidden'}}>
                <ReactApexChart options={this.state.options} series={this.state.series} type="donut" />
            </div>
        );
    }
}


