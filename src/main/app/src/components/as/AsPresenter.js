import React from "react";
import Layout from "../Layout/Layout";
import AsConnection from "../../containers/graph/AsConnection";
import Divider from "./../helper/Divider/Divider";
import AsStats from './stats/AsStats';

export default function AsPresenter(props) {
  return (
    <Layout>
      <div>
        <div className="App-container-div">
          {!props.isHidden && (
            <div className="App-left-div">
              <AsStats
                asnumber={props.asn}
                countryCode={props.countryCode}
                topConnect={props.stats}
                countryName={props.countryName}
                asProperties={props.asProperties}
              />
            </div>
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
