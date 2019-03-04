import React, { Component } from 'react';
import './App.css';
import AppNavbar from './AppNavbar';
import { Label } from 'reactstrap';


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
                    <h1>
                        {this.state.asinfos.map(item => (
                            <p>
                                <Label key={item.number}  >
                                    {item.number}
                                </Label>
                            </p>
                        ))}

                    </h1>
                </div>

            </div>
        );
    }
}

export default Graph;