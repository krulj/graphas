import React, { Component } from 'react';
import AppNavbar from './AppNavbar';
import AsinfoList from './AsinfoList';
import { Container } from 'reactstrap';

class App extends Component {
  render() {
    return (
      <div>
        <AppNavbar/>
        <Container fluid>
         <AsinfoList/>
        </Container>
      </div>
    );
  }
}

export default App;