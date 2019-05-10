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
                    breakpoint: 480,
                    options: {
                        chart: {
                            width: 200
                        },

                    }
                }],
                plotOptions: {
                    pie: {
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
            <div id="chart">
                <ReactApexChart options={this.state.options} series={this.state.series} type="donut" />
            </div>
        );
    }
}


