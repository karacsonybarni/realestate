import { useState, useEffect } from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';
import { propertyService } from '../services/api';

function PropertyDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [property, setProperty] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [activeImage, setActiveImage] = useState(0);
  const [user, setUser] = useState(null);

  useEffect(() => {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      setUser(JSON.parse(storedUser));
    }

    fetchProperty();
  }, [id]);

  const fetchProperty = async () => {
    try {
      setLoading(true);
      const response = await propertyService.getPropertyById(id);
      setProperty(response.data);
      setError(null);
    } catch (err) {
      setError('Failed to fetch property details. Please try again later.');
      console.error('Error fetching property:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async () => {
    if (!window.confirm('Are you sure you want to delete this property?')) return;
    
    try {
      await propertyService.deleteProperty(id);
      navigate('/');
    } catch (err) {
      setError('Failed to delete property. Please try again later.');
      console.error('Error deleting property:', err);
    }
  };

  if (loading) return <div className="text-center"><div className="spinner-border" role="status"></div></div>;
  if (error) return <div className="alert alert-danger">{error}</div>;
  if (!property) return <div className="alert alert-warning">Property not found.</div>;

  const isOwner = user && property.owner && user.id === property.owner.id;

  return (
    <div>
      <div className="mb-4">
        <Link to="/" className="btn btn-outline-secondary">&larr; Back to Properties</Link>
      </div>

      <div className="row">
        <div className="col-md-8">
          {property.images && property.images.length > 0 ? (
            <div>
              <img 
                src={property.images[activeImage].url} 
                className="img-fluid rounded mb-3" 
                alt={property.title} 
              />
              {property.images.length > 1 && (
                <div className="row g-2">
                  {property.images.map((image, index) => (
                    <div className="col-2" key={image.id}>
                      <img 
                        src={image.url} 
                        className={`img-thumbnail ${index === activeImage ? 'border-primary' : ''}`}
                        alt={`Thumbnail ${index + 1}`}
                        onClick={() => setActiveImage(index)}
                        style={{ cursor: 'pointer' }}
                      />
                    </div>
                  ))}
                </div>
              )}
            </div>
          ) : (
            <img 
              src="https://via.placeholder.com/800x600" 
              className="img-fluid rounded mb-3" 
              alt="No image available" 
            />
          )}
        </div>

        <div className="col-md-4">
          <div className="card">
            <div className="card-body">
              <h2 className="card-title">{property.title}</h2>
              <p className="card-text fs-4 fw-bold text-primary">${property.price.toLocaleString()}</p>
              <p className="card-text"><strong>Location:</strong> {property.location}</p>
              
              {property.owner && (
                <p className="card-text"><strong>Owner:</strong> {property.owner.name}</p>
              )}
              
              <div className="mt-4">
                <h4>Description</h4>
                <p>{property.description}</p>
              </div>

              {isOwner && (
                <div className="mt-4 d-flex gap-2">
                  <Link to={`/properties/${id}/edit`} className="btn btn-warning">Edit</Link>
                  <button onClick={handleDelete} className="btn btn-danger">Delete</button>
                </div>
              )}

              {!isOwner && user && (
                <div className="mt-4">
                  <button className="btn btn-primary w-100">Contact Owner</button>
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default PropertyDetail;
