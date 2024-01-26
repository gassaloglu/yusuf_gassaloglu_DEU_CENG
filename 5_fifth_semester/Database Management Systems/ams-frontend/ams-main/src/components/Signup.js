import { useState } from 'react';
import { useImmer } from 'use-immer';
import { useAuth } from '../hooks/useAuth';
import { axios } from '../index';

import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs from 'dayjs';

import LoadingButton from '@mui/lab/LoadingButton';
import { DatePicker } from '@mui/x-date-pickers';
import { Alert, Link, Paper, Snackbar, Stack, TextField, Typography } from '@mui/material';
import { MuiTelInput } from 'mui-tel-input';
import { GenderSelection } from './Selection';
import { useNavigate } from 'react-router-dom';

export default function Signup() {
  const navigate = useNavigate();
  const { login } = useAuth();
  const [loading, setLoading] = useState(false);
  const [signupFailed, setSignupFailed] = useState(false);
  const [error, setError] = useState(false);
  const [user, updateUser] = useImmer({
    name: '',
    surname: '',
    email: '',
    password: '',
    phone: '',
    gender: "Male",
    birth_date: '',
  });

  const set = user => {
    updateUser(draft => {
      Object.assign(draft, user);
    });
  };

  const handleClick = (_) => {
    setError(false);
    setSignupFailed(false);
    setLoading(true);

    axios.post('/register', user)
      .then(({ data: { token } }) => {
        login({ token, is_employee: false });
        navigate('/');
      })
      .catch(error => {
        if (error.response) {
          setSignupFailed(true);
        } else {
          setError(true);
        }
      })
      .finally(() => setLoading(false));
  }

  return (
    <Paper sx={{ width: '320px', p: 2 }} variant="outlined">
      <Stack sx={{ width: '100%' }} spacing={2} justifyContent="center">
        <Typography variant="h5">
          Create your account
        </Typography>

        <Typography level="caption" color="#7B91A7">
          Please fill the required information.
        </Typography>

        <Stack spacing={1.5}>
          <TextField
            error={signupFailed}
            label="Email"
            type='email'
            size="small"
            value={user.email}
            onChange={e => set({ email: e.target.value })}
          />
          <TextField
            label="Password"
            type='password'
            size="small"
            value={user.password}
            onChange={e => set({ password: e.target.value })}
          />

          <Stack direction='row' spacing={1}>
            <TextField
              label="Name"
              size="small"
              value={user.name}
              onChange={e => set({ name: e.target.value })}
            />

            <TextField
              label="Surname"
              size="small"
              value={user.surname}
              onChange={e => set({ surname: e.target.value })}
            />
          </Stack>

          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DatePicker
              label="Birthday"
              slotProps={{ textField: { size: 'small' } }}
              maxDate={dayjs()}
              onChange={b => set({ birth_date: b.format('YYYY-MM-DD') })}
            />
          </LocalizationProvider>

          <MuiTelInput
            size='small'
            label="Phone number"
            disableDropdown
            forceCallingCode
            defaultCountry='TR'
            value={user.phone}
            getFlagElement={() => { }}
            onChange={phone => set({ phone })}
          />
          <GenderSelection
            value={user.gender}
            onChange={gender => set({ gender })}
          />
        </Stack>

        <LoadingButton
          loading={loading}
          disabled={loading}
          variant='contained'
          onClick={handleClick}
        >
          Sign up
        </LoadingButton>

        <Typography
          fontSize="small"
          sx={{ alignSelf: 'center' }}
        >
          Already a member?
          <Link underline="none" href="/login"> Log in</Link>
        </Typography>

        <Snackbar open={error}>
          <Alert severity="error" variant='filled'>
            An error occurred while requesting credentials.
          </Alert>
        </Snackbar>
      </Stack>
    </Paper>
  )
}
