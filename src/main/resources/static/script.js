const formulario = document.querySelector("form");
const botao = document.querySelector("button");
const UserNome = document.querySelector(".name");
const UserMatricula = document.querySelector(".matricula");
const UserEmail = document.querySelector(".email");
const UserSenha = document.querySelector(".password");
const UserCategoria = document.querySelector(".role");

function cadastrar() {

    fetch("http://localhost:1234/cadastrarUsuario",
    {
        headers: {
            'Accept':'application/json',
            'Content-Type':'application/json'
        },
        method:"POST",
        body: JSON.stringify({
            nome: UserNome.value,
            matricula: UserMatricula.value,
            email: UserEmail.value,
            senha: UserSenha.value,
            categoria: UserCategoria.value
    
        })
    })
    .then(function (res) {console.log(res)})
    .catch(function(res) {console.log(res)})
};

formulario.addEventListener('submit', function(event){
    event.preventDefault();
    cadastrar();

});