import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { fetchProperties } from '../services/api';

const Home = () => {
  const [properties, setProperties] = useState([]);

  useEffect(() => {
    const getProperties = async () => {
      const data = await fetchProperties();
      setProperties(data);
    };

    getProperties();
  }, []);

  return (
    <div>
      <h1>Properties</h1>
      <ul>
        {properties.map(property => (
          <li key={property.id}>
            <Link to={`/property/${property.id}`}>{property.title}</Link>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Home;
