document.addEventListener('DOMContentLoaded', () => {

    // 1. --- CONFIGURAÇÃO DA API ---
    // Ajuste esta URL se seu backend não estiver na porta 8080!
    const API_PRODUCTS_URL = 'http://localhost:8090'; 

    // Seleciona o container onde os produtos serão exibidos (na página inicial)
    const productContainer = document.getElementById('product-container');

    // 2. --- FUNÇÃO PRINCIPAL DE EXIBIÇÃO ---
    async function fetchAndRenderProducts() {
        if (!productContainer) return; // Garante que estamos na página correta

        // Indica que está carregando
        productContainer.innerHTML = '<p style="text-align:center; color:#777;">Carregando produtos...</p>';
        
        try {
            // 1. Faz a requisição GET usando o Axios
            const response = await axios.get(API_PRODUCTS_URL);
            
            // Assume que a resposta.data é uma lista de objetos Produto
            const products = response.data; 

            if (products && products.length > 0) {
                
                // 2. Mapeia a lista de produtos para um array de strings HTML (os cards)
                const productsHtml = products.map(product => {
                    // Formata o preço para R$ 0.000,00
                    const formattedPrice = product.preco ? product.preco.toFixed(2).replace('.', ',').replace(/\B(?=(\d{3})+(?!\d))/g, '.') : 'Preço Indisponível';

                    // Cria o HTML de um único card de produto
                    return `
                        <div class="product-item" data-id="${product.id}">
                            <img src="${product.imageUrl || 'imagens/placeholder.png'}" alt="${product.name}" class="product-img">
                            <h3 class="product-name">${product.name}</h3>
                            <p class="product-price">R$ ${formattedPrice}</p>
                            <p class="product-description">${product.description.substring(0, 70)}...</p>
                            <button class="add-to-cart-btn">
                                <i class="fa-solid fa-cart-plus"></i> Adicionar ao Carrinho
                            </button>
                        </div>
                    `;
                }).join(''); // Junta todas as strings HTML em uma só
                
                // 3. Insere o HTML gerado no container
                productContainer.innerHTML = productsHtml;
            } else {
                // Caso a API retorne uma lista vazia
                productContainer.innerHTML = '<p style="text-align:center; color:#777;">Nenhum produto em destaque encontrado.</p>';
            }

        } catch (error) {
            // 4. Trata erros de conexão (Backend inativo, URL errada, CORS, etc.)
            console.error('Erro ao buscar produtos da API:', error);
            productContainer.innerHTML = `
                <p style="text-align:center; color:red;">
                    ❌ **Não foi possível carregar os produtos.** <br>
                    Verifique se o seu backend Java está ativo em **${API_PRODUCTS_URL}**
                </p>
            `;
        }
    }

    // 3. --- EXECUÇÃO ---
    // Chama a função para buscar e exibir os produtos assim que a página estiver pronta
    fetchAndRenderProducts();

    // ... (Aqui você colocaria o código do carrossel, acessibilidade, etc.) ... 
    
    
    // EX: Adicionando apenas a lógica de acessibilidade que você tinha no outro arquivo (se precisar)
    const btnAcessibilidade = document.getElementById('btnAcessibilidade');
    const acessibilidadeMenu = document.getElementById('acessibilidade-menu');
    const closeSidebar = document.getElementById('close-sidebar');

    if (btnAcessibilidade) {
        btnAcessibilidade.addEventListener('click', () => {
            acessibilidadeMenu.classList.toggle('open');
        });
    }

    if (closeSidebar) {
        closeSidebar.addEventListener('click', () => {
            acessibilidadeMenu.classList.remove('open');
        });
    }
    
    // ...
});