document.getElementById('forgot-password-link').addEventListener('click', function(event) {
    event.preventDefault();
    document.getElementById('reset-password-container').classList.add('active');
});

document.getElementById('reset-password-form').addEventListener('submit', function(event) {
    const newPassword = document.getElementById('new-password').value;
    const confirmPassword = document.getElementById('confirm-password').value;
    if (newPassword !== confirmPassword) {
        event.preventDefault();
        document.getElementById('error-message').textContent = "Les mots de passe ne correspondent pas. Veuillez réessayer.";
    }
});
