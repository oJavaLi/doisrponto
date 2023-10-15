function obtemToken() {
    return sessionStorage.getItem("token");
}

function verificaLogin() {
    const token = sessionStorage.getItem("token");
    if (!token) {
      window.location.href = "/index.html";
      return;
    }
    return;
}

function resetToken() {
    sessionStorage.removeItem("token");
}

function obtemMatriculaUsuarioLogado() {
    return parseInt(atob(obtemToken()).substring(7));
}