
var firebaseConfig = {
    apiKey: "AIzaSyCv1VVX3642zCL-bzUJi8jdVgHSoc3zloU",
    authDomain: "learnfirebase-3a35a.firebaseapp.com",
    databaseURL: "https://learnfirebase-3a35a.firebaseio.com",
    projectId: "learnfirebase-3a35a",
    storageBucket: "learnfirebase-3a35a.appspot.com",
    messagingSenderId: "7905472273",
    appId: "1:7905472273:web:50fb32490547111d2da6b0",
    measurementId: "G-YDN7PX1DXS"
  };
  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);

    //if you close the browser the
    firebase.auth.Auth.Persistence.LOCAL

    $('#btn-login').click(function(){
        var email=$('#email').val();
        var password=$('#password').val();
        firebase.auth().signInWithEmailAndPassword(email, password).catch(function(error) {
            // Handle Errors here.
            var errorCode = error.code;
            var errorMessage = error.message;
            // ...

            console.log(errorCode);
            console.log(errorMessage);
        });
         

    });

    $("#btn-logout").click(function(){
        firebase.auth().signOut();
    });