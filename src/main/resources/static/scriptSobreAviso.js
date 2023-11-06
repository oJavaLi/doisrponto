const formulario = document.querySelector("form");
const botao = document.querySelector("#submit");

const entrada = document.querySelector(".entrada");
const saida = document.querySelector(".saida");
const cliente = document.querySelector(".cliente");
const projeto = document.querySelector(".projeto");
const justificativa = document.querySelector(".justificativa");

function getQueryParameter(name) {
    const urlSearchParams = new URLSearchParams(window.location.search);
    return urlSearchParams.get(name);
}

const username = getQueryParameter('username');
const apontamentoId = getQueryParameter('apontamentoId');
const categoria = getQueryParameter('categoria') == "SobreAviso" ? "1" : "0";
const metodo = getQueryParameter('metodo');

// Obtém o elemento <h1> pelo id
const usernameDisplay = document.getElementById('user-matricula');
usernameDisplay.textContent = `Matrícula: ${username}`;

const dataHoraEntrada = entrada.value;
const dataHora = new Date(dataHoraEntrada);
const dataHoraEntradaFormatada = `${dataHora.getFullYear()}-${(dataHora.getMonth() + 1).toString().padStart(2, '0')}-${dataHora.getDate().toString().padStart(2, '0')} ${dataHora.getHours().toString().padStart(2, '0')}:${dataHora.getMinutes().toString().padStart(2, '0')}:${dataHora.getSeconds().toString().padStart(2, '0')}`;
const dataHoraSaida = saida.value;
const dataHoraS = new Date(dataHoraSaida);
const dataHoraSaidaFormatada = `${dataHoraS.getFullYear()}-${(dataHoraS.getMonth() + 1).toString().padStart(2, '0')}-${dataHoraS.getDate().toString().padStart(2, '0')} ${dataHoraS.getHours().toString().padStart(2, '0')}:${dataHoraS.getMinutes().toString().padStart(2, '0')}:${dataHoraS.getSeconds().toString().padStart(2, '0')}`;


const crSelect = document.getElementById('cr');
function cadastrar(){
    const dataHoraEntrada = entrada.value;
    const dataHora = new Date(dataHoraEntrada);
    const dataHoraEntradaFormatada = `${dataHora.getFullYear()}-${(dataHora.getMonth() + 1).toString().padStart(2, '0')}-${dataHora.getDate().toString().padStart(2, '0')} ${dataHora.getHours().toString().padStart(2, '0')}:${dataHora.getMinutes().toString().padStart(2, '0')}:${dataHora.getSeconds().toString().padStart(2, '0')}`;
    const dataHoraSaida = saida.value;
    const dataHoraS = new Date(dataHoraSaida);
    const dataHoraSaidaFormatada = `${dataHoraS.getFullYear()}-${(dataHoraS.getMonth() + 1).toString().padStart(2, '0')}-${dataHoraS.getDate().toString().padStart(2, '0')} ${dataHoraS.getHours().toString().padStart(2, '0')}:${dataHoraS.getMinutes().toString().padStart(2, '0')}:${dataHoraS.getSeconds().toString().padStart(2, '0')}`;

    fetch("http://localhost:1234/cadastrarApontamentos",
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: "POST",
            body: JSON.stringify({

                categoria: categoria,
                data_hora_inicio: dataHoraEntradaFormatada,
                data_hora_fim: dataHoraSaidaFormatada,
                justificativa: justificativa.value,
                usuarioMatricula: username,
                centroResultadosId: cr.value,
                aprovado: false,
                avaliador: null
            })
        })
        .then(function (res) {
            // Verifica se a resposta da requisição foi bem-sucedida
            if (res.ok) {
                // Redireciona para a outra página HTML após o cadastro bem-sucedido
                window.location.href = `listarApontamentos.html?username=${username}`;
            } else {
                console.log("Erro ao cadastrar");
            }
        })
        .catch(function (res) {
            console.log(res)
        })
}
    formulario.addEventListener('submit', function (event) {
        event.preventDefault();
        cadastrar();
    });
    document.getElementById("submit2").addEventListener("click", function () {
        // Volte para a página anterior no histórico de navegação
        window.location.href = `/listarApontamentos.html?username=${username}`;
    });
// Faça uma solicitação AJAX (ou fetch) para buscar os IDs da URL
document.addEventListener('DOMContentLoaded', function () {

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