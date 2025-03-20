import { Link } from 'react-router-dom';

function PropertyCard({ property }) {
  return (
    <div className="card h-100 shadow-sm">
      {property.images && property.images.length > 0 ? (
        <img 
          src={property.images[0].url} 
          className="card-img-top" 
          alt={property.title} 
          style={{ height: '200px', objectFit: 'cover' }} 
        />
      ) : (
        <div 
          className="bg-secondary text-white d-flex align-items-center justify-content-center"
          style={{ height: '200px' }}
        >
          No Image Available
        </div>
      )}
      <div className="card-body">
        <h5 className="card-title">{property.title}</h5>
        <p className="card-text text-muted mb-1">{property.location}</p>
        <p className="card-text fw-bold">${property.price.toLocaleString()}</p>
        <p className="card-text">
          {property.description.length > 100 
            ? `${property.description.substring(0, 100)}...` 
            : property.description}
        </p>
      </div>
      <div className="card-footer bg-white border-top-0">
        <Link to={`/properties/${property.id}`} className="btn btn-primary w-100">
          View Details
        </Link>
      </div>
    </div>
  );
}

export default PropertyCard;