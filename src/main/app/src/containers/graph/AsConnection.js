import React, { Component, createRef } from "react";
import { Network, DataSet } from 'vis';
import { withRouter } from "react-router-dom";
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
        improvedLayout: true
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
        this.props.history.push("/asgraph/" + params.nodes);
    }

    setValueForEveryNode(network, nodes) {
        nodes.forEach(function (node) {
            var connected = network.getConnectedEdges(node.id).length;
            node.value = connected;
            nodes.update([{ id: node.id, value: connected }]);
        });
    }

    calcStats(nodes) {

        let start = Date.now();

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

        this.props.getStats(stats);
        console.log("Calc completed for:" + (start - Date.now()))
    }

    componentDidMount() {

        var nodes = new DataSet(this.props.graph.nodes);
        var edges = new DataSet(this.props.graph.edges);
        var data = {
            nodes: nodes,
            edges: edges
        };
        this.network = new Network(this.appRef.current, data, options);
        this.setValueForEveryNode(this.network, nodes);
        this.network.redraw();
        this.network.on("doubleClick", this.redirect);
        this.calcStats(nodes);
    }

    render() {
        return (
            <div style={wrapperStyles} ref={this.appRef} />
        );
    }
}

export default withRouter(AsConnection)