// Get Authenticated User
function getAuthenticatedUser() {
    fetch("http://localhost:8080/api/user")
        .then(res => res.json())
        .then(data => {
            let email = `<span class="p-1" id="userEmail">${data.email}</span>`
            document.querySelector("#userEmail").innerHTML = email;
            let roles = data.roles.map(role => ' ' + role.authority.substring(5))
            document.querySelector("#userRoles").innerHTML = roles;
            let user = `
                <tr>
                    <td>${data.id}</td>
                    <td>${data.first_name}</td>
                    <td>${data.last_name}</td>
                    <td>${data.age}</td>
                    <td>${data.email}</td>
                    <td>${data.roles.map(el => el.name)}</td>

                </tr>`
            document.querySelector("#userPageInfo").innerHTML = user;
        })
}

getAuthenticatedUser()