import { useState } from 'react';

import Tab from '@mui/material/Tab';
import TabContext from '@mui/lab/TabContext';
import TabList from '@mui/lab/TabList';
import TabPanel from '@mui/lab/TabPanel'

import TextField from '@mui/material/TextField';
import Select from '@mui/material/Select';
import MenuItem from '@mui/material/MenuItem';
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';

import Stack from '@mui/material/Stack';

import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import { SwapHoriz as SwapHorizIcon, Flight as FlightIcon, AirplaneTicket as AirplaneTicketIcon } from '@mui/icons-material'

import dayjs from 'dayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';

import { useNavigate } from "react-router-dom";

import airports from "../airports.json"

export default function Booker() {
  const [activeTab, setActiveTab] = useState('1');

  const handleChange = (event, newValue) => {
    setActiveTab(newValue);
  };

  return (
    <TabContext value={activeTab}>
      <TabList onChange={handleChange} variant='fullWidth'>
        <Tab icon={<FlightIcon />} iconPosition='start' label="Flights" value="1" />
        <Tab icon={<AirplaneTicketIcon />} iconPosition='start' label="Check-in" value="2" />
      </TabList>
      <TabPanel value="1"> <FlightsTab /> </TabPanel>
      <TabPanel value="2"> <CheckInTab /> </TabPanel>
    </TabContext>
  );
}

function FlightsTab() {
  const [fromAirport, setFromAirport] = useState('');
  const [toAirport, setToAirport] = useState('');
  const [departureDate, setDepartureDate] = useState(null);

  const navigate = useNavigate()

  const handleClick = e => {
    if (!fromAirport || !toAirport || !departureDate)
      return e.preventDefault();

    const date = departureDate.format("YYYY-MM-DD");
    const link = `/flights/${fromAirport}/${toAirport}/${date}`;

    navigate(link);
  }

  return (
    <Stack direction="row" spacing={2} justifyContent="space-evenly">
      <AirportSelection
        label="From"
        airport={fromAirport}
        setAirport={setFromAirport}
        disabledAirport={toAirport}
      />

      <IconButton
        aria-label="swap"
        onClick={(_) => {
          const tmp = fromAirport;
          setFromAirport(toAirport);
          setToAirport(tmp);
        }}
      >
        <SwapHorizIcon />
      </IconButton>

      <AirportSelection
        label="To"
        airport={toAirport}
        setAirport={setToAirport}
        disabledAirport={fromAirport}
      />

      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <DatePicker
          label="Departure date"
          value={departureDate}
          onChange={(value) => setDepartureDate(value)}
          minDate={dayjs()}
        />
      </LocalizationProvider>

      <Button
        variant="contained"
        onClick={handleClick}
      >
        Search flights
      </Button>
    </Stack>
  );
}

function AirportSelection({ label, airport, setAirport, disabledAirport }) {
  return (
    <FormControl>
      <InputLabel>{label}</InputLabel>
      <Select
        sx={{ width: 150 }}
        value={airport}
        onChange={(event) => setAirport(event.target.value)}
        label={label}
        renderValue={(value) => value ? value : ''}
      >
        {airports.map((option) => (
          <MenuItem
            key={option.iata}
            value={option.iata}
            disabled={option.iata === disabledAirport}
          >
            {option.name}
          </MenuItem>
        ))}
      </Select>
    </FormControl>
  );
}

function CheckInTab() {
  const navigate = useNavigate();
  const [surname, setSurname] = useState('');
  const [pnr, setPnr] = useState('');

  const handleClick = event => {
    if (!pnr || !surname) return event.preventDefault();
    navigate(`/checkin/${pnr}/${surname}`);
  }

  return (
    <Stack direction="row" spacing={2} justifyContent="space-evenly">
      <TextField
        value={pnr}
        onChange={(e) => setPnr(e.target.value.toUpperCase())}
        inputProps={{ maxLength: 8, style: { textTransform: 'uppercase' } }}
        sx={{ width: '300px' }}
        label="Reservation number (PNR)"
      />

      <TextField
        value={surname}
        onChange={(e) => setSurname(e.target.value)}
        sx={{ width: '300px' }}
        label="Surname"
      />

      <Button
        sx={{ width: '150px' }}
        variant="contained"
        onClick={handleClick}
      >
        Continue
      </Button>
    </Stack>
  );
}