import React, { Component } from "react";
import "../../App.css";
import AppNavbar from "../../AppNavbar";

import Loading from "../../components/helper/Loading";
import AsPresenter from "./../../components/as/AsPresenter";

class GraphAsn extends Component {
  constructor(props) {
    super(props);
    this.state = {
      graph: [],
      asn: 0,
      isLoading: true,
      isHidden: false,
      stats: [],
      countryName: "",
      asProperties: []
    };
    this.toggleHidden = this.toggleHidden.bind(this);
    this.getStats = this.getStats.bind(this);
    this.loadComponentData = this.loadComponentData.bind(this);
  }

  componentDidMount() {
    this.loadComponentData();
  }

  componentDidUpdate(prevProps, prevState) {
    // only update chart if the data has changed
    if (prevProps.location.pathname !== this.props.location.pathname) {
      this.loadComponentData();
    }
  }

  loadComponentData() {
    this.setState({ isLoading: true });
    var pathname = this.props.location.pathname;
    var asn = pathname.substr(9);
    var number = parseInt(asn, 10);
    this.setState({ asn: number });

    fetch("/graphas/api/as-connections-graph/" + asn)
      .then(response => response.json())
      .then(data => this.setState({ graph: data, isLoading: false }));

    fetch("/graphas/api/asnumber/" + number)
      .then(response => response.json())
      .then(data =>
        this.setState({
          countryName: data.country,
          asProperties: data.asProperties
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
      stats: stats
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
      <AsPresenter
        asnumber={this.state.asn}
        countryCode={this.state.countryCode}
        topConnect={this.state.stats}
        countryName={this.state.countryName}
        asProperties={this.state.asProperties}
        graph={this.state.graph}
        getStats={this.getStats}
        stats={this.state.stats}
        toggleHidden={this.toggleHidden}
        isHidden={this.state.isHidden}
      ></AsPresenter>
    );
  }
}

export default GraphAsn;
