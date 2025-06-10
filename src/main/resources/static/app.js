// Variables para estado de usuario, perfil y token
let currentUser = null;
let currentPerfil = null;
let currentPermisos = [];
let jwtToken = null;

// Función para mostrar el menú superior según estado login
function renderTopMenu() {
    const container = document.getElementById('topMenuContainer');
    if (!container) return;

    let html = `<div style="background: gray; padding: 10px;">`;

    if (currentUser) {
        html += `
            <span>Logged as: ${currentUser} (Perfil: ${currentPerfil})</span> |
            <a href="#" id="logoutLink">Logout</a> |
            <a href="#" id="profileLink">Profile</a>
        `;
    } else {
        html += `
            <a href="#" id="loginLink">Login</a> |
            <a href="#" id="registerLink">Register</a>
        `;
    }

    html += `</div>`;
    container.innerHTML = html;

    if (currentUser) {
        document.getElementById('logoutLink').addEventListener('click', logout);
    } else {
        document.getElementById('loginLink').addEventListener('click', showLoginForm);
        document.getElementById('registerLink').addEventListener('click', showRegisterForm);
    }
}

// Función para mostrar menú lateral con permisos
function renderAsideMenu() {
    const container = document.getElementById('asideMenuContainer');
    if (!container) return;

    let menuHtml = `<div style="background: lightgreen; padding: 10px; height: 100vh;"> <ul style="list-style:none; padding-left:0;">`;

    menuHtml += `<li><a href="#" id="linkHome">Home</a></li>`;

    if (currentPermisos.includes("ProfilePage")) {
        menuHtml += `<li><a href="#" id="linkUser">User Profile</a></li>`;
    }

    if (currentPermisos.includes("AccessApprovalPage")) {
        menuHtml += `<li><a href="#" id="linkAdmin">Admin Panel</a></li>`;
    }

    if (currentPermisos.includes("BookingPage")) {
        menuHtml += `<li><a href="#" id="linkPrivate">Book Appointment</a></li>`;
    }

    menuHtml += `</ul></div>`;
    container.innerHTML = menuHtml;

    document.getElementById('linkHome').addEventListener('click', () => showMessage("Welcome to the public Home Page"));

    if (document.getElementById('linkUser')) {
        document.getElementById('linkUser').addEventListener('click', loadUserPage);
    }
    if (document.getElementById('linkAdmin')) {
        document.getElementById('linkAdmin').addEventListener('click', loadAdminPage);
    }
    if (document.getElementById('linkPrivate')) {
        document.getElementById('linkPrivate').addEventListener('click', loadPrivatePage);
    }
}

// Footer
function renderFooter() {
    const container = document.getElementById('footerContainer');
    if (!container) return;

    container.innerHTML = `
        <div style="background: lightblue; padding: 10px; text-align: center;">
            My Page Right
        </div>
    `;
}

// Mensaje principal
function showMessage(message) {
    const container = document.getElementById('mainContainer');
    if (!container) return;
    container.innerHTML = `<h2>${message}</h2>`;
}

// Login con JWT
function showLoginForm() {
    const container = document.getElementById('mainContainer');
    if (!container) return;

    container.innerHTML = `
        <h2>Login</h2>
        <form id="loginForm">
            <label>Login: <input type="text" id="login" required /></label><br/><br/>
            <label>Password: <input type="password" id="password" required /></label><br/><br/>
            <button type="submit">Login</button>
        </form>
        <div id="loginError" style="color: red;"></div>
    `;

    document.getElementById('loginForm').addEventListener('submit', async e => {
        e.preventDefault();
        const login = document.getElementById('login').value;
        const password = document.getElementById('password').value;

        try {
            const response = await fetch('/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ login, password })
            });

            if (!response.ok) throw new Error('Login failed');
            const data = await response.json();

            jwtToken = data.token;

            const payload = JSON.parse(atob(jwtToken.split('.')[1]));
            currentUser = payload.sub;
            currentPerfil = payload.perfil;
            currentPermisos = payload.permisos || [];

            renderTopMenu();
            renderAsideMenu();
            showMessage(`Welcome ${currentUser} (Perfil: ${currentPerfil})`);
        } catch (err) {
            document.getElementById('loginError').textContent = 'Login failed: ' + err.message;
        }
    });
}

// Fetch con JWT
async function fetchWithAuth(url, options = {}) {
    if (!options.headers) options.headers = {};
    options.headers['Authorization'] = 'Bearer ' + jwtToken;
    return fetch(url, options);
}

// Acceso a páginas basado en permisos
async function loadUserPage() {
    if (!currentPermisos.includes("ProfilePage")) {
        showMessage('Access denied. User Profile permission required.');
        return;
    }
    showMessage('User Profile Page');
}

async function loadAdminPage() {
    if (!currentPermisos.includes("AccessApprovalPage")) {
        showMessage('Access denied. Admin Panel permission required.');
        return;
    }
    showMessage('Admin Panel Page');
}

async function loadPrivatePage() {
    if (!currentPermisos.includes("BookingPage")) {
        showMessage('Access denied. Booking Page permission required.');
        return;
    }
    showMessage('Booking Page');
}

// Logout
function logout() {
    currentUser = null;
    currentPerfil = null;
    currentPermisos = [];
    jwtToken = null;
    renderTopMenu();
    renderAsideMenu();
    showMessage('You have logged out.');
}

// Init
function init() {
    renderTopMenu();
    renderAsideMenu();
    renderFooter();
    showMessage('Welcome to the public Home Page');
}

window.addEventListener('DOMContentLoaded', init);

