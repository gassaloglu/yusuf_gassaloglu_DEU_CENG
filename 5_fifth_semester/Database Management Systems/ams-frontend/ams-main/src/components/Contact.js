import { Avatar, Paper, Typography, Stack, IconButton } from "@mui/material";
import { GitHub, LinkedIn } from "@mui/icons-material";

export function Contact({ name, surname, email, github, linkedin }) {
  return (
    <Paper
      elevation={5}
      sx={{
        borderRadius: 3,
        p: 3
      }}
    >
      <Stack sx={{
        width: '240px',
        height: '240px',
      }}
        justifyContent='center'
        alignItems='center'
        spacing={1}
      >
        <Avatar src={github + '.png'} sx={{ width: '100px', height: '100px' }} />

        <Stack justifyContent='center' alignItems='center'>
          <Typography fontWeight='bold' fontSize='30px'>
            {name} {surname}
          </Typography>
          <Typography variant="body2" color='grey'>
            {email}
          </Typography>
        </Stack>

        <Stack direction='row' justifyContent='center' alignItems='center'>
          <IconButton href={github}>
            <GitHub />
          </IconButton>

          <IconButton href={linkedin}>
            <LinkedIn />
          </IconButton>
        </Stack>
      </Stack>
    </Paper>
  )
}