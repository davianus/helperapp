angular.module('helperapp.controllers')

.controller('RegistrationCtrl',function($scope, $ionicModal, $timeout, $state, $cordovaCamera, User, $base64, $http, Camera) {

  // Form data for the login modal
  $scope.loginData = {};
  $scope.loginForm = {loginData:{password:{}}};
  $scope.error = false;
  $scope.errorMsg = "";


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
    $scope.username = window.localStorage['username'];
    $scope.loginModal.show();
  };


  // Perform the login action when the user submits the login form
  $scope.doLogin = function(loginData) {
    //console.log('Doing login', $scope.loginData);
    //set HTTP Basic Auth Header
    $http.defaults.headers.common.Authorization = 'Basic ' +
      $base64.encode(loginData.username + ':' + loginData.password);
    window.localStorage['username'] = loginData.username;
    window.localStorage['password'] = loginData.password;
    User.get({id: 'me'}).$promise.then(
        function(resp) {
            //window.localStorage['user'] = loginData.username;
            //window.localStorage['password'] = loginData.password;


            $scope.closeLogin();
            $state.go('app.needs.all');
          }, function(resp) {
            //error
            $scope.error = true;
            if(resp.data != null) {
              $scope.errorMessage = "Login failed: " + angular.fromJson(resp.data).message;
            } else {
              $scope.errorMessage = "Login failed: " + resp.data;
            }
          }
      );

    // Simulate a login delay. Remove this and replace with your login
    // code if using a login system
    $timeout(function() {

    }, 1000);
  };

  $scope.user = new User();
  $scope.doRegistration = function(user) {
    if(user.password != user.confirmPw)
    {
      $scope.error = true;
      $scope.errorMsg = "Passwords do not match!";
      return;
    }

    window.localStorage['username'] = undefined;
    window.localStorage['password'] = undefined;

    user.$save().then(
        function(response) {
            $scope.error = false;
            $scope.errorMsg = "";
        //window.localStorage['username'] = user.username;
            $scope.login();
            $scope.user = new User();
        }, function (resp) {
          $scope.error = true;
          $scope.errorMsg = angular.fromJson(resp.data).message;
        }
    );
  };


});

