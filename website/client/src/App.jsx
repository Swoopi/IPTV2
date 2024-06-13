import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Header from './components/header/Header';
import Login from './components/login/Login'; // Create this component
import StripeCheckout from './components/checkout/CheckoutForm';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Header />} />
        <Route path="/login" element={<Login />} />
        <Route path="/checkout" element={<StripeCheckout />} />
      </Routes>
    </Router>
  );
}

export default App;
