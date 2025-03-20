import { Routes, Route } from 'react-router-dom';
import { useEffect } from 'react';
import './App.css';
import Navbar from './components/Navbar';
import HomePage from './pages/HomePage';
import PropertiesPage from './pages/PropertiesPage';
import PropertyDetailsPage from './pages/PropertyDetailsPage';
import AddPropertyPage from './pages/AddPropertyPage';

function App() {
  // Load Bootstrap JavaScript for components like carousel, navbar, etc.
  useEffect(() => {
    // Dynamic import of Bootstrap JavaScript
    const loadBootstrapJs = async () => {
      try {
        await import('bootstrap/dist/js/bootstrap.bundle.min.js');
      } catch (error) {
        console.error('Failed to load Bootstrap JS:', error);
      }
    };
    
    loadBootstrapJs();
  }, []);

  return (
    <>
      <Navbar />
      <main>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/properties" element={<PropertiesPage />} />
          <Route path="/properties/:id" element={<PropertyDetailsPage />} />
          <Route path="/add-property" element={<AddPropertyPage />} />
        </Routes>
      </main>
      <footer className="container py-5 mt-5 border-top">
        <p className="text-center text-muted">© 2025 Real Estate App. All rights reserved.</p>
      </footer>
    </>
  );
}

export default App;