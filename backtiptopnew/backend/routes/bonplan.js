const express = require('express');
const router = express.Router();

const userCtrl = require('../controllers/bonplan');
const multer = require('../middleware/multer-config')


router.post('/', multer, userCtrl.createBonPlan);
router.get('/:id', userCtrl.getOneBonPlan);
router.get('/', userCtrl.getAllBonPlan);
// router.put('/:id', modifyThing);
router.delete('/:id', userCtrl.deleteBonPlan);

module.exports = router;