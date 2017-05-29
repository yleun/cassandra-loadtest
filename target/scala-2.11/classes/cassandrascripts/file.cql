
-- localhost
drop keyspace group;
CREATE KEYSPACE group WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 };

USE group;

DROP TABLE group_id IF EXISTS;
CREATE TABLE group_id (
  id uuid,
  group_id uuid,
  create_ts timestamp,
  PRIMARY KEY(group_id, id)
) WITH CLUSTERING ORDER BY (id DESC);

INSERT INTO group_id(
id, group_id, create_ts) VALUES
(minTimeuuid('1990-01-01 00:00+0000'), 827ccb0e-ea8a-306c-8c34-a16891f84e7b, 
'2017-03-01');

INSERT INTO group_id(
id, group_id, 
create_ts) VALUES
(minTimeuuid('1990-01-01 00:01+0000'), a3590023-df66-3c92-ae35-e3316026d17d,
'2017-03-20');

INSERT INTO group_id(
id, group_id, create_ts) VALUES
(minTimeuuid('1990-01-01 00:02+0000'), a3590023-df66-3c92-ae35-e3316026d17d, 
'2017-03-20');

