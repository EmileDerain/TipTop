const User = require('../models/user');
bcrypt = require('bcrypt');
// const jwt = require('jsonwebtoken');

exports.signup = (req, res, next) => {
    console.log("SIGNUP", req.body);
    bcrypt.hash(req.body.password, 1)
        .then(hash => {
            const user = new User({
                email: req.body.email,
                userName: req.body.userName,
                password: hash
            });
            user.save()
                .then(() => res.status(201).json({message: 'Utilisateur créé !'}))
                .catch(error => {
                    console.log("ERROR: " + error);
                    res.status(400).json({error});
                });
        })
        .catch(error => res.status(500).json({error}));
};

exports.login = (req, res, next) => {
    User.findOne({email: req.body.email})
        .then(user => {
            if (!user) {
                return res.status(401).json({error: 'Utilisateur non trouvé', correct: "false"});
            }
            bcrypt.compare(req.body.password, user.password)
                .then(valid => {
                    if (!valid) {
                        return res.status(401).json({error: 'Mot de passe incorrect', correct: "false"});
                    }
                    res.status(200).json({
                        correct: "true",
                        error: 'connexion',
                        user: user.userName,
                    });
                })
                .catch(error => res.status(500).json({error, correct: "false"}));
        })
        .catch(error => res.status(500).json({error, correct: "false"}));
};