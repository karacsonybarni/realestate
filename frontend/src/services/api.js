import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

export const fetchProperties = async () => {
  try {
    const response = await axios.get(`${API_BASE_URL}/properties`);
    return response.data;
  } catch (error) {
    console.error('Error fetching properties:', error);
    throw error;
  }
};

export const fetchPropertyDetails = async (id) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/properties/${id}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching property details for ID ${id}:`, error);
    throw error;
  }
};

export const fetchUserProfile = async (id) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/users/${id}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching user profile for ID ${id}:`, error);
    throw error;
  }
};
