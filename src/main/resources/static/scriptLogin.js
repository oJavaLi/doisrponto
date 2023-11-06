const loginForm = document.querySelector("form");

loginForm.addEventListener('submit', function (event) {
    event.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;


    // Construa o objeto de dados a ser enviado
    const loginData = {
        matricula: username,
        senha: password
    };

    console.log(loginData)

    // Faça uma solicitação POST para o endpoint de autenticação no servidor Spring Boot
    fetch("http://localhost:1234/login", {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginData)
    })
    .then(response => {
        if (response.status === 200) {
            // Autenticação bem-sucedida, você pode redirecionar o usuário para outra página ou executar ações apropriadas.
            console.log("Autenticação bem-sucedida");
            response.text().then(token => {
                sessionStorage.setItem('username', username)
                sessionStorage.setItem('token', token);
                // Redirecione para a página desejada após o login bem-sucedido
                window.location.href = '/listarApontamentos.html';
            })
        } else {
            // Autenticação falhou, você pode exibir uma mensagem de erro ao usuário.
            console.error("Falha na autenticação");
            alert("Credenciais Inválidas!");
        }
    })
    .catch(error => {
        console.error("Erro na solicitação:", error);
        alert("Falha na autenticação!")
    });
});