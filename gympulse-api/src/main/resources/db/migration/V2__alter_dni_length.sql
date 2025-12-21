-- Change the length of the dni column in the members table
ALTER TABLE members
    ALTER COLUMN dni TYPE VARCHAR(8);