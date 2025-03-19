import { useState } from 'react';
import { Link } from 'react-router-dom';
import { userService } from '../services/api';

function LoginForm({ onLogin }) {
  const [formData, setFormData] = useState({
    email: '',
    password: ''
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    // In a real application, you would actually verify the credentials with your backend
    // For this demo, we'll just simulate a successful login with the first user
    try {
      const response = await userService.getAllUsers();
      if (response.data && response.data.length > 0) {
        // Simulate login with first user for demo
        const user = response.data[0];
        onLogin(user);
      } else {
        setError('No users found. Please register first.');
      }
    } catch (err) {
      setError('Failed to login. Please check your credentials and try again.');
      console.error('Login error:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="row justify-content-center">
      <div className="col-md-6">
        <div className="card">
          <div className="card-body">
            <h2 className="card-title text-center mb-4">Login</h2>
            
            {error && <div className="alert alert-danger">{error}</div>}
            
            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label htmlFor="email" className="form-label">Email</label>
                <input
                  type="email"
                  className="form-control"
                  id="email"
                  name="email"
                  value={formData.email}
                  onChange={handleChange}
                  required
                />
              </div>
              
              <div className="mb-3">
                <label htmlFor="password" className="form-label">Password</label>
                <input
                  type="password"
                  className="form-control"
                  id="password"
                  name="password"
                  value={formData.password}
                  onChange={handleChange}
                  required
                />
              </div>
              
              <div className="d-grid gap-2">
                <button 
                  type="submit" 
                  className="btn btn-primary" 
                  disabled={loading}
                >
                  {loading ? (
                    <><span className="spinner-border spinner-border-sm me-2" role="status"></span>Logging in...</>
                  ) : (
                    'Login'
                  )}
                </button>
              </div>
            </form>
            
            <div className="text-center mt-3">
              <p>Don't have an account? <Link to="/register">Register</Link></p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default LoginForm;
