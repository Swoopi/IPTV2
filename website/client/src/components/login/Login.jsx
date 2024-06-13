import React from 'react';
import { Link } from 'react-router-dom';

const Login = () => {
  return (
    <div>
      <h2>Login Page</h2>
      {/* Add your login form here */}
      <Link to="/checkout" className="btn btn-primary">Go to Checkout</Link>
    </div>
  );
}

export default Login;
