const express = require('express');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');
const path = require('path');

const stuffRoutes = require("./routes/stuff")
const userRoutes = require("./routes/user")
const bonplanRoutes = require("./routes/bonplan")
const {response} = require("express");

mongoose.connect('mongodb+srv://Emile:test@cluster0.nsscu.mongodb.net/myFirstDatabase?retryWrites=true&w=majority',
    {
        useNewUrlParser: true,
        useUnifiedTopology: true
    })
    .then(() => console.log('Connexion à MongoDB réussie !'))
    .catch(() => console.log('Connexion à MongoDB échouée !'));

const app = express();

app.use(express.json());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));

// app.use((req, res, next) => {
//     res.setHeader('Access-Control-Allow-Origin', '*');
//     res.setHeader('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content, Accept, Content-Type, Authorization');
//     res.setHeader('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, PATCH, OPTIONS');
//     next();
// });

app.use('/images', express.static(path.join(__dirname, 'images')))

app.use("/api/stuff", stuffRoutes);
app.use("/api/auth", userRoutes);
app.use("/api/bonplan", bonplanRoutes);

module.exports = app;
