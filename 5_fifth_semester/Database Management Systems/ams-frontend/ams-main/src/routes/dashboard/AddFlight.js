import { Paper, TextField, Stack, MenuItem, Select, FormControl, InputLabel, Typography, Alert, Grow, OutlinedInput, InputAdornment } from "@mui/material"
import { EmptyPage } from "../../components/Page"
import { Center } from "../../components/Styled"
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs"
import { LocalizationProvider, DateTimePicker } from "@mui/x-date-pickers"
import dayjs from "dayjs"
import { LoadingButton } from "@mui/lab"
import { useImmer } from "use-immer"
import { useState } from "react"
import { axios } from '../../index'
import airports from '../../airports.json'
import { isInteger } from "underscore-contrib"

export default function AddFlight() {
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const [alert, setAlert] = useState(null);
  const [form, updateForm] = useImmer({
    flight_number: "",
    departure_airport: "",
    destination_airport: "",
    departure_time: "",
    arrival_time: "",
    gate_number: "Gate ",
    plane_registration: "TC-",
    status: "",
    price: "",
  });

  const update = source => {
    updateForm(destination => {
      Object.assign(destination, source);
    })
  }

  const handleFlightNumber = event => {
    const flight_number = event.target.value.toUpperCase();
    update({ flight_number })
  }

  const handlePlaneRegistration = event => {
    const value = event.target.value.toUpperCase();
    if (!value.startsWith("TC-")) return event.preventDefault();
    update({ plane_registration: value.toUpperCase() });
  }

  const handleGateNumber = event => {
    const value = event.target.value;
    if (!value.startsWith("Gate ")) return event.preventDefault();
    const gate_number = "Gate " + value.slice("Gate ".length).toUpperCase();
    update({ gate_number });
  }

  const checkInputs = () => {
    if (!form.flight_number) {
      setError('Please enter a flight number.');
    } else if (!form.departure_airport) {
      setError('Please select a departure airport.');
    } else if (!form.destination_airport) {
      setError('Please select a destination airport.');
    } else if (!form.departure_time) {
      setError('Please select a departure time.');
    } else if (!form.arrival_time) {
      setError('Please select an arrival time.');
    } else if (dayjs(form.arrival_time).diff(dayjs(form.departure_time)) <= 0) {
      setError('Arrival time cannot be sooner than departure time.');
    } else if (form.plane_registration.endsWith("TC-")) {
      setError('Please enter a plane registration.');
    } else if (form.gate_number.endsWith("Gate ")) {
      setError('Please enter a gate number');
    } else if (!form.status) {
      setError('Please select a flight status.');
    } else if (!isInteger(form.price) || Number(form.price) < 1) {
      setError('Please enter a positive integer price.');
    } else {
      setError('');
      return true
    }

    return false;
  }

  const submit = event => {
    setAlert(null);

    if (!checkInputs()) return event.preventDefault();

    setLoading(true);

    axios.post('/flight/addFlight', form)
      .then(() => {
        setAlert({ severity: 'success', message: 'Flight successfully added.' });
      })
      .catch(error => {
        if (error.response) {
          setAlert({ severity: 'error', message: 'Error returned: ' + error.response.data })
        } else {
          setAlert({ severity: 'warning', message: 'It seems that your connection is lost.' })
        }
      })
      .finally(() => setLoading(false));
  }

  return (
    <EmptyPage>
      <Center>
        <Paper sx={{ p: 2, borderRadius: 3 }} elevation={3}>
          {
            alert && <Grow in={Boolean(alert)}>
              <Alert
                sx={{ mb: 2 }}
                severity={alert.severity}
              >
                {alert.message}
              </Alert>
            </Grow>
          }

          <Stack spacing={1}>
            <TextField
              label="Flight number"
              value={form.flight_number}
              onChange={handleFlightNumber}
              inputProps={{ maxLength: 7, style: { textTransform: 'uppercase' } }}
            />

            <FormControl fullWidth>
              <InputLabel id="demo-simple-select-label">From</InputLabel>
              <Select
                labelId="demo-simple-select-label"
                id="demo-simple-select"
                value={form.departure_airport}
                label="From"
                onChange={e => update({ departure_airport: e.target.value })}
              >
                {airports.map(airport => (
                  <MenuItem
                    key={airport.iata}
                    value={airport.iata}
                    disabled={form.destination_airport === airport.iata}
                  >
                    {airport.name}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>

            <FormControl fullWidth>
              <InputLabel id="demo-simple-select-label">To</InputLabel>
              <Select
                labelId="demo-simple-select-label"
                id="demo-simple-select"
                value={form.destination_airport}
                label="To"
                onChange={e => update({ destination_airport: e.target.value })}
              >
                {airports.map(airport => (
                  <MenuItem
                    key={airport.iata}
                    value={airport.iata}
                    disabled={form.departure_airport === airport.iata}
                  >
                    {airport.name}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>

            <Stack direction='row' spacing={1}>
              <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DateTimePicker
                  onChange={date => update({ departure_time: dayjs(date).format('YYYY-MM-DD hh:mm:ss') })}
                  label="Departure"
                  format='DD/MM/YYYY hh:mm A'
                  minDate={dayjs()}
                />
              </LocalizationProvider>
              <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DateTimePicker
                  onChange={date => update({ arrival_time: dayjs(date).format('YYYY-MM-DD hh:mm:ss') })}
                  label="Arrival"
                  format='DD/MM/YYYY hh:mm A'
                  minDate={dayjs()}
                />
              </LocalizationProvider>
            </Stack>

            <Stack direction='row' spacing={1}>
              <TextField
                fullWidth
                label="Plane registration"
                value={form.plane_registration}
                onChange={handlePlaneRegistration}
                inputProps={{ maxLength: 6, style: { textTransform: "uppercase" } }}
              />
              <TextField
                fullWidth
                label="Gate number"
                value={form.gate_number}
                onChange={handleGateNumber}
                inputProps={{ maxLength: 15 }}
              />
            </Stack>


            <Stack direction='row' spacing={1}>
              <FormControl fullWidth>
                <InputLabel id="demo-simple-select-label">Status</InputLabel>
                <Select
                  labelId="demo-simple-select-label"
                  id="demo-simple-select"
                  value={form.status}
                  label="Status"
                  onChange={e => update({ status: e.target.value })}
                >
                  <MenuItem value={'scheduled'}>Scheduled</MenuItem>
                  <MenuItem value={'onflight'}>Onflight</MenuItem>
                  <MenuItem value={'completed'}>Completed</MenuItem>
                </Select>
              </FormControl>


              <FormControl fullWidth>
                <InputLabel htmlFor="outlined-adornment-amount">Price</InputLabel>
                <OutlinedInput
                  id="outlined-adornment-price"
                  endAdornment={<InputAdornment position="end">₺</InputAdornment>}
                  label="Price"
                  onChange={e => update({ price: e.target.value })}
                />
              </FormControl>
            </Stack>

            <Stack direction='row' justifyContent='space-between' alignItems='center'>
              <Typography color='error' variant="caption" fontWeight='bold'>
                {error}
              </Typography>

              <LoadingButton
                variant='contained'
                sx={{ width: '150px', height: '50px' }}
                onClick={submit}
                loading={loading}
              >
                Add Flight
              </LoadingButton>
            </Stack>
          </Stack>
        </Paper>
      </Center>
    </EmptyPage>
  )
}