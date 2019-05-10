import React, { Component } from 'react';
import './App.css';
import PieChart from './charts/PieChart';

class CountryStats extends Component {

    constructor(props) {
        super(props);
        this.state = {
            asNums: [],
            countryName: '',
            isLoading: true
        };
        this.extractArray = this.extractArray.bind(this);
    }

    componentDidMount() {
        this.setState({ isLoading: true });

        fetch('/graphas/api/countryStats/' + this.props.countryCode)
            .then(response => response.json())
            .then(data => this.setState({ countryName: data.country, asNums: data.asNums, isLoading: false }));
    }

    extractArray(countryName, maxNeighbours, position) {
        var myArray = [];
        var array = Object.entries(maxNeighbours)
            .sort(function (a, b) {
                // Order by num of neighbours
                return b[1] - a[1];
            })
            .filter(function (value, index, arr) {
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
        console.log(myArray);
        return myArray;
    }

    extractConnectionArray(maxNeighbours, position) {
        var myArray = [];
        var array = maxNeighbours
            .sort(function (a, b) {
                // Order by num of neighbours
                return b[1] - a[1];
            })

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
        console.log(myArray);
        return myArray;
    }

    render() {
        if (this.state.isLoading === true) {
            return (
                <div>
                    <p>
                        .
                    </p>
                </div>

            )
        }
        return (
            <div>
                <h2>
                    {this.state.countryName}
                </h2>
                <p>
                    Total number of Autonomus system registred in {this.state.countryName}: {this.state.asNums.length}
                </p>
                <PieChart labels={this.extractConnectionArray(this.props.topConnect.maxNode, 0)}
                    series={this.extractConnectionArray(this.props.topConnect.maxNode, 1)}>
                </PieChart>
                <p>
                    Top connected countries:
                </p>
                <PieChart labels={this.extractArray(this.state.countryName, this.props.topConnect.maxNeighbours, 0)}
                    series={this.extractArray(this.state.countryName, this.props.topConnect.maxNeighbours, 1)}>
                </PieChart>
            </div>

        );
    }
}

export default CountryStats;