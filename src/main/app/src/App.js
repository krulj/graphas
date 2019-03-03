import React, { Component } from 'react';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Asinfo from './Asinfo';

class App extends Component {
    render() {
        return (
            <Router>
                <Switch>
                    <Route path='/' exact={true} component={Home} />
                    <Route path='/asinfo' exact={true} component={Asinfo} />
                </Switch>
            </Router>
        );
    }
}

export default App;