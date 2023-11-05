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
const categoria = getQueryParameter('categoria') == "HoraExtra" ? "0" : "1";
const metodo = getQueryParameter('metodo');

// Obtém o elemento <h1> pelo id
const usernameDisplay = document.getElementById('usernameDisplay');

if(metodo==="CADASTRAR") {
// Define o texto do elemento <h1> com o valor do 'username'
    usernameDisplay.textContent = `Matrícula: ${username}`; // Exemplo de mensagem de boas-vindas

    function cadastrar() {
        const dataHoraEntrada = entrada.value;
        const dataHora = new Date(dataHoraEntrada);
        const dataHoraEntradaFormatada = `${dataHora.getFullYear()}-${(dataHora.getMonth() + 1).toString().padStart(2, '0')}-${dataHora.getDate().toString().padStart(2, '0')} ${dataHora.getHours().toString().padStart(2, '0')}:${dataHora.getMinutes().toString().padStart(2, '0')}:${dataHora.getSeconds().toString().padStart(2, '0')}`;
        const dataHoraSaida = saida.value;
        const dataHoraS = new Date(dataHoraSaida);
        const dataHoraSaidaFormatada = `${dataHoraS.getFullYear()}-${(dataHoraS.getMonth() + 1).toString().padStart(2, '0')}-${dataHoraS.getDate().toString().padStart(2, '0')} ${dataHoraS.getHours().toString().padStart(2, '0')}:${dataHoraS.getMinutes().toString().padStart(2, '0')}:${dataHoraS.getSeconds().toString().padStart(2, '0')}`;

// Agora, você pode usar dataHoraFormatada ao invés de dataHoraEntrada ao enviar os dados para o servidor

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
}
else if(metodo==="EDITAR") {
    document.addEventListener('DOMContentLoaded', function () {

        function fetchApontamentosAndPopulateTable() {
            const token = sessionStorage.getItem("token");
            if (!token) {
                window.location.href = "/index.html";
            }
            // Execute este código após a página ser completamente carregada
            const entrada = document.getElementById('entrada');
            const saida = document.getElementById('saida');
            const cliente = document.getElementById('cliente');
            const projeto = document.getElementById('projeto');
            const crSelect = document.getElementById('cr');
            const justificativa = document.getElementById('justificativa');


            fetch("/apontamentos/"+apontamentoId)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Erro na solicitação.');
                    }
                    return response.json();
                })
                .then(apontamento => {
                    // Preencha os campos "cliente" e "projeto" com os dados do JSON
                    entrada.value = apontamento.data_hora_inicio;
                    saida.value = apontamento.data_hora_fim;
                    justificativa.value = apontamento.justificativa;
                    const crApontamento = apontamento.centroResultadosId;
                    fetch("/CR/" + apontamento.centroResultadosId)
                        .then(response => {
                            if (!response.ok) {
                                throw new Error('Erro na solicitação.');
                            }
                            return response.json();
                        })
                        .then(data => {
                            // Preencha os campos "cliente" e "projeto" com os dados do JSON
                            cliente.value = data.nome_cliente;
                            projeto.value = data.nome_projeto;
                        })
                        .catch(error => {
                            console.error('Erro:', error);
                        });
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
                                option.textContent = obj.id.toString(); // Acesse a propriedade "id" do objeto e converta para string
                                crSelect.appendChild(option);
                                crSelect.value = crApontamento;
                            });
                        })
                        .catch(error => {
                            console.error('Erro:', error);
                        });
                })
                .catch(error => {
                    console.error('Erro:', error);
                });


        }
        fetchApontamentosAndPopulateTable();

    });
    function editar() {
        const dataHoraEntrada = entrada.value;
        const dataHora = new Date(dataHoraEntrada);
        const dataHoraEntradaFormatada = `${dataHora.getFullYear()}-${(dataHora.getMonth() + 1).toString().padStart(2, '0')}-${dataHora.getDate().toString().padStart(2, '0')} ${dataHora.getHours().toString().padStart(2, '0')}:${dataHora.getMinutes().toString().padStart(2, '0')}:${dataHora.getSeconds().toString().padStart(2, '0')}`;
        const dataHoraSaida = saida.value;
        const dataHoraS = new Date(dataHoraSaida);
        const dataHoraSaidaFormatada = `${dataHoraS.getFullYear()}-${(dataHoraS.getMonth() + 1).toString().padStart(2, '0')}-${dataHoraS.getDate().toString().padStart(2, '0')} ${dataHoraS.getHours().toString().padStart(2, '0')}:${dataHoraS.getMinutes().toString().padStart(2, '0')}:${dataHoraS.getSeconds().toString().padStart(2, '0')}`;

        fetch("http://localhost:1234/apontamentos/"+apontamentoId,
            {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                method: "PUT",
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
        editar();
    });
    document.getElementById("submit2").addEventListener("click", function () {
        // Volte para a página anterior no histórico de navegação
        window.location.href = `listarApontamentos.html?username=${username}`;
    });
}
else if(metodo==="VISUALIZAR"){
    document.addEventListener('DOMContentLoaded', function () {

            const token = sessionStorage.getItem("token");
            if (!token) {
                window.location.href = "/index.html";
            }
            // Execute este código após a página ser completamente carregada
            const entrada = document.getElementById('entrada');
            const saida = document.getElementById('saida');
            const cliente = document.getElementById('cliente');
            const projeto = document.getElementById('projeto');
            const crSelect = document.getElementById('cr');
            const justificativa = document.getElementById('justificativa');
            document.getElementById("submit").remove();
            entrada.readOnly =true;
            saida.readOnly =true;
            cliente.readOnly =true;
            projeto.readOnly =true;
            justificativa.readOnly =true;
            fetch("/apontamentos/"+apontamentoId)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Erro na solicitação.');
                    }
                    return response.json();
                })
                .then(apontamento => {
                    // Preencha os campos "cliente" e "projeto" com os dados do JSON
                    entrada.value = apontamento.data_hora_inicio;
                    saida.value = apontamento.data_hora_inicio;
                    justificativa.value = apontamento.justificativa;
                    const crApontamento = apontamento.centroResultadosId;
                    fetch("/CR/" + apontamento.centroResultadosId)
                        .then(response => {
                            if (!response.ok) {
                                throw new Error('Erro na solicitação.');
                            }
                            return response.json();
                        })
                        .then(data => {
                            // Preencha os campos "cliente" e "projeto" com os dados do JSON
                            cliente.value = data.nome_cliente;
                            projeto.value = data.nome_projeto;
                        })
                        .catch(error => {
                            console.error('Erro:', error);
                        });
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
                                option.textContent = obj.id.toString(); // Acesse a propriedade "id" do objeto e converta para string
                                crSelect.appendChild(option);
                                crSelect.value = crApontamento;
                                crSelect.disabled = true;
                            });
                        })
                        .catch(error => {
                            console.error('Erro:', error);
                        });
                })
                .catch(error => {
                    console.error('Erro:', error);
                });

    });
    document.getElementById("submit2").addEventListener("click", function () {
        window.location.href = `listarApontamentos.html?username=${username}`;
    });
}
if(metodo==="CADASTRAR") {
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
}
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






