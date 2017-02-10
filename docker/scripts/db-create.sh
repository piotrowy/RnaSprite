#!/bin/sh

#RnaSprite
su postgres -c "psql < ./scripts/rna-sprite-db-create.sql";
su postgres -c "psql -d rnasprite < ./scripts/rna-sprite-db-create-schema.sql";
