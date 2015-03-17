# drop pre-existing tables
drop table if exists Address;
drop table if exists State;
drop table if exists Country;
drop table if exists Email;
drop table if exists PortfolioPerson;
drop table if exists PortfolioAsset;

drop table if exists Asset;
drop table if exists Portfolio;
drop table if exists Person;

# The Portfolio table contains all of the fields necessary to a 
# portfolio. 
create table Portfolio(
	portfolioID int not null primary key auto_increment,
	portfolioCode varchar(24) not null,
	ownerID  int,
	managerID  int,
	beneficiaryID int,
    # Ensures that every inserted portfolio is unique by not allowing 
    # any inserted non-unique portfolioCode
	constraint uniqueCode unique index(portfolioCode)
);

# The Person table contains all of the fields
# necessary to a person. It also contains the 
# fields necessary to a broker; these fields
# aren't required for the non-broker person.
create table Person(
    personID int not null primary key auto_increment,
	personCode varchar(24) not null,
	lastName varchar(255) not null,
    firstName varchar(255) not null,
    secID varchar(24),
    brokerType varchar(1),
    # Ensures that each inserted person is unique by not allowing 
    # any inserted persons with a non-unique alphanumeric personCode
	constraint uniqueCode unique index(personCode), 
    # Ensures that the person has neither a brokerType (E,J) nor an secID
    # (not a broker) or that the person has both a brokerType and an secID
    # (is a broker)
    check ((brokerType is null 
			and secID is null) 
            or (brokerType is not null 
				and secID is not null))
);

# The State table contains the names of all
# unique states entered into the database.
create table State(
	stateID int not null primary key auto_increment,
    stateName varchar(24),
     # Ensures that every state is unique by name
	constraint uniqueState unique index(stateName)
);

# The Country table contains the names of all
# unique countries entered into the database.
create table Country(
	countryID int not null primary key auto_increment,
    countryName varchar(255),
    # Ensures that every country is unique by name
    constraint uniqueCountry unique index(countryName)
);

# The Address table contains the fields necessary to 
# an address. Each inserted address will be associated
# with a person and country, by personID and countryID, 
# respectively. It could also be associated with a 
# state by stateID, but this is not required.
create table Address(
	addressID int not null primary key auto_increment,
	street varchar(255) not null,
	city varchar(255) not null,
	stateID int,
	zipcode varchar(24) not null,
	countryID int not null,
	personID int not null,
    foreign key(stateID) references State(stateID),
    foreign key(countryID) references Country(countryID),
	foreign key (personID) references Person(personID)
);

# The Email table contains fields necessary to every email.
# Each inserted email will be associated with a person
# by a personID
create table Email(
	emailID int not null auto_increment primary key,
	emailAddress varchar(255) not null,
    personID int not null,
	foreign key (personID) references Person(personID),
    # Ensures that every inserted email address is unique. 
    # We assume that any email only belongs to one person.
    constraint uniqueEmail unique index(emailAddress)
);

# The Asset table contains the fields pertaining to all assets. 
create table Asset(
	assetID int not null primary key auto_increment,
	assetCode varchar(24) not null,
	label varchar(255) not null,
	# assetType can be P, S or D
    assetType varchar(1) not null,
	baseRate double,
    quarterlyDividend double,
    omega double,
    investmentValue double,
    symbol varchar(8),
    sharePrice double,
    beta double,
    # Ensures that every asset has a unique alphanumeric code
    constraint uniqueCode unique index(assetCode),
    # Ensures that each asset has the proper fields for its type 
    # (and none of the other asset types')
    constraint validFields check ((assetType = 'P' and omega is not null 
		and investmentValue is not null and beta is null 
        and symbol is not null and sharePrice is null 
        and baseRate is not null and quarterlyDividend is not null) 
    or (assetType = 'S' and omega is null and investmentValue is null 
		and beta is not null and symbol is not null 
        and sharePrice is not null and baseRate is not null 
        and quarterlyDividend is not null) 
    or (assetType = 'D' and omega is null and investmentValue is null 
		and beta is null and symbol is null and sharePrice is null 
        and baseRate is not null and quarterlyDividend is null))
);

