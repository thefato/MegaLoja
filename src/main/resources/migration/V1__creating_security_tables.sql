-- Core tables
CREATE TABLE db_users_credentials (
                          id_user BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                          vc_email VARCHAR(256) NOT NULL UNIQUE,
                          vc_password_hash VARCHAR(256) NOT NULL
);

CREATE TABLE db_groups (
                           id_group BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                           vc_name VARCHAR(256) NOT NULL UNIQUE
);

CREATE TABLE db_scopes (
                           id_scope BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                           vc_name VARCHAR(256) NOT NULL UNIQUE
);

CREATE TABLE db_user_groups (
                                id_user_group BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                id_user  BIGINT NOT NULL,
                                id_group BIGINT NOT NULL,
                                CONSTRAINT fk_user_groups__user
                                    FOREIGN KEY (id_user)  REFERENCES db_users_credentials  (id_user)
                                        ON DELETE CASCADE,
                                CONSTRAINT fk_user_groups__group
                                    FOREIGN KEY (id_group) REFERENCES db_groups (id_group)
                                        ON DELETE CASCADE,
                                CONSTRAINT uq_user_group UNIQUE (id_user, id_group)
);

CREATE INDEX idx_user_groups__id_user  ON db_user_groups (id_user);
CREATE INDEX idx_user_groups__id_group ON db_user_groups (id_group);

-- Junction: groups ↔ scopes
CREATE TABLE db_group_scopes (
                                 id_group_scope BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                 id_group BIGINT NOT NULL,
                                 id_scope BIGINT NOT NULL,
                                 CONSTRAINT fk_group_scopes__group
                                     FOREIGN KEY (id_group) REFERENCES db_groups (id_group)
                                         ON DELETE CASCADE,
                                 CONSTRAINT fk_group_scopes__scope
                                     FOREIGN KEY (id_scope) REFERENCES db_scopes (id_scope)
                                         ON DELETE CASCADE,
                                 CONSTRAINT uq_group_scope UNIQUE (id_group, id_scope)
);

CREATE INDEX idx_group_scopes__id_group ON db_group_scopes (id_group);
CREATE INDEX idx_group_scopes__id_scope ON db_group_scopes (id_scope);