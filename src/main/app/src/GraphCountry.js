import React, { Component } from 'react';
import './App.css';
import AppNavbar from './AppNavbar';
import AsConnection from './AsConnection';
import CountryStats from './CountryStats';
import { Button } from 'reactstrap';

const dividerButtonStyle = {
    padding: 0,
    height: "100%"
}

class GraphCountry extends Component {

    constructor(props) {
        super(props);
        this.state = {
            graph: [],
            countryCode: '',
            isLoading: true,
            isHidden: false,
            stats: []
        };
        this.toggleHidden = this.toggleHidden.bind(this);
        this.getStats = this.getStats.bind(this);
    }

    componentDidMount() {
        var pathname = this.props.location.pathname;
        var code = pathname.substr(6);
        this.setState({ isLoading: true, countryCode: code });

        fetch('/graphas/api/country-connections-graph/' + code)
            .then(response => response.json())
            .then(data => this.setState({ graph: data, isLoading: false }));
    }

    toggleHidden() {
        this.setState({
            isHidden: !this.state.isHidden
        })
    }

    getStats = (stats) => {
        this.setState({
            stats: stats
        })
    };

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
                <div className="App-container-div">
                    {!this.state.isHidden && <Child countryCode={this.state.countryCode} topConnect={this.state.stats} />}
                    <div className="App-divider">
                        <Button key={"<"}
                            style={dividerButtonStyle}
                            onClick={this.toggleHidden}>
                            {this.state.isHidden ? '>' : '<'}
                        </Button>
                    </div>
                    <div className="App-right-div">
                        <AsConnection graph={this.state.graph} getStats={this.getStats} />
                    </div>
                </div>
            </div>

        );
    }
}

const Child = ({ countryCode, topConnect }) => (
    <div className="App-left-div">
        <CountryStats countryCode={countryCode} topConnect={topConnect} />
    </div>
)

export default GraphCountry;