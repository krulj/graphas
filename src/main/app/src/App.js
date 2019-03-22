import React, { Component } from 'react';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import GraphCountry from './GraphCountry';
import GraphAsn from './GraphAsn';

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