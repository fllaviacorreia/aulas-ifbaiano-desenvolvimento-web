CREATE TYPE rolesUser AS ENUM ('CLIENT', 'ADMIN');

CREATE TABLE IF NOT EXISTS user_t (
   id SERIAL PRIMARY KEY,
   email VARCHAR(200) NOT NULL UNIQUE,
   password VARCHAR(250) NOT NULL,
   active BOOLEAN DEFAULT true,
   roles rolesUser

    client_id INT REFERENCES client(client_id)
    );
