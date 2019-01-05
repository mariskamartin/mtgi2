create table card
(
   id BIGINT AUTO_INCREMENT,
   name varchar(255) not null,
   foil BOOLEAN not null,
   created DATETIME,
   updated DATETIME,
   rarity varchar(10) not null,
   edition varchar(10) not null
);

ALTER TABLE card ADD PRIMARY KEY (name, rarity, edition, foil)