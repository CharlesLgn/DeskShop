-- table person
CREATE TABLE PERSONNE (
  id       INTEGER(11)  NOT NULL AUTO_INCREMENT,
  nom      VARCHAR(255) NOT NULL,
  prenom   VARCHAR(255),
  mail     VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1;

-- table shop
CREATE TABLE MAGASIN (
  id          INTEGER(11)  NOT NULL AUTO_INCREMENT,
  nom         VARCHAR(255) NOT NULL,
  id_personne INTEGER(11)  NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1;
ALTER TABLE MAGASIN
  ADD CONSTRAINT FK_magasin_person FOREIGN KEY (id_personne) REFERENCES PERSONNE(id) on delete cascade;

-- table article
CREATE TABLE ARTICLE (
  id          INTEGER(11)  NOT NULL AUTO_INCREMENT,
  nom         VARCHAR(255) NOT NULL,
  image       VARCHAR(255),
  description VARCHAR(255),
  prix        DOUBLE(5,2)  NOT NULL,
  id_magasin  INTEGER(11)  NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1;
ALTER TABLE ARTICLE
  ADD CONSTRAINT FK_article_magasin FOREIGN KEY (id_magasin) REFERENCES MAGASIN(id) on delete cascade;

-- table command
CREATE TABLE COMMANDE (
  id          INTEGER(11) NOT NULL AUTO_INCREMENT,
  date_achat  DATETIME    DEFAULT CURRENT_TIMESTAMP(),
  qtt_cmde    INTEGER(5)  NOT NULL,
  id_personne INTEGER(11) NOT NULL,
  id_magasin  INTEGER(11) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1;
ALTER TABLE COMMANDE
  ADD CONSTRAINT FK_command_person FOREIGN KEY (id_personne) REFERENCES PERSONNE(id) on delete cascade,
  ADD CONSTRAINT FK_command_shop FOREIGN KEY (id_magasin) REFERENCES MAGASIN(id) on delete cascade;

-- table compte
CREATE TABLE COMPTE (
  id          INTEGER(11)  NOT NULL AUTO_INCREMENT,
  nom         VARCHAR(255) NOT NULL,
  solde       DOUBLE(8,2)  NOT NULL,
  id_personne INTEGER(11)  NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1;
ALTER TABLE COMPTE
  ADD CONSTRAINT FK_compte_person FOREIGN KEY (id_personne) REFERENCES PERSONNE(id) on delete cascade;

-- table mouvement
CREATE TABLE MOUVEMENT (
  id          INTEGER(11) NOT NULL AUTO_INCREMENT,
  date_move   DATETIME    DEFAULT CURRENT_TIMESTAMP(),
  montant     DOUBLE(8,2) NOT NULL,
  id_compte INTEGER(11)   NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1;
alter table mouvement
  ADD CONSTRAINT FK_mouvement_compte FOREIGN KEY (id_compte) REFERENCES compte (id) on delete cascade;

-- table administre
CREATE TABLE ADMINISTRE (
                         id          INTEGER(11) NOT NULL AUTO_INCREMENT,
                         id_compte INTEGER(11)   NOT NULL,
                         id_admin  INTEGER(11)   NOT NULL,
                         PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1;
alter table ADMINISTRE
  ADD CONSTRAINT FK_administre_compte FOREIGN KEY (id_compte) REFERENCES COMPTE(id) on delete cascade,
  ADD CONSTRAINT FK_administre_person FOREIGN KEY (id_admin) REFERENCES PERSONNE(id) on delete cascade;