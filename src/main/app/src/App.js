import React, { Component } from 'react';
import AppNavbar from './AppNavbar';
import CountriesSelection from './CountriesSelection';
import { Container } from 'reactstrap';

class App extends Component {
    render() {
        return (
            <div>
                <AppNavbar />
                <CountriesSelection />
            </div>
        );
    }
}

export default App;