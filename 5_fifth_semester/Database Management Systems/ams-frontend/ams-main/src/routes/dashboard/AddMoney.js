import { Paper, TextField, Stack, Typography, Alert, Grow, FormControl, OutlinedInput, InputAdornment, InputLabel } from "@mui/material"
import { EmptyPage } from "../../components/Page"
import { Center } from "../../components/Styled"
import { LoadingButton } from "@mui/lab"
import { useState } from "react"
import { axios } from '../../index'
import { isInteger } from 'underscore-contrib';

export default function AddMoney() {
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const [alert, setAlert] = useState(null);
  const [user, setUser] = useState('');
  const [amount, setAmount] = useState();

  const checkInputs = () => {
    if (!isInteger(user) || Number(user) < 0) {
      setError('Please enter a non-negative integer user id.');
    } else if (!isInteger(amount) || Number(amount) < 1) {
      setError('Please enter a positive integer as amount.');
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
      user_id: user,
      money: parseFloat(amount).toFixed(2),
    }

    axios.post('/profile/money', payload)
      .then(response => {
        setAlert({
          severity: 'success',
          message: `Success: User's new balance is: ${response.data.new_balance}`,
        });
      })
      .catch(error => {
        if (error.response) {
          setAlert({
            severity: 'error',
            message: 'Error returned: ' + error.response.data
          })
        } else {
          setAlert({
            severity: 'warning',
            message: 'It seems that your connection is lost.'
          })
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
              label="User ID"
              value={user}
              onChange={e => setUser(e.target.value)}
            />

            <FormControl fullWidth sx={{ m: 1 }}>
              <InputLabel htmlFor="outlined-adornment-amount">Amount</InputLabel>
              <OutlinedInput
                id="outlined-adornment-amount"
                endAdornment={<InputAdornment position="end">₺</InputAdornment>}
                label="Amount"
                onChange={e => setAmount(e.target.value)}
              />
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
                Add Money
              </LoadingButton>
            </Stack>
          </Stack>
        </Paper>
      </Center>
    </EmptyPage>
  )
}