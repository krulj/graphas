import React, { Component } from 'react';
import { Button } from 'reactstrap';
import './App.css';
import AppNavbar from './AppNavbar';
import AsConnection from './AsConnection';


class Graph extends Component {

    constructor(props) {
        super(props);
        this.state = {
            asinfos: [],
            isLoading: true
        };
    }

    componentDidMount() {
        this.setState({ isLoading: true });
        var pathname = this.props.location.pathname;
        var countryCode = pathname.substr(6);

        fetch('/graphas/api/country/' + countryCode)
            .then(response => response.json())
            .then(data => this.setState({ asinfos: data, isLoading: false }));
    }


    render() {
        if (this.state.isLoading) {
            return (
                <div>
                    <AppNavbar />
                    <div><p>Loading...</p>
                    </div>
                </div>
            )
        }
        return (
            <div>
                <AppNavbar />
                <div>
                    <div className="App-left-div">
                        {this.state.asinfos.map(item => (
                            <Button key={item.number} className="App-button" >
                                {"AS" + item.number}
                            </Button>
                        ))}
                    </div>
                    <div className="App-right-div">
                     <h2>TODO</h2>
                        <AsConnection/>
                    </div>
                </div>
            </div>

        );
    }
}

export default Graph;