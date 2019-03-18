import React, { Component } from 'react';
import './App.css';
import AppNavbar from './AppNavbar';
import AsConnection from './AsConnection';


class GraphAsn extends Component {

    constructor(props) {
        super(props);
        this.state = {
            graph: [],
            isLoading: true
        };
    }

    componentDidMount() {
        this.setState({ isLoading: true });
        var pathname = this.props.location.pathname;
        var asn = pathname.substr(9);

        fetch('/graphas/api/as-connections-graph/' + asn)
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
        console.log(this.state)
        return (
            <div>
                <AppNavbar />
                <div>
                    <div className="App-left-div">
                        <h2>TODO get as props!</h2>
                    </div>
                    <div className="App-right-div">
                        <AsConnection {...this.state} />
                    </div>
                </div>
            </div>

        );
    }
}

export default GraphAsn;