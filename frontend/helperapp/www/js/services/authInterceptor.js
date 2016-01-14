/**
 * Created by MiiKE on 14.01.2016.
 */
'use strict';
angular.module('helperapp.services')
  .factory('authInterceptor', ['$q','$location','$base64', function ($q,$location,$base64) {

    var authInterceptorServiceFactory = {};

    authInterceptorServiceFactory.request = function (config) {

      config.headers = config.headers || {};

      var loginData = {};
      loginData.username = window.localStorage['username'];
      loginData.password = window.localStorage['password'];
      if (loginData.username) {
        config.headers.Authorization = 'Basic ' + $base64.encode(loginData.username + ':' + loginData.password);
      }

      return config;
    };

    authInterceptorServiceFactory.responseError = function (rejection) {
      if (rejection.status === 401) {

          $location.path('./registration');

      }
      return $q.reject(rejection);
    };

    return authInterceptorServiceFactory;
  }]);
