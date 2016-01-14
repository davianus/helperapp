angular.module('helperapp.services')
.factory('Need', function ($resource, baseurl) {
  return $resource(baseurl + 'requests/:id', {id: '@id'});
});
