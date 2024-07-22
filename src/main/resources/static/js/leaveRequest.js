document.addEventListener("DOMContentLoaded", function() {
    // Function to handle form submission
    function submitRequest() {
        const formData = {
            employeeId: document.getElementById('employeeId').innerText.trim(),
            dateDebut: document.getElementById('dateDebut').value,
            dateFin: document.getElementById('dateFin').value,
            reason: document.getElementById('reason').value
        };

        // Implement AJAX form submission here
        $.ajax({
            type: "POST",
            url: "/employe/demandeConge",
            contentType: "application/x-www-form-urlencoded",
            data: formData,  // Send data as URL-encoded
            success: function(response) {
                alert('Demande envoyée avec succès!');
            },
            error: function(error) {
                console.error('Erreur lors de l\'envoi de la demande : ', error);
                alert('Une erreur s\'est produite.');
            }
        });
    }

    // Function to generate and download PDF
    function downloadPDF() {
        const { jsPDF } = window.jspdf;
        const doc = new jsPDF();

        doc.setFontSize(12);
        doc.text("Demande de Congé", 20, 20);

        const employeeId = document.getElementById('employeeId').innerText.trim();
        const employeeName = document.querySelector('p:nth-of-type(2) span').innerText.trim();
        const employeeFirstName = document.querySelector('p:nth-of-type(3) span').innerText.trim();
        const employeeEmail = document.querySelector('p:nth-of-type(4) span').innerText.trim();
        const employeePhone = document.querySelector('p:nth-of-type(5) span').innerText.trim();

        doc.text(`ID Employé: ${employeeId}`, 20, 30);
        doc.text(`Nom: ${employeeName}`, 20, 40);
        doc.text(`Prénom: ${employeeFirstName}`, 20, 50);
        doc.text(`Email: ${employeeEmail}`, 20, 60);
        doc.text(`Téléphone: ${employeePhone}`, 20, 70);

        const dateDebut = document.getElementById('dateDebut').value;
        const dateFin = document.getElementById('dateFin').value;
        const reason = document.getElementById('reason').value;

        doc.text(`Date de Début: ${dateDebut}`, 20, 90);
        doc.text(`Date de Fin: ${dateFin}`, 20, 100);
        doc.text(`Motif: ${reason}`, 20, 110);

        doc.save('demande_de_conge.pdf');
    }

    // Add event listeners to buttons
    document.querySelector('button.btn-primary').addEventListener('click', submitRequest);
    document.querySelector('button.btn-secondary').addEventListener('click', downloadPDF);
});
