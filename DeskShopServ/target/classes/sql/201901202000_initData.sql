INSERT INTO PERSONNE(nom, prenom, mail, password) VALUES ('Pérol', 'François', 'f-perol@epargne.fr', '1234'),
                                                         ('Bezos', 'Jeff', 'jeff.bezos@amazon.com',  '1234'),
                                                         ('User', 'Lambda', 'lambda.user@nothing.rien', '1234');

INSERT INTO MAGASIN(nom, id_personne, iban) VALUES ('Amazon', 2, 'FR000000000000000002');

INSERT INTO ARTICLE(nom, image, description, prix, id_magasin) VALUES ('Camera', null, null, 249.99, 1),
                                                                      ('Stady cam', null, null, 49.99, 1),
                                                                      ('Microphone', null, null, 9.99, 1);

INSERT INTO COMPTE(nom, solde, id_personne, iban) VALUES ('compte courant', 1000, 1, 'FR000000000000000001'),
                                                         ('compte courant', 500,  2, 'FR000000000000000002'),
                                                         ('compte courant', 2000, 3, 'FR000000000000000003');

INSERT INTO ADMINISTRE(id_compte, id_admin) VALUES (1,1),
                                                   (2,1),
                                                   (3,1);

INSERT INTO commande(date_achat, id_personne, id_magasin) VALUES (NOW(), 3, 1);

INSERT INTO detail_commande(id_commande, id_article, qtt_cmde) VALUES (1,1,10),
                                                                      (1,2,2);


