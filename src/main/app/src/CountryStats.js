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
                    Top connected Autonomus system in country:<br/>
                    {this.props.topConnect.maxNode.label}  ({this.props.topConnect.maxNode.value})
                </p>
                <p>
                    Most connections with:<br/>
                    {this.props.topConnect.maxNeighbours}  ({this.props.topConnect.maxNeighbours})
                </p>
            </div>

        );
    }
}

export default CountryStats;