import React, { Component, createRef } from "react";
import { Network } from 'vis';
import { withRouter } from "react-router-dom";
import { Redirect } from 'react-router';
import '../node_modules/vis/dist/vis.min.css';

const wrapperStyles = {
    height: "90vh"
}

const options = {
    layout: {
        hierarchical: false
    },

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
    },
    physics:{
        enabled: true,
        solver: 'forceAtlas2Based'
    }    
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
            <div style={wrapperStyles} ref={this.appRef} />
        );
    }
}

export default withRouter(AsConnection)