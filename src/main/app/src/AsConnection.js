import React, { Component, createRef } from "react";
import { Network } from 'vis';
import { withRouter } from "react-router-dom";
import { Redirect } from 'react-router';
import '../node_modules/vis/dist/vis.min.css';


const graph = {
    nodes: [
        { id: 1, label: "Node 1", color: "#e04141" },
        { id: 2, label: "Node 2", color: "#e09c41" },
        { id: 3, label: "Node 3", color: "#e0df41" },
        { id: 4, label: "Node 4", color: "#7be041" },
        { id: 5, label: "Node 5", color: "#41e0c9" }
    ],
    edges: [{ from: 1, to: 2 }, { from: 1, to: 3 }, { from: 2, to: 4 }, { from: 2, to: 5 }]
};
const options = {
    layout: {
        hierarchical: false
    },
    height: "640px",

    nodes: {
        title: 'Hover'
    },
    
    edges: {
        color: { inherit: true },
        width: 0.20,
        scaling: {
            min: 10,
            max: 30
        },
        font: {
            size: 12,
            face: 'Tahoma'
        },
        smooth: {
            type: 'continuous'
        },
        title: 'Hover'
    },
    interaction: {
        hideEdgesOnDrag: true,
        tooltipDelay: 200
    }
};

const events = {
    select: function (event) {
        var { nodes, edges } = event;
        console.log("Selected nodes:");
        console.log(nodes);
        console.log("Selected edges:");
        console.log(edges);
    },

};



class AsConnection extends Component {

    constructor(props) {
        super(props);
        this.state = {
            redirect: false,
            asn: 0
        };
        this.network = {};
        this.appRef = createRef();
        this.redirect = this.redirect.bind(this);
    }

    redirect(params) {
        console.log(params.nodes);
        this.setState({ redirect: true, asn: params.nodes });
        //this.props.history.push('/asgraph/' + params.nodes);
    }

    handleClick(e) {
        this.saySomething("element clicked");
    }

    componentDidMount() {
        this.network = new Network(this.appRef.current, this.props.graph, options);
        this.network.on("doubleClick", this.redirect);
        this.network.on("doubleClick", this.redirect);
        this.setState({ redirect: false });
    }

    render() {
        if (this.state.redirect === true) {
            return <Redirect to={"/asgraph/" + this.state.asn} />
        }
        return (
            <div ref={this.appRef} />
        );
    }
}

export default withRouter(AsConnection)