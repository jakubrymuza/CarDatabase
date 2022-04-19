CREATE TABLE "SAMOCHOD" 
(
	"samochod_id" "int" NOT NULL,
	"pojemnosc" numeric (5,3) NOT NULL,
	"cena" "Money" NOT NULL,

	CONSTRAINT "PKC_SAMOCHOD" PRIMARY KEY
	(
		"samochod_id"
	)
)

CREATE TABLE "OSOBY" 
(
	"osoba_id" "int" NOT NULL,
	"imie" varchar (25) NOT NULL,
	"nazwisko" varchar (25) NOT NULL,
	"samochod_id" "int" NOT NULL,
	"data_prod" "Date" NULL,

	CONSTRAINT "PKC_OSOBY" PRIMARY KEY
	(
		"osoba_id"
	),

	CONSTRAINT "FKC_OSOBY_SAMOCHOD" FOREIGN KEY
	(
		"samochod_id"
	) 
	REFERENCES "dbo"."SAMOCHOD"
	(
		"samochod_id"
	)
)

INSERT INTO SAMOCHOD VALUES
(1,1,10000),
(2,2.5,50000),
(3,3,100000),
(4,3.5,200000),
(5,0.5,1000),
(6,2.3,80000),
(7,2.1,30000),
(8,1.6,10000),
(9,3,30000),
(10,4,300000)

INSERT INTO OSOBY VALUES
(1,'Jan','Kowalski',1,'2020-10-25'),
(2,'Zbigniew','Kamieniarz',3,'2012-1-2'),
(3,'Piotr','Abacki',4,'1999-10-20'),
(4,'Szymon','Babacki',6,'2010-11-13'),
(5,'Jan','Tarski',5,'2021-9-25')
