import React, { Component } from 'react';
import './App.css';

class AsStats extends Component {

    constructor(props) {
        super(props);
        this.state = {
            asnumber: 0,
            countryName: '',
            asProperties: [],
            isLoading: true
        };
    }

    componentDidMount() {
        var asnumber = this.props.asnumber;
        this.setState({ isLoading: true, asnumber: asnumber });
        fetch('/graphas/api/asnumber/' + asnumber)
            .then(response => response.json())
            .then(data => this.setState({ countryName: data.country, asProperties: data.asProperties, isLoading: false }));
    }

    render() {
        if (this.state.isLoading === true) {
            return (
                <div>
                    <p>
                        .
                    </p>
                </div>
            )
        }
        console.log(this.state);
        return (
            <div>
                <h3>
                AS{this.state.asnumber}
                </h3>
                <h4>
                    {this.state.asProperties.name}
                </h4>
                <Child description={this.state.asProperties.description}/>
                <p>
                    Autonomus system registrated in {this.state.countryName}
                </p>
            </div>

        );
    }
}

const Child = ({ description }) => (
    <div>
        {description.split(';').map((txt, index) => <p key={index}>{txt}</p>)}
    </div>
)


export default AsStats;