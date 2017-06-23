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

function mostraCorpo() {
    var acc = document.getElementsByClassName("accordion");
    var i;

    for (i = 0; i < acc.length; i++) {
        acc[i].onclick = function() {
            this.classList.toggle("active");
            var panel = this.nextElementSibling;
            if (panel.style.maxHeight){
                panel.style.maxHeight = null;
            } else {
                panel.style.maxHeight = panel.scrollHeight + "px";
            }
        }
    }
}

function nascondiAltreScelte() {
    $("#cliente").hide();
}

function nuovaRiga() {
    var tabella = document.getElementById("tabella");
    var numerodiRighe = tabella.rows.length;
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



$(document).ready(function(){

    $('input[type=password]').keyup(function() {
        var pswd = $(this).val();

        //validate the length
        if ( pswd.length < 8 ) {
            $('#length').removeClass('valid').addClass('invalid');
        } else {
            $('#length').removeClass('invalid').addClass('valid');
        }

        //validate letter
        if ( pswd.match(/[A-z]/) ) {
            $('#letter').removeClass('invalid').addClass('valid');
        } else {
            $('#letter').removeClass('valid').addClass('invalid');
        }

        //validate capital letter
        if ( pswd.match(/[A-Z]/) ) {
            $('#capital').removeClass('invalid').addClass('valid');
        } else {
            $('#capital').removeClass('valid').addClass('invalid');
        }

        //validate number
        if ( pswd.match(/\d/) ) {
            $('#number').removeClass('invalid').addClass('valid');
        } else {
            $('#number').removeClass('valid').addClass('invalid');
        }

        //validate space
        if ( pswd.match(/[^a-zA-Z0-9\-\/]/) ) {
            $('#space').removeClass('invalid').addClass('valid');
        } else {
            $('#space').removeClass('valid').addClass('invalid');
        }

    }).focus(function() {
        $('#pswd_info').show();
    }).blur(function() {
        $('#pswd_info').hide();
    });

});