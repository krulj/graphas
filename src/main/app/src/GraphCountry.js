import React, { Component } from 'react';
import './App.css';
import AppNavbar from './AppNavbar';
import AsConnection from './AsConnection';
import CountryStats from './CountryStats';


class GraphCountry extends Component {

    constructor(props) {
        super(props);
        this.state = {
            graph: [],
            countryCode: '',
            isLoading: true
        };
    }

    componentDidMount() {
        var pathname = this.props.location.pathname;
        var code = pathname.substr(6);
        this.setState({ isLoading: true, countryCode: code});

        fetch('/graphas/api/country-connections-graph/' + code)
            .then(response => response.json())
            .then(data => this.setState({ graph: data, isLoading: false }));
    }


    render() {
        if (this.state.isLoading === true) {
            return (
                <div>
                    <AppNavbar />
                    <div className="App-loading">
                        <h2>Loading...</h2>
                    </div>
                </div>
            )
        }
        return (
            <div>
                <AppNavbar />
                <div>
                    <div className="App-left-div">
                        <CountryStats countryCode={this.state.countryCode}/>
                    </div>
                    <div className="App-right-div">
                        <AsConnection {...this.state} />
                    </div>
                </div>
            </div>

        );
    }
}

export default GraphCountry;