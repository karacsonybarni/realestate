import { useState } from 'react';

function PropertySearch({ onSearch }) {
  const [location, setLocation] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    onSearch(location);
  };

  return (
    <div className="card mb-4">
      <div className="card-body">
        <h5 className="card-title">Search Properties</h5>
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label htmlFor="location" className="form-label">Location</label>
            <input
              type="text"
              className="form-control"
              id="location"
              value={location}
              onChange={(e) => setLocation(e.target.value)}
              placeholder="Enter city, neighborhood, etc."
            />
          </div>
          <button type="submit" className="btn btn-primary">Search</button>
        </form>
      </div>
    </div>
  );
}

export default PropertySearch;