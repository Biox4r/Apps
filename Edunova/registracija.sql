drop database if exists registracija;
create database registracija character set utf8 collate utf8_general_ci ;
use registracija;

create table zaposlenik(
sifra int  primary key auto_increment,
ime varchar(50) not null,
prezime varchar(50) not null,
korisnickoime char(50) not null,
lozinka char(32) not null
)engine=innodb;

create table stranka(
sifra int not null primary key auto_increment,
oib char (11) not null,
ime varchar(50) not null,
prezime varchar(50) not null,
mobitel varchar (20) not null,
adresa varchar(50) not null,
datumrodjenja datetime null,
adresa2 varchar(100) null,
drugimobitel varchar (20) null,
fiksnitelefon varchar (20) null,
email varchar(100)  null,
drzava varchar(50) null,
grad varchar (50),
zip char(5) null,
regija varchar(50) null
)engine=innodb;

create table vozilo(
sifra int not null primary key auto_increment,
stranka int not null,
modelvozila varchar(50) not null, 
kategorijavozila varchar(50) not null, 
proizvodjacvozila varchar(50) not null, 
nazivvozila varchar(100) not null,
tablice char (15) not null,
godiste int(4) not null,
boja varchar(20) not null,
brojsasije varchar(50) null,
snagamotora int,
tezinavozila decimal(18,2) null,
brojvrata int,
ostalo varchar(400) null
)engine=innodb;

create table tehnicki(
sifra int not null primary key auto_increment,
vozilo int not null,
datumtehnickog datetime not null,
cijenatehnickog decimal (18,2) not null,
ispravnovozilo boolean not null,
nazivstanice varchar(50) not null,
imeovlasteneosobe varchar(50) not null,
idtehnickog varchar(50) not null
)engine=innodb;


create table placanje(
sifra int not null primary key auto_increment,
vozilo int not null,
cijenaregistracije Decimal (18,2) not null,
cijenatehnickog Decimal (18,2) not null,
naknadazaceste Decimal (18,2) not null,
naknadazaokolis Decimal (18,2) not null,
biljezi Decimal (18,2) not null,
ukupno Decimal (18,2) not null,
gotovinakartice boolean not null,
nazivkartice varchar(30) null,
brojrata int null,
datumplacanja datetime not null,
imeplatitelja varchar (100) not null,
oibplatitelja char (11) not null,
idracuna varchar(50) not null
)engine=innodb;

create table registracija(
sifra int not null primary key auto_increment,
vozilo int not null,
placanje int not null,
datumregistracije datetime not null,
imepodnositelja varchar(50) not null,
oibpodnositelja varchar(50) not null,
nazivosiguravajucekuce varchar(100) not null,
idzahtjeva varchar(50) not null,
nazivservisa varchar(50) not null
)engine=innodb;


alter table vozilo add foreign key (stranka) references stranka (sifra);
#

alter table placanje add foreign key(vozilo) references vozilo (sifra);

#
alter table tehnicki add foreign key(vozilo) references vozilo (sifra);
#

alter table registracija add foreign key(vozilo) references vozilo (sifra);
alter table registracija add foreign key(placanje) references placanje (sifra);



insert into zaposlenik(sifra, ime, prezime, korisnickoime,lozinka) values (2,'Marko','Mel','Demon24',md5('d'));

insert into stranka(oib,ime,prezime,mobitel,adresa,datumrodjenja,adresa2,drugimobitel,fiksnitelefon,email,drzava,grad,zip,regija) values
                   (63785913173,'Marko','Valentić','+385/98-992-8421','Gornjodravska ob. 83','1994-12-03','Kapucinska 33','+385/98-992-222','+0385/32-198-173','grga@hotmail.com','Hrvatska','Osijek',31000,'Osječko Baranjska');
				   
insert into vozilo(stranka,modelvozila,kategorijavozila,proizvodjacvozila,nazivvozila,tablice,godiste,boja,brojsasije,snagamotora,tezinavozila,brojvrata,ostalo) values
(1,'VR6','Osobno vozilo','Volkswagen','Corrado VR6','Os-250-rs',1984,'crna','12132131321dt',98,1221.23,5,'krasan automobil');

insert into tehnicki (vozilo,datumtehnickog,cijenatehnickog,ispravnovozilo,nazivstanice,imeovlasteneosobe,idtehnickog)
values (1,'2013-12-03',1280.32,1,'Sunce d.d.','Pero Mikić','2323333322te');

insert into placanje(vozilo,cijenaregistracije,cijenatehnickog,naknadazaceste,naknadazaokolis,biljezi,ukupno,gotovinakartice,nazivkartice,brojrata,datumplacanja,imeplatitelja,oibplatitelja,idracuna) values
(1,1280.00,950.00,120.00,58.00,25.00,2480.00,1,null,null,'2013-12-03','John Malkovich','63785913173','1223213');

insert into registracija(vozilo,placanje,datumregistracije,imepodnositelja,oibpodnositelja,nazivosiguravajucekuce,idzahtjeva,nazivservisa) values
(1,1,'2013-12-03','John Malkovic',63785913173,'Grawe Os','12223321','MM');


ALTER TABLE zaposlenik
ADD UNIQUE INDEX ix_zaposlenik (korisnickoime);

ALTER TABLE stranka
ADD UNIQUE INDEX ix_stranka (oib);





#stavit vanjske kljuceve na not null
#stranka dvije kolone ime prezime
#ostale podatke prebacit u stranku
#izbaciti ispravno
#ista stvar sa ostalim pdoacima za vozilo kao i kod tablice stranka
#izbaciti kljuc stranke iz tablice placanje




	