# The PortfolioAsset table is a join table between portfolios and assets.
# It joins every portfolio to its asset(s) and every asset to its portfolio(s)
create table PortfolioAsset(
	portfolioAssetID int not null primary key auto_increment,
	assetID int,
    portfolioID int not null,
    # givenValue is: number of shares owned, for stocks, 
    # percent of investment owned, for private investments 
    # and balance, for deposits
    givenValue int not null default 0.0, 
    foreign key (assetID) references Asset(assetID),
    foreign key (portfolioID) references Portfolio(portfolioID)
);

#include all necessary insert statements (along with any nested select queries) to populate your database with some test data

#insert assets
Insert into Asset(assetCode,label,assetType,baseRate,quarterlyDividend, omega, investmentValue) values ('LAMH01', 'Lamar''s Home Improvement','P', 2.65,314159, 0.52, 1337357);
Insert into Asset(assetCode,label,assetType,baseRate)values ( 'MHIA32', 'Savings Account', 'D', 0.73);
Insert into Asset(assetCode,label,assetType,baseRate,quarterlyDividend, omega, investmentValue) values ('UK909', 'Jeb''s Ukuleles','P', 1.33,177245, 0.13, 82734622);
Insert into Asset(assetCode,label,assetType,baseRate,quarterlyDividend, beta, symbol, sharePrice)values ( 'GDSF11', 'Good Stuff Inc.', 'S', 47.63, 5.6, 1.69, 'GDSF', 987.65);
Insert into Asset(assetCode,label,assetType,baseRate)values ( 'ARGSAV', 'Money Market Savings', 'D', 3.05);
Insert into Asset(assetCode,label,assetType,baseRate,quarterlyDividend, beta, symbol, sharePrice)values ( 'REG', 'Regado Biosciences INC', 'S', 0.0, 1.25, 1.1, 'RGDO', 1.23);
Insert into Asset(assetCode,label,assetType,baseRate)values ( 'DEV', 'Devils Deposits', 'D', 1.25);
Insert into Asset(assetCode,label,assetType,baseRate,quarterlyDividend, omega, investmentValue) values ('SOFT102', 'Upcoming Software Firm','P', 3.5,100000.00, 1.0, 100000000.00);
Insert into Asset(assetCode,label,assetType,baseRate)values ( 'KIDC', 'Kids College Savings', 'D', 2.5);
Insert into Asset(assetCode,label,assetType,baseRate)values ( 'ISAVE', 'Time Deposit', 'D', 2.5);
Insert into Asset(assetCode,label,assetType,baseRate)values ( '65YEAH', 'Business Investment Account', 'D', 2.89);
Insert into Asset(assetCode,label,assetType,baseRate,quarterlyDividend, beta, symbol, sharePrice)values ( 'MCYDS', 'McDonald''s Corp', 'S', 0.85, 0.35, 0.38, 'MCD', 92.7);
Insert into Asset(assetCode,label,assetType,baseRate,quarterlyDividend, beta, symbol, sharePrice)values ( 'RA1K3S', 'Microsoft Corp', 'S', 0.31, 1.86, 1.00, 'MSFT', 41.22);
Insert into Asset(assetCode,label,assetType,baseRate,quarterlyDividend, omega, investmentValue) values ('LYF3', 'Ice Machine International','P', 1.9,9600.00, 0.20, 6450000);
Insert into Asset(assetCode,label,assetType,baseRate,quarterlyDividend, omega, investmentValue) values ('B00', 'The Mystery Machine','P', 2.3,1280.23, 0.31, 8645002);

