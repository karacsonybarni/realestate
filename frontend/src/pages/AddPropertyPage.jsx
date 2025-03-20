import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { createProperty, getUsers } from '../services/api';

function AddPropertyPage() {
  const navigate = useNavigate();
  const [users, setUsers] = useState([]);
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    location: '',
    price: '',
    ownerId: '',
    images: [{ url: '' }]
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const data = await getUsers();
        setUsers(data);
        // Set the first user as default owner if available
        if (data.length > 0) {
          setFormData(prev => ({ ...prev, ownerId: data[0].id }));
        }
      } catch (err) {
        setError('Failed to load users. Please try again later.');
      }
    };

    fetchUsers();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: name === 'price' ? parseFloat(value) || '' : value
    }));
  };

  const handleImageChange = (index, value) => {
    const updatedImages = [...formData.images];
    updatedImages[index] = { url: value };
    setFormData(prev => ({ ...prev, images: updatedImages }));
  };

  const addImageField = () => {
    setFormData(prev => ({
      ...prev,
      images: [...prev.images, { url: '' }]
    }));
  };

  const removeImageField = (index) => {
    if (formData.images.length > 1) {
      const updatedImages = formData.images.filter((_, i) => i !== index);
      setFormData(prev => ({ ...prev, images: updatedImages }));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    try {
      // Filter out empty image URLs
      const filteredImages = formData.images.filter(img => img.url.trim() !== '');
      
      // Prepare the property data
      const propertyData = {
        title: formData.title,
        description: formData.description,
        location: formData.location,
        price: parseFloat(formData.price),
        owner: { id: parseInt(formData.ownerId) },
        images: filteredImages
      };

      await createProperty(propertyData);
      navigate('/');
    } catch (err) {
      setError('Failed to create property. Please check your inputs and try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container mt-4">
      <h2 className="mb-4">Add New Property</h2>
      
      {error && (
        <div className="alert alert-danger" role="alert">
          {error}
        </div>
      )}
      
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label htmlFor="title" className="form-label">Title</label>
          <input
            type="text"
            className="form-control"
            id="title"
            name="title"
            value={formData.title}
            onChange={handleChange}
            required
          />
        </div>
        
        <div className="mb-3">
          <label htmlFor="description" className="form-label">Description</label>
          <textarea
            className="form-control"
            id="description"
            name="description"
            rows="5"
            value={formData.description}
            onChange={handleChange}
            required
          ></textarea>
        </div>
        
        <div className="mb-3">
          <label htmlFor="location" className="form-label">Location</label>
          <input
            type="text"
            className="form-control"
            id="location"
            name="location"
            value={formData.location}
            onChange={handleChange}
            required
          />
        </div>
        
        <div className="mb-3">
          <label htmlFor="price" className="form-label">Price ($)</label>
          <input
            type="number"
            className="form-control"
            id="price"
            name="price"
            min="0"
            step="0.01"
            value={formData.price}
            onChange={handleChange}
            required
          />
        </div>
        
        <div className="mb-3">
          <label htmlFor="ownerId" className="form-label">Owner</label>
          <select
            className="form-select"
            id="ownerId"
            name="ownerId"
            value={formData.ownerId}
            onChange={handleChange}
            required
          >
            <option value="">Select an owner</option>
            {users.map(user => (
              <option key={user.id} value={user.id}>
                {user.name} ({user.email})
              </option>
            ))}
          </select>
        </div>
        
        <div className="mb-3">
          <label className="form-label">Images</label>
          {formData.images.map((image, index) => (
            <div className="input-group mb-2" key={index}>
              <input
                type="url"
                className="form-control"
                placeholder="Image URL"
                value={image.url}
                onChange={(e) => handleImageChange(index, e.target.value)}
              />
              <button
                type="button"
                className="btn btn-outline-danger"
                onClick={() => removeImageField(index)}
                disabled={formData.images.length === 1}
              >
                Remove
              </button>
            </div>
          ))}
          <button
            type="button"
            className="btn btn-outline-secondary btn-sm"
            onClick={addImageField}
          >
            + Add Another Image
          </button>
        </div>
        
        <div className="d-grid gap-2 d-md-flex justify-content-md-end">
          <button
            type="button"
            className="btn btn-secondary me-md-2"
            onClick={() => navigate('/')}
          >
            Cancel
          </button>
          <button
            type="submit"
            className="btn btn-primary"
            disabled={loading}
          >
            {loading ? (
              <>
                <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                Saving...
              </>
            ) : 'Save Property'}
          </button>
        </div>
      </form>
    </div>
  );
}

export default AddPropertyPage;