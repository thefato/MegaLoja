// API.js
document.addEventListener("DOMContentLoaded", () => {
// -----------------------------------------------------

    // --- CADASTRO DE USUÁRIO ---
    document.getElementById("cadastroForm").addEventListener("submit", async event => {
        event.preventDefault();

        // Variáveis renomeadas para evitar conflito com o objeto global 'document'
        const name = document.getElementById("user").value;
        const cpfValue = document.getElementById("cpf").value; // Renomeado de 'document' para 'cpfValue'
        const email = document.getElementById("email").value;
        const password = document.getElementById("senha").value; // Corrigido de 'passowrd' para 'password'

        try {
            // A variável 'cpfValue' e 'password' estão sendo enviadas
            const resposta = await axios.post("http://localhost:8090/auth/v1/register", { name, document: cpfValue, email, password });
            alert(" Usuário cadastrado com sucesso!");
            console.log(resposta.data);
        } catch (erro) {
            alert(" Erro no cadastro: " + (erro.response?.data || erro.message));
        }
    }); // <-- Fechamento do Listener de Cadastro

    // -----------------------------------------------------

    // --- LOGIN DE USUÁRIO ---
    document.getElementById("loginForm").addEventListener("submit", async event => {
        event.preventDefault();

        const email = document.getElementById("email_login").value;
        const senha = document.getElementById("senha_login").value;

        try {
            const resposta = await axios.post("http://localhost:8090/auth/v1/login", { email, senha });
            alert(" Login bem-sucedido!");
            console.log(resposta.data);
        } catch (erro) {
            alert(" Erro no login: " + (erro.response?.data || erro.message));
        }

    }); // <-- Fechamento do Listener de Login

// -----------------------------------------------------
}); // <-- Fechamento final do DOMContentLoaded