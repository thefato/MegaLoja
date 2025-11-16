document.addEventListener('DOMContentLoaded', () => {

    // --- "BANCO DE DADOS" E ESTADO DE EDIÇÃO ---
    let stores = [
        { id: 1, name: 'MegaLoja - Unidade Centro', address: 'Rua Fictícia, 123' },
        { id: 2, name: 'MegaLoja - Unidade Norte', address: 'Av. Inventada, 456' }
    ];
    
    let products = [
        // Exemplo estático: ID 1 tem as lojas 1 e 2 (Centro e Norte), Categoria Eletronicos, 15 unidades em estoque
        { id: 1, name: 'Notebook Gamer Modelo X', stores: [1, 2], price: 1999.90, description: 'Descrição teste', imageUrl: '#', category: 'Eletronicos', stock: 15 }
    ];

    let selectedStoreIds = [];
    
    // VARIÁVEIS DE ESTADO PARA O MODO EDIÇÃO
    let editingStoreId = null; 
    let editingProductId = null;

    // --- SELETORES DO DOM ---
    // Loja
    const storeForm = document.getElementById('store-form');
    const storeNameInput = document.getElementById('NOME_LOJA');
    const storeAddressInput = document.getElementById('ENDERECO_LOJA');
    const storeListElement = document.getElementById('store-list');
    
    // Produto
    const productForm = document.getElementById('product-form');
    const productNameInput = document.getElementById('NOME_PRODUTO');
    const productDescriptionInput = document.getElementById('DESCRICAO_PRODUTO');
    const productPriceInput = document.getElementById('PRECO_PRODUTO');
    const productStockInput = document.getElementById('QTD_ESTOQUE');
    const productImageUrlInput = document.getElementById('URL_IMAGEM_PRODUTO');
    const productCategoryInput = document.getElementById('CATEGORIA_PRODUTO');
    const productStoreList = document.getElementById('product-store-list');
    const hiddenStoreInput = document.getElementById('LOJAS_SELECIONADAS_INPUT');
    const productListElement = document.getElementById('product-list');

    // --- FUNÇÕES AUXILIARES ---

    function updateHiddenStoreInput() {
        hiddenStoreInput.value = selectedStoreIds.join(',');
    }
    
    function clearStoreEditMode() {
        editingStoreId = null;
        storeForm.reset();
        document.getElementById('store-submit-button').textContent = 'Salvar Loja';
    }

    function clearProductEditMode() {
        editingProductId = null;
        productForm.reset();
        selectedStoreIds = [];
        renderStoreOptions();
        document.getElementById('product-submit-button').textContent = 'Salvar Produto';
    }


    // --- FUNÇÕES DE RENDERIZAÇÃO ---

    function renderStoreList() {
        storeListElement.innerHTML = '';
        stores.forEach(store => {
            const li = document.createElement('li');
            li.innerHTML = `
                <span>${store.name}</span>
                <div class="item-actions">
                    <button class="btn-edit" data-id="${store.id}">Editar</button>
                    <button class="btn-remove" data-id="${store.id}">Remover</button>
                </div>
            `;
            storeListElement.appendChild(li);
        });
    }

    function renderStoreOptions() {
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
    
    /* Inclui a quantidade em estoque na exibição da lista de produtos */
    function renderProductList() {
        productListElement.innerHTML = '';

        products.forEach(product => {
            const storeNames = product.stores.map(storeId => {
                const store = stores.find(s => s.id === storeId);
                return store ? store.name.replace('MegaLoja - ', '') : 'Loja Desconhecida';
            }).join(', ');
            
            const li = document.createElement('li');
            li.innerHTML = `
                <span>${product.name} (${storeNames}) - Categoria: ${product.category} | Estoque: ${product.stock}</span> 
                <div class="item-actions">
                    <button class="btn-edit" data-id="${product.id}">Editar</button>
                    <button class="btn-remove" data-id="${product.id}">Remover</button>
                </div>
            `;
            productListElement.appendChild(li);
        });
    }


    // --- FUNÇÕES DE AÇÃO ---

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

    /*
      Carrega o campo de Estoque para edição */
    function handleEditProduct(productId) {
        const id = parseInt(productId);
        const productToEdit = products.find(product => product.id === id);
        
        if (productToEdit) {
            productNameInput.value = productToEdit.name;
            productDescriptionInput.value = productToEdit.description;
            productPriceInput.value = productToEdit.price;
            productStockInput.value = productToEdit.stock; // CARREGA O ESTOQUE
            productImageUrlInput.value = productToEdit.imageUrl;
            productCategoryInput.value = productToEdit.category;

            editingProductId = id;
            document.getElementById('product-submit-button').textContent = 'Atualizar Produto';

            selectedStoreIds = [...productToEdit.stores];
            renderStoreOptions();
        }
    }

    function handleAddStore(event) {
        event.preventDefault(); 
        const storeName = storeNameInput.value.trim();
        const storeAddress = storeAddressInput.value.trim();

        if (storeName === '') {
            alert('Por favor, informe o nome da loja.');
            return;
        }

        if (editingStoreId) {
            const storeIndex = stores.findIndex(store => store.id === editingStoreId);
            if (storeIndex !== -1) {
                stores[storeIndex].name = storeName;
                stores[storeIndex].address = storeAddress;
                alert(`Loja "${storeName}" atualizada com sucesso!`);
            }
        } else {
            const newStore = { id: Date.now(), name: storeName, address: storeAddress };
            stores.push(newStore); 
            alert(`Loja "${storeName}" salva com sucesso!`);
        }

        clearStoreEditMode();
        renderStoreList();
        renderStoreOptions();
        renderProductList();
    }

    function handleRemoveStore(storeId) {
        if (!confirm('Tem certeza que deseja remover esta loja? Isso afetará os produtos ligados a ela.')) {
            return;
        }
        const id = parseInt(storeId);
        
        stores = stores.filter(store => store.id !== id);
        
        products.forEach(product => {
             product.stores = product.stores.filter(sId => sId !== id);
        });

        selectedStoreIds = selectedStoreIds.filter(selectedId => selectedId !== id);

        renderStoreList();
        renderStoreOptions();
        renderProductList();
        updateHiddenStoreInput();
        clearStoreEditMode();
    }
    
    function handleStoreSelect(event) {
        const clickedLi = event.target;
        if (clickedLi.tagName !== 'LI') return; 
        
        const storeId = parseInt(clickedLi.dataset.id);
        const index = selectedStoreIds.indexOf(storeId);

        if (index > -1) {
            selectedStoreIds.splice(index, 1);
            clickedLi.classList.remove('selected');
        } else {
            selectedStoreIds.push(storeId);
            clickedLi.classList.add('selected');
        }
        
        updateHiddenStoreInput();
    }
    
    /* Coleta e salva o campo de estoque
     */
    function handleAddProduct(event) {
        event.preventDefault();

        const name = productNameInput.value.trim();
        const price = parseFloat(productPriceInput.value);
        const stock = parseInt(productStockInput.value); 
        const description = productDescriptionInput.value.trim();
        const imageUrl = productImageUrlInput.value.trim();
        const category = productCategoryInput.value;
        
        // Validação: Estoque deve ser um número inteiro não negativo
        if (name === '' || isNaN(price) || price <= 0 || category === '' || isNaN(stock) || stock < 0) {
            alert('Por favor, preencha o nome, o preço, a categoria e o estoque (número inteiro >= 0) corretamente.');
            return;
        }
        
        const productStores = [...selectedStoreIds];

        const productData = {
            name: name,
            description: description,
            price: price,
            stock: stock,
            imageUrl: imageUrl,
            category: category,
            stores: productStores
        };

        if (editingProductId) {
            const productIndex = products.findIndex(p => p.id === editingProductId);
            if (productIndex !== -1) {
                products[productIndex] = { ...products[productIndex], ...productData };
                alert(`Produto "${name}" atualizado com sucesso!`);
            }
        } else {
            const newProduct = { id: Date.now(), ...productData };
            products.push(newProduct);
            alert(`Produto "${name}" salvo com sucesso!`);
        }

        clearProductEditMode();
        renderProductList();
    }

    function handleRemoveProduct(productId) {
        if (!confirm('Tem certeza que deseja remover este produto?')) {
            return;
        }
        const id = parseInt(productId);
        
        products = products.filter(product => product.id !== id);
        
        renderProductList();
        clearProductEditMode();
    }


    // --- EVENT LISTENERS ---

    storeForm.addEventListener('submit', handleAddStore);
    productForm.addEventListener('submit', handleAddProduct);

    storeListElement.addEventListener('click', (event) => {
        const clickedElement = event.target;
        if (clickedElement.classList.contains('btn-remove')) {
            handleRemoveStore(clickedElement.dataset.id);
        }
        if (clickedElement.classList.contains('btn-edit')) {
            handleEditStore(clickedElement.dataset.id);
        }
    });
    
    productStoreList.addEventListener('click', handleStoreSelect);
    
    productListElement.addEventListener('click', (event) => {
        const clickedElement = event.target;
        if (clickedElement.classList.contains('btn-remove')) {
            handleRemoveProduct(clickedElement.dataset.id);
        }
        if (clickedElement.classList.contains('btn-edit')) {
            handleEditProduct(clickedElement.dataset.id);
        }
    });

    // --- INICIALIZAÇÃO ---
    function init() {
        renderStoreList();
        renderStoreOptions();
        renderProductList();
    }
    
    init();

});