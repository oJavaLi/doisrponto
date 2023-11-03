const formulario = document.querySelector("form");
const botao = document.querySelector("button");
const UserNome = document.querySelector(".name");
const UserMatricula = document.querySelector(".matricula");
const UserEmail = document.querySelector(".email");
const UserSenha = document.querySelector(".password");
const UserCategoria = document.querySelector(".role");

function cadastrar() {
     // Verifique se o usuário está autenticado
     const token = sessionStorage.getItem("token");
     if (!token) {
       window.location.href = "/index.html";
       return;
     }
     const userData = {
        nome: UserNome.value,
        matricula: UserMatricula.value,
        email: UserEmail.value,
        senha: UserSenha.value,
        categoria: UserCategoria.value,
        recover_password_token: "",
      };
  
      // Faça a solicitação de cadastro autenticada com o método "POST"
      fetch("http://localhost:1234/api/cadastrarUsuario", {
        method: "POST", // Use o método "POST" aqui
        headers: {
          'Authorization': token, // Adicione o token de autenticação aos cabeçalhos
          'Accept': 'application/json',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(userData),
      })
        .then(function (response) {
          if (response.ok) {
            return response.json();
          } else {
            throw new Error('Erro na solicitação');
          }
        })
        .then(function (data) {
          // Lide com a resposta do servidor (por exemplo, exiba uma mensagem de sucesso)
          console.log(data);
        })
        .catch(function (error) {
          // Lide com erros (por exemplo, exiba uma mensagem de erro)
          console.error(error);
        });
    }
  
    formulario.addEventListener('submit', function (event) {
      event.preventDefault();
      cadastrar(); // Chame a função de cadastro aqui
    });
