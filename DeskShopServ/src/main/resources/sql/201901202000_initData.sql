INSERT INTO PERSONNE(nom, prenom, mail, password) VALUES ('Pérol', 'François', 'f-perol@epargne.fr', '1234'),
                                                         ('Bezos', 'Jeff', 'jeff.bezos@amazon.com',  '1234'),
                                                         ('User', 'Lambda', 'lambda.user@nothing.rien', '1234');

INSERT INTO MAGASIN(nom, id_personne) VALUES ('Amazon', 2);

INSERT INTO ARTICLE(nom, image, description, prix, id_magasin) VALUES ('Camera', null, null, 249.99, 1),
                                                                      ('Stady cam', null, null, 49.99, 1),
                                                                      ('Microphone', null, null, 9.99, 1);

INSERT INTO COMPTE(nom, solde, id_personne) VALUES ('compte courant', 1000, 1),
                                                   ('compte courant', 500,  2),
                                                   ('compte courant', 2000, 3);

INSERT INTO ADMINISTRE(id_compte, id_admin) VALUES (1,1),
                                                   (2,1),
                                                   (3,1);