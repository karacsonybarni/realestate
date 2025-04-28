import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { fetchPropertyDetails } from '../services/api';

const PropertyDetails = () => {
  const { id } = useParams();
  const [property, setProperty] = useState(null);

  useEffect(() => {
    const getPropertyDetails = async () => {
      const data = await fetchPropertyDetails(id);
      setProperty(data);
    };

    getPropertyDetails();
  }, [id]);

  if (!property) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <h1>{property.title}</h1>
      <p>{property.description}</p>
      <p>Location: {property.location}</p>
      <p>Price: ${property.price}</p>
      <div>
        {property.images.map((image, index) => (
          <img key={index} src={image.url} alt={`Property Image ${index + 1}`} />
        ))}
      </div>
    </div>
  );
};

export default PropertyDetails;
