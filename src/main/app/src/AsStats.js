import React, { Component } from 'react';
import './App.css';

class AsStats extends Component {

    constructor(props) {
        super(props);
        this.state = {
            asnumber: 0,
            countryName: '',
            asProperties: [],
            isLoading: true
        };
    }

    componentDidMount() {
        var asnumber = this.props.asnumber;
        this.setState({ isLoading: true, asnumber: asnumber });
        fetch('/graphas/api/asnumber/' + asnumber)
            .then(response => response.json())
            .then(data => this.setState({ countryName: data.country, asProperties: data.asProperties, isLoading: false }));
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
        console.log(this.state);
        return (
            <div>
                <h2>
                Autonomus system AS{this.state.asnumber}
                </h2>
                <h3>
                    {this.state.asProperties.name}
                </h3>
                <h4>
                    {this.state.asProperties.description}
                </h4>
                <p>
                    Autonomus system registrated in {this.state.countryName}
                </p>
            </div>

        );
    }
}


export default AsStats;