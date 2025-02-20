import React from 'react';
import { Link } from 'react-router-dom';

const Navbar = () => {
  return (
    <nav>
      <ul>
        <li>
          <Link to="/">Home</Link>
        </li>
        <li>
          <Link to="/property/:id">Property Details</Link>
        </li>
        <li>
          <Link to="/user/:id">User Profile</Link>
        </li>
      </ul>
    </nav>
  );
};

export default Navbar;
