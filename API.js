// --- CADASTRO DE USUÁRIO ---
document.getElementById("cadastroForm").addEventListener("submit", async (event) => {          // pega o formulário de cadastro pelo ID
  event.preventDefault();

  const nome = document.getElementById("user").value;                   // pega o valor do campo nome
  const cpf = document.getElementById("cpf").value;                    // pega o valor do campo cpf
  const email = document.getElementById("email").value;               // pega o valor do campo email
  const senha = document.getElementById("senha").value;              // pega o valor do campo senha

  try {
    const resposta = await axios.post("http://localhost:3000/register", { nome, cpf, email, senha });     // envia os dados para o servidor
    alert("✅ Usuário cadastrado com sucesso!");
    console.log(resposta.data);
  } catch (erro) {
    alert("❌ Erro no cadastro: " + (erro.response?.data || erro.message));
  }
});


// --- LOGIN DE USUÁRIO --- 
document.getElementById("loginForm").addEventListener("submit", async (event) => {         // pega o formulário de login pelo ID
  event.preventDefault();

  const email = document.getElementById("email_login").value;                     // pega o valor do campo email
  const senha = document.getElementById("senha_login").value;                    // pega o valor do campo senha

  try {
    const resposta = await axios.post("http://localhost:3000/login", { email, senha });        // envia os dados para o servidor
    alert("✅ Login bem-sucedido!");
    console.log(resposta.data);
  } catch (erro) {
    alert("❌ Erro no login: " + (erro.response?.data || erro.message));
  }
});
