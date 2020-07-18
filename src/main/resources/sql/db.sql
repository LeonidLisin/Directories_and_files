CREATE DATABASE dirsandfiles
    WITH OWNER postgres;

CREATE SCHEMA IF NOT EXISTS public;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS full_path_tbl
(
    id uuid NOT NULL
        CONSTRAINT full_path_tbl_pk
            PRIMARY KEY ,
    full_path VARCHAR NOT NULL ,
    date TIMESTAMP NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS full_path_tbl_id_uindex
    ON full_path_tbl (id);

CREATE UNIQUE INDEX IF NOT EXISTS full_path_tbl_path_uindex
    ON full_path_tbl (full_path);

CREATE TABLE IF NOT EXISTS file_tbl
(
    id UUID NOT NULL
        CONSTRAINT file_tbl_pk
            PRIMARY KEY ,
    name VARCHAR NOT NULL ,
    size BIGINT NOT NULL null,
    full_path UUID
        CONSTRAINT file_tbl___path_fk
            REFERENCES full_path_tbl
            ON UPDATE CASCADE ON DELETE CASCADE,
    is_dir BOOLEAN
);

CREATE UNIQUE INDEX IF NOT EXISTS file_tbl_id_uindex
    ON file_tbl (id);








