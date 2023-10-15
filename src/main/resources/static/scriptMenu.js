// menuControl.js

// Função para ocultar um elemento HTML com base no seu ID
function hideElementById(id) {
    var element = document.getElementById(id);
    if (element) {
        element.style.display = 'none';
    }
}

// Função para exibir um elemento HTML com base no seu ID
function showElementById(id) {
    var element = document.getElementById(id);
    if (element) {
        element.style.display = 'block';
    }
}

// Função para controlar a exibição dos itens de menu com base no tipo de usuário
function setupMenuForUserType(usuario) {
    const tipoUsuario = usuario.categoria;

    // Primeiro, oculte todos os itens de menu
    hideElementById('homeMenuItem');
    hideElementById('usuarioMenuItem');
    hideElementById('listaUsuarioMenuItem');
    hideElementById('clientesMenuItem');
    hideElementById('crMenuItem');
    hideElementById('horasExtrasMenuItem');
    hideElementById('sobreavisoMenuItem');
    hideElementById('listaApontamentosMenuItem');
    hideElementById('aprovarApontamentoMenuItem');
    hideElementById('relatoriosMenuItem');
    hideElementById('ParametrizacaoMenuItem');
    hideElementById('logoutMenuItem');

    // Em seguida, exiba os itens de menu com base no tipo de usuário
    if (tipoUsuario === 'FUNCIONARIO') {
        showElementById('homeMenuItem');
        showElementById('horasExtrasMenuItem');
        showElementById('sobreavisoMenuItem');
        showElementById('logoutMenuItem');
    } else if (tipoUsuario === 'GESTOR') {
        showElementById('homeMenuItem');
        showElementById('clientesMenuItem');
        showElementById('crMenuItem');
        showElementById('horasExtrasMenuItem');
        showElementById('sobreavisoMenuItem');
        showElementById('listaApontamentosMenuItem');
        hideElementById('aprovarApontamentoMenuItem');
        showElementById('relatoriosMenuItem');
        showElementById('logoutMenuItem');
    } else if (tipoUsuario === 'RH') {
        // Se o tipo de usuário for 'rh', todos os itens de menu serão exibidos
        hideElementById('homeMenuItem');
        hideElementById('usuarioMenuItem');
        hideElementById('listaUsuarioMenuItem');
        hideElementById('clientesMenuItem');
        hideElementById('crMenuItem');
        hideElementById('horasExtrasMenuItem');
        hideElementById('sobreavisoMenuItem');
        hideElementById('listaApontamentosMenuItem');
        hideElementById('aprovarApontamentoMenuItem');
        hideElementById('relatoriosMenuItem');
        hideElementById('ParametrizacaoMenuItem');
        hideElementById('logoutMenuItem');
    }
}

// Chame a função para configurar o menu com base no tipo de usuário
obtemUsuarioLogado().then(usuario => setupMenuForUserType(usuario));