#insert persons
Insert into Person(personCode,lastName,firstName,secID, brokerType) values ('the1', 'Potter',' Harry', 'sec123','J');
Insert into Person(personCode,lastName,firstName,secID, brokerType) values ('not3xp3l', 'Granger',' Hermione', 'sec998','E');
Insert into Person(personCode,lastName,firstName) values ('weasley6', 'Weasley',' Ron');
Insert into Person(personCode,lastName,firstName,secID, brokerType) values ('1pm1ph', 'Centaur',' Firenze', 'sec762','J');
Insert into Person(personCode,lastName,firstName) values ('durm1', 'Krum',' Viktor');
Insert into Person(personCode,lastName,firstName,secID, brokerType) values ('weasley7', 'Weasley',' Ginny', 'sec777','J');
Insert into Person(personCode,lastName,firstName) values ('voldie', 'Riddle',' Tom');
Insert into Person(personCode,lastName,firstName) values ('b3sts0n', 'Malfoy',' Draco');
Insert into Person(personCode,lastName,firstName) values ('p819', 'O''Reilley',' Bill');
Insert into Person(personCode,lastName,firstName,secID, brokerType) values ('mp1944', 'Hannity',' Sean', 'sec830','E');
Insert into Person(personCode,lastName,firstName) values ('92ku', 'Limbaugh',' Rush');
Insert into Person(personCode,lastName,firstName,secID, brokerType) values ('3w8ss', 'Palin',' Sarah', 'sec554','J');
Insert into Person(personCode,lastName,firstName) values ('ck69', 'Coulter',' Leeanne');
Insert into Person(personCode,lastName,firstName,secID, brokerType) values ('8eqd', 'Pinkerton',' Jim', 'sec137','E');
Insert into Person(personCode,lastName,firstName) values ('8th5', 'Ghallager',' Trace');
Insert into Person(personCode,lastName,firstName,secID, brokerType) values ('267s', 'Roberts',' John', 'sec291','J');
Insert into Person(personCode,lastName,firstName,secID, brokerType) values ('9a5', 'Melcher',' Jacob', 'sec915','E');
Insert into Person(personCode,lastName,firstName) values ('666', 'Diablo',' L.');
Insert into Person(personCode,lastName,firstName,secID, brokerType) values ('tall1', 'Stiles',' Ryan', 'sec916','J');
Insert into Person(personCode,lastName,firstName,secID, brokerType) values ('h0h0', 'Claus',' Santa', 'sec1010','E');
Insert into Person(personCode,lastName,firstName) values ('d1e', 'Reaper',' Grimm');
Insert into Person(personCode,lastName,firstName,secID, brokerType) values ('haha', 'Carey',' Drew', 'sec105','J');
Insert into Person(personCode,lastName,firstName,secID, brokerType) values ('bald1', 'Mochrie',' Colin', 'sec156','E');
Insert into Person(personCode,lastName,firstName) values ('nnee3', 'Brady',' Wayne');

#insert states
Insert Ignore into State(stateName) values('Surrey');
Insert Ignore into State(stateName) values('ON');
Insert Ignore into State(stateName) values('Devon');
Insert Ignore into State(stateName) values('NS');
Insert Ignore into State(stateName) values('Durmstrang');
Insert Ignore into State(stateName) values('MagicLand');
Insert Ignore into State(stateName) values('Banffshire');
Insert Ignore into State(stateName) values('Wiltshire');
Insert Ignore into State(stateName) values('CA');
Insert Ignore into State(stateName) values('FL');
Insert Ignore into State(stateName) values('MO');
Insert Ignore into State(stateName) values('AK');
Insert Ignore into State(stateName) values('PA');
Insert Ignore into State(stateName) values('CT');
Insert Ignore into State(stateName) values('CA');
Insert Ignore into State(stateName) values('ND');
Insert Ignore into State(stateName) values('NE');
Insert Ignore into State(stateName) values('NM');
Insert Ignore into State(stateName) values('ON');
Insert Ignore into State(stateName) values('AK');
Insert Ignore into State(stateName) values('MN');
Insert Ignore into State(stateName) values('OH');
Insert Ignore into State(stateName) values('Buckinghamshire');
Insert Ignore into State(stateName) values('GA');

