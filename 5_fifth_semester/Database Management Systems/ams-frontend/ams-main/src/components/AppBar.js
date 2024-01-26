import React from 'react';
import { useAuth } from '../hooks/useAuth';
import { Link, NavLink as NavLinkBase } from 'react-router-dom';
import { AppBar as MuiAppBar, Toolbar, Typography, IconButton, Stack, Button, Divider } from '@mui/material';
import AirlinesIcon from '@mui/icons-material/Airlines';

const NavLink = React.forwardRef((props, ref) => (
  <NavLinkBase
    ref={ref}
    {...props}
    style={({ isActive }) => {
      return {
        backgroundColor: isActive ? "#1565c0" : "",
      };
    }}
  />
));

export default function AppBar() {
  const { user, logout } = useAuth();

  return (
    <MuiAppBar position='sticky'>
      <Toolbar>
        <IconButton
          disableRipple
          size='large'
          edge='start'
          color='inherit'
          aria-label='logo'
          LinkComponent={Link}
          to='/'
        >
          <AirlinesIcon sx={{ fontSize: '40px' }} />
        </IconButton>
        <Typography variant='h6' sx={{ flexGrow: 1 }}>
          AIRLINE COMPANY
        </Typography>

        <Stack
          direction="row"
          spacing={1}
          divider={<Divider variant='' orientation="vertical" flexItem />}
        >
          {
            (user && user.is_employee) &&
            <Button component={NavLink} to='/dashboard' variant='text' color='inherit'>Dashboard</Button>
          }

          <Button component={NavLink} to='/about' variant='text' color='inherit'>About us</Button>
          <Button component={NavLink} to='/signup' variant='text' color='inherit'>Sign up</Button>

          {
            user
              ? <Button onClick={() => logout()} variant='text' color='inherit'>Log out</Button>
              : <Button component={NavLink} to='/login' variant='text' color='inherit'>Log in</Button>
          }
        </Stack>
      </Toolbar>
    </MuiAppBar>
  );
}