const API_BASE = 'http://localhost:8080/auth';

export async function loginUser(credentials) {
    console.log("🛠️ Enviando solicitud de login con:", credentials);

    try {
        const response = await fetch(`${API_BASE}/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                usuarioId: credentials.usuarioId,  // Ahora correcto
                clave: credentials.clave           // Aseguramos que coincida con `LoginDTO`
            }),
        });

        if (!response.ok) {
            const errorData = await response.json();
            console.error("❌ Error en autenticación:", errorData.message);
            throw new Error(errorData.message || "Login failed");
        }

        const data = await response.json();
        console.log("✅ Login exitoso, datos recibidos:", data);

        return data;
    } catch (error) {
        console.error("⚠️ Error en la solicitud de login:", error.message);
        throw error;
    }
}

export async function registerUser(userInfo) {
    try {
        const response = await fetch(`${API_BASE}/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(userInfo),
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Registration failed');
        }

        const data = await response.json();
        window.dispatchEvent(new CustomEvent('registerSuccess', { detail: data }));

        return data;
    } catch (error) {
        window.dispatchEvent(new CustomEvent('registerFailed', { detail: error.message }));
        throw error;
    }
}

export function logoutUser() {
    localStorage.removeItem('token');
    localStorage.removeItem('perfil');
    localStorage.removeItem('permisos');

    window.dispatchEvent(new CustomEvent('logout'));
}

export function isAuthenticated() {
    return !!localStorage.getItem('token');
}

export function getUserProfile() {
    return localStorage.getItem('perfil');
}

export function getUserPermissions() {
    return JSON.parse(localStorage.getItem('permisos') || "[]");
}
