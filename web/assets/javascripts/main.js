function deleteSelectRole(){
    document.getElementById("def").style.display="none";
}

function cercaPerNome() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("cercaPerNome");
    filter = input.value.toUpperCase();     //converta la stringa in input in uppercase
    table = document.getElementById("tabella");
    tr = table.getElementsByTagName("tr");      //tr riga, th colonna, td cella
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[0];
        //nascondo le righe che non metchano l'input
        if (td) {
            if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {      //innerHTML ottengo il valore di quell'elemento, indexOf ottengo l'indice dell'elemento cercato
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}

function mostraCorpo() {
    var acc = document.getElementsByClassName("accordion");
    var i;

    for (i = 0; i < acc.length; i++) {
        acc[i].onclick = function() {
            this.classList.toggle("active");
            var panel = this.nextElementSibling;    //prossimo fratello della lista
            if (panel.style.maxHeight){     //controlla dimensione massima del div
                panel.style.maxHeight = null;       //imposta il div a null
            } else {
                panel.style.maxHeight = panel.scrollHeight + "px";  //ottiene altezza e larhezza dell'elemento e gli aggiunge px, poi lo assegna
            }
        }
    }
}

function nuovaRiga() {
    var tabella = document.getElementById("tabella");
    var numerodiRighe = tabella.rows.length;
    var riga = tabella.insertRow(numerodiRighe);
    var cella1 = riga.insertCell(0);
    var t1 = document.createElement("input");
    t1.name = "cf";
    t1.placeholder="CF paziente";
    t1.required="true";
    t1.className = "form-control";
    cella1.appendChild(t1);
    var cella2 = riga.insertCell(1);
    var t2 = document.createElement("input");
    t2.className = "form-control";
    t2.name = "nome";
    t2.placeholder="Nome paziente";
    t2.required="true";
    cella2.appendChild(t2);
    var cella3 = riga.insertCell(2);
    var t3 = document.createElement("input");
    t3.className = "form-control";
    t3.name = "cognome";
    t3.placeholder="Cognome paziente";
    t3.required="true";
    cella3.appendChild(t3);
    var cella4 = riga.insertCell(3);
    var t4 = document.createElement("input");
    t4.name = "dataDiNAscita";
    t4.className = "form-control";
    t4.placeholder = "aaaa-MM-dd";
    t4.type = "date";
    t4.required="true";
    cella4.appendChild(t4);
    var cella5 = riga.insertCell(4);
    var t5 = document.createElement("select");
    var option1 = document.createElement( "option" );
    option1.text = "";
    t5.appendChild(option1);
    var option2 = document.createElement( "option" );
    option2.text = "Si";
    t5.appendChild(option2);
    t5.name = "cliente";
    cella5.appendChild(t5);

    $("#nuovoPaziente").hide();
}