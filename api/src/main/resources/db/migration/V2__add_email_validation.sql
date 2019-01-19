ALTER TABLE IF EXISTS public.owner ADD COLUMN active BOOLEAN DEFAULT FALSE NOT NULL;

CREATE TABLE IF NOT EXISTS public.verification_token (
  id INT NOT NULL UNIQUE,
  token VARCHAR NOT NULL UNIQUE,
  expiry_time TIMESTAMP WITH TIME ZONE NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id) REFERENCES public.owner (id)
);
CREATE UNIQUE INDEX IF NOT EXISTS token_idx ON public.verification_token (token);
