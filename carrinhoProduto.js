const API_BASE_URL = "http://localhost:8080/api"; 

// Buscar itens do carrinho
async function buscarItensCarrinho(idUsuario) {
    try {
        const response = await axios.get(`${API_BASE_URL}/carrinho/${idUsuario}`);
        return response.data;
    } catch (error) {
        console.error("Erro ao buscar carrinho:", error);
        return [];
    }
}

document.addEventListener("DOMContentLoaded", async () => {
    let idUsuario = 1; // Exemplo - depois puxa do login

    const itens = await buscarItensCarrinho(idUsuario);
    const container = document.getElementById("product-cart-grid");
    const summaryValue = document.getElementById("summary-products-value");

    let total = 0;
    container.innerHTML = "";

    itens.forEach(item => {
        total += item.preco * item.quantidade;

        container.innerHTML += `
            <div class="product-cart-item">
                <img src="${item.imagem}" class="product-img"/>

                <div class="product-info">
                    <h4>${item.nome}</h4>
                    <p>Quantidade: ${item.quantidade}</p>
                    <p>Preço: R$ ${item.preco.toFixed(2)}</p>
                </div>

                <button class="remove-btn">
                    <i class="fa-regular fa-trash-can"></i>
                </button>
            </div>
        `;
    });

    summaryValue.innerText = "R$ " + total.toFixed(2);
});

// Remover itens do carrinho
document.getElementById("product-cart-grid").addEventListener("click", async (event) => {
    if (event.target.closest(".remove-btn")) {
        const itemElement = event.target.closest(".product-cart-item");
        const itemName = itemElement.querySelector(".product-info h4").innerText;
        let idUsuario = 1;

        try {
           await axios.delete(`${API_BASE_URL}/carrinho/${idUsuario}/item/${item.id}`);


            // Remover do HTML
            const price = parseFloat(
                itemElement.querySelector(".product-info p:nth-child(3)")
                .innerText.replace("Preço: R$ ", "")
            );
            const quantity = parseInt(
                itemElement.querySelector(".product-info p:nth-child(2)")
                .innerText.replace("Quantidade: ", "")
            );

            const summaryValue = document.getElementById("summary-products-value");
            let currentTotal = parseFloat(summaryValue.innerText.replace("R$ ", ""));

            currentTotal -= price * quantity;
            summaryValue.innerText = "R$ " + currentTotal.toFixed(2);

            itemElement.remove();

        } catch (error) {
            console.error("Erro ao remover item do carrinho:", error);
        }
    }
});

// Atualizar quantidade (se for usar depois)
async function atualizarQuantidadeCarrinho(idUsuario, nomeProduto, novaQuantidade) {
    try {
        await axios.put(`${API_BASE_URL}/carrinho/${idUsuario}/item`, { 
            nome: nomeProduto, 
            quantidade: novaQuantidade 
        });
    } catch (error) {
        console.error("Erro ao atualizar quantidade no carrinho:", error);
    }   
}
            container.innerHTML = "<p>Erro ao carregar produtos recomendados.</p>";