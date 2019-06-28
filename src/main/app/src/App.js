import React, { Component } from 'react';
import Home from './containers/home/Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import GraphCountry from './containers/country/GraphCountry';
import GraphAsn from './containers/as/GraphAsn';

class App extends Component {
    render() {
        return (
            <Router>
                <Switch>
                    <Route path='/' exact={true} component={Home} />
                    <Route path='/graph/:value' exact={true} component={GraphCountry} />
                    <Route path='/asgraph/:value' exact={true} component={GraphAsn} />
                </Switch>
            </Router>
        );
    }
}

export default App;