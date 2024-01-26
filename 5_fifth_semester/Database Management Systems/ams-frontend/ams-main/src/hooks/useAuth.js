import { createContext, useContext, useMemo, useState } from "react";
import { useNavigate } from "react-router-dom";
import { axios } from '../index';

const AuthContext = createContext();

// https://blog.logrocket.com/complete-guide-authentication-with-react-router-v6
export function AuthProvider({ children }) {
  const [user, setUser] = useState(JSON.parse(localStorage.getItem('user')));
  const navigate = useNavigate();

  const login = async user => {
    setUser(user);
    localStorage.setItem('user', JSON.stringify(user));
    axios.defaults.headers = { Authorization: `Bearer ${user.token}` };
  };

  const logout = () => {
    setUser(null);
    localStorage.removeItem('user');
    axios.defaults.headers = {};
    navigate("/", { replace: true });
  };

  const provided = useMemo(() => ({ user, login, logout }), [user]);

  return (
    <AuthContext.Provider value={provided}>
      {children}
    </AuthContext.Provider>
  );
}

export const useAuth = () => useContext(AuthContext);