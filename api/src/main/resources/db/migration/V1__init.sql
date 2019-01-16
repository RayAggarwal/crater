-- create public tables
CREATE TABLE IF NOT EXISTS public.owner (
  id INT GENERATED ALWAYS AS IDENTITY,
  username VARCHAR(64) NOT NULL UNIQUE,
  password VARCHAR NOT NULL,
  applicationID VARCHAR(36),
  PRIMARY KEY (id)
);
CREATE UNIQUE INDEX IF NOT EXISTS username_idx ON public.owner (username);

-- owner templates tables
CREATE SCHEMA IF NOT EXISTS owner_template;
CREATE TABLE IF NOT EXISTS owner_template.user (
  id INT GENERATED ALWAYS AS IDENTITY,
  owner_id INT NOT NULL UNIQUE,
  username VARCHAR(64) NOT NULL UNIQUE,
  email VARCHAR(64) NOT NULL UNIQUE,
  password VARCHAR(64) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (owner_id) REFERENCES public.owner (id)
);
CREATE UNIQUE INDEX IF NOT EXISTS username_idx on owner_template.user (username);
CREATE UNIQUE INDEX IF NOT EXISTS email_idx on owner_template.user (email);

-- trigger to create schema for new owners
CREATE OR REPLACE FUNCTION public.add_owner_schema()
  RETURNS TRIGGER AS
$BODY$
DECLARE
  objecto TEXT;
  buffer TEXT;
  rec RECORD;
BEGIN
  EXECUTE 'CREATE SCHEMA owner_'||new.id|| ' AUTHORIZATION crater';
  FOR objecto IN SELECT TABLE_NAME::TEXT FROM information_schema.TABLES WHERE table_schema = 'owner_template'
  LOOP
    buffer := 'owner_' || new.id || '.' || objecto;
    EXECUTE 'CREATE TABLE ' || buffer || ' (LIKE owner_template.' || objecto || ' INCLUDING ALL)';
    FOR rec IN SELECT oid, conname FROM pg_constraint WHERE contype = 'f' AND conrelid = FORMAT('owner_template.%s', objecto)::regclass
    LOOP
      EXECUTE 'ALTER TABLE ' || buffer || ' ADD CONSTRAINT ' || rec.conname|| ' ' || pg_get_constraintdef(rec.oid);
    END LOOP;
  END LOOP;
  RETURN NULL;
END;
$BODY$ LANGUAGE plpgsql;

CREATE TRIGGER new_owner
  AFTER INSERT
  ON public.owner
  FOR EACH ROW
  EXECUTE PROCEDURE public.add_owner_schema();