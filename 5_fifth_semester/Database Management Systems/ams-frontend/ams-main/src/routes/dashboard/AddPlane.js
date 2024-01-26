import { Paper, TextField, Stack, Typography, Alert, Grow, FormControl, Select, MenuItem, InputLabel } from "@mui/material"
import { EmptyPage } from "../../components/Page"
import { Center } from "../../components/Styled"
import { LoadingButton } from "@mui/lab"
import { useState } from "react"
import { axios } from '../../index'

export default function AddPlane() {
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const [alert, setAlert] = useState(null);
  const [registration, setRegistration] = useState('TC-');
  const [model, setModel] = useState('');

  const handleRegistrationInput = event => {
    if (!event.target.value.startsWith("TC-"))
      return event.preventDefault();

    setRegistration(event.target.value.toUpperCase());
  }

  const checkInputs = () => {
    if (registration.trim().length != 6) {
      setError('Please enter a plane registration.');
    } else if (!model) {
      setError('Please enter a plane model.');
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

    axios.post('/plane/add', { registration, model })
      .then(() => {
        setAlert({ severity: 'success', message: 'Plane successfully added.' });
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

          <Stack spacing={1} sx={{ width: '450px' }}>
            <TextField
              label="Plane Registration"
              value={registration}
              onChange={handleRegistrationInput}
              inputProps={{ maxLength: 6, style: { textTransform: "uppercase" } }}
            />

            <FormControl fullWidth>
              <InputLabel id="demo-simple-select-label">Plane Model</InputLabel>
              <Select
                labelId="demo-simple-select-label"
                id="demo-simple-select"
                value={model}
                label="Plane Model"
                onChange={e => setModel(e.target.value)}
              >
                <MenuItem value={'Airbus A350'}>Airbus A350</MenuItem>
                <MenuItem value={'Boeing 777'}>Boeing 777</MenuItem>
                <MenuItem value={'Boeing 787'}>Boeing 787</MenuItem>
              </Select>
            </FormControl>

            <Stack direction='row' spacing={1}>
              <Typography
                color='error'
                variant="caption"
                fontWeight='bold'
                flexGrow={1}
              >
                {error}
              </Typography>

              <LoadingButton
                variant='contained'
                sx={{
                  width: '150px',
                  height: '50px',
                }}
                onClick={submit}
                loading={loading}
              >
                Add Plane
              </LoadingButton>
            </Stack>
          </Stack>
        </Paper>
      </Center>
    </EmptyPage>
  )
}