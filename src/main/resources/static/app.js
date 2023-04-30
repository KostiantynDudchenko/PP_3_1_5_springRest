let editModalForm = document.forms['editModalForm']
let deleteModalForm = document.forms['deleteModalForm']
let newUserForm = document.forms['newUserForm']

async function getUser(id) {
    let response = await fetch('http://localhost:8080/api/admin/' + id)
    return await response.json()
}

async function fillModalForm(form, modal, id) {
    modal.show()
    let user = await getUser(id)
    form.id.value = user.id
    form.first_name.value = user.first_name
    form.last_name.value = user.last_name
    form.age.value = user.age
    form.email.value = user.email
    form.roles.value = user.roles
}


// Filling Users Table
function printUsersTable() {
    fetch('http://localhost:8080/api/admin')
        .then(res => {
                res.json()
                    .then(data => {
                            let usersTableInfo = "";
                            data.forEach((user) => {
                                usersTableInfo += `   
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.first_name}</td>
                        <td>${user.last_name}</td>
                        <td>${user.age}</td>
                        <td>${user.email}</td>
                        <td>${user.roles.map(el => el.name)}</td>
                        <td>
                                <button type="button" class="btn btn-info"
                                data-bs-toogle="modal"
                                data-bs-target="#editModal"
                                onclick="openEditModal(${user.id})"
                                >Edit</button>
                            </td>
                            <td>
                                <button type="button" class="btn btn-danger" 
                                data-toggle="modal"
                                data-bs-target="#deleteModal"
                                onclick="openDeleteModal(${user.id})">Delete</button>
                            </td>                 
                    </tr>`
                            })
                            document.querySelector("#allUsersTable").innerHTML = usersTableInfo;
                        }
                    )
            }
        )
}

printUsersTable()

//создание нового пользователя

addUser()

function addUser() {
    newUserForm.addEventListener("submit", event => {
        event.preventDefault();
        let newUserRoles = []
        for (let i = 0; i < newUserForm.roles.options.length; i++) {
            if (newUserForm.roles.options[i].selected) {
                newUserRoles.push({
                    id: newUserForm.roles.options[i].value,
                    authority: "ROLE_" + newUserForm.roles.options[i].text
                })
            }
        }
        fetch('http://localhost:8080/api/users', {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                first_name: newUserForm.newFirstName.value,
                last_name: newUserForm.newLastName.value,
                age: newUserForm.newAge.value,
                email: newUserForm.newEmail.value,
                password: newUserForm.newPassword.value,
                roles: newUserRoles,
            })
        })
            .then(() => {
                newUserForm.reset();
                document.getElementById("nav-home-tab").click()
                printUsersTable();
            })
    })
}

// Editing User
async function openEditModal(id) {
    const modal = new bootstrap.Modal(document.querySelector('#editModal'))
    await fillModalForm(editModalForm, modal, id)
}

editUser()

function editUser() {
    editModalForm.addEventListener('submit', event => {
        event.preventDefault()
        let editedUserRoles = []
        for (let i = 0; i < editModalForm.roles.options.length; i++) {
            if (editModalForm.roles.options[i].selected) {
                editedUserRoles.push({
                    id: editModalForm.roles.options[i].value,
                    authority: 'ROLE_' + editModalForm.roles.options[i].text
                })
            }
        }

        fetch('http://localhost:8080/api/admin', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: editModalForm.id.value,
                first_name: editModalForm.first_name.value,
                last_name: editModalForm.last_name.value,
                age: editModalForm.age.value,
                email: editModalForm.email.value,
                password: editModalForm.password.value,
                roles: editedUserRoles,
            })
        }).then(() => {
            editModalForm.reset();
            document.getElementById("closeEdit").click()
            printUsersTable();
        })
    })
}

// User Deletion
async function openDeleteModal(id) {
    const modal = new bootstrap.Modal(document.querySelector('#deleteModal'))
    await fillModalForm(deleteModalForm, modal, id)
}

function deleteUser() {
    deleteModalForm.addEventListener("submit", event => {
        event.preventDefault()
        fetch("http://localhost:8080/api/admin/" + deleteModalForm.id.value, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(() => {
            deleteModalForm.reset();
            document.getElementById("closeDelete").click()
            printUsersTable();
        })
    })
}

deleteUser()

// Get Authenticated Admin
function getAuthenticatedAdmin() {
    fetch("http://localhost:8080/api/user")
        .then(res => res.json())
        .then(data => {
            let email = `<span class="p-1" id="adminEmail">${data.email}</span>`
            document.querySelector("#adminEmail").innerHTML = email;
            let roles = data.roles.map(role => ' ' + role.authority.substring(5))
            document.querySelector("#adminRoles").innerHTML = roles;
            let user = `
                <tr>
                    <td>${data.id}</td>
                    <td>${data.first_name}</td>
                    <td>${data.last_name}</td>
                    <td>${data.age}</td>
                    <td>${data.email}</td>
                    <td>${data.roles.map(el => el.name)}</td>

                </tr>`
            document.querySelector("#adminPageInfo").innerHTML = user;
        })
}

getAuthenticatedAdmin()
