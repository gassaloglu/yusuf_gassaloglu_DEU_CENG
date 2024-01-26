import { theme } from '../index';
import hero from '../images/home-hero.png'

import { Box, Stack } from '@mui/material';
import Booker from '../components/Booker';
import AppBar from '../components/AppBar';

export default function Home() {
  return (
    <>
      <AppBar />
      <Stack alignItems="center">
        <Hero />
        <Displace by='88px'>
          <Booker />
        </Displace>
      </Stack >
    </>
  );
}

function Displace({ by, children }) {
  return (
    <Box
      sx={{
        width: '900px',
        position: 'relative',
        bottom: by,
        backgroundColor: theme.palette.background.default,
        boxShadow: 3,
        borderRadius: 2
      }}
    >
      {children}
    </Box>
  );
}

function Hero() {
  return (
    <Box
      component="img"
      src={hero}
      sx={{ objectFit: 'cover', width: '100%', height: '420px' }}
    />
  );
}