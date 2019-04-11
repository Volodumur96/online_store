let user_page_number = 0;

$('#user_bt').click(function () {
    $('#content').html('');
    $('#content').append(`
    
            <div>

            <table id="users">
                <!---->
            </table>

            <footer>
            
                        <button id="u_previous_page"><i class="material-icons" id="prev-ico">keyboard_arrow_left</i></button>
                        <output id="u_page_number"></output>
                        <button id="u_next_page"><i class="material-icons">keyboard_arrow_right</i></button>
                    
            </footer>
            </div>
    `);


    getAllUsers();
});



function getAllUsers() {
    $.ajax({
        url: mainUrl + "/user?page="+user_page_number+"&size=10",
        type: "GET",
        headers: {
            'Authorize': window.localStorage.getItem('token')
        },
        contentType: "application/json",
        success: function (dataResponse) {
            let tableOfUsers = $("#users");
            tableOfUsers.html('');
            tableOfUsers.append(`
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Login</th>
                    <th>Role</th>
                    <th></th>
                </tr>
                
            `);


            setUsersToTable(dataResponse.data);
            let user_button = $('#user_bt');
            paginationForUsers(user_button, dataResponse.totalPages);
            setActionOnDeleteButtonsUser();
        },
        error: function (error) {
            alert(error.message);
        }
    });
}

function setUsersToTable(users) {
    for (let i = 0; i < users.length; i++) {
        setUserToTable(users[i]);
    }
}



function setUserToTable(user) {
    let tableOfUsers = $("#users");

    tableOfUsers.append('<tr>' +
        '<td>' + user.id + '</td>' +
        '<td>' + user.name + '</td>' +
        '<td>' + user.login + '</td>' +
        '<td>' + user.role + '</td>' +
        '<td>' + '<button class="buttonDeleteUser buttonDelete" value="' + user.id + '">Delete</button></td>' +
        '</tr>');
}

function setActionOnDeleteButtonsUser() {
    $(".buttonDeleteUser").each(function (index) {
        $(this).click(function () {
            $.ajax({
                url: mainUrl + "/user/" + $(this).val(),
                type: "DELETE",
                headers: {
                    'Authorize': window.localStorage.getItem('token')
                },
                success: function () {
                    $('#user_bt').click();
                },
                error: function (error) {
                    alert(error.message);
                }
            });
        })
    })
}

function paginationForUsers(button, totalPages,) {

    let button_next = document.getElementById('u_next_page');
    let button_previous = document.getElementById('u_previous_page');

    $('#u_next_page').click(function () {
        user_page_number += 1;
        button.click();
    });

    $('#u_page_number').val(user_page_number+1 +"/"+totalPages);


    if (user_page_number===0) {
        button_previous.disabled=true;
    } else button_previous.disabled=false;

    if (totalPages===user_page_number+1) {
        button_next.disabled=true;
    } else button_next.disabled=false;

    $('#u_previous_page').click(function () {
        user_page_number -= 1;
        button.click();
    });
}