#insert countries
Insert Ignore into Country(countryName) values('England');
Insert Ignore into Country(countryName) values('Canada');
Insert Ignore into Country(countryName) values('England');
Insert Ignore into Country(countryName) values('Canada');
Insert Ignore into Country(countryName) values('Bulgaria');
Insert Ignore into Country(countryName) values('Scotland');
Insert Ignore into Country(countryName) values('Scotland');
Insert Ignore into Country(countryName) values('England');
Insert Ignore into Country(countryName) values('USA');
Insert Ignore into Country(countryName) values('USA');
Insert Ignore into Country(countryName) values('USA');
Insert Ignore into Country(countryName) values('USA');
Insert Ignore into Country(countryName) values('USA');
Insert Ignore into Country(countryName) values('USA');
Insert Ignore into Country(countryName) values('USA');
Insert Ignore into Country(countryName) values('USA');
Insert Ignore into Country(countryName) values('USA');
Insert Ignore into Country(countryName) values('USA');
Insert Ignore into Country(countryName) values('Canada');
Insert Ignore into Country(countryName) values('USA');
Insert Ignore into Country(countryName) values('USA');
Insert Ignore into Country(countryName) values('USA');
Insert Ignore into Country(countryName) values('UK');
Insert Ignore into Country(countryName) values('USA');

