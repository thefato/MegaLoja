CREATE TABLE db_product_category (
                                     id_category    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                     vc_name        VARCHAR(256) NOT NULL UNIQUE
);

CREATE INDEX idx_db_product_category__vc_name ON db_product_category (vc_name);

CREATE TABLE db_product (
                            id_product   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                            id_category  BIGINT NOT NULL,
                            vc_name      VARCHAR(256) NOT NULL,
                            num_amount   BIGINT NOT NULL,
                            num_cost     DECIMAL(12,2) NOT NULL,
                            CONSTRAINT fk_product__category
                                FOREIGN KEY (id_category) REFERENCES db_product_category (id_category)
                                    ON DELETE RESTRICT,
                            CONSTRAINT ck_product__num_amount_nonneg CHECK (num_amount >= 0),
                            CONSTRAINT ck_product__num_cost_nonneg   CHECK (num_cost   >= 0)
);

CREATE INDEX idx_db_product__id_category ON db_product (id_category);
CREATE INDEX idx_db_product__vc_name ON db_product (vc_name);

CREATE TABLE db_user_details (
                                 id_user              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                 id_user_credentials  BIGINT NOT NULL,
                                 vc_name              VARCHAR(256) NOT NULL,
                                 vc_document          VARCHAR(256),
                                 CONSTRAINT fk_user_details__credentials
                                     FOREIGN KEY (id_user_credentials) REFERENCES db_users_credentials (id_user)
                                         ON DELETE CASCADE,
                                 CONSTRAINT uq_user_details__id_user_credentials UNIQUE (id_user_credentials)
);

CREATE INDEX idx_db_user_details__vc_name ON db_user_details (vc_name);
