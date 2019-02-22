
import React, { Component } from "react"
import {
    ComposableMap,
    ZoomableGroup,
    Geographies,
    Geography,
    Markers,
    Marker,
} from "react-simple-maps"

const wrapperStyles = {
    width: "100%",
    maxWidth: 980,
    margin: "0 auto",
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
    render() {
        return (
            <div>
                <div style={wrapperStyles}>
                    {
                        this.state.cities.map((city, i) => (
                            <button
                                key={i}
                                className="btn px1"
                                data-city={i}
                                onClick={this.handleCitySelection}
                            >
                                {city.name}
                            </button>
                        ))
                    }
                    <button onClick={this.handleReset}>
                        {"Reset"}
                    </button>
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
                            <Markers>
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
                            </Markers>
                        </ZoomableGroup>
                    </ComposableMap>
                </div>
            </div>
        )
    }
}

export default SimpleMap
