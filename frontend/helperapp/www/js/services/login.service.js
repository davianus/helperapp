angular.module('starter.controllers')
  .factory('Login', function ($resource, baseurl) {
    return $resource(baseurl + 'user/token/', {}, {
      'query': { method: 'GET', isArray: true},
      'get': {
        method: 'GET',
      },
      'update': { method:'PUT' },
      'post': { method: 'POST'},
      'login': { method: 'POST'}
    });
  });
