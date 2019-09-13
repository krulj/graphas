import React from "react";
import Layout from "../Layout/Layout";
import AsConnection from "../../containers/graph/AsConnection";
import Divider from "../helper/Divider/Divider";
import CountryStats from "./stats/CountryStats";

export default function CountryPresenter(props) {
  return (
    <Layout>
      <div>
        <div className="App-container-div">
          {!props.isHidden ? (
            <div className="App-left-div">
              <CountryStats
                isLoading={props.isLoading}
                countryName={props.countryName}
                countryCode={props.countryCode}
                maxNeighbours={props.maxNeighbours}
                maxNode={props.maxNode}
                asNums={props.asNums}
              />
            </div>
          ) : (
            <div></div>
          )}
          <Divider
            toggleHidden={props.toggleHidden}
            isHidden={props.isHidden}
          />
          <div className="App-right-div">
            <AsConnection graph={props.graph} getStats={props.getStats} />
          </div>
        </div>
      </div>
    </Layout>
  );
}
