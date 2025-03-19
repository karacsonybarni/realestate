import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { propertyService } from '../services/api';

function CreateProperty() {
  const navigate = useNavigate();
  const [user, setUser] = useState(null);
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    location: '',
    price: '',
    imageUrls: ['']
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      setUser(JSON.parse(storedUser));
    }
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleImageUrlChange = (index, value) => {
    const newImageUrls = [...formData.imageUrls];
    newImageUrls[index] = value;
    setFormData(prev => ({
      ...prev,
      imageUrls: newImageUrls
    }));
  };

  const addImageUrlField = () => {
    setFormData(prev => ({
      ...prev,
      imageUrls: [...prev.imageUrls, '']
    }));
  };

  const removeImageUrlField = (index) => {
    if (formData.imageUrls.length === 1) return;
    
    const newImageUrls = formData.imageUrls.filter((_, i) => i !== index);
    setFormData(prev => ({
      ...prev,
      imageUrls: newImageUrls
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!user) {
      setError('You must be logged in to create a property.');
      return;
    }
    
    setLoading(true);
    setError(null);
    
    try {
      // Filter out empty image URLs
      const filteredImageUrls = formData.imageUrls.filter(url => url.trim() !== '');
      
      const propertyData = {
        ...formData,
        price: parseFloat(formData.price),
        ownerId: user.id,
        imageUrls: filteredImageUrls
      };
      
      const response = await propertyService.createProperty(propertyData);
      navigate(`/properties/${response.data.id}`);
    } catch (err) {
      setError('Failed to create property. Please try again later.');
      console.error('Error creating property:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <h2 className="mb-4">Create New Property</h2>
      
      {error && <div className="alert alert-danger">{error}</div>}
      
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
          <label htmlFor="price" className="form-label">Price</label>
          <div className="input-group">
            <span className="input-group-text">$</span>
            <input
              type="number"
              className="form-control"
              id="price"
              name="price"
              value={formData.price}
              onChange={handleChange}
              min="0"
              step="0.01"
              required
            />
          </div>
        </div>
        
        <div className="mb-3">
          <label htmlFor="description" className="form-label">Description</label>
          <textarea
            className="form-control"
            id="description"
            name="description"
            rows="4"
            value={formData.description}
            onChange={handleChange}
            required
          ></textarea>
        </div>
        
        <div className="mb-3">
          <label className="form-label">Images</label>
          {formData.imageUrls.map((url, index) => (
            <div className="input-group mb-2" key={index}>
              <input
                type="url"
                className="form-control"
                placeholder="Image URL"
                value={url}
                onChange={(e) => handleImageUrlChange(index, e.target.value)}
              />
              <button 
                type="button" 
                className="btn btn-outline-danger"
                onClick={() => removeImageUrlField(index)}
                disabled={formData.imageUrls.length === 1}
              >
                Remove
              </button>
            </div>
          ))}
          <button 
            type="button" 
            className="btn btn-outline-secondary"
            onClick={addImageUrlField}
          >
            Add Another Image
          </button>
        </div>
        
        <div className="d-grid gap-2 d-md-flex justify-content-md-end">
          <button 
            type="button" 
            className="btn btn-secondary"
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
              <><span className="spinner-border spinner-border-sm me-2" role="status"></span>Creating...</>
            ) : (
              'Create Property'
            )}
          </button>
        </div>
      </form>
    </div>
  );
}

export default CreateProperty;