import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { propertyService } from '../services/api';

function PropertyList() {
  const [properties, setProperties] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchLocation, setSearchLocation] = useState('');

  useEffect(() => {
    fetchProperties();
  }, []);

  const fetchProperties = async () => {
    try {
      setLoading(true);
      const response = await propertyService.getAllProperties();
      setProperties(response.data);
      setError(null);
    } catch (err) {
      setError('Failed to fetch properties. Please try again later.');
      console.error('Error fetching properties:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async (e) => {
    e.preventDefault();
    if (!searchLocation.trim()) {
      fetchProperties();
      return;
    }
    
    try {
      setLoading(true);
      const response = await propertyService.searchProperties(searchLocation);
      setProperties(response.data);
      setError(null);
    } catch (err) {
      setError('Failed to search properties. Please try again later.');
      console.error('Error searching properties:', err);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <div className="text-center"><div className="spinner-border" role="status"></div></div>;

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2>Properties</h2>
        <form className="d-flex" onSubmit={handleSearch}>
          <input 
            className="form-control me-2" 
            type="search" 
            placeholder="Search by location" 
            value={searchLocation}
            onChange={(e) => setSearchLocation(e.target.value)}
          />
          <button className="btn btn-outline-primary" type="submit">Search</button>
        </form>
      </div>

      {error && <div className="alert alert-danger">{error}</div>}

      {properties.length === 0 && !loading && !error ? (
        <div className="alert alert-info">No properties found.</div>
      ) : (
        <div className="row row-cols-1 row-cols-md-3 g-4">
          {properties.map((property) => (
            <div className="col" key={property.id}>
              <div className="card h-100 property-card">
                <img 
                  src={property.images && property.images.length > 0 ? property.images[0].url : 'https://via.placeholder.com/300x200'} 
                  className="card-img-top property-image" 
                  alt={property.title} 
                />
                <div className="card-body">
                  <h5 className="card-title">{property.title}</h5>
                  <p className="card-text">{property.location}</p>
                  <p className="card-text fw-bold">${property.price.toLocaleString()}</p>
                  <Link to={`/properties/${property.id}`} className="btn btn-primary">View Details</Link>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default PropertyList;
