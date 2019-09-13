import React, { Component } from "react";
import "../../App.css";
import AppNavbar from "../../AppNavbar";
import Loading from "./../../components/helper/Loading";
import CountryPresenter from "../../components/country/CountryPresenter";

class GraphCountry extends Component {
  constructor(props) {
    super(props);
    this.state = {
      graph: [],
      countryCode: "",
      countryName: "",
      isLoading: true,
      isLoadingStats: true,
      isLoadingGraph: true,
      isHidden: false,
      asNums: [],
      maxNode: null,
      maxNeighbours: null
    };
    this.toggleHidden = this.toggleHidden.bind(this);
    this.getStats = this.getStats.bind(this);
  }

  componentDidMount() {
    var pathname = this.props.location.pathname;
    var code = pathname.substr(6);
    this.setState({ isLoading: true, countryCode: code });

    fetch("/graphas/api/country-connections-graph/" + code)
      .then(response => response.json())
      .then(data => this.setState({ graph: data, isLoading: false }));

    fetch("/graphas/api/countryStats/" + code)
      .then(response => response.json())
      .then(data =>
        this.setState({
          countryName: data.country,
          asNums: data.asNums,
          isLoadingStats: false
        })
      );
  }

  toggleHidden() {
    this.setState({
      isHidden: !this.state.isHidden
    });
  }

  getStats = stats => {
    this.setState({
      maxNeighbours: stats.maxNeighbours,
      maxNode: stats.maxNode
    });
  };

  render() {
    if (this.state.isLoading === true) {
      return (
        <div>
          <AppNavbar />
          <Loading />
        </div>
      );
    }
    return (
      <CountryPresenter
        countryCode={this.state.countryCode}
        maxNeighbours={this.state.maxNeighbours}
        maxNode={this.state.maxNode}
        countryName={this.state.countryName}
        asNums={this.state.asNums}
        isLoading={this.state.isLoadingStats}
        toggleHidden={this.toggleHidden}
        isHidden={this.state.isHidden}
        graph={this.state.graph}
        getStats={this.getStats}
      ></CountryPresenter>
    );
  }
}

export default GraphCountry;