#insert addresses
Insert into Address(street,city,stateID,zipcode,countryID,personID) values ('4 Privet Drive', 'Little Winging',(Select stateID from State where (stateName = 'Surrey')),'77777',(Select countryID from Country where (countryName = 'England')),(Select personID from Person where (personCode = 'the1')));
Insert into Address(street,city,stateID,zipcode,countryID,personID) values ('234 Perfection Lane', 'Toronto',(Select stateID from State where (stateName = 'ON')),'M4W 2V3',(Select countryID from Country where (countryName = 'Canada')),(Select personID from Person where (personCode = 'not3xp3l')));
Insert into Address(street,city,stateID,zipcode,countryID,personID) values ('654 The Burrow', 'Ottery St Catchpole',(Select stateID from State where (stateName = 'Devon')),'98654',(Select countryID from Country where (countryName = 'England')),(Select personID from Person where (personCode = 'weasley6')));
Insert into Address(street,city,stateID,zipcode,countryID,personID) values ('333 Forbidden Forest', 'Halifax',(Select stateID from State where (stateName = 'NS')),'M5N 2W3',(Select countryID from Country where (countryName = 'Canada')),(Select personID from Person where (personCode = '1pm1ph')));
Insert into Address(street,city,stateID,zipcode,countryID,personID) values ('607 Durmstrang Institute', 'Sofia',(Select stateID from State where (stateName = 'Durmstrang')),'34522',(Select countryID from Country where (countryName = 'Bulgaria')),(Select personID from Person where (personCode = 'durm1')));
Insert into Address(street,city,stateID,zipcode,countryID,personID) values ('706 Hogwarts Residential', 'Hogsmeade',(Select stateID from State where (stateName = 'MagicLand')),'78475',(Select countryID from Country where (countryName = 'Scotland')),(Select personID from Person where (personCode = 'weasley7')));
Insert into Address(street,city,stateID,zipcode,countryID,personID) values ('432 Secret Chamber', 'Dufftown',(Select stateID from State where (stateName = 'Banffshire')),'66666',(Select countryID from Country where (countryName = 'Scotland')),(Select personID from Person where (personCode = 'voldie')));
Insert into Address(street,city,stateID,zipcode,countryID,personID) values ('664 Malfoy Manor', 'Deatheaterville',(Select stateID from State where (stateName = 'Wiltshire')),'21422',(Select countryID from Country where (countryName = 'England')),(Select personID from Person where (personCode = 'b3sts0n')));
Insert into Address(street,city,stateID,zipcode,countryID,personID) values ('5748 West America Boulevard', 'Rancho Cucamonga',(Select stateID from State where (stateName = 'CA')),'29670',(Select countryID from Country where (countryName = 'USA')),(Select personID from Person where (personCode = 'p819')));
Insert into Address(street,city,stateID,zipcode,countryID,personID) values ('3827 High Street', 'Fort Meyer',(Select stateID from State where (stateName = 'FL')),'37518',(Select countryID from Country where (countryName = 'USA')),(Select personID from Person where (personCode = 'mp1944')));
Insert into Address(street,city,stateID,zipcode,countryID,personID) values ('195 North Patriot Avenue', 'Mizzoula',(Select stateID from State where (stateName = 'MO')),'83765',(Select countryID from Country where (countryName = 'USA')),(Select personID from Person where (personCode = '92ku')));
Insert into Address(street,city,stateID,zipcode,countryID,personID) values ('4832 Summers Street', 'Juneau',(Select stateID from State where (stateName = 'AK')),'45893',(Select countryID from Country where (countryName = 'USA')),(Select personID from Person where (personCode = '3w8ss')));
Insert into Address(street,city,stateID,zipcode,countryID,personID) values ('1209 Winding Way', 'Westchester',(Select stateID from State where (stateName = 'PA')),'22793',(Select countryID from Country where (countryName = 'USA')),(Select personID from Person where (personCode = 'ck69')));
Insert into Address(street,city,stateID,zipcode,countryID,personID) values ('7896 Clifford Street', 'Rockford',(Select stateID from State where (stateName = 'CT')),'10101',(Select countryID from Country where (countryName = 'USA')),(Select personID from Person where (personCode = '8eqd')));
Insert into Address(street,city,stateID,zipcode,countryID,personID) values ('600 Orange Road', 'Bellevue',(Select stateID from State where (stateName = 'CA')),'29777',(Select countryID from Country where (countryName = 'USA')),(Select personID from Person where (personCode = '8th5')));
Insert into Address(street,city,stateID,zipcode,countryID,personID) values ('555 A Street', 'Township',(Select stateID from State where (stateName = 'ND')),'55555',(Select countryID from Country where (countryName = 'USA')),(Select personID from Person where (personCode = '267s')));
Insert into Address(street,city,stateID,zipcode,countryID,personID) values (' 23708 Walnut Cir', 'Waterloo',(Select stateID from State where (stateName = 'NE')),'68069',(Select countryID from Country where (countryName = 'USA')),(Select personID from Person where (personCode = '9a5')));
Insert into Address(street,city,stateID,zipcode,countryID,personID) values ('1 Hot Place', 'Las Cruces',(Select stateID from State where (stateName = 'NM')),'88001',(Select countryID from Country where (countryName = 'USA')),(Select personID from Person where (personCode = '666')));
Insert into Address(street,city,stateID,zipcode,countryID,personID) values ('Some Place Cold', 'Ottawa',(Select stateID from State where (stateName = 'ON')),'K1A 0G9',(Select countryID from Country where (countryName = 'Canada')),(Select personID from Person where (personCode = 'tall1')));
Insert into Address(street,city,stateID,zipcode,countryID,personID) values ('101 St. Nicholas Drive', 'North Pole',(Select stateID from State where (stateName = 'AK')),'99705',(Select countryID from Country where (countryName = 'USA')),(Select personID from Person where (personCode = 'h0h0')));
Insert into Address(street,city,stateID,zipcode,countryID,personID) values ('Hells Doors', 'Minneapolis',(Select stateID from State where (stateName = 'MN')),'55401',(Select countryID from Country where (countryName = 'USA')),(Select personID from Person where (personCode = 'd1e')));
Insert into Address(street,city,stateID,zipcode,countryID,personID) values ('Funny Joke Lane', 'Cleveland',(Select stateID from State where (stateName = 'OH')),'44101',(Select countryID from Country where (countryName = 'USA')),(Select personID from Person where (personCode = 'haha')));
Insert into Address(street,city,stateID,zipcode,countryID,personID) values ('123 Oxford Road', 'Aylesbury',(Select stateID from State where (stateName = 'Buckinghamshire')),'HP19 3EQ',(Select countryID from Country where (countryName = 'UK')),(Select personID from Person where (personCode = 'bald1')));
Insert into Address(street,city,stateID,zipcode,countryID,personID) values ('15454 Rhodes Road', 'Columbus',(Select stateID from State where (stateName = 'GA')),'31917',(Select countryID from Country where (countryName = 'USA')),(Select personID from Person where (personCode = 'nnee3')));

