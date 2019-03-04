import React, { Component } from 'react';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Asinfo from './Asinfo';
import Graph from './Graph';

class App extends Component {
    render() {
        return (
            <Router>
                <Switch>
                    <Route path='/' exact={true} component={Home} />
                    <Route path='/asinfo' exact={true} component={Asinfo} />
                    <Route name='/graph/:value' exact={true} component={Graph} />
                </Switch>
            </Router>
        );
    }
}

export default App;