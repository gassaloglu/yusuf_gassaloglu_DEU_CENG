import { Paper, TextField, Stack, FormControl, InputLabel, Typography, Alert, Grow, OutlinedInput, InputAdornment } from "@mui/material"
import { EmptyPage } from "../../components/Page"
import { Center } from "../../components/Styled"
import { LoadingButton } from "@mui/lab"
import { useState } from "react"
import { axios } from '../../index'
import { isNumeric, isInteger } from "underscore-contrib"

export default function CheckInPanel() {
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const [alert, setAlert] = useState(null);

  const [pnr, setPnr] = useState('');
  const [weight, setWeight] = useState('');
  const [piece, setPiece] = useState('');

  const checkInputs = () => {
    if (!pnr) {
      setError('Please enter a PNR number.');
    } else if (!isNumeric(weight) || Number(weight) < 0) {
      setError('Please enter a non-negative weight.');
    } else if (!isInteger(piece) || Number(piece) < 0) {
      setError('Please enter a non-negative piece number.');
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

    const payload = {
      pnr,
      piece,
      weight: parseFloat(weight).toFixed(2),
    }

    axios.post('/passenger/employeecheckin', payload)
      .then(() => {
        setAlert({ severity: 'success', message: 'Check-in successful.' });
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
              label="Reservation number (PNR)"
              value={pnr}
              onChange={e => setPnr(e.target.value.toUpperCase())}
              inputProps={{ maxLength: 8, style: { textTransform: 'uppercase' } }}
            />

            <FormControl fullWidth>
              <InputLabel htmlFor="outlined-adornment-amount">Weight</InputLabel>
              <OutlinedInput
                id="outlined-adornment-weight"
                endAdornment={<InputAdornment position="end">kg.</InputAdornment>}
                label="Weight"
                onChange={e => setWeight(e.target.value)}
              />
            </FormControl>

            <TextField
              label="Piece"
              value={piece}
              onChange={e => setPiece(e.target.value)}
            />

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
                Check-in
              </LoadingButton>
            </Stack>
          </Stack>
        </Paper>
      </Center>
    </EmptyPage>
  )
}