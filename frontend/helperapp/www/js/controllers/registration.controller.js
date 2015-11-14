angular.module('starter.controllers')

.controller('RegistrationCtrl',function($scope, $ionicModal, $timeout, $state) {

  // Form data for the login modal
  $scope.loginData = {};

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
  $scope.doLogin = function() {
    //console.log('Doing login', $scope.loginData);
    //TODO: check password
    $state.go('app.requests');
    // Simulate a login delay. Remove this and replace with your login
    // code if using a login system
    $timeout(function() {
      $scope.closeLogin();
      $state.go('app.requests');
    }, 1000);
  };

  $scope.doRegistration = function() {
    //TODO: registrate
    $timeout(function() {
      $scope.login();
    }, 1000);
  };

});
