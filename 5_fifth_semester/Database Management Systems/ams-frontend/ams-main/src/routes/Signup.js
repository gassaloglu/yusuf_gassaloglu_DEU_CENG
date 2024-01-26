import SingupForm from '../components/Signup';
import { Center } from '../components/Styled';
import Page from '../components/Page';

export default function Signup() {
  return (
    <Page>
      <Center>
        <SingupForm />
      </Center>
    </Page>
  );
}