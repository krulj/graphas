import React, { Component } from 'react';
import './App.css';
import AppNavbar from './AppNavbar';
import CountriesSelection from './CountriesSelection';

class Home extends Component {
    render() {
        return (
            <div>
                <AppNavbar />
                <CountriesSelection />
            </div>
        );
    }
}

export default Home;