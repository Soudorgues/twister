function connexion(form) {
    var login = form.login.value;
    var password = form.password.value;
    var ok = verif_formulaire_connexion(login, password);
    if (ok) {
        connect(login, password);
    }
}

function verif_formulaire_connexion(login, password) {
    if (login.length == 0 || password.length == 0) {
        error_login("Pb connexion");
        return false;
    }
    if (login.length > 20) {
        error_login("login > 20");
        return false;
    }
    return true;
}

function connexionajax(form) {
    $.ajax({
        "type": "POST",
        "url" : "login",
        "data": "login="+form.login.value+ "&pwd=" + form.mdp.value,
        "success": function (rep) { console.log("Success"); },
        "error": function () { alert("Error"); }
 
    });
}

function error_login(msg) {
    var msg_box = "<div id=\"msg_error_connexion\">" + msg + "</div>";
    var old_msg = $("#msg_error_connexion");
    if (old_msg.length == 0) {
        $("#form").prepend(msg_box);
    }
    else {
        old_msg.replaceWith(msg_box);
    }
    $("#msg_error_connexion").css({"color": "red"});
}
/*
    makeConnexionPanel()
    $("body").html(s)    remplace
    $("body").append(s)  ajoute à la fin
    $("body").prepend(s) ajoute au début

function makeConnexionPanel() {
    var s = "<div id=\"connexion_main\"> ... <div id=\"link_enregistrement\" \
        onClick=\"javascript:makeEnregistrementPanel()\">...</div>";
    $("body").html(s);
}
*/

function _makeConnexionPanel() {
	//$("body").load("login.html");
	$("body").load("session.html");
}

function _makeMainPanel() {
    $("body").load("principale.html");
}

/*
function makeEnregistrementPanel() {
    var s = "<div></div>";
    $("body").html(s);
}
*/

function _makeEnregistrementPanel() {
	$("body").load("enregistrement.html");
}

function connect(login, password) {
    console.log("connexion" + login + " " + password);
    var idUser = 78;
    var key = "ABCDEFGH";
    if (!noConnexion) {
        return false;
    }
    else {
        reponseConnexion({"key": key, "id": idUser, "login": login,
            "follow": [2]});
    }
}

function responseConnexion(rep) {
    if (rep.erreur = undefined) {
        env.key = rep.key;
        env.id = rep.id;
        env.login = rep.login;
        env.follows = new Set();
    }
    for (var i = 0; i < rep.follows.length; i++) {
        env.follows.add(rep.follows[i]);
    }
    if (noConnexion) {
        follows[rep.id] = new Set();
        for (var i = 0; i < rep.follows.length; i++) {
            follows[rep.id].add(rep.follows[i]);
        }
    }
    _makeMainPanel(); // td 07
    error_login(rep.erreur);
}

/*
    enregistrement
    responseConnexion (makeMainPanel)
*/

function developpeMessage(id) {
    var m = env.msg[id];
    var elmt = $("#message_"+id+".comments");
    for (var i = 0; i < m.length; i++) {
        var c = m.comments[i];
        elmt.append(c.getHTML());
    }
    elmt = $("#message_"+id+".new_comments");
    elmt.append("<form name=\"new_comment_form\" id=\"new_comment_form\" \
        action=\"javascript:func_new_comment("+id+")\">");
    $("#message_"+id+"img").replaceWith("<img src=\"...\" onClick=\" \
        javascript:plieMessage("+id+")\"/>");
}

function plieMessage(id) {
    var m = env.msg[id];
    var elmt = $("#message_"+id+".comments");
    elmt.html(" ");
    $("#message_"+id+".img").replaceWith("<img src=\"...\" onClick=\" \
        javascript:developpeMessage("+id+")\"/>");
}

function newComment(id) {
    var text = $("#new_"+id).val();
    if (!noConnexion) {
        return;
    }
    else {
        newComment_reponse(id, JSON.Stringify(
        new Commentaire(env.msg[id].comments.length+1,{"id":env.idlogin})))
    }
}

function newComment_response(id, rep) {
    var com = JSON.parse(rep, revival);
    if (com != undefined && com.erreur == undefined) {
        var elmt = $("#message_"+id+".comments");
        elmt.append(com.getHTML());
        env.msg[i].comments.push(com);
        if (noConnexion) {
            localdb[id] = env.msg[id];
        }
    }
    else {
        alert(comm.error);
    }
}