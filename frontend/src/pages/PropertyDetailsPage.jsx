import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { getProperty } from '../services/api';

function PropertyDetailsPage() {
  const { id } = useParams();
  const [property, setProperty] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchProperty = async () => {
      try {
        const data = await getProperty(id);
        setProperty(data);
        setLoading(false);
      } catch (err) {
        setError('Failed to load property details. Please try again later.');
        setLoading(false);
      }
    };

    fetchProperty();
  }, [id]);

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

  if (!property) {
    return (
      <div className="container mt-5">
        <div className="alert alert-warning" role="alert">
          Property not found.
        </div>
        <Link to="/" className="btn btn-primary">Back to Home</Link>
      </div>
    );
  }

  return (
    <div className="container mt-4">
      <nav aria-label="breadcrumb">
        <ol className="breadcrumb">
          <li className="breadcrumb-item"><Link to="/">Home</Link></li>
          <li className="breadcrumb-item active" aria-current="page">{property.title}</li>
        </ol>
      </nav>

      <div className="row mb-4">
        <div className="col-md-8">
          <h1 className="mb-3">{property.title}</h1>
          <h5 className="text-muted mb-4">{property.location}</h5>

          {property.images && property.images.length > 0 ? (
            <div id="propertyCarousel" className="carousel slide mb-4" data-bs-ride="carousel">
              <div className="carousel-inner">
                {property.images.map((image, index) => (
                  <div className={`carousel-item ${index === 0 ? 'active' : ''}`} key={image.id}>
                    <img 
                      src={image.url} 
                      className="d-block w-100 rounded" 
                      alt={`Property image ${index + 1}`}
                      style={{ height: '400px', objectFit: 'cover' }}
                    />
                  </div>
                ))}
              </div>
              {property.images.length > 1 && (
                <>
                  <button className="carousel-control-prev" type="button" data-bs-target="#propertyCarousel" data-bs-slide="prev">
                    <span className="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span className="visually-hidden">Previous</span>
                  </button>
                  <button className="carousel-control-next" type="button" data-bs-target="#propertyCarousel" data-bs-slide="next">
                    <span className="carousel-control-next-icon" aria-hidden="true"></span>
                    <span className="visually-hidden">Next</span>
                  </button>
                </>
              )}
            </div>
          ) : (
            <div 
              className="bg-secondary text-white d-flex align-items-center justify-content-center rounded mb-4"
              style={{ height: '400px' }}
            >
              No Images Available
            </div>
          )}

          <div className="card mb-4">
            <div className="card-body">
              <h5 className="card-title">Description</h5>
              <p className="card-text">{property.description}</p>
            </div>
          </div>
        </div>

        <div className="col-md-4">
          <div className="card sticky-top" style={{ top: '2rem' }}>
            <div className="card-body">
              <h5 className="card-title">Price</h5>
              <h3 className="card-text mb-4">${property.price.toLocaleString()}</h3>
              
              <h5 className="card-title">Contact Owner</h5>
              <p className="card-text">
                <strong>Name:</strong> {property.owner.name}<br />
                <strong>Email:</strong> {property.owner.email}
              </p>

              <button className="btn btn-primary w-100 mb-2">Contact Owner</button>
              <button className="btn btn-outline-primary w-100">Schedule Viewing</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default PropertyDetailsPage;