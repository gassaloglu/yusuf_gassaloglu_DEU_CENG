import LoginForm from '../components/Login';
import { Center } from '../components/Styled';
import Page from '../components/Page';

export default function Login() {
  return (
    <Page>
      <Center>
        <LoginForm />
      </Center>
    </Page>
  );
}