
var storageRef = firebase.storage().ref();

function uuidv4() {
    return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, c =>
        (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
    ) + ".png"
}

function send() {
    document.getElementById("sendFile").onclick = function(){
        var file = document.getElementById("image_input").files[0];
        getBase64(file).then(data => {
            writeFileToStorage(data);
        });
    };
}



function writeFileToStorage(file) {

    var uploadTask = storageRef.child('images/' + uuidv4()).putString(file, 'data_url');


    uploadTask.on(firebase.storage.TaskEvent.STATE_CHANGED,
        function(snapshot) {

            var progress = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
            console.log('Upload is ' + progress + '% done');
            switch (snapshot.state) {
                case firebase.storage.TaskState.PAUSED:
                    console.log('Upload is paused');
                    break;
                case firebase.storage.TaskState.RUNNING:
                    console.log('Upload is running');
                    break;
            }
        }, function(error) {
            console.log(error);
        }, function() {
            // Upload completed successfully, now we can get the download URL
            uploadTask.snapshot.ref.getDownloadURL().then(function(downloadURL) {
                let button_send = document.getElementById('sendFile');
                button_send.style.backgroundColor = "lime";
                $('#sendFile').val("DONE");
                console.log('File available at', downloadURL);
                window.localStorage.setItem("image_url", downloadURL);
            });
        });
}


function getBase64(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result);
        reader.onerror = error => reject(error);
    });
}