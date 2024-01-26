import { Stack } from "@mui/material"
import AppBar from "./AppBar"

export default function Page({ children }) {
  return (
    <Stack sx={{ minHeight: '100vh' }}>
      <AppBar />
      {children}
    </Stack>
  );
}

export function EmptyPage({ children }) {
  return (
    <Stack sx={{ width: '100%', height: '100vh' }}>
      {children}
    </Stack>
  );
}