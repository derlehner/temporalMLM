# Open PGAdmin

docker exec -it timescaledb psql -U postgres
-> \c example
-> create database: create database example;

# SETUP 
create table TObjects(id TEXT, t timestamp);

Create Table EAttribute(id TEXT, type TEXT, t timestamp, value TEXT);

Create Table EReference(id TEXT, sourceType TEXT, targetType TEXT, t timestamp, value TEXT);

# SET

Drop Table EAttribute;

Insert Into EAttribute(id, type, t, value) 
VALUES 
('isProcessed', 'item1', '2021-01-01 09:00:00.000', 'true');

Delete From EAttribute;

Insert into EReference(id, sourceType, targetType, t, value)
VALUES
('eContents', 'ROOT', 'Item' , '2021-01-01 09:00:00.000', 'item1');
Delete From EReference;

Select * from EReference where sourceType = 'ROOT' and id='eContents';

## GET

Select * from EAttribute where t <= '2022-01-27 15:02:55.339676' and t >= (select max(t) from EAttribute where t <= '2022-01-27 15:02:55.339676');

Select * from EAttribute where type = '' and value = '' and id = '';

# rqs

RQ1: Production Cost — Is there a significant difference of the time required
for producing and manipulating temporal model elements? This question is of
particular relevance for efficient model simulators which have to run for longer
periods to produce a target model as well as the traces to reach such a model.
RQ2: Storage Cost — Is there a significant difference of the storage size of
temporal models? This question is of particular interest since the output of a
model simulation may have to be stored for provenance reasons or for comparing
different variants.
RQ3: Reproduction Cost — Is there a significant difference of restoring previous versions of temporal model elements? This question is of relevance as the
different properties of a simulation run have to be computed 