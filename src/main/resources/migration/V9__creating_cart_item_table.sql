-- Tabela principal do carrinho
CREATE TABLE db_cart (
                         id_cart      BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                          -- opcional: vínculo com usuário logado, pode ser NULL
                         id_user      BIGINT,
                         -- identificador genérico do carrinho (cookie/token, etc), não depende de sessão autenticada
                         vc_cart_key  VARCHAR(256) NOT NULL UNIQUE,
                         dt_created   TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
                         dt_updated   TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
                         CONSTRAINT fk_cart__user_details
                             FOREIGN KEY (id_user) REFERENCES db_user_details (id_user)
                                 ON DELETE SET NULL
);

CREATE INDEX idx_db_cart__id_user     ON db_cart (id_user);
CREATE INDEX idx_db_cart__dt_created  ON db_cart (dt_created);
CREATE INDEX idx_db_cart__dt_updated  ON db_cart (dt_updated);

------------------------------------------------------------
-- Itens do carrinho (vários produtos por carrinho)
------------------------------------------------------------
CREATE TABLE db_cart_item (
                              id_cart_item BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                              id_cart      BIGINT NOT NULL,
                              id_product   BIGINT NOT NULL,
                              num_quantity BIGINT NOT NULL,
                              dt_created   TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
                              dt_updated   TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),

                              CONSTRAINT fk_cart_item__cart
                                  FOREIGN KEY (id_cart)    REFERENCES db_cart    (id_cart)
                                      ON DELETE CASCADE,

                              CONSTRAINT fk_cart_item__product
                                  FOREIGN KEY (id_product) REFERENCES db_product (id_product)
                                      ON DELETE RESTRICT,

                              CONSTRAINT ck_cart_item__quantity_pos CHECK (num_quantity > 0),

                              CONSTRAINT uq_cart_item__cart_product UNIQUE (id_cart, id_product)
);

CREATE INDEX idx_db_cart_item__id_cart    ON db_cart_item (id_cart);
CREATE INDEX idx_db_cart_item__id_product ON db_cart_item (id_product);
