import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { getProperties } from '../services/api';
import PropertyCard from '../components/PropertyCard';
import PropertySearch from '../components/PropertySearch';
import { searchProperties } from '../services/api';

function PropertiesPage() {
  const [properties, setProperties] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    const fetchProperties = async () => {
      try {
        const data = await getProperties();
        setProperties(data);
        setLoading(false);
      } catch (err) {
        setError('Failed to load properties. Please try again later.');
        setLoading(false);
      }
    };

    fetchProperties();
  }, []);

  const handleSearch = async (location) => {
    setSearchTerm(location);
    setLoading(true);
    try {
      const results = await searchProperties(location);
      setProperties(results);
      setLoading(false);
    } catch (err) {
      setError('Failed to search properties. Please try again.');
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="container mt-5">
        <div className="text-center">
          <div className="spinner-border" role="status">
            <span className="visually-hidden">Loading...</span>
          </div>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="container mt-5">
        <div className="alert alert-danger" role="alert">
          {error}
        </div>
      </div>
    );
  }

  return (
    <div className="container mt-4">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2>All Properties {searchTerm && <span>({searchTerm})</span>}</h2>
        <Link to="/add-property" className="btn btn-primary">
          Add New Property
        </Link>
      </div>

      <div className="row">
        <div className="col-md-3">
          <PropertySearch onSearch={handleSearch} />
        </div>
        <div className="col-md-9">
          {properties.length === 0 ? (
            <div className="alert alert-info">
              No properties found. Try a different search or add a new property.
            </div>
          ) : (
            <div className="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
              {properties.map(property => (
                <div className="col" key={property.id}>
                  <PropertyCard property={property} />
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default PropertiesPage;