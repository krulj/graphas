import React from "react";
import classes from "./Divider.module.css";
import { Button } from "reactstrap";

export default function Divider(props) {
  return (
    <div className={classes.divider}>
      <Button
        key={"<"}
        className={classes.dividerButton}
        onClick={props.toggleHidden}
      >
        {props.isHidden ? ">" : "<"}
      </Button>
    </div>
  );
}
