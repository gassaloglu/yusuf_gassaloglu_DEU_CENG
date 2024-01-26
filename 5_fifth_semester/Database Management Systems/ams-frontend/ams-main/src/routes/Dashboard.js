import { Avatar, Box, Button, Divider, Drawer, IconButton, List, ListItemButton, ListItemIcon, ListItemText, Stack, Toolbar, Typography } from '@mui/material';
import React from 'react';
import { Link, NavLink as NavLinkBase, Outlet, useRouteError } from 'react-router-dom';
import Error from "../components/Error";
import { Center } from "../components/Styled";
import { useAuth } from '../hooks/useAuth';
import { AirlineSeatReclineNormal, Airlines, AirplaneTicket, AttachMoney, Badge, ConnectingAirports, Flight, FlightTakeoff, Person, PersonAddAlt1, Warehouse } from '@mui/icons-material';
import { EmptyPage } from '../components/Page';

const DARK_BLUE = "#1565c0";
const DRAWER_WIDTH = "300px";

const tools = {
  listFlights: { name: "List flights", link: "/dashboard/list-flight", icon: <FlightTakeoff /> },
  listPlanes: { name: "List planes", link: "/dashboard/list-plane", icon: <Flight /> },
  listPassengers: { name: "List passengers", link: "/dashboard/list-passenger", icon: <AirlineSeatReclineNormal /> },
  listEmployee: { name: "List employees", link: "/dashboard/list-employee", icon: <Badge /> },
  listUser: { name: "List users", link: "/dashboard/list-user", icon: <Person /> },
  addPlane: { name: "Add plane", link: "/dashboard/add-plane", icon: <Warehouse /> },
  addEmployee: { name: "Add employee", link: "/dashboard/add-employee", icon: <PersonAddAlt1 /> },
  addMoney: { name: "Add money to user", link: "/dashboard/add-money", icon: <AttachMoney /> },
  addFlight: { name: "Add flight", link: "/dashboard/add-flight", icon: <ConnectingAirports /> },
  checkIn: { name: "Check-in", link: "/dashboard/check-in", icon: <AirplaneTicket /> },
};

const toolsOfPermission = {
  "flight_planner": [
    tools.listFlights,
    tools.listPlanes,
    tools.addFlight,
  ],
  "passenger_services": [
    tools.listFlights,
    tools.listPassengers,
    tools.checkIn,
  ],
  "seller": [
    tools.listUser,
    tools.addMoney
  ],
  "admin": Object.values(tools),
};

const NavLink = React.forwardRef((props, ref) => (
  <NavLinkBase
    ref={ref}
    {...props}
    style={({ isActive }) => {
      return {
        backgroundColor: isActive ? DARK_BLUE : "",
      };
    }}
  />
));

export function Dashboard() {
  const { user, logout } = useAuth();

  return (
    <Stack direction='row'>
      <Drawer
        anchor='left'
        variant='permanent'
        sx={{
          width: DRAWER_WIDTH,
          flexShrink: 0,
          '& .MuiDrawer-paper': {
            width: DRAWER_WIDTH,
            boxSizing: 'border-box',
            color: "white",
            backgroundColor: "primary.main",
            overflowX: 'hidden',
          },
        }}
      >
        <Toolbar
          sx={{
            p: 2,
            minWidth: '300px',
            alignItems: 'center',
            justifyContent: 'center',
            flexFlow: 'column'
          }}>
          <IconButton
            disableRipple
            color='inherit'
            aria-label='logo'
            LinkComponent={Link}
            to='/'
          >
            <Airlines sx={{ fontSize: '150px' }} />
          </IconButton>
          <Typography variant='h6'> AIRLINE COMPANY </Typography>
        </Toolbar>

        <Divider />

        <List>
          {
            toolsOfPermission[user.permission].map(tool =>
              <ListItemButton key={tool.name} LinkComponent={NavLink} to={tool.link}>
                <ListItemIcon sx={{ display: 'flex', justifyContent: 'center', color: 'white' }}>
                  {tool.icon}
                </ListItemIcon>
                <ListItemText primary={tool.name} />
              </ListItemButton>
            )
          }
        </List>

        <Box flexGrow={1} />

        <Divider />

        <Stack spacing={2} sx={{ p: 2 }}>
          <Stack direction='row' spacing={1} alignItems='center'>
            <Avatar sx={{ bgcolor: 'primary.main', border: 1 }}>
              {user.name[0] + user.surname[0]}
            </Avatar>
            <Stack>
              <Typography fontWeight='bold'> {user.name} {user.surname}</Typography>
              <Typography variant='caption'> {permissionString[user.permission]}</Typography>
            </Stack>
          </Stack>

          <Button
            variant='outlined'
            color='inherit'
            onClick={() => logout()}
          >
            Log out
          </Button>

        </Stack>

      </ Drawer >
      <Outlet />
    </Stack>
  );
}

export function DashboardErrorBoundary() {
  const error = useRouteError();

  return (
    <EmptyPage>
      <Center>
        {
          error.response
            ?
            <Error title="Error">
              <strong>Status:</strong> {error.response.status} {error.response.statusText}
              <br />
              <strong>Error message:</strong> {error.response.data}
            </Error>
            :
            <Error title="Something went wrong">
              An unknown error has occurred, please check your internet connection.
            </Error>
        }
      </Center >
    </EmptyPage>
  );
}

export const permissionString = {
  'admin': 'Admin',
  'passenger_services': 'Passenger services',
  'flight_planner': 'Flight planner',
  'seller': 'Seller',
}