angular.module('helperapp.services')
.factory('User', function ($resource, baseurl) {
  return $resource(baseurl + 'user/:id', {id: '@id'}, {
    'login': { method: 'POST' }
  });
});
