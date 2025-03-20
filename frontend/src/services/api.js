import axios from 'axios';

const API_URL = 'http://localhost:8080/api';

export const getProperties = async () => {
  try {
    const response = await axios.get(`${API_URL}/properties`);
    return response.data;
  } catch (error) {
    console.error('Error fetching properties:', error);
    throw error;
  }
};

export const getProperty = async (id) => {
  try {
    const response = await axios.get(`${API_URL}/properties/${id}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching property ${id}:`, error);
    throw error;
  }
};

export const searchProperties = async (location) => {
  try {
    const response = await axios.get(`${API_URL}/properties/search?location=${encodeURIComponent(location)}`);
    return response.data;
  } catch (error) {
    console.error('Error searching properties:', error);
    throw error;
  }
};

export const createProperty = async (propertyData) => {
  try {
    const response = await axios.post(`${API_URL}/properties`, propertyData);
    return response.data;
  } catch (error) {
    console.error('Error creating property:', error);
    throw error;
  }
};

export const updateProperty = async (id, propertyData) => {
  try {
    const response = await axios.put(`${API_URL}/properties/${id}`, propertyData);
    return response.data;
  } catch (error) {
    console.error(`Error updating property ${id}:`, error);
    throw error;
  }
};

export const deleteProperty = async (id) => {
  try {
    await axios.delete(`${API_URL}/properties/${id}`);
    return true;
  } catch (error) {
    console.error(`Error deleting property ${id}:`, error);
    throw error;
  }
};

export const getUsers = async () => {
  try {
    const response = await axios.get(`${API_URL}/users`);
    return response.data;
  } catch (error) {
    console.error('Error fetching users:', error);
    throw error;
  }
};

export const getUser = async (id) => {
  try {
    const response = await axios.get(`${API_URL}/users/${id}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching user ${id}:`, error);
    throw error;
  }
};

export const createUser = async (userData) => {
  try {
    const response = await axios.post(`${API_URL}/users`, userData);
    return response.data;
  } catch (error) {
    console.error('Error creating user:', error);
    throw error;
  }
};
