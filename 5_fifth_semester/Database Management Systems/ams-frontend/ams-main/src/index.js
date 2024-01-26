import React from 'react';
import ReactDOM from 'react-dom/client';
import { createBrowserRouter, RouterProvider, Link } from "react-router-dom";
import Axios from 'axios';

import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';

import Auth from './routes/Auth';
import { Protected, EmployeeOnly } from './routes/Protected';
import Home from './routes/Home';
import Login from './routes/Login';
import Signup from './routes/Signup';
import { Booking, BookingErrorBoundary, bookingLoader } from './routes/Booking';
import { CheckIn, CheckInErrorBoundary, checkInLoader } from './routes/CheckIn';
import { Flights, FlightsErrorBoundary, flightsLoader } from './routes/Flights';

import AddEmployee from './routes/dashboard/AddEmployee';
import AddFlight from './routes/dashboard/AddFlight';
import AddMoney from './routes/dashboard/AddMoney';
import AddPlane from './routes/dashboard/AddPlane';
import CheckInPanel from './routes/dashboard/CheckInPanel';
import DashboardIndex from './routes/dashboard/DashboardIndex';
import { ListEmployee, listEmployeeLoader } from './routes/dashboard/ListEmployee';
import { ListFlight, listFlightLoader } from './routes/dashboard/ListFlight';
import { ListPassenger, listPassengerLoader } from './routes/dashboard/ListPassenger';
import { ListPlane, listPlaneLoader } from './routes/dashboard/ListPlane';
import { ListUser, listUserLoader } from './routes/dashboard/ListUser';
import { Dashboard, DashboardErrorBoundary } from './routes/Dashboard';

import { Typography, Button, Stack } from '@mui/material';

import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';
import './styles/index.css';
import About from './routes/About';

const root = ReactDOM.createRoot(document.getElementById('root'));

const user = JSON.parse(localStorage.getItem('user'));
export const axios = Axios.create({
  headers: user ? { Authorization: `Bearer ${user.token}` } : {},
  baseURL: "http://localhost:5500",
});

export const theme = createTheme({
  palette: {
    mode: 'light',
  },
});

const Fallback = () => (
  <Stack
    spacing={1}
    alignItems='center'
    justifyContent='center'
    sx={{
      width: '100%',
      height: '100vh'
    }}
  >
    <Typography variant='h2'> 404 Not Found </Typography>
    <Typography> The page you are looking for can't be found.</Typography>
    <Button variant='outlined' component={Link} to='/'> Return back to home</Button>
  </Stack>
);

const router = createBrowserRouter([
  {
    element: <Auth />,
    path: '/',
    errorElement: <Fallback />,
    children: [
      {
        path: "/",
        element: <Home />,
      },
      {
        path: "/login",
        element: <Login />,
      },
      {
        path: "/signup",
        element: <Signup />,
      },
      {
        path: "/about",
        element: <About />,
      },
      {
        path: "/flights/:from/:to/:date",
        loader: flightsLoader,
        element: <Flights />,
        errorElement: <FlightsErrorBoundary />,
      },
      {
        path: "/booking/:flight_number/:plan",
        loader: bookingLoader,
        element: <Protected Layout={Booking} />,
        errorElement: <BookingErrorBoundary />,
      },
      {
        path: "/checkin/:pnr/:surname",
        loader: checkInLoader,
        element: <CheckIn />,
        errorElement: <CheckInErrorBoundary />,
      },
      {
        path: "/dashboard",
        element: <EmployeeOnly Layout={Dashboard} />,
        children: [
          {
            index: true,
            element: <DashboardIndex />,
          },
          {
            path: "list-flight",
            element: <ListFlight />,
            loader: listFlightLoader,
          },
          {
            path: "list-plane",
            element: <ListPlane />,
            loader: listPlaneLoader,
          },
          {
            path: "list-passenger",
            element: <ListPassenger />,
            loader: listPassengerLoader,
          },
          {
            path: "list-employee",
            element: <ListEmployee />,
            loader: listEmployeeLoader,
          },
          {
            path: "list-user",
            element: <ListUser />,
            loader: listUserLoader,
          },
          {
            path: "add-plane",
            element: <AddPlane />
          },
          {
            path: "add-employee",
            element: <AddEmployee />
          },
          {
            path: "add-money",
            element: <AddMoney />
          },
          {
            path: "add-flight",
            element: <AddFlight />
          },
          {
            path: "check-in",
            element: <CheckInPanel />
          }
        ].map(route => Object.assign(route, { errorElement: <DashboardErrorBoundary /> }))
      },
    ],
  },
])

root.render(
  <React.StrictMode>
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <RouterProvider router={router} />
    </ThemeProvider>
  </React.StrictMode>
);