#insert emails
Insert into Email(emailAddress,personID) values ('theboywholived@scar.com',(Select personID from Person where (personCode = 'the1')));
Insert into Email(emailAddress,personID) values ('lightningboy@darkmark.com',(Select personID from Person where (personCode = 'the1')));
Insert into Email(emailAddress,personID) values ('flitwickfavorite@gmail.com',(Select personID from Person where (personCode = 'not3xp3l')));
Insert into Email(emailAddress,personID) values ('ronsthebomb@levi0sa.com',(Select personID from Person where (personCode = 'not3xp3l')));
Insert into Email(emailAddress,personID) values ('ratsarentfriends@ouch.com',(Select personID from Person where (personCode = 'weasley6')));
Insert into Email(emailAddress,personID) values ('notahalfbreed@ownspecies.com',(Select personID from Person where (personCode = '1pm1ph')));
Insert into Email(emailAddress,personID) values ('num1redhead@hotmail.com',(Select personID from Person where (personCode = 'weasley7')));
Insert into Email(emailAddress,personID) values ('DAforlyf3@order.com',(Select personID from Person where (personCode = 'weasley7')));
Insert into Email(emailAddress,personID) values ('youknowwho@youknowwhere.com',(Select personID from Person where (personCode = 'voldie')));
Insert into Email(emailAddress,personID) values ('ilovemydad@help.com',(Select personID from Person where (personCode = 'b3sts0n')));
Insert into Email(emailAddress,personID) values ('billy@oreilleyfactor.org',(Select personID from Person where (personCode = 'p819')));
Insert into Email(emailAddress,personID) values ('shannity@foxnews.com',(Select personID from Person where (personCode = 'mp1944')));
Insert into Email(emailAddress,personID) values ('radiofree@therealtruth.net',(Select personID from Person where (personCode = '92ku')));
Insert into Email(emailAddress,personID) values ('icanseerussia@myhouse.gov',(Select personID from Person where (personCode = '3w8ss')));
Insert into Email(emailAddress,personID) values ('objective@reporting.net',(Select personID from Person where (personCode = 'ck69')));
Insert into Email(emailAddress,personID) values ('michaelj@fox.com',(Select personID from Person where (personCode = '8eqd')));
Insert into Email(emailAddress,personID) values ('tghags@enduringfreedom.com',(Select personID from Person where (personCode = '8th5')));
Insert into Email(emailAddress,personID) values ('mynameis@generic.net',(Select personID from Person where (personCode = '267s')));
Insert into Email(emailAddress,personID) values ('Melcher1211@yahoo.com',(Select personID from Person where (personCode = '9a5')));
Insert into Email(emailAddress,personID) values ('Diablo@ComcastSupport.com',(Select personID from Person where (personCode = '666')));
Insert into Email(emailAddress,personID) values ('ManInRed@NorthPole.com',(Select personID from Person where (personCode = 'h0h0')));
Insert into Email(emailAddress,personID) values ('FunScythe@aol.com',(Select personID from Person where (personCode = 'd1e')));
Insert into Email(emailAddress,personID) values ('WhoseLineIsItAnyways@aol.com',(Select personID from Person where (personCode = 'haha')));
Insert into Email(emailAddress,personID) values ('BaldJokes@yahoo.com',(Select personID from Person where (personCode = 'haha')));
Insert into Email(emailAddress,personID) values ('Colin@Bald.com',(Select personID from Person where (personCode = 'bald1')));

