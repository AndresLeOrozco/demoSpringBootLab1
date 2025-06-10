import { loginUser } from '../auth.js';

export function showLoginForm() {
    const main = document.getElementById('main');
    main.innerHTML = `
    <h2>Login</h2>
    <form id="loginForm">
      <input type="text" id="usuarioId" placeholder="User ID" required><br>
      <input type="password" id="clave" placeholder="Password" required><br>
      <button type="submit">Login</button>
    </form>
    <div id="loginError" style="color: red;"></div>
  `;

    document.getElementById('loginForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        const usuarioId = document.getElementById('usuarioId').value;
        const clave = document.getElementById('clave').value;

        console.log("🔎 Datos capturados del formulario:", { usuarioId, clave });

        try {
            const data = await loginUser({ usuarioId, clave });

            // Emitir evento de login exitoso
            window.dispatchEvent(new CustomEvent('loginSuccess', { detail: data }));
        } catch (error) {
            document.getElementById('loginError').textContent = 'Login failed: ' + error.message;
        }
    });
}