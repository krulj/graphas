import React, { Component } from 'react';
import './App.css';
import AppNavbar from './AppNavbar';
import AsConnection from './AsConnection';
import AsStats from './AsStats';
import { Button } from 'reactstrap';

const dividerButtonStyle = {
    padding: 0,
    height: "100%"
}

class GraphAsn extends Component {

    constructor(props) {
        super(props);
        this.state = {
            graph: [],
            asn: 0,
            isLoading: true,
            isHidden: false
        };
        this.toggleHidden = this.toggleHidden.bind(this);
    }

    componentDidMount() {
        this.setState({ isLoading: true });
        var pathname = this.props.location.pathname;
        var asn = pathname.substr(9);
        var number = parseInt(asn, 10)
        this.setState({ asn: number });

        fetch('/graphas/api/as-connections-graph/' + asn)
            .then(response => response.json())
            .then(data => this.setState({ graph: data, isLoading: false }));
    }

    toggleHidden() {
        this.setState({
            isHidden: !this.state.isHidden
        })
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
                <div className="App-container-div">
                    {!this.state.isHidden && <Child asn={this.state.asn} />}
                    <div className="App-divider">
                        <Button key={"<"}
                            style={dividerButtonStyle}
                            onClick={this.toggleHidden}>
                            {this.state.isHidden ? '>' : '<'}
                        </Button>
                    </div>
                    <div className="App-right-div">
                        <AsConnection {...this.state} />
                    </div>
                </div>
            </div>

        );
    }
}

const Child = ({ asn }) => (
    <div className="App-left-div">
        <AsStats asnumber={asn} />
    </div>
)

export default GraphAsn;