angular.module('helperapp.controllers')

.controller('RegistrationCtrl',function($scope, $ionicModal, $timeout, $state, $cordovaCamera, User, $base64, $http) {

  // Form data for the login modal
  $scope.loginData = {};
  $scope.loginForm = {loginData:{password:{}}};
  $scope.error = false;
  $scope.errorMsg = "";

  $scope.user = new User();


  // Create the login modal that we will use later
  $ionicModal.fromTemplateUrl('templates/login.html', {
    scope: $scope
  }).then(function(modal) {
    $scope.loginModal = modal;
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
    window.localStorage['username'] = loginData.username;
    window.localStorage['password'] = loginData.password;

    User.get({id: 'me'}).$promise.then(
        function(resp) {
            $scope.closeLogin();
            $state.go('app.needs.all');
          }, function(resp) {
            //error

            window.localStorage.removeItem('username');
            window.localStorage.removeItem('password');

            $scope.error = true;
            if(resp.data != null) {
              $scope.errorMessage = "Login failed: " + angular.fromJson(resp.data).message;
            } else {
              $scope.errorMessage = "Login failed: " + resp.data;
            }
          }
      );
  };

  $scope.doRegistration = function(user) {
    if(user.password != user.confirmPw)
    {
      $scope.error = true;
      $scope.errorMsg = "Passwords do not match!";
      return;
    }

    window.localStorage.removeItem('username');
    window.localStorage.removeItem('password');

    user.$save().then(
        function(response) {
            $scope.error = false;
            $scope.errorMsg = "";
            $scope.login();
        }, function (resp) {
          $scope.error = true;
          $scope.errorMsg = angular.fromJson(resp.data).message;
        }
    );
  };
});
