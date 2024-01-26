import { FormControl, FormControlLabel, FormLabel, Radio, RadioGroup, Typography } from '@mui/material';

export function GenderSelection({ value, onChange }) {
  return (
    <FormControl >
      <FormLabel id="demo-controlled-radio-buttons-group"><Typography variant='body2'> Gender </Typography></FormLabel>
      <RadioGroup
        aria-labelledby="demo-controlled-radio-buttons-group"
        name="controlled-radio-buttons-group"
        value={value}
        onChange={e => onChange(e.target.value)}
        row
      >
        <FormControlLabel value={"Male"} control={<Radio />} label="Male" />
        <FormControlLabel value={"Female"} control={<Radio />} label="Female" />
      </RadioGroup>
    </FormControl>
  );
}

export function DisabledSelection({ value, onChange }) {
  return (
    <FormControl >
      <FormLabel id="demo-controlled-radio-buttons-group"><Typography variant='body2'> Disabled </Typography></FormLabel>
      <RadioGroup
        aria-labelledby="demo-controlled-radio-buttons-group"
        name="controlled-radio-buttons-group"
        value={value}
        onChange={e => onChange(e.target.value)}
        row
      >
        <FormControlLabel value={0} control={<Radio />} label="No" />
        <FormControlLabel value={1} control={<Radio />} label="Yes" />
      </RadioGroup>
    </FormControl>
  );
}