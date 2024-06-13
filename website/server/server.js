const express = require('express');
const bodyParser = require('body-parser');
const dotenv = require('dotenv');
const stripe = require('stripe');
const cors = require('cors');
const fs = require('fs');
const https = require('https');

// Load environment variables
dotenv.config();

const app = express();
app.use(bodyParser.json());
app.use(cors({ origin: 'https://localhost:3000' }));

app.post('/create-payment-intent', async (req, res) => {
  const { amount } = req.body;
  try {
    const paymentIntent = await stripe(process.env.STRIPE_SECRET_KEY).paymentIntents.create({
      amount,
      currency: 'usd',
    });
    res.send({
      clientSecret: paymentIntent.client_secret,
    });
  } catch (error) {
    res.status(500).send({ error: error.message });
  }
});

const PORT = process.env.PORT || 5001;
const key = fs.readFileSync('./ssl/key.pem');
const cert = fs.readFileSync('./ssl/cert.pem');

https.createServer({ key, cert }, app).listen(PORT, () => {
  console.log(`Server running on port ${PORT}`);
});
