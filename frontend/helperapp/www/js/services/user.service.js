angular.module('helperapp.services')
.factory('User', function ($resource, baseurl) {
  return $resource(baseurl + 'user', {
    'login': { method: 'POST' }
  });
});
