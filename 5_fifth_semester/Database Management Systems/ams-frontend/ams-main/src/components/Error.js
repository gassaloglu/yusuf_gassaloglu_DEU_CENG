import { Paper, Typography } from "@mui/material";

export default function Error({ title, children }) {
  return (
    <Paper sx={{ p: 5, borderRadius: 2, minWidth: '500px' }} elevation={3} >
      <Typography variant='h3'>
        {title}
      </Typography>
      <Typography sx={{ mt: 3 }}>
        {children}
      </Typography>
    </Paper>
  )
}