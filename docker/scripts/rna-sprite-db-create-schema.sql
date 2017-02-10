-------------------------------------------------------------------------------------------
--
-- Initial database script for: rnasprite db
--
---------------------------------------------------------------------------------------------

DROP SCHEMA IF EXISTS public;

--rnasprite
CREATE SCHEMA IF NOT EXISTS rnasprite AUTHORIZATION sprite;
GRANT ALL ON SCHEMA rnasprite TO sprite;
ALTER ROLE sprite SET search_path = 'rnasprite';
