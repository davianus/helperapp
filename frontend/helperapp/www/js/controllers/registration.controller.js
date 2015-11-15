angular.module('starter.controllers')

.controller('RegistrationCtrl',function($scope, $ionicModal, $timeout, $state, $cordovaCamera, User, Login) {

  // Form data for the login modal
  $scope.loginData = {};

  // Create the login modal that we will use later
  $ionicModal.fromTemplateUrl('templates/login.html', {
    scope: $scope
  }).then(function(modal) {
    $scope.loginModal = modal;
  });

  $ionicModal.fromTemplateUrl('templates/image-upload.html', {
    scope: $scope
  }).then(function(modal) {
    $scope.uploadModal = modal;
  });

  // Triggered in the login modal to close it
  $scope.closeLogin = function() {
    $scope.loginModal.hide();
  };

  // Open the login modal
  $scope.login = function() {
    $scope.loginModal.show();
  };


  // Perform the login action when the user submits the login form
  $scope.doLogin = function(loginData) {
    //console.log('Doing login', $scope.loginData);
      Login.post(loginData,function(resp) {
        //Success
        $scope.closeLogin();
        $state.go('app.needs.byme')
      }, function(resp) {
        //error
        $scope.loginForm.loginData.password.$error = "Error";
      });


    // Simulate a login delay. Remove this and replace with your login
    // code if using a login system
    $timeout(function() {

    }, 1000);
  };

  $scope.closeUpload = function() {
    $scope.uploadModal.hide();
    $scope.photo = "";
  };

  $scope.upload = function() {
    $scope.uploadModal.show();
    initializeCamera();
  };

  $scope.doUpload = function() {
    $scope.uploadModal.hide();
  };

  $scope.changeImage = function() {
    initializeCamera();
  };

  var initializeCamera = function() {
    document.addEventListener("deviceready", function () {
      var options = {
        quality: 50,
        destinationType: Camera.DestinationType.DATA_URL,
        sourceType: Camera.PictureSourceType.CAMERA,
        allowEdit: true,
        encodingType: Camera.EncodingType.JPEG,
        targetWidth: 100,
        targetHeight: 100,
        popoverOptions: CameraPopoverOptions,
        saveToPhotoAlbum: false,
        correctOrientation:true
      };

      $cordovaCamera.getPicture(options).then(function(imageData) {
        $scope.photo = "data:image/jpeg;base64," + imageData;
      }, function(err) {
        // error
      });
    });
  };

  $scope.user = {};
  $scope.doRegistration = function(user) {
    // TODO: Check password if(user.password == user.confirmPw)
    User.post(user);
  };
});
