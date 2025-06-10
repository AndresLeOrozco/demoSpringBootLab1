import { showMessage } from '../mainContainer.js';
import { getAuthToken } from '../auth.js';

export function loadAsideMenu() {
    const aside = document.getElementById('aside-menu');
    aside.innerHTML = `
    <div>
      <h3>Menu</h3>
      <ul>
        <li><a href="#" id="home-link">Public Home</a></li>
        <li><a href="#" id="user-link">User Page</a></li>
        <li><a href="#" id="admin-link">Admin Page</a></li>
      </ul>
    </div>
  `;

    document.getElementById('home-link').addEventListener('click', () => {
        showMessage('Welcome to the public home page!');
    });

    document.getElementById('user-link').addEventListener('click', async () => {
        try {
            const response = await fetch('/user/private', {
                headers: {
                    'Authorization': `Bearer ${getAuthToken()}`
                }
            });
            if (response.ok) {
                const data = await response.text();
                showMessage(data);
            } else {
                showMessage(`Access denied: ${response.status}`);
            }
        } catch (error) {
            showMessage('Error accessing User page.');
        }
    });

    document.getElementById('admin-link').addEventListener('click', async () => {
        try {
            const response = await fetch('/admin/private', {
                headers: {
                    'Authorization': `Bearer ${getAuthToken()}`
                }
            });
            if (response.ok) {
                const data = await response.text();
                showMessage(data);
            } else {
                showMessage(`Access denied: ${response.status}`);
            }
        } catch (error) {
            showMessage('Error accessing Admin page.');
        }
    });
}
