export function showRegisterForm() {
    const main = document.getElementById('main');
    main.innerHTML = `
    <h2>Register</h2>
    <form id="registerForm">
      <input type="text" id="regUsername" placeholder="Username" required><br>
      <input type="password" id="regPassword" placeholder="Password" required><br>
      <button type="submit">Register</button>
    </form>
  `;

    document.getElementById('registerForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        const username = document.getElementById('regUsername').value;
        const password = document.getElementById('regPassword').value;

        const response = await fetch('/auth/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ login: username, password })
        });

        if (response.ok) {
            alert('User registered successfully. Please login.');
            showLoginForm();
        } else {
            alert('Registration failed.');
        }
    });
}