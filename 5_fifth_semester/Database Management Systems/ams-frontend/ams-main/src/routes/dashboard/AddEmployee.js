import { Paper, TextField, Stack, MenuItem, Select, FormControl, InputLabel, Typography, Alert, Grow } from "@mui/material"
import { EmptyPage } from "../../components/Page"
import { Center } from "../../components/Styled"
import { GenderSelection } from "../../components/Selection"
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs"
import { LocalizationProvider, DatePicker } from "@mui/x-date-pickers"
import dayjs from "dayjs"
import { LoadingButton } from "@mui/lab"
import { MuiTelInput, matchIsValidTel } from "mui-tel-input"
import { useImmer } from "use-immer"
import { isValidId, isValidEmail, isValidName } from "../../components/PassengerForm"
import { useState } from "react"
import { axios } from '../../index';

export default function AddEmployee() {
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const [alert, setAlert] = useState(null);
  const [form, updateForm] = useImmer({
    national_id: "",
    name: "",
    surname: "",
    email: "",
    phone: "",
    gender: "Male",
    birth_date: "",
    password: "",
    permission: "",
    title: ""
  });

  const update = source => {
    updateForm(destination => {
      Object.assign(destination, source);
    })
  }

  const checkInputs = () => {
    if (!isValidName(form.name)) {
      setError('Please enter a valid name.');
    } else if (!isValidName(form.surname)) {
      setError('Please enter a surname.');
    } else if (!isValidId(form.national_id)) {
      setError('Please enter a national id.');
    } else if (!isValidEmail(form.email)) {
      setError('Please enter a valid email.');
    } else if (!matchIsValidTel(form.phone)) {
      setError('Please enter a valid phone number.');
    } else if (!form.permission) {
      setError('Please select a permission.');
    } else if (!form.password) {
      setError('Please enter a password.');
    } else if (!form.birth_date) {
      setError('Please select a birthday.');
    } else if (!form.title) {
      setError('Please enter a title.');
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

    axios.post('/register/addEmployee', form)
      .then(() => {
        setAlert({ severity: 'success', message: 'Employee successfully added.' });
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
              label="National id"
              inputProps={{ maxLength: 11 }}
              value={form.national_id}
              onChange={(e) => update({ national_id: e.target.value })}
            />

            <Stack direction='row' spacing={1}>
              <TextField
                label="Name"
                value={form.name}
                onChange={(e) => update({ name: e.target.value })}
              />

              <TextField
                label="Surname"
                value={form.surname}
                onChange={(e) => update({ surname: e.target.value })}
              />
            </Stack>

            <LocalizationProvider dateAdapter={AdapterDayjs}>
              <DatePicker
                onChange={date => update({ birth_date: dayjs(date).format('YYYY-MM-DD') })}
                label="Birthday"
                format='DD/MM/YYYY'
                maxDate={dayjs()}
              />
            </LocalizationProvider>

            <MuiTelInput
              label="Phone number"
              disableDropdown
              forceCallingCode
              getFlagElement={() => { }}
              defaultCountry='TR'
              value={form.phone}
              onChange={phone => update({ phone })}
            />

            <TextField
              label="Email"
              type='email'
              value={form.email}
              onChange={(e) => update({ email: e.target.value })}
            />

            <TextField
              label="Password"
              type="password"
              value={form.password}
              onChange={(e) => update({ password: e.target.value })}
            />

            <TextField
              label="Title"
              value={form.title}
              onChange={(e) => update({ title: e.target.value })}
            />

            <FormControl fullWidth>
              <InputLabel id="demo-simple-select-label">Permission</InputLabel>
              <Select
                labelId="demo-simple-select-label"
                id="demo-simple-select"
                value={form.permission}
                label="Permission"
                onChange={e => update({ permission: e.target.value })}
              >
                <MenuItem value={'admin'}>Admin</MenuItem>
                <MenuItem value={'flight_planner'}>Flight Planner</MenuItem>
                <MenuItem value={'passenger_services'}>Passenger Services</MenuItem>
                <MenuItem value={'seller'}>Seller</MenuItem>
              </Select>
            </FormControl>

            <Stack direction='row' justifyContent='space-between' alignItems='center'>
              <GenderSelection value={form.gender} onChange={gender => update({ gender })} />
              <LoadingButton
                variant='contained'
                sx={{ width: '150px', height: '50px' }}
                onClick={submit}
                loading={loading}
              >
                Add Employee
              </LoadingButton>
            </Stack>
          </Stack>

          {error && <Typography color='error' variant="caption" fontWeight='bold'> {error} </Typography>}
        </Paper>
      </Center>
    </EmptyPage>
  )
}