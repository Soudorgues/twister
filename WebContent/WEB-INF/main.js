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
    return "<div id=\"message_" + this.id + "\" \
    class=\"message\"> \
    <div class=\"text_message\"> \
    " + this.text + "<div class=\"infos_message\"> \
    <span> Post de " + this.auteur + "\
    </span></div></div></div>";
}


function revival1(key, value) {
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
    var m2 = new Message(13,"doums","neymar",new Date(),undefined);
    var tab = [m1, m2];
    var s = "";

    for (var i = 0; i < tab.length; i++) {
        s = s + tab[i].getHTML();
    		console.log(tab[i]);
        //$("#main").append(tab[i]);
    }
    document.getElementById("listMessage").innerHTML = s;
}

function makeMainPanel(fromId, fromLogin, query) {
    env.messages = [];
    if (fromID == undefined) {
        fromId = 1;
    }
    env.fromID = fromID;
    env.fromLogin = fromLogin;
    console.log(env.fromLogin);
    env.query = query;
    var s = "<header id=\"top\">";
    if (env.fromID < 0) {
        s += "<div id=\"title\"> Actualités</div>";
    }
    else {
        if (!env.follows.has(env.fromID)) {
            s += "<div id=\"title\"> Page de " + fromLogin + "<div \
            class=\"add\"><img src=\"Images/add.png\" title=\"suivre\" \
            onclick= javascript:follow()\"></div></div>";
        }
        else {
            s += "<div id=\"title\"> Page de " + fromLogin + "<div \
            class=\"add\"><img src=\"Images/remove.png\" \
            onclick= javascript:stopfollow()\"></div></div>";
        }
    }
    s += "</div><div id=\"connect\"><span id=\"log\" \
    onclick=\"javascript.pageUser(" + env.id + "," + env.login + ")\"> \
    <img src=\"Images/logout.png\" \
            onclick= javascript:logout()\"></div></div>";
    return s;
}

function completeMessages() {
    if (!noConnection) {

    } else {
        var tab = getFromLocalDB(env.fromId, -1, env.minId, 1);
        completeMessagesResponse(JSON.stringify(tab));
    }
}

function completeMessagesResponse(rep) {
    var tab = JSON.parse(rep, revival1);
    for (var i = 0; i < tab.length; i++) {
        var m = tab[i];
        alert(m.getHTML());
        env.messages[m.id] = m;
        if (m.id > env.maxId) {
            env.maxId = m.id;
        }
        if (env.minId < 0 || m.id < env.minId) {
            env.minId = m.id;
        }
    }
}

function getFromLocalDB(from, minId, maxId, nbMax) {
    var tab = [];
    var nb = 0;
    var f = new Set();
    if (from > 0) {
        f = follows[from];
    }
    for (var i = localdb.length - 1; i >= 0; i--) {
        messages += i;
    }
    return tab
}