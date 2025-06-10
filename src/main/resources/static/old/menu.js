export function renderTopMenu(user, onLoginClick, onLogoutClick) {
    const topMenu = document.createElement('div');
    topMenu.className = 'top-menu';

    if (user) {
        topMenu.innerHTML = `
            <span>Logged in as <strong>${user.perfil}</strong></span> |
            <span>Permissions: ${user.permisos.join(", ")}</span>
            <button id="logoutBtn">Logout</button>
        `;
    } else {
        topMenu.innerHTML = `<button id="loginBtn">Login</button>`;
    }

    if (user) {
        topMenu.querySelector('#logoutBtn').addEventListener('click', () => {
            onLogoutClick();
            window.dispatchEvent(new CustomEvent('logout'));
        });
    } else {
        topMenu.querySelector('#loginBtn').addEventListener('click', () => {
            onLoginClick();
            window.dispatchEvent(new CustomEvent('loginSuccess', { detail: user }));
        });
    }

    return topMenu;
}
