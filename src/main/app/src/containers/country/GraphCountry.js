import React, { Component } from "react";
import "../../App.css";
import AppNavbar from "../../AppNavbar";
import Loading from "./../../components/helper/Loading";
import CountryPresenter from "../../components/country/CountryPresenter";
import axios from "axios";

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

    axios
      .get("/graphas/api/country-connections-graph/" + code)
      .then(response => {
        console.log(response);
        this.setState({ graph: response.data, isLoading: false });
      });

    axios.get("/graphas/api/countryStats/" + code).then(response =>
      this.setState({
        countryName: response.data.country,
        asNums: response.data.asNums,
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
