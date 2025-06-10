import { loginUser, logoutUser, isAuthenticated, getUsuarioId, getUserProfile, getUserPermissions, registerUser } from './auth.js';
import { showLoginForm, showRegisterForm } from './forms.js';
import { showMessage } from './mainContainer.js';

const topMenuDiv = document.createElement('div');
topMenuDiv.style.backgroundColor = 'gray';
topMenuDiv.style.padding = '10px';
topMenuDiv.style.display = 'flex';
topMenuDiv.style.justifyContent = 'space-between';
topMenuDiv.style.alignItems = 'center';

function createMenuItem(text, onClick) {
    const item = document.createElement('button');
    item.textContent = text;
    item.style.margin = '0 5px';
    item.style.cursor = 'pointer';
    item.addEventListener('click', onClick);
    return item;
}

function renderMenu() {
    topMenuDiv.innerHTML = ''; // Limpiar

    if (!isAuthenticated()) {
        const loginBtn = createMenuItem('Login', () => showLoginForm());
        const registerBtn = createMenuItem('Register', () => showRegisterForm());
        topMenuDiv.appendChild(loginBtn);
        topMenuDiv.appendChild(registerBtn);
    } else {
        const userInfoDiv = document.createElement('div');
        userInfoDiv.style.display = 'flex';
        userInfoDiv.style.alignItems = 'center';

        const usernameSpan = document.createElement('span');
        usernameSpan.textContent = `User: ${getUsuarioId() || "Unknown"}`;

        const profileSpan = document.createElement('span');
        profileSpan.textContent = `Profile: ${getUserProfile() || "None"}`;
        profileSpan.style.margin = '0 15px';

        const logoutBtn = createMenuItem('Logout', () => {
            logoutUser();
            showMessage('You have logged out');
            renderMenu(); // Refresca el menú al cerrar sesión
        });

        userInfoDiv.appendChild(usernameSpan);
        userInfoDiv.appendChild(profileSpan);
        userInfoDiv.appendChild(logoutBtn);

        topMenuDiv.appendChild(userInfoDiv);

        // Agregar elementos basados en permisos
        const userPermissions = getUserPermissions();

        if (userPermissions.includes('AccessApprovalPage')) {
            const adminPanelBtn = createMenuItem('Admin Panel', () => showMessage('Admin Panel Access'));
            topMenuDiv.appendChild(adminPanelBtn);
        }

        if (userPermissions.includes('BookingPage')) {
            const bookingBtn = createMenuItem('Book Appointment', () => showMessage('Booking Page Access'));
            topMenuDiv.appendChild(bookingBtn);
        }

        if (userPermissions.includes('ProfilePage')) {
            const profileBtn = createMenuItem('User Profile', () => showMessage('User Profile Access'));
            topMenuDiv.appendChild(profileBtn);
        }
    }
}

// React to auth events to re-render menu
window.addEventListener('loginSuccess', renderMenu);
window.addEventListener('logout', renderMenu);
window.addEventListener('registerSuccess', () => {
    showMessage('Registration successful! Please log in.');
    renderMenu();
});
window.addEventListener('loginFailed', e => showMessage(`Login failed: ${e.detail}`));
window.addEventListener('registerFailed', e => showMessage(`Registration failed: ${e.detail}`));

// Inicial render
renderMenu();

export function getTopMenu() {
    return topMenuDiv;
}
