
import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import { ComposableMap, Geographies, Geography, ZoomableGroup } from "react-simple-maps";
import ReactTooltip from 'react-tooltip';
import { Button } from 'reactstrap';




const wrapperStyles = {
    width: "100%",
    maxWidth: 980,
    margin: "0 auto",
}

const topDiv = {
    display: "flex"
}

class SimpleMap extends Component {
    constructor() {
        super()
        this.state = {
            center: [0, 20],
            zoom: 1,
            cities: [
                { name: "Europe", coordinates: [8, 47] },
                { name: "North America", coordinates: [-100, 50] },
                { name: "Australia", coordinates: [140, -20] },
                { name: "Africa", coordinates: [10, 0] },
                { name: "South America", coordinates: [-70, -20] },
                { name: "Asia", coordinates: [103, 45] },
            ]
        }
        this.handleCitySelection = this.handleCitySelection.bind(this)
        this.handleReset = this.handleReset.bind(this)
        this.handleClick = this.handleClick.bind(this)
    }

    handleClick(geo) {
        //return  <Link to="/graph">Old Match, to be redirected</Link>
        //console.log("Geography data: ", geo)
        //console.log(this.props)
        this.props.history.push('/graph/' + geo.id);
    }


    handleCitySelection(evt) {
        const cityId = evt.target.getAttribute("data-city")
        const city = this.state.cities[cityId]
        this.setState({
            center: city.coordinates,
            zoom: 2,
        })
    }

    handleReset() {
        this.setState({
            center: [0, 20],
            zoom: 1,
        })
    }

    componentDidMount() {
        setTimeout(() => {
            ReactTooltip.rebuild()
        }, 100)
    }

    render() {
        return (
            <div style={topDiv}>
                <div className="App-map-button">
                    {
                        this.state.cities.map((city, i) => (
                            <Button className="App-button"
                                key={i}
                                data-city={i}
                                onClick={this.handleCitySelection}
                            >
                                {city.name}
                            </Button>
                        ))
                    }
                    <Button className="App-button"
                        onClick={this.handleReset}>
                        {"Reset"}
                    </Button>
                </div>
                <div style={wrapperStyles}>
                    <ComposableMap
                        projectionConfig={{
                            scale: 205,
                        }}
                        width={980}
                        height={551}
                        style={{
                            width: "100%",
                            height: "auto",
                        }}
                    >
                        <ZoomableGroup center={this.state.center} zoom={this.state.zoom}>
                            <Geographies geography={process.env.PUBLIC_URL + '/world-50m.json'}>
                                {(geographies, projection) => geographies.map((geography, i) => geography.id !== "ATA" && (
                                    <Geography
                                        key={i}
                                        geography={geography}
                                        projection={projection}
                                        data-tip={geography.properties.name}
                                        onClick={this.handleClick}
                                        style={{
                                            default: {
                                                fill: "#ECEFF1",
                                                stroke: "#607D8B",
                                                strokeWidth: 0.75,
                                                outline: "none",
                                            },
                                            hover: {
                                                fill: "#607D8B",
                                                stroke: "#607D8B",
                                                strokeWidth: 0.75,
                                                outline: "none",
                                            },
                                            pressed: {
                                                fill: "#FF5722",
                                                stroke: "#607D8B",
                                                strokeWidth: 0.75,
                                                outline: "none",
                                            },
                                        }}
                                    />
                                ))}
                            </Geographies>

                            {/* <Markers>
                                {
                                    this.state.cities.map((city, i) => (
                                        <Marker key={i} marker={city}>
                                            <circle
                                                cx={0}
                                                cy={0}
                                                r={6}
                                                fill="#FF5722"
                                                stroke="#DF3702"
                                            />
                                        </Marker>
                                    ))
                                }
                            </Markers> */}
                        </ZoomableGroup>
                    </ComposableMap>
                    <ReactTooltip />
                </div>

            </div>
        )
    }
}

export default withRouter(SimpleMap)
