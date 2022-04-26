const Bonplan = require('../models/bonplan');

exports.createBonPlan = (req, res, next) => {
    // console.log('LOG(createBonPlan): ', req.body);
    const pbObject = req.body;
    // const pbObject2 = JSON.parse(req.body.bonplan);
    console.log('pbObject.description: ', pbObject.description);
    // console.log('pbObject2: ', pbObject2);
    // console.log('req.file.filename: ', `${req.protocol}://${req.get('host')}/images/${req.file.filename}`);
    const bp = new Bonplan({
        nomrestaurant: pbObject.nomrestaurant,
        adresserestaurant: pbObject.adresserestaurant,
        typebonplan: pbObject.typebonplan,
        description: pbObject.description,
        dateexpiration: pbObject.dateexpiration,
        // imageUrl: `${req.protocol}://${req.get('host')}/images/${req.file.filename}`  //UTILITER ??
    });
    bp.save()
        .then(() => res.status(201).json({message: 'Objet enregistré !'}))
        .catch(error => res.status(400).json({error}));
};

exports.getOneBonPlan = (req, res, next) => {
    console.log('LOG(getOneBonPlan): ', req.params.id);
    Bonplan.findOne({_id: req.params.id})
        .then(thing => res.status(200).json(thing))
        .catch(error => res.status(404).json({error}));
};

exports.getAllBonPlan = (req, res, next) => {
    Bonplan.find()
        .then(things => res.status(200).json(things))
        .catch(error => res.status(400).json({error}));
};

exports.deleteBonPlan = (req, res, next) => {
    Bonplan.deleteOne({_id: req.params.id})
        .then(() => res.status(200).json({message: 'Objet supprimé !'}))
        .catch(error => res.status(400).json({error}));
};