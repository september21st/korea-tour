import React, { useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Rating from '@material-ui/lab/Rating';
import Box from '@material-ui/core/Box';

const labels = {
  0.5: 0.5,
  1: 1.0,
  1.5: 1.5,
  2: 2.0,
  2.5: 2.5,
  3: 3.0,
  3.5: 3.5,
  4: 4.0,
  4.5: 4.5,
  5: 5.0,
};

const useStyles = makeStyles({
  root: {
    width: 200,
    display: 'flex',
    alignItems: 'center',
  },
});

export default function HoverRating(props) {
  const [value, setValue] = props.value;
  const [hover, setHover] = useState(-1);
  const classes = useStyles();

  const changeText = e => {
    let star = e.target.value;
    let starValue = star.replace(',', '');
    starValue = Number(starValue);
    setValue({ ...value, [e.target.name]: starValue });
  };

  return (
    <div className={classes.root}>
      <Rating
        name={props.name}
        value={props.value}
        precision={0.5}
        onChange={changeText}
        onChangeActive={(event, newHover) => {
          setHover(newHover);
        }}
      />
       {value !== null && <Box ml={2}>{labels[hover !== -1 ? hover : value]}</Box>}
    </div>
  );
}