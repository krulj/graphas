import React, { Component, createRef } from "react";
import { Network, DataSet } from 'vis';
import { withRouter } from "react-router-dom";
import { Redirect } from 'react-router';
import '../../../node_modules/vis/dist/vis.min.css';

const wrapperStyles = {
    height: "88vh"
}

const options = {
    layout: {
        hierarchical: {
            enabled: false,
            levelSeparation: 150,
            nodeSpacing: 200,
            treeSpacing: 200,
            blockShifting: true,
            edgeMinimization: true,
            parentCentralization: false,
            direction: 'UD',        // UD, DU, LR, RL
            sortMethod: 'hubsize'   // hubsize, directed
        },
        improvedLayout: false
    },

    nodes: {
        scaling: {
            min: 10,
            max: 12,
            label: {
                enabled: true,
                min: 14,
                max: 18,
            }

        },
        title: 'Hover'
    },

    edges: {
        color: { inherit: true },
        width: 0.20,
        scaling: {
            min: 1,
            max: 4
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
    physics: {
        enabled: true,
        solver: 'forceAtlas2Based',
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
        this.setValueForEveryNode = this.setValueForEveryNode.bind(this);
        this.calcStats = this.calcStats.bind(this);
    }

    redirect(params) {
        if (params.nodes.length === 0) {
            return;
        }
        console.log(params);
        this.setState({ redirect: true, asn: params.nodes });
    }

    setValueForEveryNode(network, nodes) {
        nodes.forEach(function (node) {
            var connected = network.getConnectedEdges(node.id).length;
            node.value = connected;
            nodes.update([{ id: node.id, value: connected }]);
        });
    }

    calcStats(nodes) {

        var allCountries = nodes.map(node => node.title);
        
        var maxNeighbours = {};
        allCountries.forEach(function (i) {
            maxNeighbours[i] = (maxNeighbours[i] || 0) + 1;
        });

        var maxNode = nodes.map(function(node) {
            return [node.id, node.value];
        });

        var stats = [];
        stats.maxNode = maxNode;
        stats.maxNeighbours = maxNeighbours;

        console.log(stats);
        console.log(maxNeighbours);
        this.props.getStats(stats);
    }

    componentDidMount() {

        var nodes = new DataSet(this.props.graph.nodes);
        var edges = new DataSet(this.props.graph.edges);
        var data = {
            nodes: nodes,
            edges: edges
        };
        this.network = new Network(this.appRef.current, data, options);
        //console.log(this.network.getConnectedEdges(43940));
        this.setValueForEveryNode(this.network, nodes);
        this.network.redraw();
        this.network.on("doubleClick", this.redirect);
        this.calcStats(nodes);
        this.setState({ redirect: false });
    }

    componentDidUpdate(prevProps, prevState) {
        // only update chart if the data has changed
        if (prevState.asn !== this.state.asn) {
            this.setState({ redirect: false });
        }
    }

    render() {
        if (this.state.redirect === true) {
            return <Redirect push to={"/asgraph/" + this.state.asn} />
        }
        return (
            <div style={wrapperStyles} ref={this.appRef} />
        );
    }
}

export default withRouter(AsConnection)