import React from 'react';
import { Link } from 'react-router-dom';
import './header.css';

const Header = () => {
  return (
    <header className="header">
      <video autoPlay muted loop className="header__video">
        <source src="path_to_your_video.mp4" type="video/mp4" />
        Your browser does not support the video tag.
      </video>
      <div className="header__overlay">
        <div className="header__top">
          <div className="header__logo">Logo</div>
          <nav className="header__nav">
            <ul>
              <li><Link to="/">Home</Link></li>
              <li><Link to="/Pricing">Pricing</Link></li>
              <li><Link to="/Channels">Channel List</Link></li>
              <li><Link to="/FAQ">FAQ</Link></li>
            </ul>
          </nav>
          <div className="header__cta">
            <Link to="/login" className="btn btn-primary">Login</Link>
          </div>
        </div>
        <div className="header__container">
          <div className="me">
            <div className="header__text">
              <h3>Fast & Reliable IPTV Service at an Affordable Price</h3>
            </div>
            <h5 className="subtitle">
              Experience breathtaking HD quality with our IPTV service. Enjoy your favorite shows and movies from around the world.
            </h5>
          </div>
        </div>
      </div>
    </header>
  );
};

export default Header;
