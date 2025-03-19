import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import Navbar from './components/Navbar';
import PropertyList from './components/PropertyList';
import PropertyDetail from './components/PropertyDetail';
import LoginForm from './components/LoginForm';
import RegisterForm from './components/RegisterForm';
import CreateProperty from './components/CreateProperty';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

function App() {
  const [user, setUser] = useState(null);

  useEffect(() => {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      setUser(JSON.parse(storedUser));
    }
  }, []);

  const handleLogin = (userData) => {
    setUser(userData);
    localStorage.setItem('user', JSON.stringify(userData));
  };

  const handleLogout = () => {
    setUser(null);
    localStorage.removeItem('user');
  };

  return (
    <BrowserRouter>
      <Navbar user={user} onLogout={handleLogout} />
      <div className="container mt-4">
        <Routes>
          <Route path="/" element={<PropertyList />} />
          <Route path="/properties/:id" element={<PropertyDetail />} />
          <Route path="/login" element={!user ? <LoginForm onLogin={handleLogin} /> : <Navigate to="/" />} />
          <Route path="/register" element={!user ? <RegisterForm onLogin={handleLogin} /> : <Navigate to="/" />} />
          <Route path="/create-property" element={user ? <CreateProperty /> : <Navigate to="/login" />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
