import { Center } from "../components/Styled";
import Page from "../components/Page";
import { Contact } from "../components/Contact";
import { Grid, Stack, Typography } from "@mui/material";

export default function About() {
  return (
    <Page>
      <Center>
        <Stack alignItems='center' spacing={5}>
          <Typography fontWeight='bold' variant="h2"> ✨ Our amazing team ✨</Typography>
          <Stack direction='row' spacing={2}>
            <Contact
              name="Berkay"
              surname="Dinç"
              email="berkayydinc@gmail.com"
              github="https://github.com/brkydnc"
              linkedin="https://linkedin.com/in/berkay-dinç-2918b0222"
            />

            <Contact
              name="Can"
              surname="Çelenay"
              email="cancelenay@gmail.com"
              github="https://github.com/Brogolem35"
              linkedin="https://linkedin.com/in/can-celenay"
            />

            <Contact
              name="Yusuf"
              surname="Gassaloğlu"
              email="yusufgassaloglu00@gmail.com"
              github="https://github.com/gassaloglu"
              linkedin="https://linkedin.com/in/gassaloglu"
            />

            <Contact
              name="Güney"
              surname="Söğüt"
              email="guneysogut.10@gmail.com"
              github="https://github.com/guneysogutt"
              linkedin="https://linkedin.com/in/guney-sogut-23a081223"
            />
          </Stack>
        </Stack>
      </Center>
    </Page >
  );
}