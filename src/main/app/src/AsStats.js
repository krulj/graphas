import React, { Component } from 'react';
import './App.css';
import PieChart from './charts/PieChart';

class AsStats extends Component {

    constructor(props) {
        super(props);
        this.state = {
            asnumber: 0,
            countryName: '',
            asProperties: [],
            isLoading: true
        };
        this.loadComponentData = this.loadComponentData.bind(this);
    }

    componentDidMount() {
        this.loadComponentData();
    }

    componentDidUpdate(prevProps, prevState) {
        // only update chart if the data has changed
        if (prevProps.asnumber !== this.props.asnumber) {
            this.loadComponentData();
        }
    }

    loadComponentData() {
        var asnumber = this.props.asnumber;
        this.setState({ isLoading: true, asnumber: asnumber });
        fetch('/graphas/api/asnumber/' + asnumber)
            .then(response => response.json())
            .then(data => this.setState({ countryName: data.country, asProperties: data.asProperties, isLoading: false }));
    }
  
    extractArray(maxNeighbours, position) {
        var myArray = [];
        var array = Object.entries(maxNeighbours)
            .sort(function (a, b) {
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
    }

    render() {
        if (this.state.isLoading === true) {
            return (
                <div>
                    <p>
                        Loading ...
                    </p>
                </div>
            )
        }
        console.log(this.state);
        return (
            <div>
                <h3>
                AS{this.state.asnumber}
                </h3>
                <h4>
                    {this.state.asProperties.name}
                </h4>
                <Child description={this.state.asProperties.description}/>
                <PieChart labels={this.extractArray(this.props.topConnect.maxNeighbours, 0)}
                    series={this.extractArray(this.props.topConnect.maxNeighbours, 1)}>
                </PieChart>
                <p>
                    Autonomus system registrated in {this.state.countryName}
                </p>
            </div>

        );
    }
}

const Child = ({ description }) => (
    <div>
        {description.split(';').map((txt, index) => <p key={index}>{txt}</p>)}
    </div>
)


export default AsStats;