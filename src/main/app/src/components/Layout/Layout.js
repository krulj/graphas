import React from "react";
import AppNavbar from "./../../AppNavbar";

export default function Layout(props) {
  return (
    <React.Fragment>
      <AppNavbar />
      <main>{props.children}</main>
    </React.Fragment>
  );
}
