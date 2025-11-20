INSERT INTO db_product (id_category, vc_name, num_amount, num_cost, vc_description) VALUES
-- 1 - Eletrônicos
(1, 'Smartphone Galaxy S23 128GB',          100, 3999.90, 'Smartphone Android com tela 6.1", 5G e câmera tripla.'),
(1, 'Smartphone iPhone 14 128GB',           80, 4999.90, 'iPhone 14 com tela 6.1", iOS e câmera dupla avançada.'),
(1, 'Smartwatch Fitness Pro',              150,  799.90, 'Relógio inteligente com monitor cardíaco e GPS.'),
(1, 'Caixa de Som Bluetooth Portátil',     200,  249.90, 'Caixa de som portátil com bateria recarregável e som 360°.'),

-- 2 - Informática & Acessórios
(2, 'Notebook Ultra 15.6" i5 8GB 256GB SSD', 60, 3299.90, 'Notebook para uso diário e trabalho remoto.'),
(2, 'Mouse Gamer RGB 7200 DPI',            300,  129.90, 'Mouse gamer com alta precisão e iluminação RGB.'),
(2, 'Teclado Mecânico ABNT2',              180,  349.90, 'Teclado mecânico com layout ABNT2 e switches blue.'),

-- 3 - Celulares & Smartphones
(3, 'Smartphone Android 128GB Câmera Tripla', 120, 1899.90, 'Smartphone intermediário com ótima bateria.'),
(3, 'Smartphone Android 64GB Básico',      200,  999.90, 'Smartphone de entrada com tela 6.0".'),
(3, 'Capa Anti-impacto para Smartphone',   400,   59.90, 'Capa em TPU resistente a quedas.'),

-- 4 - Tablets & e-Readers
(4, 'Tablet 10.1" 64GB Wi-Fi',             90, 1499.90, 'Tablet ideal para estudos e consumo de mídia.'),
(4, 'e-Reader Tela 6" Iluminada',          70,  699.90, 'Leitor de e-books com tela e-ink iluminada.'),

-- 5 - TV & Home Theater
(5, 'Smart TV 55" 4K UHD',                 40, 2899.90, 'Smart TV com apps integrados e HDR.'),
(5, 'Soundbar 2.1 Canais Bluetooth',       85,  899.90, 'Soundbar com subwoofer e conexão sem fio.'),

-- 6 - Áudio & Fones
(6, 'Fone Bluetooth TWS',                  250,  199.90, 'Fone totalmente sem fio com estojo de carregamento.'),
(6, 'Headphone Over-Ear com Fio',          140,  159.90, 'Headphone confortável para uso prolongado.'),

-- 7 - Câmeras & Drones
(7, 'Câmera de Ação 4K',                    60,  799.90, 'Câmera de ação resistente à água com 4K.'),
(7, 'Drone com Câmera HD',                  45, 1299.90, 'Drone recreativo com câmera e estabilização.'),

-- 8 - Games & Consoles
(8, 'Console de Videogame Next Gen',        35, 3999.90, 'Console de última geração com 1TB.'),
(8, 'Controle Gamer Sem Fio',              160,  349.90, 'Controle sem fio compatível com PC e console.'),
(8, 'Jogo de Ação RPG',                    200,  299.90, 'Jogo de ação e RPG para console.'),

-- 9 - Livros
(9, 'Livro - Introdução à Programação Java', 120,   89.90, 'Livro técnico para iniciantes em Java.'),
(9, 'Livro - Romance Contemporâneo',       150,   59.90, 'Romance leve e envolvente.'),
(9, 'Livro - Finanças Pessoais na Prática',130,   69.90, 'Guia prático de organização financeira.'),

-- 10 - Filmes, Séries & Música
(10, 'Box Série de TV - 1ª Temporada',      50,  149.90, 'Box em mídia física da primeira temporada da série.'),
(10, 'Vinil Clássico de Rock',              40,  129.90, 'Álbum clássico de rock em vinil.'),

-- 11 - Moda Masculina
(11, 'Camiseta Básica Masculina Algodão',  300,   39.90, 'Camiseta 100% algodão, várias cores.'),
(11, 'Calça Jeans Masculina Slim',         200,  129.90, 'Calça jeans corte slim confortável.'),

