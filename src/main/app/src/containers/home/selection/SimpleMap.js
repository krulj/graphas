import React, { Component } from "react";
import {
  ComposableMap,
  Geographies,
  Geography,
  ZoomableGroup
} from "react-simple-maps";
import ReactTooltip from "react-tooltip";

const wrapperStyles = {
  width: "100%",
  maxWidth: "95%",
  margin: "0 auto"
};

const topDiv = {
  display: "flex"
};

class SimpleMap extends Component {
  constructor() {
    super();
    this.state = {
      center: [0, 20],
      zoom: 1
    };
    this.onWheelHangler = this.onWheelHangler.bind(this);
  }

  componentDidMount() {
    setTimeout(() => {
      ReactTooltip.rebuild();
    }, 100);
  }

  onWheelHangler(event) {
    let zoomValue = this.state.zoom;
    //let centerValue = [event.screenX, event.screenY];
    let centerValue = [0, 20];
    if (event.deltaY > 0) {
      zoomValue += 0.3;
    } else {
      zoomValue -= 0.3;
    }
    if (zoomValue < 1) {
      zoomValue = 1;
      centerValue = [0, 20];
    }

    console.log(event);
    this.setState({
      center: centerValue,
      zoom: zoomValue
    });
  }

  render() {
    return (
      <div style={topDiv}>
        <div style={wrapperStyles} onWheel={this.onWheelHangler}>
          <ComposableMap
            projectionConfig={{
              scale: 205
            }}
            width={980}
            height={551}
            style={{
              width: "100%",
              height: "auto"
            }}
          >
            <ZoomableGroup center={this.state.center} zoom={this.state.zoom}>
              <Geographies
                geography={process.env.PUBLIC_URL + "/world-50m.json"}
              >
                {(geographies, projection) =>
                  geographies.map(
                    (geography, i) =>
                      geography.id !== "ATA" && (
                        <Geography
                          key={i}
                          geography={geography}
                          projection={projection}
                          data-tip={geography.properties.name}
                          onClick={this.props.handleClick}
                          style={{
                            default: {
                              fill: "#ECEFF1",
                              stroke: "#607D8B",
                              strokeWidth: 0.75,
                              outline: "none"
                            },
                            hover: {
                              fill: "#607D8B",
                              stroke: "#607D8B",
                              strokeWidth: 0.75,
                              outline: "none"
                            },
                            pressed: {
                              fill: "#FF5722",
                              stroke: "#607D8B",
                              strokeWidth: 0.75,
                              outline: "none"
                            }
                          }}
                        />
                      )
                  )
                }
              </Geographies>
            </ZoomableGroup>
          </ComposableMap>
          <ReactTooltip />
        </div>
      </div>
    );
  }
}

export default SimpleMap;
