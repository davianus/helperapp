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
      // TODO Fix check for undefined
      if (loginData.username != 'undefined' && loginData.password != 'undefined') {
        config.headers.Authorization = 'Basic ' + $base64.encode(loginData.username + ':' + loginData.password);
      } else {
        config.headers.Authorization = undefined;
      }

      return config;
    };

    authInterceptorServiceFactory.responseError = function (rejection) {
      if (rejection.status === 401) {

          $location.path('#/registration');

      }
      return $q.reject(rejection);
    };

    return authInterceptorServiceFactory;
  }]);
