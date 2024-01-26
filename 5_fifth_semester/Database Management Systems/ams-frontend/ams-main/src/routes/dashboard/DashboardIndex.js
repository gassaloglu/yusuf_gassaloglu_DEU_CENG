import { Typography, Stack } from "@mui/material";
import { EmptyPage } from "../../components/Page";
import { useAuth } from "../../hooks/useAuth";

export default function DashboardIndex() {
  const { user } = useAuth();

  return (
    <EmptyPage>
      <Stack
        alignItems='center'
        justifyContent='center'
        sx={{ width: '100%', height: '100vh' }}
      >
        <Typography variant='h2' fontWeight='bold'>
          Welcome to your dashboard,
        </Typography>

        <Typography variant='h2'>
          {user.name} {user.surname}!
        </Typography>
      </Stack>
    </EmptyPage>
  );
}