-- 12 - Moda Feminina
(12, 'Vestido Midi Estampado',             180,  149.90, 'Vestido leve para uso casual.'),
(12, 'Blusa Feminina Manga Curta',         220,   59.90, 'Blusa básica feminina em malha.'),

-- 13 - Moda Infantil
(13, 'Conjunto Infantil Camiseta e Shorts',180,   79.90, 'Conjunto confortável para crianças.'),
(13, 'Pijama Infantil Personagens',        160,   89.90, 'Pijama infantil com estampa divertida.'),

-- 14 - Calçados
(14, 'Tênis Casual Masculino',             140,  189.90, 'Tênis casual para uso diário.'),
(14, 'Sandália Feminina Conforto',         130,  159.90, 'Sandália leve com palmilha macia.'),

-- 15 - Bolsas & Acessórios
(15, 'Mochila Executiva para Notebook',    110,  199.90, 'Mochila acolchoada para notebooks até 15.6".'),
(15, 'Bolsa Feminina Tiracolo',            120,  149.90, 'Bolsa tiracolo em couro sintético.'),

-- 16 - Joias & Relógios
(16, 'Relógio Analógico Masculino',         90,  249.90, 'Relógio resistente à água 5ATM.'),
(16, 'Colar Feminino com Pingente',        150,   99.90, 'Colar delicado banhado a ouro.'),

-- 17 - Casa & Cozinha
(17, 'Jogo de Panelas Antiaderentes 5 Peças', 80,  329.90, 'Jogo de panelas antiaderentes.'),
(17, 'Conjunto de Copos de Vidro 6 Unid.', 200,   59.90, 'Copos de vidro transparente para uso diário.'),

-- 18 - Móveis & Decoração
(18, 'Mesa de Escritório Compacta',         60,  399.90, 'Mesa para home office com prateleira.'),
(18, 'Cadeira Gamer Reclinável',            50,  899.90, 'Cadeira ergonômica com apoio de braço.'),

-- 19 - Eletrodomésticos
(19, 'Geladeira Frost Free 400L',           30, 2899.90, 'Geladeira frost free com prateleiras ajustáveis.'),
(19, 'Micro-ondas 20L Branco',              90,  599.90, 'Micro-ondas com funções rápidas.'),

-- 20 - Ferramentas & Construção
(20, 'Furadeira Elétrica 600W',             70,  299.90, 'Furadeira com função impacto.'),
(20, 'Jogo de Chaves de Fenda 6 Peças',    200,   69.90, 'Kit de chaves de fenda variadas.'),

-- 21 - Jardim & Varanda
(21, 'Mangueira de Jardim 20m',            100,   89.90, 'Mangueira flexível com engates rápidos.'),
(21, 'Kit Ferramentas para Jardim 5 Peças', 90,   99.90, 'Kit com pá, rastelo e tesoura de poda.'),

-- 22 - Esporte & Fitness
(22, 'Halteres Emborrachados 5kg (Par)',    80,  149.90, 'Par de halteres revestidos emborrachados.'),
(22, 'Tapete de Yoga Antiderrapante',      120,   89.90, 'Tapete para exercícios e yoga.'),

-- 23 - Brinquedos & Hobbies
(23, 'Blocos de Montar 500 Peças',         130,  129.90, 'Brinquedo educativo com blocos coloridos.'),
(23, 'Quebra-cabeça 1000 Peças',           140,   79.90, 'Quebra-cabeça temático de paisagem.'),

-- 24 - Bebês
(24, 'Kit Body Bebê 3 Peças',              160,   99.90, 'Bodies em algodão para bebês.'),
(24, 'Carrinho de Bebê Reclinável',         40,  899.90, 'Carrinho com múltiplas posições e cinto de 5 pontos.'),

-- 25 - Pet Shop
(25, 'Ração Premium Cães Adultos 10kg',     90,  199.90, 'Ração completa para cães adultos.'),
(25, 'Arranhador para Gatos com Brinquedo',100,  149.90, 'Arranhador com base ampla e brinquedo pendurado.');
