let paginaCorrente = 0;

$(document).ready(function () {
    // Nasconde i form all'avvio
    $('#formRichiediPrestito').hide();
    $('#formInviaDenaro').hide();
    $('#formPrestiti').hide();

    // Mostra/nasconde i form al click dei bottoni
    $('#toggleFormInviaDenaro').on('click', function () {
        $('#formRichiediPrestito, #formPrestiti').hide(400);
        $('#formInviaDenaro').slideToggle(400);
    });

    $('#toggleFormRichiediPrestito').on('click', function () {
        $('#formInviaDenaro, #formPrestiti').hide(400);
        $('#formRichiediPrestito').slideToggle(400);
    });

    $('#toggleGestisciPrestiti').on('click', function () {
        $('#formInviaDenaro, #formRichiediPrestito').hide(400);
        $('#formPrestiti').slideToggle(400);
        if ($('#formPrestiti').is(':visible')) {
            loadPrestiti();
        }
    });

    // Pulsante carica transazioni
    $('#transazioni-button').on('click', aggiornaTransazioni);

    // Logout
    $('#logout-button').click(function() {
        $.post('/logout')
            .done(() => window.location.href = '/login')
            .fail((xhr, status, error) => console.error('Errore logout:', error));
    });

    // Apri conto
    $('#apri-conto-button').click(() => window.location.href = '/apri-conto');

    // Cambio conto
    $('#conto-select').change(function() {
        cambiaConto($(this).val());
    });

    // Invia denaro
    $('#formInviaDenaro').on('submit', function(e) {
        e.preventDefault();

        $.ajax({
            url: '/api/inviaDenaro',
            type: 'POST',
            data: $(this).serialize(),
            success: function(response) {
                window.location.href = '/?successo';
            },
            error: function(xhr) {
                let msg = xhr.responseText || "Errore nell'invio del denaro";
                if ($("#errorInviaDenaro").length === 0) {
                    $("#formInviaDenaro").append('<div id="errorInviaDenaro" style="color:red; margin-top:10px;"></div>');
                }
                $("#errorInviaDenaro").text(msg);
            }
        });
    });

    // Richiedi prestito
    $('#opzioniPrestito').on('submit', function(e) {
        e.preventDefault();

        $.ajax({
            url: '/api/prestito/richiedi',
            type: 'POST',
            data: $(this).serialize(),
            success: function(response) {
                window.location.href = '/?successo';
            },
            error: function(xhr) {
                let msg = xhr.responseText || "Errore nella richiesta di prestito";
                if ($("#errorRichiediPrestito").length === 0) {
                    $("#opzioniPrestito").append('<div id="errorRichiediPrestito" style="color:red; margin-top:10px;"></div>');
                }
                $("#errorRichiediPrestito").text(msg);
            }
        });
    });

    // Paga rata
    $(document).on('click', '.pagaRataBtn', function() {
        const row = $(this).closest('tr');
        const idPrestito = row.data('id');

        $.ajax({
            url: '/api/prestiti/pagaRata/' + idPrestito,
            method: 'POST',
            success: function() {
                loadPrestiti();
                window.location.href = '/?successo'

            },
            error: function(err) {
                alert('Errore nel pagamento della rata');
                console.error(err);
            }
        });
    });

    // Counter prestito
    let valore = 5000;
    const step = 5000, min = 5000, max = 50000;

    function aggiornaDisplay() {
        $("#importoValore").text(valore);
        $("#importoInput").val(valore);
    }

    $("#increment").click(function () {
        if (valore + step <= max) { valore += step; aggiornaOpzioni(valore); aggiornaDisplay(); }
    });
    $("#decrement").click(function () {
        if (valore - step >= min) { valore -= step; aggiornaOpzioni(valore); aggiornaDisplay(); }
    });

    aggiornaOpzioni(5000);
    aggiornaTransazioni();
    aggiornaDisplay();
});

// Funzione formato data italiana
function formatDateItalian(dateString) {
    const date = new Date(dateString);
    const formatted = new Intl.DateTimeFormat("it-IT", {
        day: "2-digit",
        month: "long",
        year: "numeric",
        hour: "2-digit",
        minute: "2-digit"
    }).format(date);
    return formatted.replace(/\b([a-z])/, char => char.toUpperCase());
}

// Transazioni
async function aggiornaTransazioni() {
    const transazioni = await getTransazioni();
    transazioni.forEach(t => {
        $("#transazioni tbody").append(`
            <tr>
                <td>${t.descrizione}</td>
                <td>${formatDateItalian(t.data)}</td>
                <td>${t.importo}€</td>
            </tr>
        `);
    });
}

// Opzioni prestito
async function aggiornaOpzioni(importo) {
    const opzioni = await getOpzioniPrestito(importo);
    $("#opzioniPrestito").empty();
    opzioni.forEach(o => {
        $("#opzioniPrestito").append(`
            <label>
                <input type="radio" name="numeroRate" value="${o.numeroRate}">
                ${o.numeroRate} rate da €${o.rataMensile.toFixed(2)} (TAEG ${(o.taeg*100).toFixed(2)}%, Totale €${o.totaleDovuto.toFixed(2)})
            </label>
        `);
    });
    $("#opzioniPrestito").append(`
        <input type="hidden" name="importoRichiesto" value="${importo}">
        <input type="submit" value="Submit">
    `);
}

// Funzioni AJAX
function getTransazioni() {
    return $.ajax({
        url: '/api/transazioni/ottieni',
        method: 'GET',
        data: { pagina: paginaCorrente },
        dataType: 'json',
        xhrFields: { withCredentials: true }
    }).done(() => paginaCorrente++);
}

function getOpzioniPrestito(importo) {
    return $.ajax({
        url: '/api/prestito/opzioni',
        method: 'GET',
        data: { importo: importo },
        dataType: 'json'
    });
}

// Cambia conto
function cambiaConto(alias) {
    $.ajax({
        url: '/api/conto/cambia',
        method: 'GET',
        data: { alias: alias },
        success: () => window.location.href = "/",
        error: xhr => alert("Errore: " + xhr.responseText)
    });
}

function loadPrestiti() {
    $.ajax({
        url: '/api/prestiti',
        method: 'GET',
        dataType: 'json',
        success: function(data) {
            const tbody = $('#prestitiTable tbody');
            tbody.empty();
            data.forEach(prestito => {
                tbody.append(`
                    <tr data-id="${prestito.idPrestito}">
                        <td>€${prestito.importo}</td>
                        <td>${prestito.numeroRate}</td>
                        <td>${(prestito.taeg*100).toFixed(2)}%</td>
                        <td>€${prestito.rataMensile.toFixed(2)}</td>
                        <td>€${prestito.importoPagato.toFixed(2)}</td>
                        <td>
                            <button class="pagaRataBtn">Paga rata</button>
                        </td>
                    </tr>
                `);
            });
        }
    });
}


