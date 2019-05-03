import React, { Component } from 'react';
import './App.css';

class CountryStats extends Component {

    constructor(props) {
        super(props);
        this.state = {
            asNums: [],
            countryName: '',
            isLoading: true
        };
    }

    componentDidMount() {
        this.setState({ isLoading: true });

        fetch('/graphas/api/countryStats/' + this.props.countryCode)
            .then(response => response.json())
            .then(data => this.setState({ countryName: data.country, asNums: data.asNums, isLoading: false }));
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
                <p>
                    Top connected Autonomus system in country:<br />
                    {this.props.topConnect.maxNode.label}  ({this.props.topConnect.maxNode.value})
                </p>
                <Child countryName={this.state.countryName} maxNeighbours={this.props.topConnect.maxNeighbours} />
            </div>

        );
    }
}

const Child = ({ countryName, maxNeighbours }) => (
    <div>
        Most connections with:<br />
        {Object.entries(maxNeighbours)
            .sort(function (a, b) {
                // Order by num of neighbours
                return b[1] - a[1];
            })
            .filter(function (value, index, arr) {
                // remove current country
                return value[0] !== countryName;
            })
            .splice(0, 5)
            .map((txt, index) =>
                <span key={index}>{txt[0]}: {txt[1]}<br /></span>)}
        {console.log(Object.entries(maxNeighbours))}
    </div>
)

export default CountryStats;