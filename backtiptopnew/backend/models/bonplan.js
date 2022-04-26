const mongoose = require('mongoose');

const bonplanSchema = mongoose.Schema({
    nomrestaurant: {type: String, required: true},
    adresserestaurant: {type: String, required: true},
    typebonplan: {type: String, required: true},
    description: {type: String, required: true},
    dateexpiration: {type: String, required: true},
});

module.exports = mongoose.model('BonPlan', bonplanSchema);