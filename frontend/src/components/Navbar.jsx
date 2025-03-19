import { Link } from 'react-router-dom';

function Navbar({ user, onLogout }) {
  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
      <div className="container">
        <Link className="navbar-brand" to="/">Real Estate App</Link>
        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav me-auto">
            <li className="nav-item">
              <Link className="nav-link" to="/">Properties</Link>
            </li>
            {user && (
              <li className="nav-item">
                <Link className="nav-link" to="/create-property">Add Property</Link>
              </li>
            )}
          </ul>
          <div className="d-flex">
            {user ? (
              <div className="d-flex align-items-center">
                <span className="text-light me-3">Welcome, {user.name}</span>
                <button className="btn btn-outline-light" onClick={onLogout}>Logout</button>
              </div>
            ) : (
              <div>
                <Link className="btn btn-outline-light me-2" to="/login">Login</Link>
                <Link className="btn btn-light" to="/register">Register</Link>
              </div>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
}

export default Navbar;
