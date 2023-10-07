const formulario = document.querySelector("sobre-aviso-form");
const botao = document.querySelector("submit");

const entrada = document.querySelector(".entrada");
const saida = document.querySelector(".saida");
const cliente = document.querySelector(".cliente");
const projeto = document.querySelector(".projeto");
const cr = document.querySelector(".cr");
const justificativa = document.querySelector(".justificativa");
const matricula = 1;

function getQueryParameter(name) {
    const urlSearchParams = new URLSearchParams(window.location.search);
    return urlSearchParams.get(name);
}

const username = getQueryParameter('username');
const categoria = getQueryParameter('categoria');

// Obtém o elemento <h1> pelo id
const usernameDisplay = document.getElementById('usernameDisplay');

// Define o texto do elemento <h1> com o valor do 'username'
usernameDisplay.textContent = `Matrícula: ${username}`; // Exemplo de mensagem de boas-vindas
function cadastrar(){
    fetch("http://localhost:1234/cadastrarApontamentos",
        {
            headers: {
                'Accept':'application/json',
                'Content-Type':'application/json'
            },
            method:"POST",
            body: JSON.stringify({
                categoria: categoria,
                data_hora_inicio:  entrada.value,
                data_hora_fim: saida.value,
                justificativa: justificativa.value,
                usuarioMatricula:  username,
                centroResultadosId: cr.value

            })
        })
        .then(function (res){
            // Verifica se a resposta da requisição foi bem-sucedida
            if (res.ok) {
                // Redireciona para a outra página HTML após o cadastro bem-sucedido
                window.location.href = `listarApontamentos.html?username=${username}`;
            } else {
                console.log("Erro ao cadastrar");
            }
        })
        .catch(function(res) {console.log(res)})
}
formulario.addEventListener('submit', function(event){
    event.preventDefault();
    cadastrar();
});
document.getElementById("submit2").addEventListener("click", function () {
    // Volte para a página anterior no histórico de navegação
    window.location.href = `listarApontamentos.html?username=${username}`;
});

//Opções nos CR
document.addEventListener('DOMContentLoaded', function () {
    // Execute este código após a página ser completamente carregada

    // Obtém a referência ao elemento <select> com id="cr"
    const crSelect = document.getElementById('cr');

    // Faça uma solicitação AJAX (ou fetch) para buscar os IDs da URL
    fetch('/CR')
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro na solicitação.');
            }
            return response.json();
        })
        .then(data => {
            // Preencha as opções do <select> com as propriedades "id" dos objetos
            data.forEach(obj => {
                const option = document.createElement('option');
                option.value = obj.id.toString(); // Acesse a propriedade "id" do objeto e converta para string
                option.textContent = obj.id.toString(); // Acesse a propriedade "id" do objeto e converta para string
                crSelect.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Erro:', error);
        });
});

//Cliente e Projeto
document.addEventListener('DOMContentLoaded', function () {

    function fetchApontamentosAndPopulateTable() {
        const token = sessionStorage.getItem("token");
        if (!token) {
            window.location.href = "/index.html";
        }
        // Execute este código após a página ser completamente carregada
        const crSelect = document.getElementById('cr');
        const clienteInput = document.getElementById('cliente');
        const projetoInput = document.getElementById('projeto');

        // Adicione um evento de alteração ao campo "CR"
        crSelect.addEventListener('change', function () {
            const crValue = this.value; // Obtém o valor selecionado no campo "CR"

            // Certifique-se de que um valor foi selecionado
            if (!crValue) {
                clienteInput.value = ''; // Limpa o campo "Cliente"
                projetoInput.value = ''; // Limpa o campo "Projeto"
                return;
            }

            // Faça uma solicitação GET para a URL /CR/{crValue} (onde {crValue} é o valor do campo CR)
            fetch("/CR/" + crValue)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Erro na solicitação.');
                    }
                    return response.json();
                })
                .then(data => {
                    // Preencha os campos "cliente" e "projeto" com os dados do JSON
                    clienteInput.value = data.nome_cliente;
                    projetoInput.value = data.nome_projeto;
                })
                .catch(error => {
                    console.error('Erro:', error);
                });
        });
    }
    fetchApontamentosAndPopulateTable();

});


