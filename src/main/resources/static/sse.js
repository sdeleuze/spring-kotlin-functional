var eventSource = new EventSource("/api/users");
eventSource.onmessage = function(e) {
    var li = document.createElement("li");
    var user = JSON.parse(e.data);
    li.innerText = "User: " + user.firstName + " " + user.lastName + " - " + user.birthDate;
    document.getElementById("users").appendChild(li);
}