#insert portfolios
Insert into Portfolio(portfolioCode, ownerID, managerID, beneficiaryID)values('AA', (select personID from Person where (personCode = 'weasley6')), (select personID from Person where (personCode = 'the1')),(select personID from Person where (personCode = '')));
Insert into Portfolio(portfolioCode, ownerID, managerID, beneficiaryID)values('AB', (select personID from Person where (personCode = 'durm1')), (select personID from Person where (personCode = 'not3xp3l')),(select personID from Person where (personCode = 'weasley6')));
Insert into Portfolio(portfolioCode, ownerID, managerID, beneficiaryID)values('AC', (select personID from Person where (personCode = 'voldie')), (select personID from Person where (personCode = '1pm1ph')),(select personID from Person where (personCode = '')));
Insert into Portfolio(portfolioCode, ownerID, managerID, beneficiaryID)values('AD', (select personID from Person where (personCode = 'b3sts0n')), (select personID from Person where (personCode = 'weasley7')),(select personID from Person where (personCode = '')));
Insert into Portfolio(portfolioCode, ownerID, managerID, beneficiaryID)values('AE', (select personID from Person where (personCode = 'p819')), (select personID from Person where (personCode = 'mp1944')),(select personID from Person where (personCode = 'not3xp3l')));
Insert into Portfolio(portfolioCode, ownerID, managerID, beneficiaryID)values('AF', (select personID from Person where (personCode = '92ku')), (select personID from Person where (personCode = '3w8ss')),(select personID from Person where (personCode = '')));
Insert into Portfolio(portfolioCode, ownerID, managerID, beneficiaryID)values('AG', (select personID from Person where (personCode = 'ck69')), (select personID from Person where (personCode = '8eqd')),(select personID from Person where (personCode = '')));
Insert into Portfolio(portfolioCode, ownerID, managerID, beneficiaryID)values('AH', (select personID from Person where (personCode = '8th5')), (select personID from Person where (personCode = '267s')),(select personID from Person where (personCode = '')));
Insert into Portfolio(portfolioCode, ownerID, managerID, beneficiaryID)values('AI', (select personID from Person where (personCode = '666')), (select personID from Person where (personCode = '9a5')),(select personID from Person where (personCode = '')));
Insert into Portfolio(portfolioCode, ownerID, managerID, beneficiaryID)values('AJ', (select personID from Person where (personCode = 'd1e')), (select personID from Person where (personCode = 'tall1')),(select personID from Person where (personCode = '')));
Insert into Portfolio(portfolioCode, ownerID, managerID, beneficiaryID)values('AK', (select personID from Person where (personCode = 'nnee3')), (select personID from Person where (personCode = 'h0h0')),(select personID from Person where (personCode = '')));
Insert into Portfolio(portfolioCode, ownerID, managerID, beneficiaryID)values('AL', (select personID from Person where (personCode = '3w8ss')), (select personID from Person where (personCode = 'haha')),(select personID from Person where (personCode = '8th5')));
Insert into Portfolio(portfolioCode, ownerID, managerID, beneficiaryID)values('AM', (select personID from Person where (personCode = '92ku')), (select personID from Person where (personCode = 'bald1')),(select personID from Person where (personCode = '')));
Insert into Portfolio(portfolioCode, ownerID, managerID, beneficiaryID)values('AN', (select personID from Person where (personCode = 'tall1')), (select personID from Person where (personCode = 'bald1')),(select personID from Person where (personCode = '')));
Insert into Portfolio(portfolioCode, ownerID, managerID, beneficiaryID)values('AO', (select personID from Person where (personCode = 'the1')), (select personID from Person where (personCode = 'h0h0')),(select personID from Person where (personCode = '')));
Insert into Portfolio(portfolioCode, ownerID, managerID, beneficiaryID)values('AP', (select personID from Person where (personCode = 'the1')), (select personID from Person where (personCode = 'h0h0')),(select personID from Person where (personCode = '')));

