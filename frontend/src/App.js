import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Navbar from './components/Navbar';
import Home from './pages/Home';
import PropertyDetails from './pages/PropertyDetails';
import UserProfile from './pages/UserProfile';

function App() {
  return (
    <Router>
      <div>
        <Navbar />
        <Switch>
          <Route path="/" exact component={Home} />
          <Route path="/property/:id" component={PropertyDetails} />
          <Route path="/user/:id" component={UserProfile} />
        </Switch>
      </div>
    </Router>
  );
}

export default App;
