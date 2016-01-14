/**
 * Created by MiiKE on 14.01.2016.
 */
'use strict';
angular.module('helperapp.services')
  .factory('authInterceptor', ['$q','$injector','$base64', function ($q,$injector,$base64) {

    var authInterceptorServiceFactory = {};

    authInterceptorServiceFactory.request = function (config) {

      config.headers = config.headers || {};

      var loginData = {};
      loginData.username = window.localStorage['username'];
      loginData.password = window.localStorage['password'];
      
      if (loginData.username && loginData.password) {
        config.headers.Authorization = 'Basic ' + $base64.encode(loginData.username + ':' + loginData.password);
      } else {
        config.headers.Authorization = undefined;
      }

      return config;
    };

    authInterceptorServiceFactory.responseError = function (rejection) {
      if (rejection.status === 401) {

          var $state = $injector.get('$state');
          $state.go('registration', {}, {reload: true});

      }
      return $q.reject(rejection);
    };

    return authInterceptorServiceFactory;
  }]);
