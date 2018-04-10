function Message(id, login, text, date, comments) {
    this.id = id;
    this.auteur = login;
    this.text = text;
    this.date = date;
    if (comments == undefined) {
        comments = [];
    }
    this.comments = comments;
}

Message.prototype.getHTML = function() {
    return "<div id=\"message_" + this.id + "\" \
    class=\"message\"> \
    <div class=\"text_message\"> \
    " + this.text + "<div class=\"infos_message\"> \
    <span> Post de " + this.auteur + "\
    </span></div></div></div>";
}

function Commentaire(id, auteur, text, date) {
    this.id = id;
    this.auteur = login;
    this.text = text;
    this.date = date;
}

Commentaire.prototype.getHTML = function() {
    return "<div id=\"commentaire_" + this.id + "\" \
    class=\"commentaire\"> \
    <div class=\"text_commentaire\"> \
    " + this.text + "<div class=\"infos_message\"> \
    <span> Post de " + this.auteur + "\
    </span></div></div></div>";
}


function revivall(key, value) {
    if (value.comment != undefined) {
        var c = new Message(value.id, value.auteur, value.text, value.date, value.comments);
        return c;
    }
    else if (value.text != undefined) {
        var c = new Commentaire(value.id, value.auteur, value.text, value.date);
        return c;
    }
    else if (key == date) {
        var d = new Date(value);
        return d;
    }
    return value;
}

function SetVirtualDB() {
    localdb = [];
    follows = [];
    var u1 = {"id": 1, "login": "sly"};
    var u2 = {"id": 2, "login": "joe"};
    var u3 = {"id": 3, "login": "luc"};
}

function init() {
    noConnection = true;
    env = new Object();
    SetVirtualDB();
    var m1 = new Message(12, "vinc", "Bonjour", new Date(), undefined);
    var m2 = new Message(12, "Amadou", "hello", new Date(), undefined);    
    var m3 = new Message(12, "prof",  "recu",   new Date(), undefined);
    var m4 = new Message(12, "prof",  "recu",   new Date(), undefined);
    var tab = [m1, m2, m3, m4];
    var s=" ";
    for (var i = 0; i < tab.length; i++) {
        s = tab[i].getHTML();
        $("#listMessage").append(s);
    }
    //document.getElementById("listMessage").innerHTML = s;
}

function test() {
    alert("teskj"); 
}
init();