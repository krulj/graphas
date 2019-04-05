import React, { Component } from 'react';
import { Button, Input } from 'reactstrap';
import './App.css';
import SimpleMap from './SimpleMap';
import { Redirect } from 'react-router';

class CountriesSelection extends Component {


    constructor(props) {
        super(props);
        this.state = {
            countries: [],
            isLoading: true
        };
    }

    componentDidMount() {
        this.setState({ isLoading: true });

        fetch('/graphas/api/countries')
            .then(response => response.json())
            .then(data => this.setState({ countries: data, isLoading: false, query: '' }));
    }

    render() {
        if (this.state.isLoading) {
            return (
                <div className="App-loading">
                    <h2>Loading...</h2>
                </div>
            );
        }

        return (
            <div className="App-container-div">
                <div className="App-left-div">
                    <List items={this.state.countries} />
                </div>
                <div className="App-right-div">
                    <SimpleMap />
                </div>
            </div>
        );
    }
}

class List extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            filtered: [],
            notFiltered: [],
            redirect: false,
            country: ''
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleCountrySelection = this.handleCountrySelection.bind(this)
    }

    componentDidMount() {
        this.setState({
            notFiltered: this.props.items,
            redirect: false
        });
    }

    componentWillReceiveProps(nextProps) {
        this.setState({
            notFiltered: nextProps.items
        });
    }

    handleCountrySelection(evt) {
        console.log(evt.target);
        var countryId = evt.target.getAttribute("data-country");
        this.setState({
            redirect: true,
            country: countryId.substr(0, 3)
        });
    }

    handleChange(e) {
        // Variable to hold the original version of the list
        let currentList = [];
        // Variable to hold the filtered list before putting into state
        let newList = [];
        let counter = 0;

        // If the search bar isn't empty
        if (e.target.value !== "") {
            // Assign the original list to currentList
            currentList = this.props.items;

            // Use .filter() to determine which items should be displayed
            // based on the search terms
            newList = currentList.filter(item => {
                // change current item to lowercase
                const lc = item.toLowerCase();
                // change search term to lowercase
                const filter = e.target.value.toLowerCase();
                // check to see if the current list item includes the search term
                // If it does, it will be added to newList. Using lowercase eliminates
                // issues with capitalization in search terms and search content
                var include = lc.includes(filter);
                if (include && counter < 5) {
                    counter++;
                    return include;
                }
                return false;
            });
        }
        // Set the filtered state based on what our rules added to newList
        this.setState({
            filtered: newList
        });
    }

    render() {
        if (this.state.redirect === true) {
            return <Redirect to={'/graph/' + this.state.country} />
        }

        return (
            <div>
                <Input type="text" className="App-input" onChange={this.handleChange} placeholder="Search..." />

                {this.state.filtered.map(item => (
                    <Button key={item}
                        data-country={item}
                        className="App-button"
                        onClick={this.handleCountrySelection}>
                        {item}
                    </Button>
                ))}

            </div>
        )
    }
}

export default CountriesSelection;

