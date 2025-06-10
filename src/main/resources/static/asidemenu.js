// asideMenu.js

// Renderiza el menú lateral con los enlaces y eventos correspondientes
export function renderAsideMenu() {
    const container = document.getElementById('asideMenuContainer');
    if (!container) return;

    container.innerHTML = `
    <div style="background: lightgreen; padding: 10px; height: 100vh;">
      <ul style="list-style:none; padding-left:0;">
        <li><a href="#" id="linkHome">Home</a></li>
        <li><a href="#" id="linkUser">User Page</a></li>
        <li><a href="#" id="linkAdmin">Admin Page</a></li>
      </ul>
    </div>
  `;

    document.getElementById('linkHome').addEventListener('click', e => {
        e.preventDefault();
        // Mostrar mensaje para página pública
        const event = new CustomEvent('showMessage', { detail: "Welcome to the public Home Page" });
        window.dispatchEvent(event);
    });

    document.getElementById('linkUser').addEventListener('click', e => {
        e.preventDefault();
        // Emitir evento para cargar página privada user
        const event = new CustomEvent('loadUserPage');
        window.dispatchEvent(event);
    });

    document.getElementById('linkAdmin').addEventListener('click', e => {
        e.preventDefault();
        // Emitir evento para cargar página privada admin
        const event = new CustomEvent('loadAdminPage');
        window.dispatchEvent(event);
    });
}
