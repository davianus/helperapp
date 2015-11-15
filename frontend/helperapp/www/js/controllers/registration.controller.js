angular.module('starter.controllers')

.controller('RegistrationCtrl',function($scope, $ionicModal, $timeout, $state, User, Login) {

  // Form data for the login modal
  $scope.loginData = {};
  $scope.loginForm = {loginData:{password:{}}};
  $scope.error = false;
  $scope.errorMessage = 'Error';


  // Create the login modal that we will use later
  $ionicModal.fromTemplateUrl('templates/login.html', {
    scope: $scope
  }).then(function(modal) {
    $scope.modal = modal;
  });

  // Triggered in the login modal to close it
  $scope.closeLogin = function() {
    $scope.modal.hide();
  };

  // Open the login modal
  $scope.login = function() {
    $scope.modal.show();
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
        $scope.error = true;
        $scope.errorMessage = resp.data.message;
      });


    // Simulate a login delay. Remove this and replace with your login
    // code if using a login system
    $timeout(function() {

    }, 1000);
  };
  $scope.user = {};
  $scope.doRegistration = function(user) {
    // TODO: Check password if(user.password == user.confirmPw)
      User.post(user);


  };
});
