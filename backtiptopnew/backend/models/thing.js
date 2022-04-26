const mongoose = require('mongoose');

const thingSchema = mongoose.Schema({
    title: { type: String, required: true },
    description: { type: String, required: true },
});

module.exports = mongoose.model('Thing', thingSchema);