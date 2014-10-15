ALTER TABLE accounts
ADD COLUMN password varchar(60);

UPDATE accounts
SET password='no password'
WHERE password is null;

ALTER TABLE accounts
ALTER COLUMN password SET NOT NULL;
