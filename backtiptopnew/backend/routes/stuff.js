const express = require('express');
const Thing = require("../models/thing");
const {createThing, getOneThing, modifyThing, deleteThing, getAllThing} = require("../controllers/stuff");

const router = express.Router();

router.post('/', createThing);
router.get('/:id', getOneThing);
router.get('/', getAllThing);
router.put('/:id', modifyThing);
router.delete('/:id', deleteThing);

module.exports = router;