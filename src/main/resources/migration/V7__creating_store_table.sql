CREATE TABLE db_store (
                          id_store       BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                          vc_name        VARCHAR(256) NOT NULL UNIQUE,
                          vc_description VARCHAR(2048)
);

CREATE INDEX idx_db_store__vc_name ON db_store (vc_name);
