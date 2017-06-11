function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
}

function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
}

function deleteSelectRole()
{
    document.getElementById("def").style.display="none";
}

function cercaPerNome() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("cercaPerNome");
    filter = input.value.toUpperCase();
    table = document.getElementById("tabella");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[0];
        if (td) {
            if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}

function nuovaRiga() {
    var tabella = document.getElementById("tabella");
    //var x = document.getElementById("intestazione");
    //x.insertCell(4);
    //x.innerHTML = "Dottore che ha emesso la ricetta"
    var intestazione = tabella.rows[1].cells[2];
    var numerodiRighe = tabella.rows.length;
    alert(intestazione);
    var riga = tabella.insertRow(numerodiRighe);
    var cella1 = riga.insertCell(0);
    var t1 = document.createElement("input");
        t1.name = "cf";
        cella1.appendChild(t1);
    var cella2 = riga.insertCell(1);
    var t2 = document.createElement("input");
        t2.name = "nome";
        cella2.appendChild(t2);
    var cella3 = riga.insertCell(2);
    var t3 = document.createElement("input");
        t3.name = "cognome";
        cella3.appendChild(t3);
    var cella4 = riga.insertCell(3);
    var t4 = document.createElement("input");
        t4.name = "dataDiNAscita";
        cella4.appendChild(t4);
    var cella5 = riga.insertCell(4);
    var t5 = document.createElement("select");
        t5.options = new Option('Si', 'Si');
        t5.name= "cliente";
        cella5.appendChild(t5);

    $("#nuovoPaziente").hide();

}