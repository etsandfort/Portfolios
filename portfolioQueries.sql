# Homework 4
# Database Query File
# portfolioQueries.sql
# Authored by: Libby Gentry, Jacob Melcher and Elliot Sandfort

# 1 - A query to retrieve the major fields for every person
select * from Person;

# 2 - A query to retrieve the email(s) of a given person (Colin Mochrie)
select emailAddress 
from Email 
where personID = (select personID 
				  from Person 
				  where personCode = 'bald1');

# 3 - A query to add an email to a specific person (Colin Mochrie)
insert into Email (emailAddress, PersonID) 
values ('funnym4n@whoseline.net', 
		(select personID 
		 from Person 
         where (personCode = 'bald1'))); 

# 4 - A query to change the email address of a given email record (email above)
update Email 
set emailAddress = 'notRyanStiles@whoseline.net' 
where emailAddress= 'funnym4n@whoseline.net';

# 5 - A query (or series of queries) to remove a given person record
set SQL_SAFE_UPDATES=0;
delete from PortfolioAsset 
where portfolioAssetID 
	in (select portfolioAssetID 
	from (select * from PortfolioAsset) as portAsset 
          where portfolioID 
          in ((select portfolioID 
               from Portfolio 
               where ownerID 
               in (select personID 
				   from Person 
                   where personCode = 'bald1') or managerID 
                   in (select personID 
					   from Person 
                       where personCode = 'bald1'))));
                       
delete from Portfolio 
where (ownerID = (select personID 
				  from Person 
                  where personCode = 'bald1') or 
                  managerID = (select personID 
							   from Person 
                               where personCode = 'bald1'));
                               
delete from Email 
where personID = (select personID 
				  from Person 
                  where personCode = 'bald1');
                  
delete from Address 
where personID = (select personID 
				  from Person 
                  where personCode = 'bald1');
                  
delete from Person 
where personID = (select personID 
				  from (select * from Person) as person 
                  where personCode = 'bald1');

# 6 - A query to create a person record - we are creating Colin Mochrie, who was previously deleted
insert into Person(personCode, lastName, firstName, secID, brokerType) 
values ('bald1', 'Mochrie',' Colin', 'sec156','E');

# 7 - A query to get all the assets in a particular portfolio
select * from Asset 
where assetID in 
	(select assetID 
     from PortfolioAsset 
     where portfolioID = 
		(select portfolioID 
         from Portfolio 
         where portfolioCode = 'AO'));
        
# 8 - A query to get all the assets of a particular person
select * from Asset 
where assetID in 
	(select assetID 
     from PortfolioAsset 
	where portfolioID in 
		(select portfolioID 
         from Portfolio 
         where ownerID = 
			(select personID 
             from Person 
             where personCode = 'weasley6')));

# 9 - A query to create a new asset record
insert into Asset(assetCode, label, assetType, baseRate) 
values ('1654', 'NewDepositHere','D', 1.00);

# 10 - A query to create a new portfolio record
insert into Portfolio(portfolioCode, ownerID, managerID, beneficiaryID) 
values ('P123',
		(select personID 
		 from Person 
         where (personCode = 'bald1')),
		(select personID 
         from Person 
         where(personCode = 'h0h0')),
		(select personID 
         from Person 
         where(personCode = 'tall1')));

# 11 - A query to associate a particular asset with a particular portfolio
insert into PortfolioAsset(assetID, portfolioID, givenValue) 
values ((select assetID 
         from Asset 
         where (assetCode = '1654')), 
		(select portfolioID 
         from Portfolio 
         where (PortfolioCode = 'P123')), 
         152);

# 12 - A query to find the total number of portfolios owned by each person
select  person.firstName, 
		person.lastName, 
		count(portf.portfolioCode) PortfoliosOwned 
from Person person
left join Portfolio portf on person.personID = portf.ownerID
group by person.firstName;

# 13 - A query to find the total number of portfolios managed by each person
select  person.firstName,
		person.lastName,
		count(portf.portfolioCode) PortfoliosManaged 
from Person person
left join Portfolio portf on person.personID = portf.managerID
group by person.firstName;

# 14 - A query to find the total value of all stocks in each portfolio that contains stocks
select portf.portfolioCode, 
    sum(portAsset.givenValue*asset.sharePrice) as totalStockValue 
from Asset as asset 
left join PortfolioAsset as portAsset on asset.assetID = portAsset.assetID
left join Portfolio as portf on portAsset.portfolioID = portf.portfolioID
where asset.assetType = 'S'
group by portf.portfolioCode;

# 15 - A query to detect an invalid distribution of private investment assets (determine which assets have a percentage owned of more than 100 percent)
select  Asset.assetCode, 
		sum(PortfolioAsset.givenValue) as InvalidDistributionAmount 
from PortfolioAsset
join Asset on PortfolioAsset.AssetID = Asset.assetID 
where (Asset.assetType = 'P') 
group by assetCode 
having sum(PortfolioAsset.givenValue)>100;

# Extra #1 - A query to count how many assets are in each portfolio
select  portf.portfolioCode, 
		count(portAsset.assetID) AssetsInPortfolio 
from Portfolio portf
left join PortfolioAsset portAsset on portAsset.portfolioID = portf.portfolioID
group by portf.portfolioCode;