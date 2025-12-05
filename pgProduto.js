document.addEventListener("DOMContentLoaded", async () => {

    // Pega ID do produto pela URL
    const urlParams = new URLSearchParams(window.location.search);
    const idProduto = urlParams.get("id"); 

    if (!idProduto) {
        alert("Produto não encontrado!");
        return;
    }

    // Buscar produto no back-end
    const produto = await buscarProduto(idProduto);

    if (!produto) {
        alert("Erro ao carregar produto.");
        return;
    }

    // Preencher HTML
    document.querySelector("h2").innerText = produto.nome;
    document.querySelector("img").src = produto.imagem;

    document.querySelector(".product-cart-grid span").innerText = produto.descricao;

    document.getElementById("VALOR DO PRODUTO AQUI").innerText = 
        "R$ " + produto.preco.toFixed(2);

    document.getElementById("NOME DA LOJA AQUI").innerText = produto.loja;
    document.getElementById("ENDEREÇO DA LOJA AQUI").innerText = produto.enderecoLoja;

    document.getElementById("INFORMAÇÃO DO ESTOQUE DA LOJA AQUI").innerText =
        produto.estoque > 0 ? "Disponível" : "Indisponível";


    // Controle de quantidade
    const quantityInput = document.getElementById("quantity-input");

    document.getElementById("quantity-increase").onclick = () => {
        quantityInput.value = Number(quantityInput.value) + 1;
    };

    document.getElementById("quantity-decrease").onclick = () => {
        if (quantityInput.value > 1) {
            quantityInput.value = Number(quantityInput.value) - 1;
        }
    };

    // Adicionar ao carrinho
    document.querySelector(".btn-addCart").addEventListener("click", async () => {
        const quantidade = Number(quantityInput.value);
        const idUsuario = 1; // depois troca pelo usuário logado

        await adicionarAoCarrinho(idUsuario, produto.id, quantidade);

        alert("Produto adicionado ao carrinho!");
    });

});
