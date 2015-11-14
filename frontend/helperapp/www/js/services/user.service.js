angular.module('starter.controllers')
.factory('User', function ($resource, baseurl) {
  return $resource(baseurl + 'user/:id', {}, {
    'query': { method: 'GET', isArray: true},
    'get': {
      method: 'GET',
    },
    'update': { method:'PUT' },
    'post': { method: 'POST'}
  });
});
