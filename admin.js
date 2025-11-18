document.addEventListener('DOMContentLoaded', () => {

    // --- "BANCO DE DADOS" (LOCALSTORAGE) ---
    // Funções para ler e salvar dados para que persistam entre as páginas
    
    function getData(key, defaultData) {
        const data = localStorage.getItem(key);
        return data ? JSON.parse(data) : defaultData;
    }

    function saveData(key, data) {
        localStorage.setItem(key, JSON.stringify(data));
    }

    // Dados iniciais (se o localStorage estiver vazio)
    const defaultStores = [
        { id: 1, name: 'MegaLoja - Unidade Centro', address: 'Rua Fictícia, 123' },
        { id: 2, name: 'MegaLoja - Unidade Norte', address: 'Av. Inventada, 456' }
    ];

    const defaultProducts = [
        { id: 1, name: 'Notebook Gamer Modelo X', stores: [1, 2], price: 1999.90, description: 'Descrição teste', imageUrl: '#', category: 'Eletronicos', stock: 15 }
    ];

    // Carrega os dados
    let stores = getData('megaloja_stores', defaultStores);
    let products = getData('megaloja_products', defaultProducts);

    // --- VARIÁVEIS DE ESTADO ---
    let selectedStoreIds = [];
    let editingStoreId = null; 
    let editingProductId = null;

    // --- SELETORES DO DOM (Gerais e Específicos) ---
    // Verificamos se os elementos existem antes de tentar usá-los (pois estamos em páginas separadas)
    
    // Elementos da Página de LOJAS
    const storeForm = document.getElementById('store-form');
    const storeNameInput = document.getElementById('NOME_LOJA');
    const storeAddressInput = document.getElementById('ENDERECO_LOJA');
    const storeListElement = document.getElementById('store-list');

    // Elementos da Página de PRODUTOS
    const productForm = document.getElementById('product-form');
    const productNameInput = document.getElementById('NOME_PRODUTO');
    const productDescriptionInput = document.getElementById('DESCRICAO_PRODUTO');
    const productPriceInput = document.getElementById('PRECO_PRODUTO');
    const productStockInput = document.getElementById('QTD_ESTOQUE');
    const productImageUrlInput = document.getElementById('URL_IMAGEM_PRODUTO');
    const productCategoryInput = document.getElementById('CATEGORIA_PRODUTO');
    const productStoreList = document.getElementById('product-store-list'); // Checkboxes no form
    const hiddenStoreInput = document.getElementById('LOJAS_SELECIONADAS_INPUT');
    const productListElement = document.getElementById('product-list');
    
    // Elemento do FILTRO (Novo)
    const filterSelect = document.getElementById('filtro-loja-select');


    // --- FUNÇÕES AUXILIARES ---

    function updateHiddenStoreInput() {
        if(hiddenStoreInput) hiddenStoreInput.value = selectedStoreIds.join(',');
    }
    
    function clearStoreEditMode() {
        editingStoreId = null;
        if(storeForm) {
            storeForm.reset();
            document.getElementById('store-submit-button').textContent = 'Salvar Loja';
        }
    }

    function clearProductEditMode() {
        editingProductId = null;
        if(productForm) {
            productForm.reset();
            selectedStoreIds = [];
            renderStoreOptionsInForm(); // Reseta os checkboxes
            document.getElementById('product-submit-button').textContent = 'Salvar Produto';
        }
    }


    // --- FUNÇÕES DE RENDERIZAÇÃO ---

    // 1. Renderiza Lista de Lojas (Página de Lojas)
    function renderStoreList() {
        if (!storeListElement) return; // Se não estiver na página de lojas, sai.

        storeListElement.innerHTML = '';
        stores.forEach(store => {
            const li = document.createElement('li');
            li.innerHTML = `
                <span>${store.name}</span>
                <div class="item-actions">
                    <button class="btn-edit" data-id="${store.id}"><i class="fa-solid fa-pen-to-square"></i></button>
                    <button class="btn-remove" data-id="${store.id}">Remover</button>
                </div>
            `;
            storeListElement.appendChild(li);
        });
    }

    // 2. Renderiza Opções de Loja no Formulário de Produto (Checkboxes)
    function renderStoreOptionsInForm() {
        if (!productStoreList) return; // Se não estiver na página de produtos, sai.

        productStoreList.innerHTML = '';
        stores.forEach(store => {
            const li = document.createElement('li');
            li.dataset.id = store.id;
            li.textContent = store.name;
            
            if (selectedStoreIds.includes(store.id)) {
                li.classList.add('selected');
            }
            
            productStoreList.appendChild(li);
        });
        updateHiddenStoreInput();
    }

    // 3. Popula o Select de Filtro (Página de Produtos) - NOVO!
    function populateFilterSelect() {
        if (!filterSelect) return;

        // Mantém a opção "Todas" e limpa o resto
        filterSelect.innerHTML = '<option value="todas">Todas as Lojas</option>';

        stores.forEach(store => {
            const option = document.createElement('option');
            option.value = store.id;
            option.textContent = store.name;
            filterSelect.appendChild(option);
        });
    }
    
    // 4. Renderiza Lista de Produtos (com suporte a Filtro)
    function renderProductList(filterStoreId = 'todas') {
        if (!productListElement) return; // Se não estiver na página de produtos, sai.

        productListElement.innerHTML = '';

        // Filtra os produtos baseado na seleção
        let filteredProducts = products;

        if (filterStoreId !== 'todas') {
            const idToFilter = parseInt(filterStoreId);
            // Filtra produtos que tenham o ID da loja no array 'stores'
            filteredProducts = products.filter(product => product.stores.includes(idToFilter));
        }

        if (filteredProducts.length === 0) {
            productListElement.innerHTML = '<li style="justify-content:center; color:#777;">Nenhum produto encontrado nesta loja.</li>';
            return;
        }

        filteredProducts.forEach(product => {
            // Busca nomes das lojas para exibir
            const storeNames = product.stores.map(storeId => {
                const store = stores.find(s => s.id === storeId);
                return store ? store.name.replace('MegaLoja - ', '') : 'Loja Removida';
            }).join(', ');
            
            const li = document.createElement('li');
            li.innerHTML = `
                <div style="display:flex; flex-direction:column; gap:4px;">
                    <span>${product.name}</span>
                    <small style="color:#666; font-weight:normal;">
                        ${product.category} | Estoque: <strong>${product.stock}</strong> | Lojas: ${storeNames}
                    </small>
                </div>
                <div class="item-actions">
                    <button class="btn-edit" data-id="${product.id}"><i class="fa-solid fa-pen-to-square"></i></button>
                    <button class="btn-remove" data-id="${product.id}">Remover</button>
                </div>
            `;
            productListElement.appendChild(li);
        });
    }


    // --- HANDLERS (Lógica de Salvar/Editar/Remover) ---

    // --- LOJAS ---
    function handleAddStore(event) {
        event.preventDefault(); 
        const storeName = storeNameInput.value.trim();
        const storeAddress = storeAddressInput.value.trim();

        if (storeName === '') {
            alert('Por favor, informe o nome da loja.');
            return;
        }

        if (editingStoreId) {
            // Edição
            const storeIndex = stores.findIndex(store => store.id === editingStoreId);
            if (storeIndex !== -1) {
                stores[storeIndex].name = storeName;
                stores[storeIndex].address = storeAddress;
                alert('Loja atualizada!');
            }
        } else {
            // Criação
            const newStore = { id: Date.now(), name: storeName, address: storeAddress };
            stores.push(newStore); 
            alert('Loja cadastrada!');
        }

        saveData('megaloja_stores', stores); // Salva no LocalStorage
        clearStoreEditMode();
        renderStoreList();
    }

    function handleEditStore(storeId) {
        const id = parseInt(storeId);
        const storeToEdit = stores.find(store => store.id === id);
        
        if (storeToEdit) {
            storeNameInput.value = storeToEdit.name;
            storeAddressInput.value = storeToEdit.address;
            editingStoreId = id;
            document.getElementById('store-submit-button').textContent = 'Atualizar Loja';
        }
    }

    function handleRemoveStore(storeId) {
        if (!confirm('Deseja remover esta loja? Isso removerá a loja dos produtos vinculados.')) return;
        
        const id = parseInt(storeId);
        stores = stores.filter(store => store.id !== id);
        
        // Remove essa loja de dentro dos produtos também
        products.forEach(product => {
             product.stores = product.stores.filter(sId => sId !== id);
        });

        saveData('megaloja_stores', stores); // Salva Lojas atualizadas
        saveData('megaloja_products', products); // Salva Produtos atualizados

        renderStoreList();
    }

    // --- PRODUTOS ---

    // Seleção visual das lojas no form de produto
    function handleStoreSelectInForm(event) {
        const clickedLi = event.target;
        if (clickedLi.tagName !== 'LI') return; 
        
        const storeId = parseInt(clickedLi.dataset.id);
        const index = selectedStoreIds.indexOf(storeId);

        if (index > -1) {
            selectedStoreIds.splice(index, 1); // Remove
            clickedLi.classList.remove('selected');
        } else {
            selectedStoreIds.push(storeId); // Adiciona
            clickedLi.classList.add('selected');
        }
        
        updateHiddenStoreInput();
    }

    function handleAddProduct(event) {
        event.preventDefault();

        const name = productNameInput.value.trim();
        const price = parseFloat(productPriceInput.value);
        const stock = parseInt(productStockInput.value); 
        const description = productDescriptionInput.value.trim();
        const imageUrl = productImageUrlInput.value.trim();
        const category = productCategoryInput.value;
        
        if (name === '' || isNaN(price) || isNaN(stock)) {
            alert('Preencha os campos obrigatórios corretamente.');
            return;
        }

        // array para não perder a referência
        const productStores = [...selectedStoreIds]; 

        if (productStores.length === 0) {
            alert('Selecione pelo menos uma loja para este produto.');
            return;
        }

        const productData = {
            name, description, price, stock, imageUrl, category, stores: productStores
        };

        if (editingProductId) {
            // Edição
            const productIndex = products.findIndex(p => p.id === editingProductId);
            if (productIndex !== -1) {
                products[productIndex] = { ...products[productIndex], ...productData };
                alert('Produto atualizado!');
            }
        } else {
            // Criação
            const newProduct = { id: Date.now(), ...productData };
            products.push(newProduct);
            alert('Produto cadastrado!');
        }

        saveData('megaloja_products', products); // Salva no LocalStorage
        clearProductEditMode();
        
        // Atualiza a lista mantendo o filtro atual
        renderProductList(filterSelect.value); 
    }

    function handleEditProduct(productId) {
        const id = parseInt(productId);
        const productToEdit = products.find(product => product.id === id);
        
        if (productToEdit) {
            productNameInput.value = productToEdit.name;
            productDescriptionInput.value = productToEdit.description;
            productPriceInput.value = productToEdit.price;
            productStockInput.value = productToEdit.stock;
            productImageUrlInput.value = productToEdit.imageUrl;
            productCategoryInput.value = productToEdit.category;

            editingProductId = id;
            document.getElementById('product-submit-button').textContent = 'Atualizar Produto';

            // Carrega as lojas selecionadas desse produto
            selectedStoreIds = [...productToEdit.stores];
            renderStoreOptionsInForm(); 
            
            // Rola a página para o formulário
            window.scrollTo({ top: 0, behavior: 'smooth' });
        }
    }

    function handleRemoveProduct(productId) {
        if (!confirm('Remover este produto?')) return;
        
        const id = parseInt(productId);
        products = products.filter(product => product.id !== id);
        
        saveData('megaloja_products', products);
        renderProductList(filterSelect.value); // Mantém o filtro
        clearProductEditMode();
    }


    // --- EVENT LISTENERS ---

    // Verifica se estamos na página de LOJAS
    if (storeForm) {
        storeForm.addEventListener('submit', handleAddStore);
        storeListElement.addEventListener('click', (event) => {
            if (event.target.classList.contains('btn-remove')) handleRemoveStore(event.target.dataset.id);
            if (event.target.classList.contains('btn-edit')) handleEditStore(event.target.dataset.id);
        });
        
        // Render inicial
        renderStoreList();
    }

    // Verifica se estamos na página de PRODUTOS
    if (productForm) {
        productForm.addEventListener('submit', handleAddProduct);
        
        // Clique nos checkboxes de loja do formulário
        productStoreList.addEventListener('click', handleStoreSelectInForm);
        
        // Clique nos botões da lista de produtos
        productListElement.addEventListener('click', (event) => {
            const btn = event.target;
            // Usa closest para garantir que pegamos o botão mesmo se clicar num ícone dentro dele
            if (btn.classList.contains('btn-remove')) handleRemoveProduct(btn.dataset.id);
            if (btn.classList.contains('btn-edit')) handleEditProduct(btn.dataset.id);
        });

        // Evento do FILTRO
        if (filterSelect) {
            filterSelect.addEventListener('change', (e) => {
                const selectedStore = e.target.value;
                renderProductList(selectedStore);
            });
        }

        // Render inicial da página de produtos
        populateFilterSelect();     // Preenche o dropdown de filtro
        renderStoreOptionsInForm(); // Preenche os checkboxes do form
        renderProductList();        // Preenche a lista de produtos
    }

});