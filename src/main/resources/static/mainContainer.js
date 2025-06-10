// mainContainer.js

export function showMessage(message) {
    const mainContainer = document.getElementById('main-container');
    mainContainer.innerHTML = `<p>${message}</p>`;
}

export function clearMessage() {
    const mainContainer = document.getElementById('main-container');
    mainContainer.innerHTML = '';
}
