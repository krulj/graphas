import React, { Component } from "react";
import "../../App.css";
import CountriesSelection from "../../components/home/selection/CountriesSelection";
import Layout from "../../components/Layout/Layout";

class Home extends Component {
  constructor(props) {
    super(props);
    this.state = {
      countries: [],
      isLoading: true
    };

    this.handleClick = this.handleClick.bind(this);
  }

  componentDidMount() {
    this.setState({ isLoading: true });

    fetch("/graphas/api/countries")
      .then(response => response.json())
      .then(data =>
        this.setState({ countries: data, isLoading: false, query: "" })
      );
  }

  handleClick(geo) {
    this.props.history.push("/graph/" + geo.id);
  }

  render() {
    return (
      <Layout>
        <CountriesSelection
          isLoading={this.state.isLoading}
          countries={this.state.countries}
          handleClick={this.handleClick}
        />
      </Layout>
    );
  }
}

export default Home;