#insert portfolio assets
Insert into PortfolioAsset(assetID,portfolioID,givenValue) values((Select assetID from Asset where (assetCode = 'LAMH01')), (Select portfolioID from Portfolio where (portfolioCode = 'AA')),51.00);
Insert into PortfolioAsset(assetID,portfolioID,givenValue) values((Select assetID from Asset where (assetCode = 'MHIA32')), (Select portfolioID from Portfolio where (portfolioCode = 'AA')),1500.00);
Insert into PortfolioAsset(assetID,portfolioID,givenValue) values((Select assetID from Asset where (assetCode = 'UK909')), (Select portfolioID from Portfolio where (portfolioCode = 'AB')),99.00);
Insert into PortfolioAsset(assetID,portfolioID,givenValue) values((Select assetID from Asset where (assetCode = 'GDSF11')), (Select portfolioID from Portfolio where (portfolioCode = 'AC')),55.00);
Insert into PortfolioAsset(assetID,portfolioID,givenValue) values((Select assetID from Asset where (assetCode = 'ARGSAV')), (Select portfolioID from Portfolio where (portfolioCode = 'AD')),50000.00);
Insert into PortfolioAsset(assetID,portfolioID,givenValue) values((Select assetID from Asset where (assetCode = 'LAMH01')), (Select portfolioID from Portfolio where (portfolioCode = 'AD')),45.00);
Insert into PortfolioAsset(assetID,portfolioID,givenValue) values((Select assetID from Asset where (assetCode = 'REG')), (Select portfolioID from Portfolio where (portfolioCode = 'AE')),10000.00);
Insert into PortfolioAsset(assetID,portfolioID,givenValue) values((Select assetID from Asset where (assetCode = 'GDSF11')), (Select portfolioID from Portfolio where (portfolioCode = 'AE')),50000.00);
Insert into PortfolioAsset(assetID,portfolioID,givenValue) values((Select assetID from Asset where (assetCode = 'DEV')), (Select portfolioID from Portfolio where (portfolioCode = 'AF')),1000000.00);
Insert into PortfolioAsset(assetID,portfolioID,givenValue) values((Select assetID from Asset where (assetCode = 'SOFT102')), (Select portfolioID from Portfolio where (portfolioCode = 'AG')),2.00);
Insert into PortfolioAsset(assetID,portfolioID,givenValue) values((Select assetID from Asset where (assetCode = 'ARGSAV')), (Select portfolioID from Portfolio where (portfolioCode = 'AG')),500.00);
Insert into PortfolioAsset(assetID,portfolioID,givenValue) values((Select assetID from Asset where (assetCode = 'KIDC')), (Select portfolioID from Portfolio where (portfolioCode = 'AH')),1.00);
Insert into PortfolioAsset(assetID,portfolioID,givenValue) values((Select assetID from Asset where (assetCode = 'DEV')), (Select portfolioID from Portfolio where (portfolioCode = 'AH')),50000.00);
Insert into PortfolioAsset(assetID,portfolioID,givenValue) values((Select assetID from Asset where (assetCode = 'ISAVE')), (Select portfolioID from Portfolio where (portfolioCode = 'AI')),10000000.00);
Insert into PortfolioAsset(assetID,portfolioID,givenValue) values((Select assetID from Asset where (assetCode = 'DEV')), (Select portfolioID from Portfolio where (portfolioCode = 'AI')),1000000.00);
Insert into PortfolioAsset(assetID,portfolioID,givenValue) values((Select assetID from Asset where (assetCode = '65YEAH')), (Select portfolioID from Portfolio where (portfolioCode = 'AJ')),15000.00);
Insert into PortfolioAsset(assetID,portfolioID,givenValue) values((Select assetID from Asset where (assetCode = 'MCYDS')), (Select portfolioID from Portfolio where (portfolioCode = 'AK')),25000.00);
Insert into PortfolioAsset(assetID,portfolioID,givenValue) values((Select assetID from Asset where (assetCode = 'RA1K3S')), (Select portfolioID from Portfolio where (portfolioCode = 'AL')),152000.00);
Insert into PortfolioAsset(assetID,portfolioID,givenValue) values((Select assetID from Asset where (assetCode = 'LYF3')), (Select portfolioID from Portfolio where (portfolioCode = 'AM')),55.00);
Insert into PortfolioAsset(assetID,portfolioID,givenValue) values((Select assetID from Asset where (assetCode = 'GDSF11')), (Select portfolioID from Portfolio where (portfolioCode = 'AM')),100000.00);
Insert into PortfolioAsset(assetID,portfolioID,givenValue) values((Select assetID from Asset where (assetCode = 'B00')), (Select portfolioID from Portfolio where (portfolioCode = 'AN')),100.00);
Insert into PortfolioAsset(assetID,portfolioID,givenValue) values((Select assetID from Asset where (assetCode = 'SOFT102')), (Select portfolioID from Portfolio where (portfolioCode = 'AO')),5);
Insert into PortfolioAsset(assetID,portfolioID,givenValue) values((Select assetID from Asset where (assetCode = 'UK909')), (Select portfolioID from Portfolio where (portfolioCode = 'AO')),10.00);
Insert into PortfolioAsset(assetID,portfolioID,givenValue) values((Select assetID from Asset where (assetCode = 'REG')), (Select portfolioID from Portfolio where (portfolioCode = 'AO')),54654.00);