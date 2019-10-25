import React from "react";
import AppNavbar from "./../../AppNavbar";

export default function Layout(props) {
  return (
    <React.Fragment>
      <AppNavbar />
      <main className="App-main-window">{props.children}</main>
      <div></div>
    </React.Fragment>
  );
}
